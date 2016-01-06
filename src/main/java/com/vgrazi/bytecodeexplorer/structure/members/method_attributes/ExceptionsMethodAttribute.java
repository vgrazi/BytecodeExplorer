package com.vgrazi.bytecodeexplorer.structure.members.method_attributes;

import com.vgrazi.bytecodeexplorer.structure.ConstantPoolSection;
import com.vgrazi.bytecodeexplorer.structure.constantTypes.ConstantClass;
import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * Created by vgrazi on 1/3/16.
 */
//Exceptions_attribute {
//    u2 attribute_name_index;
//    u4 attribute_length;
//    u2 number_of_exceptions;
//    u2 exception_index_table[number_of_exceptions];
//    }
public class ExceptionsMethodAttribute extends MethodAttribute {

    private int numberOfExceptions;

    @Override
    public String getIdentifier() {
        return "Exceptions";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("<table>");

        getFormattedNameAndLength(sb);
        numberOfExceptions = Utils.getTwoBytes(getBytes(), getStartByteIndex() + 6);
        sb.append("<tr><td>" + Utils.formatAsFourByteHexString(getStartByteIndex() + 6) + "</td><td># exceptions:" + "</td><td>" + numberOfExceptions).append("</td></tr>");
        for(int i = 0; i < numberOfExceptions; i++) {
            int index = getStartByteIndex() + 8 + 2 * i;
            int exceptionRef = Utils.getTwoBytes(getBytes(), index);
            ConstantClass value = (ConstantClass) ConstantPoolSection.getConstants().get(exceptionRef -1);
            sb.append("<tr><td>" + Utils.formatAsFourByteHexString(index) + " Exception: </td><td colspan='2'>#" + exceptionRef + " " + value.getConstantValue()).append("</td></tr>");
        }
        sb.append("</table>");

        return sb.toString();
    }

//    @Override
//    public int length() {
//        return 8 + 2 * numberOfExceptions;
//    }

    public int getAttributeLength() {
        return numberOfExceptions * 2 + 8;
    }


}
