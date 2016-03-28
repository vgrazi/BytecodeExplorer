package com.vgrazi.bytecodeexplorer.debugger;

import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequestManager;
import org.jdiscript.util.StreamRedirectThread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by vgrazi on 2/13/16.
 */
public class Debugger {

  public ExecutorService executor = Executors.newCachedThreadPool();
  private volatile boolean running = true;
  private VirtualMachine vm;

  public static void main(String[] args) throws IOException, IllegalConnectorArgumentsException, IncompatibleThreadStateException, AbsentInformationException, VMStartException, InterruptedException {
    new Debugger().launch();
  }


  public <E extends Enum<E>> void launch() throws IOException, IllegalConnectorArgumentsException, IncompatibleThreadStateException, AbsentInformationException, VMStartException, InterruptedException {
    VirtualMachineManager vmm = Bootstrap.virtualMachineManager();
    LaunchingConnector connector = vmm.defaultConnector();
    Map<String, ? extends Connector.Argument> map = connector.defaultArguments();
    map.get("main").setValue("com.vgrazi.sample.SampleClass");
    map.get("options").setValue("-cp " +
      "/Users/vgrazi/IdeaProjects/java-debugging/target/classes:" +
      "/Library/Java/JavaVirtualMachines/jdk1.8.0_65.jdk/Contents/Home/lib/tools.jar:" +
      "");

    vm = connector.launch(map);
    addShutdownHook();

    EventRequestManager reqMan = vm.eventRequestManager();
    ClassPrepareRequest r = reqMan.createClassPrepareRequest();
    r.addClassFilter("com.vgrazi.sample.SampleClass");
    r.enable();

    vm.resume();
    launchEventListenerThread(vm);

  }

  private Thread redirect(String name, InputStream in, OutputStream out) {
    Thread t = new StreamRedirectThread(name, in, out);
    t.setDaemon(true);
    t.start();
    return t;
  }

  private void classLoaded() throws IncompatibleThreadStateException, AbsentInformationException {
    System.out.println("Ready...");
    // Find thread by name "main"
    ThreadReference mainThread = null;
    int j = 0;
    List<ThreadReference> threads = vm.allThreads();
    for (ThreadReference threadReference : threads) {
      String mainSpecifier = "  ";
      if (threadReference.name().equals("main")) {
        mainThread = threadReference;
        mainSpecifier = "* ";
      }
      System.out.println("Thread " + j++ + ":" + mainSpecifier + threadReference);
    }
    System.out.println();

//    // Suspend it
//    mainThread.suspend();
//
//    // Look what's in its stack trace
//    List<StackFrame> frames = mainThread.frames();
//    for (int i = 0; i < frames.size(); i++) {
//      System.out.println("Frame " + i + ": " + frames.get(i));
//    }
//    System.out.println();
//
    Location locationForBreakpoint = getLocation("com.vgrazi.sample.SampleClass", 70);
//
//    setBreakpoint(locationForBreakpoint);
    mainThread.resume();

//    StepRequest request = evReqMan.createStepRequest(mainThread,
//      StepRequest.STEP_LINE,
//      StepRequest.STEP_OVER);
//    request.addCountFilter(1);  // next step only
//    request.enable();
//      mainThread.resume();
//
//     request = evReqMan.createStepRequest(mainThread,
//      StepRequest.STEP_LINE,
//      StepRequest.STEP_OVER);
//    request.addCountFilter(1);  // next step only
//    request.enable();
    mainThread.resume();
  }

  private void launchEventListenerThread(VirtualMachine vm) {
    executor.execute(() -> {
        try {
          int i = 0;
          while (true) {
            System.out.println(i++ + "th iteration");
            EventQueue eventQueue = vm.eventQueue();
            EventSet eventSet = eventQueue.remove();
            EventIterator eventIterator = eventSet.eventIterator();
            if (eventIterator.hasNext()) {
              Event event = eventIterator.next();
              System.out.println("Event:" + event);
              if (event instanceof ClassPrepareEvent) {
                System.out.println("Class loaded. Launching processor");
                vm.resume();
                classLoaded();
              }
            }
          }
        } catch (InterruptedException | AbsentInformationException | IncompatibleThreadStateException e) {
          e.printStackTrace();
        }
        System.out.println("EvenListener exiting");
      }
    );
  }

  private Location getLocation(String className, int lineNumber) throws AbsentInformationException {
    EventRequestManager evReqMan = vm.eventRequestManager();
    ClassPrepareRequest classPrepareRequest = evReqMan.createClassPrepareRequest();
    classPrepareRequest.addClassFilter(className);
    classPrepareRequest.enable();
    List<ReferenceType> classes = vm.classesByName(className);
    ReferenceType referenceType = classes.get(0);
    List<Location> locations = referenceType.locationsOfLine(lineNumber);
    return locations.get(0);
  }

  private void setBreakpoint(Location locationForBreakpoint) {
    EventRequestManager evReqMan = vm.eventRequestManager();
    BreakpointRequest bpReq = evReqMan.createBreakpointRequest(locationForBreakpoint);
    bpReq.enable();
  }

  private void displayVariables(StackFrame stackFrame) throws AbsentInformationException {
    List<LocalVariable> variables = stackFrame.visibleVariables();
    for (int k = 0; k < variables.size(); k++) {
      if (k == 0) {
        System.out.println(stackFrame);
      }
      LocalVariable var = variables.get(k);
      boolean visible = var.isVisible(stackFrame);

      Value value;
      if (visible) {
        value = stackFrame.getValue(var);
      } else {
        value = null;
      }
      System.out.println("  var:" + var.typeName() + " " + var.name() + " = " + value);
    }
  }


  private void displayFields(ObjectReference objRef) {
    ReferenceType refType = objRef.referenceType();
    List<Field> objFields = refType.allFields();
    System.out.println("Instance and class variables " + refType);
    for (int m = 0; m < objFields.size(); m++) {
      try {
        Field nextField = objFields.get(m);
        Type type = nextField.type();
        Value value = objRef.getValue(nextField);
        if (nextField.isTransient()) {
          System.out.print("transient ");
        }
        if (nextField.isVolatile()) {
          System.out.print("volatile ");
        }
        if (nextField.isEnumConstant()) {
          System.out.print("Enum ");
        }
        System.out.println(type + " " + nextField + " " + value);
      } catch (ClassNotLoadedException e) {

      }
    }
  }

  private void addShutdownHook() {
    Thread outThread = redirect("Subproc stdout", vm.process().getInputStream(), System.out);
    Thread errThread = redirect("Subproc stderr", vm.process().getErrorStream(), System.err);

    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        outThread.interrupt();
        errThread.interrupt();
        vm.process().destroy();
      }
    });
  }
}
