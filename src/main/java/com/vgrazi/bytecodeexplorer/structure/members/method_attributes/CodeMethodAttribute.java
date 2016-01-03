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
        StringBuilder sb = new StringBuilder();

        sb.append("Name index:" + getAttributeNameIndex()).append("<br/>");
        sb.append("Name:" + getAttributeName()).append("<br/>");
        sb.append("Attributes length:" + Utils.getFourBytes(getBytes(), getStartByteIndex() + 2)).append("<br/>");
        sb.append("Max stack:" + Utils.getTwoBytes(getBytes(), getStartByteIndex() + 6)).append("<br/>");
        sb.append("Max locals:" + Utils.getTwoBytes(getBytes(), getStartByteIndex() + 8)).append("<br/>");
        sb.append("Code length" + Utils.getFourBytes(getBytes(), getStartByteIndex() + 10)).append("<br/>");


        return sb.toString();
    }
}
