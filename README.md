# BytecodeExplorer
## Current state
This application consist of two interfaces; Java and AngularJS, that allow the user to upload a .class file (does not go to the server), and opens the file in a 
Hex editor on the left. As you mouse over the hex, the decompile pane on the right displays the translation of the bytecode.

For example, mousing over the constant pool displays the translation of each constant. Mousing over any method displays the disassembled
method instruction, such as would be seen in javap

This is intended to help users understand the organization of bytecode.

## Goals
Eventually I would like to finish translating this to Java, with the purpose of creating an agent to execute the desired code in the JVM and
monitor the stack and program registers. 

## Usage
This easiest way to get this going is to load the project in IntelliJ (Community edition will do for execution, but Ultimate Edition or 
WebStorm recommended for coding). Open the file bytecode-explorer.html in IntelliJ
and mouse over the top right of the editor screen. A list of browser icons will appear. Choose Chrome (I have not tested it in other browsers yet)

The URL http://localhost:63342/Bytecode-Explorer/src/main/html/bytecode-explorer.html should open.

Select a class file (start with something simple, for example com.vgrazi.SampleClass.class) in the "Choose files" selector.

When the class file opens in the hex editor in the left browser pane, mouse over the hex portion to see the disassembled code in the right frame of the browser page.

&copy; Victor Grazi 2015