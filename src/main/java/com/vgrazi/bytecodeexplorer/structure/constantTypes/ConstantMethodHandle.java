package com.vgrazi.bytecodeexplorer.structure.constantTypes;

import com.vgrazi.bytecodeexplorer.utils.Utils;

import java.util.List;

/**
 * Created by vgrazi on 8/13/15.
 * CONSTANT_MethodHandle_info {
 * u1 tag;
 * u1 reference_kind;
 * u2 reference_index;
 * }
 * reference_kind:
 * 1(REF_getField),
 * 2(REF_getStatic),
 * 3(REF_putField),or
 * 4(REF_putStatic),
 * then the constant_pool entry at that index must be a CONSTANT_Fieldref_info(§4.4.2)structure representing a field for which a method handle is to be created.
 * <p>
 * 5(REF_invokeVirtual),
 * 6(REF_invokeStatic),
 * 7(REF_invokeSpecial),or
 * 8(REF_newInvokeSpecial),then the constant_pool entry at that index must be a CONSTANT_Methodref_info structure(§4.4.2)representing a class's method or constructor (§2.9) for which a method handle is to be created.
 * <p>
 * 9(REF_invokeInterface),then the constant_pool entry at that index must be a CONSTANT_InterfaceMethodref_info(§4.4.2)structure representing an interface's method for which a method handle is to be created.
 * <p>
 * 5(REF_invokeVirtual),
 * 6(REF_invokeStatic),
 * 7(REF_invokeSpecial),or
 * 9(REF_invokeInterface),the name of the method represented by a CONSTANT_Methodref_info structure must not be<init>or<clinit>.
 * <p>
 * If the value is 8(REF_newInvokeSpecial),the name of the method represented by a CONSTANT_Methodref_info structure must be<init>.
 */
public class ConstantMethodHandle extends ConstantType {
    private int startByteIndex;
    private int referenceKind;
    private int referenceIndex;

    /**
     * "tag item" is how the documentation refers to the type byte
     *
     * @return
     */
    @Override
    public byte getTag() {
        return 15;
    }

    @Override
    public void setData(byte[] bytes, int index) {
        this.startByteIndex = index;
        this.referenceKind = bytes[index + 1];
        this.referenceIndex = Utils.getTwoBytes(bytes, index + 2);
    }

    public String toString() {
        List<ConstantType> constants = getConstants();
        String string = getFormattedConstantIndex() + Utils.formatAsFourByteHexString(startByteIndex) + " MethodHandle" +
            referenceKind + " " + getKind() + " <span style='color:blue'>#" + referenceIndex + "</span>";
        if (constants != null) {
            string +=
                "<br/> " +
                    "<span style='color:red'>" + constants.get(referenceIndex - 1) + "</span><br/>";
        }
        return string;

    }

    /**
     * length in bytes of this section
     *
     * @return length in bytes of this section
     */
    @Override
    public int length() {
        return 4;
    }

    /**
     * Index to the first byte of this section relative to the constant pool
     *
     * @return
     */
    @Override
    public int getStartByteIndex() {
        return startByteIndex;
    }

    private String getKind() {
        switch (referenceKind) {
            case 1:
                return "REF_getField";
            case 2:
                return "REF_getStatic";
            case 3:
                return "REF_putField";
            case 4:
                return "REF_putStatic";
            case 5:
                return "REF_invokeVirtual";
            case 6:
                return "REF_invokeStatic";
            case 7:
                return "REF_invokeSpecial";
            case 8:
                return "REF_newInvokeSpecial";
            case 9:
                return "REF_invokeInterface";
            default:
                return "unknown kind:" + referenceKind;

        }

    }
}
