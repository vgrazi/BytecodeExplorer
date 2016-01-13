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

import com.vgrazi.bytecodeexplorer.ui.swing.PointSelector;
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

        int codeLength = Utils.getFourBytes(getBytes(), getStartByteIndex() + 10);
        getFormattedNameAndLength(sb);
        String style1 = PointSelector.getStyleForByte(getStartByteIndex() + 6, 2);
        String style2 = PointSelector.getStyleForByte(getStartByteIndex() + 8, 2);
        String style3 = PointSelector.getStyleForByte(getStartByteIndex() + 10, 4);
        sb.append("<tr><td>" + Utils.formatAsFourByteHexString(getStartByteIndex() + 6) + "</td><td colspan=2><span style='" + style1 + "'>Max stack:"   +        "</span></td><td><span style='" + style1 + "'>" + Utils.getTwoBytes(getBytes(), getStartByteIndex() + 6)).append("</span></td></tr>");
        sb.append("<tr><td>" + Utils.formatAsFourByteHexString(getStartByteIndex() + 8) + "</td><td colspan=2><span style='" + style2 + "'>Max locals:"  +        "</span></td><td><span style='" + style2 + "'>" + Utils.getTwoBytes(getBytes(), getStartByteIndex() + 8)).append("</span></td></tr>");
        sb.append("<tr><td>" + Utils.formatAsFourByteHexString(getStartByteIndex() + 10) +"</td><td colspan=2><span style='" + style3 + "'>Code length"  +        "</span></td><td><span style='" + style3 + "'>" + codeLength).append("</span></td></tr>");
        sb.append(BytecodeInstructionDecompiler.decompile(getStartByteIndex()+14, getBytes(), codeLength));
        sb.append("</table>");

        return sb.toString();
    }

}
