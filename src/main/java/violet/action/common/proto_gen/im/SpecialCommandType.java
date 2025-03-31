// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/im.proto

package violet.action.common.proto_gen.im;

/**
 * Protobuf enum {@code im.SpecialCommandType}
 */
public enum SpecialCommandType
        implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>SpecialCommandType_Not_Use = 0;</code>
     */
    SpecialCommandType_Not_Use(0),
    /**
     * <code>Create_Conversation = 1;</code>
     */
    Create_Conversation(1),
    /**
     * <code>Disband_Conversation = 2;</code>
     */
    Disband_Conversation(2),
    /**
     * <code>Update_Conversation = 3;</code>
     */
    Update_Conversation(3),
    /**
     * <code>Add_Members = 4;</code>
     */
    Add_Members(4),
    /**
     * <code>Remove_Members = 5;</code>
     */
    Remove_Members(5),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>SpecialCommandType_Not_Use = 0;</code>
     */
    public static final int SpecialCommandType_Not_Use_VALUE = 0;
    /**
     * <code>Create_Conversation = 1;</code>
     */
    public static final int Create_Conversation_VALUE = 1;
    /**
     * <code>Disband_Conversation = 2;</code>
     */
    public static final int Disband_Conversation_VALUE = 2;
    /**
     * <code>Update_Conversation = 3;</code>
     */
    public static final int Update_Conversation_VALUE = 3;
    /**
     * <code>Add_Members = 4;</code>
     */
    public static final int Add_Members_VALUE = 4;
    /**
     * <code>Remove_Members = 5;</code>
     */
    public static final int Remove_Members_VALUE = 5;


    public final int getNumber() {
        if (this == UNRECOGNIZED) {
            throw new java.lang.IllegalArgumentException(
                    "Can't get the number of an unknown enum value.");
        }
        return value;
    }

    /**
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static SpecialCommandType valueOf(int value) {
        return forNumber(value);
    }

    public static SpecialCommandType forNumber(int value) {
        switch (value) {
            case 0:
                return SpecialCommandType_Not_Use;
            case 1:
                return Create_Conversation;
            case 2:
                return Disband_Conversation;
            case 3:
                return Update_Conversation;
            case 4:
                return Add_Members;
            case 5:
                return Remove_Members;
            default:
                return null;
        }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<SpecialCommandType>
    internalGetValueMap() {
        return internalValueMap;
    }

    private static final com.google.protobuf.Internal.EnumLiteMap<
            SpecialCommandType> internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<SpecialCommandType>() {
                public SpecialCommandType findValueByNumber(int number) {
                    return SpecialCommandType.forNumber(number);
                }
            };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
    getValueDescriptor() {
        return getDescriptor().getValues().get(ordinal());
    }

    public final com.google.protobuf.Descriptors.EnumDescriptor
    getDescriptorForType() {
        return getDescriptor();
    }

    public static final com.google.protobuf.Descriptors.EnumDescriptor
    getDescriptor() {
        return violet.action.common.proto_gen.im.Im.getDescriptor().getEnumTypes().get(3);
    }

    private static final SpecialCommandType[] VALUES = values();

    public static SpecialCommandType valueOf(
            com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != getDescriptor()) {
            throw new java.lang.IllegalArgumentException(
                    "EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
            return UNRECOGNIZED;
        }
        return VALUES[desc.getIndex()];
    }

    private final int value;

    private SpecialCommandType(int value) {
        this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:im.SpecialCommandType)
}

