package com.vgrazi.sample;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;

/**
 * Created by vgrazi on 2/13/16.
 */
public class SampleRunner {
//  VirtualMachineManager vmManager = Bootstrap.virtualMachineManager();
  private ExecutorService service = Executors.newSingleThreadExecutor();
  private volatile boolean running = true;

  public static void main(String[] args) throws IOException, IllegalConnectorArgumentsException {
    new SampleRunner().launch();
  }

  private void launch() throws IOException, IllegalConnectorArgumentsException {
//    List<Connector> connectors = vmManager.allConnectors();
//    for (int i = 0; i < connectors.size(); i++) {
//      Connector connector = connectors.get(i);
//      System.out.println(i + " " + connector);
//    }

//    Connector connector = vmManager.defaultConnector();
//    System.out.println("Connector:" + connector);
//    System.out.println("Default args:" + connector.defaultArguments());
//    System.out.println("Description:" + connector.description());
//    System.out.println("Name:" + connector.name());
//    System.out.println("Transport:" + connector.transport());



    service.submit(new Runnable() {
                     @Override
                     public void run() {
                       for (int i = 0; running; i++)
                         SampleRunner.this.invoke(i);
                     }
                   }
    );
  }

  private void invoke(int index) {
    String test = "Test";
    try {
      System.out.println(System.currentTimeMillis());
      Thread.sleep(1000);
      System.out.println("Woke " + index);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
