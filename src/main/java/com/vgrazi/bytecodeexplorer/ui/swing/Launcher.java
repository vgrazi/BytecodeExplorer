package com.vgrazi.bytecodeexplorer.ui.swing;

/**
 * Created by vgrazi on 11/8/15.
 */
public class Launcher {
    private static final BytecodeRenderer bytecodeRenderer = new BytecodeRenderer(null);
    public static void main(String[] args) {
        bytecodeRenderer.launch();
        startTestThread();
    }

    public static void startTestThread() {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                bytecodeRenderer.rerender(i);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        ).start();
    }

}
