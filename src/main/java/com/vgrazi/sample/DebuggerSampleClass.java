package com.vgrazi.sample;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by vgrazi on 2/13/16.
 */
public class DebuggerSampleClass {
  VirtualMachineManager vmManager = Bootstrap.virtualMachineManager();
  private ExecutorService service = Executors.newSingleThreadExecutor();
  private volatile boolean running = true;
  private final JLabel label = new JLabel("Success!");

  public static void main(String[] args) throws IOException, IllegalConnectorArgumentsException {
    new DebuggerSampleClass().launch();
  }

  private void launch() throws IOException, IllegalConnectorArgumentsException {
    JFrame frame = new JFrame("Testing");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setBounds(100, 100, 500, 500);
    JPanel panel = new JPanel();
    panel.setBackground(Color.black);
    label.setForeground(Color.white);
    panel.add(label);
    frame.getContentPane().add("Center", panel);



    frame.setVisible(true);

    Connector connector = vmManager.defaultConnector();
    System.out.println("Connector:" + connector);
    System.out.println("Default args:" + connector.defaultArguments());
    System.out.println("Description:" + connector.description());
    System.out.println("Name:" + connector.name());
    System.out.println("Transport:" + connector.transport());



      service.submit(() ->
      {
        for (int i = 0; running; i++)
          invoke(i);
      }
    );
  }

  private void invoke(int index) {
    String test = "Test";
    try {
      System.out.println(System.currentTimeMillis());
      Thread.sleep(1000);
//      System.out.println("Woke " + index);
      label.setText("Woke " + index);

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
