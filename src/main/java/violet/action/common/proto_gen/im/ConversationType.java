// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/im.proto

package violet.action.common.proto_gen.im;

/**
 * Protobuf enum {@code im.ConversationType}
 */
public enum ConversationType
        implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>ConversationType_Not_Use = 0;</code>
     */
    ConversationType_Not_Use(0),
    /**
     * <code>One_Chat = 1;</code>
     */
    One_Chat(1),
    /**
     * <code>Group_Chat = 2;</code>
     */
    Group_Chat(2),
    /**
     * <code>Service_Chat = 3;</code>
     */
    Service_Chat(3),
    /**
     * <code>AI_Chat = 4;</code>
     */
    AI_Chat(4),
    /**
     * <code>System_Chat = 5;</code>
     */
    System_Chat(5),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>ConversationType_Not_Use = 0;</code>
     */
    public static final int ConversationType_Not_Use_VALUE = 0;
    /**
     * <code>One_Chat = 1;</code>
     */
    public static final int One_Chat_VALUE = 1;
    /**
     * <code>Group_Chat = 2;</code>
     */
    public static final int Group_Chat_VALUE = 2;
    /**
     * <code>Service_Chat = 3;</code>
     */
    public static final int Service_Chat_VALUE = 3;
    /**
     * <code>AI_Chat = 4;</code>
     */
    public static final int AI_Chat_VALUE = 4;
    /**
     * <code>System_Chat = 5;</code>
     */
    public static final int System_Chat_VALUE = 5;


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
    public static ConversationType valueOf(int value) {
        return forNumber(value);
    }

    public static ConversationType forNumber(int value) {
        switch (value) {
            case 0:
                return ConversationType_Not_Use;
            case 1:
                return One_Chat;
            case 2:
                return Group_Chat;
            case 3:
                return Service_Chat;
            case 4:
                return AI_Chat;
            case 5:
                return System_Chat;
            default:
                return null;
        }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<ConversationType>
    internalGetValueMap() {
        return internalValueMap;
    }

    private static final com.google.protobuf.Internal.EnumLiteMap<
            ConversationType> internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<ConversationType>() {
                public ConversationType findValueByNumber(int number) {
                    return ConversationType.forNumber(number);
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
        return violet.action.common.proto_gen.im.Im.getDescriptor().getEnumTypes().get(0);
    }

    private static final ConversationType[] VALUES = values();

    public static ConversationType valueOf(
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

    private ConversationType(int value) {
        this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:im.ConversationType)
}

