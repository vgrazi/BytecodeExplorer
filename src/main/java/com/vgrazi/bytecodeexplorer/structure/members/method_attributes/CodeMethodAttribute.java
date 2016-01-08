package com.vgrazi.bytecodeexplorer.structure.members.method_attributes;
//Code_attribute {
//    u2 attribute_name_index;
//    u4 attribute_length;
//    u2 max_stack;
//    u2 max_locals;
//    u4 code_length;
//    u1 code[code_length];
//    u2 exception_table_length;
//    {   u2 start_pc;
//        u2 end_pc;
//        u2 handler_pc;
//        u2 catch_type;
//    } exception_table[exception_table_length];
//    u2 attributes_count;
//    attribute_info attributes[attributes_count];
//}

import com.vgrazi.bytecodeexplorer.utils.BytecodeInstructionDecompiler;
import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 1/3/16
 */
public class CodeMethodAttribute extends MethodAttribute {

    @Override
    public String getIdentifier() {
        return "Code";
    }

    @Override
    public String toString() {
//        StringBuilder sb = new StringBuilder("<table border=1>");
        StringBuilder sb = new StringBuilder("<table>");

        getFormattedNameAndLength(sb);
        sb.append("<tr><td>" + Utils.formatAsFourByteHexString(getStartByteIndex() + 6) + "</td><td colspan=2>Max stack:" +         "</td><td>" + Utils.getTwoBytes(getBytes(), getStartByteIndex() + 6)).append("</td></tr>");
        sb.append("<tr><td>" + Utils.formatAsFourByteHexString(getStartByteIndex() + 8) + "</td><td colspan=2>Max locals:" +        "</td><td>" + Utils.getTwoBytes(getBytes(), getStartByteIndex() + 8)).append("</td></tr>");
        int codeLength = Utils.getFourBytes(getBytes(), getStartByteIndex() + 10);
        sb.append("<tr><td>" + Utils.formatAsFourByteHexString(getStartByteIndex() + 10) + "</td><td colspan=2>Code length" +        "</td><td>" + codeLength).append("</td></tr>");
        sb.append(BytecodeInstructionDecompiler.decompile(getStartByteIndex()+14, getBytes(), codeLength));
        sb.append("</table>");

        return sb.toString();
    }

}
