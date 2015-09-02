function createConstantType(tag, bytes, index) {

    switch(tag) {
        case 1:
            return new ConstantUTF8(tag, bytes, index);

        case 3:
            return new ConstantInteger(tag, bytes, index);

        case 4:
            return new ConstantFloat(tag, bytes, index);

        case 5:
            return new ConstantLong(tag, bytes, index);

        case 7:
            return new ConstantClass(tag, bytes, index);

        case 8:
            return new ConstantString(tag, bytes, index);

        case 9:
            return new ConstantFieldRef(tag, bytes, index);

        case 10:
            return new ConstantMethodRef(tag, bytes, index);

        case 11:
            return new ConstantInterfaceMethodRef(tag, bytes, index);

        case 12:
            return new ConstantNameAndType(tag, bytes, index);
    }
}