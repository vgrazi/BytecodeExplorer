package com.vgrazi.bytecodeexplorer.structure.members.method_attributes;

import com.vgrazi.bytecodeexplorer.utils.Utils;

/**
 * <pre>
 * StackMapTable_attribute {
 *    u2              attribute_name_index;
 *    u4              attribute_length;
 *    u2              number_of_entries;
 *    stack_map_frame entries[number_of_entries];
 * }
 * </pre>
 */
public class StackMapTableMethodAttribute extends MethodAttribute {


  @Override
  public String getIdentifier() {
    return "StackMapTable";
  }

  @Override
  public String toString() {
    int numberOfEntries = Utils.getTwoBytes(getBytes(), getStartByteIndex() + 6);

    int attributeLength = getAttributeLength();
    StringBuilder sb = new StringBuilder("<tr><td>" + getStartByteIndex() +
      "</td><td>Attribute name:" + getAttributeName() +
      "</td><td>Attribute length:" + attributeLength +
      "</td><td># entries:" + numberOfEntries
    );

    for (int i = 0; i < numberOfEntries; i++) {
      int index = getStartByteIndex() + 8 + i;

      int startPc = Utils.getOneByte(getBytes(), index);
      sb.append("<tr><td>" + index);
      sb.append("</td><td>StartPc:" + startPc);
      sb.append("</td></tr>");
    }


    return sb.toString();
  }
}
