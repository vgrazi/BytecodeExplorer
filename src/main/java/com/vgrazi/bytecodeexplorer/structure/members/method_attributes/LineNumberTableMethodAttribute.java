package com.vgrazi.bytecodeexplorer.structure.members.method_attributes;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;
import com.vgrazi.bytecodeexplorer.ui.swing.PointSelector;
import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * <pre>
 *   LineNumberTable_attribute {
 *      u2 attribute_name_index;
 *      u4 attribute_length;
 *      u2 line_number_table_length;
 *      {   u2 start_pc;
 *          u2 line_number;
 *      } line_number_table[line_number_table_length];
 *   }
 * </pre>
 */
public class LineNumberTableMethodAttribute extends MethodAttribute {


  @Override
  public String getIdentifier() {
    return "LineNumberTable";
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();


    getFormattedNameAndLength(sb);

    int lineTableLength = Utils.getTwoBytes(getBytes(), getStartByteIndex() + 6);
    String style = PointSelector.getStyleForByte(getStartByteIndex() + 6, 2);
    sb.append("<td>" + Utils.formatAsTwoByteHexString(getStartByteIndex() + 6) + "<td><span style='" + style + "'>Line# table length<td><span style='" + style + "'>" + lineTableLength + "</span>");
    for (int i = 0; i < lineTableLength; i++) {

      // the start pc
      int lineNumberIndex = getStartByteIndex() + 8 + i * 4;

      style = PointSelector.getStyleForByte(lineNumberIndex, 2);
      int startPc = Utils.getTwoBytes(getBytes(), lineNumberIndex);
      sb.append("</td></tr><tr><td>" + Utils.formatAsTwoByteHexString(lineNumberIndex) + "<td><span style='" + style + "'>StartPc:" + startPc +"</span>");

      // the line number
      style = PointSelector.getStyleForByte(lineNumberIndex + 2, 2);
      int lineNumber = Utils.getTwoBytes(getBytes(), lineNumberIndex + 2);
      sb.append("</td><td><span style='" + style + "'>Line number:" + lineNumber + "</span></td></tr>");
    }


    return sb.toString();
  }
}
