// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/action.proto

package violet.action.common.proto_gen.action;

/**
 * Protobuf enum {@code action.StatusCode}
 */
public enum StatusCode
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>StatusCode_Not_Use = 0;</code>
   */
  StatusCode_Not_Use(0),
  /**
   * <code>Success = 1000;</code>
   */
  Success(1000),
  /**
   * <code>Server_Error = 1001;</code>
   */
  Server_Error(1001),
  /**
   * <code>Param_Error = 1002;</code>
   */
  Param_Error(1002),
  /**
   * <code>OverFrequency_Error = 1003;</code>
   */
  OverFrequency_Error(1003),
  /**
   * <code>OverLimit_Error = 1004;</code>
   */
  OverLimit_Error(1004),
  /**
   * <code>Duplicate_Error = 1005;</code>
   */
  Duplicate_Error(1005),
  /**
   * <code>RetryTime_Error = 1006;</code>
   */
  RetryTime_Error(1006),
  /**
   * <code>Not_Found_Error = 1007;</code>
   */
  Not_Found_Error(1007),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>StatusCode_Not_Use = 0;</code>
   */
  public static final int StatusCode_Not_Use_VALUE = 0;
  /**
   * <code>Success = 1000;</code>
   */
  public static final int Success_VALUE = 1000;
  /**
   * <code>Server_Error = 1001;</code>
   */
  public static final int Server_Error_VALUE = 1001;
  /**
   * <code>Param_Error = 1002;</code>
   */
  public static final int Param_Error_VALUE = 1002;
  /**
   * <code>OverFrequency_Error = 1003;</code>
   */
  public static final int OverFrequency_Error_VALUE = 1003;
  /**
   * <code>OverLimit_Error = 1004;</code>
   */
  public static final int OverLimit_Error_VALUE = 1004;
  /**
   * <code>Duplicate_Error = 1005;</code>
   */
  public static final int Duplicate_Error_VALUE = 1005;
  /**
   * <code>RetryTime_Error = 1006;</code>
   */
  public static final int RetryTime_Error_VALUE = 1006;
  /**
   * <code>Not_Found_Error = 1007;</code>
   */
  public static final int Not_Found_Error_VALUE = 1007;


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
  public static StatusCode valueOf(int value) {
    return forNumber(value);
  }

  public static StatusCode forNumber(int value) {
    switch (value) {
      case 0: return StatusCode_Not_Use;
      case 1000: return Success;
      case 1001: return Server_Error;
      case 1002: return Param_Error;
      case 1003: return OverFrequency_Error;
      case 1004: return OverLimit_Error;
      case 1005: return Duplicate_Error;
      case 1006: return RetryTime_Error;
      case 1007: return Not_Found_Error;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<StatusCode>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      StatusCode> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<StatusCode>() {
          public StatusCode findValueByNumber(int number) {
            return StatusCode.forNumber(number);
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
    return violet.action.common.proto_gen.action.Action.getDescriptor().getEnumTypes().get(0);
  }

  private static final StatusCode[] VALUES = values();

  public static StatusCode valueOf(
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

  private StatusCode(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:action.StatusCode)
}

