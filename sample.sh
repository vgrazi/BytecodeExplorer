#!/usr/bin/env bash
mvn clean install exec:java -Dexec.mainClass=com.vgrazi.bytecodeexplorer.BytecodeExplorer -Dexec.args="target/classes/com/vgrazi/SampleClass.class"