package com.vgrazi.bytecodeexplorer;

import com.vgrazi.bytecodeexplorer.structure.ClassFile;
import com.vgrazi.bytecodeexplorer.ui.swing.BytecodeRenderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Given a class file, performs the analysis of the class, producing a fully populated ClassFile instance
 */
public class BytecodeExplorer {
    private ClassFile classFile;

    public static void main(String[] args) throws IOException {
        new BytecodeExplorer(args[0]);
    }

    public BytecodeExplorer(String filename) throws IOException {
        launch(filename);
    }

    private void launch(String file) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(file));

        classFile = new ClassFile(bytes);
        new BytecodeRenderer(classFile);
    }

    public ClassFile getClassFile() {
        return classFile;
    }
}
