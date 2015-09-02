# BytecodeExplorer
This is an AngularJS application that allows the user to upload a .class file (does not go to the server), and opens the file in a 
Hex editor on the left. As you mouse over the hex, the decompile pane on the right displays the translation of the bytecode. 

For example, mousing over the constant pool displays the translation of each constant. Mousing over any method displays the disassembled
method instruction, such as would be seen in javap

This is intended to help users understand the organization of bytecode
