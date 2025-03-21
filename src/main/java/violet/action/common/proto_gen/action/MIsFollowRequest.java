// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/action.proto

package violet.action.common.proto_gen.action;

/**
 * Protobuf type {@code action.MIsFollowRequest}
 */
public  final class MIsFollowRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:action.MIsFollowRequest)
    MIsFollowRequestOrBuilder {
  // Use MIsFollowRequest.newBuilder() to construct.
  private MIsFollowRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private MIsFollowRequest() {
    fromUserId_ = 0L;
    toUserIds_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private MIsFollowRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 8: {

            fromUserId_ = input.readInt64();
            break;
          }
          case 16: {
            if (!((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
              toUserIds_ = new java.util.ArrayList<java.lang.Long>();
              mutable_bitField0_ |= 0x00000002;
            }
            toUserIds_.add(input.readInt64());
            break;
          }
          case 18: {
            int length = input.readRawVarint32();
            int limit = input.pushLimit(length);
            if (!((mutable_bitField0_ & 0x00000002) == 0x00000002) && input.getBytesUntilLimit() > 0) {
              toUserIds_ = new java.util.ArrayList<java.lang.Long>();
              mutable_bitField0_ |= 0x00000002;
            }
            while (input.getBytesUntilLimit() > 0) {
              toUserIds_.add(input.readInt64());
            }
            input.popLimit(limit);
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
        toUserIds_ = java.util.Collections.unmodifiableList(toUserIds_);
      }
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return violet.action.common.proto_gen.action.Action.internal_static_action_MIsFollowRequest_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return violet.action.common.proto_gen.action.Action.internal_static_action_MIsFollowRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            violet.action.common.proto_gen.action.MIsFollowRequest.class, violet.action.common.proto_gen.action.MIsFollowRequest.Builder.class);
  }

  private int bitField0_;
  public static final int FROM_USER_ID_FIELD_NUMBER = 1;
  private long fromUserId_;
  /**
   * <code>int64 from_user_id = 1;</code>
   */
  public long getFromUserId() {
    return fromUserId_;
  }

  public static final int TO_USER_IDS_FIELD_NUMBER = 2;
  private java.util.List<java.lang.Long> toUserIds_;
  /**
   * <code>repeated int64 to_user_ids = 2;</code>
   */
  public java.util.List<java.lang.Long>
      getToUserIdsList() {
    return toUserIds_;
  }
  /**
   * <code>repeated int64 to_user_ids = 2;</code>
   */
  public int getToUserIdsCount() {
    return toUserIds_.size();
  }
  /**
   * <code>repeated int64 to_user_ids = 2;</code>
   */
  public long getToUserIds(int index) {
    return toUserIds_.get(index);
  }
  private int toUserIdsMemoizedSerializedSize = -1;

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    getSerializedSize();
    if (fromUserId_ != 0L) {
      output.writeInt64(1, fromUserId_);
    }
    if (getToUserIdsList().size() > 0) {
      output.writeUInt32NoTag(18);
      output.writeUInt32NoTag(toUserIdsMemoizedSerializedSize);
    }
    for (int i = 0; i < toUserIds_.size(); i++) {
      output.writeInt64NoTag(toUserIds_.get(i));
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (fromUserId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(1, fromUserId_);
    }
    {
      int dataSize = 0;
      for (int i = 0; i < toUserIds_.size(); i++) {
        dataSize += com.google.protobuf.CodedOutputStream
          .computeInt64SizeNoTag(toUserIds_.get(i));
      }
      size += dataSize;
      if (!getToUserIdsList().isEmpty()) {
        size += 1;
        size += com.google.protobuf.CodedOutputStream
            .computeInt32SizeNoTag(dataSize);
      }
      toUserIdsMemoizedSerializedSize = dataSize;
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof violet.action.common.proto_gen.action.MIsFollowRequest)) {
      return super.equals(obj);
    }
    violet.action.common.proto_gen.action.MIsFollowRequest other = (violet.action.common.proto_gen.action.MIsFollowRequest) obj;

    boolean result = true;
    result = result && (getFromUserId()
        == other.getFromUserId());
    result = result && getToUserIdsList()
        .equals(other.getToUserIdsList());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + FROM_USER_ID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getFromUserId());
    if (getToUserIdsCount() > 0) {
      hash = (37 * hash) + TO_USER_IDS_FIELD_NUMBER;
      hash = (53 * hash) + getToUserIdsList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static violet.action.common.proto_gen.action.MIsFollowRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static violet.action.common.proto_gen.action.MIsFollowRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.MIsFollowRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static violet.action.common.proto_gen.action.MIsFollowRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.MIsFollowRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static violet.action.common.proto_gen.action.MIsFollowRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.MIsFollowRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static violet.action.common.proto_gen.action.MIsFollowRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.MIsFollowRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static violet.action.common.proto_gen.action.MIsFollowRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.MIsFollowRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static violet.action.common.proto_gen.action.MIsFollowRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(violet.action.common.proto_gen.action.MIsFollowRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code action.MIsFollowRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:action.MIsFollowRequest)
      violet.action.common.proto_gen.action.MIsFollowRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return violet.action.common.proto_gen.action.Action.internal_static_action_MIsFollowRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return violet.action.common.proto_gen.action.Action.internal_static_action_MIsFollowRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              violet.action.common.proto_gen.action.MIsFollowRequest.class, violet.action.common.proto_gen.action.MIsFollowRequest.Builder.class);
    }

    // Construct using violet.action.common.proto_gen.action.MIsFollowRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      fromUserId_ = 0L;

      toUserIds_ = java.util.Collections.emptyList();
      bitField0_ = (bitField0_ & ~0x00000002);
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return violet.action.common.proto_gen.action.Action.internal_static_action_MIsFollowRequest_descriptor;
    }

    public violet.action.common.proto_gen.action.MIsFollowRequest getDefaultInstanceForType() {
      return violet.action.common.proto_gen.action.MIsFollowRequest.getDefaultInstance();
    }

    public violet.action.common.proto_gen.action.MIsFollowRequest build() {
      violet.action.common.proto_gen.action.MIsFollowRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public violet.action.common.proto_gen.action.MIsFollowRequest buildPartial() {
      violet.action.common.proto_gen.action.MIsFollowRequest result = new violet.action.common.proto_gen.action.MIsFollowRequest(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      result.fromUserId_ = fromUserId_;
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        toUserIds_ = java.util.Collections.unmodifiableList(toUserIds_);
        bitField0_ = (bitField0_ & ~0x00000002);
      }
      result.toUserIds_ = toUserIds_;
      result.bitField0_ = to_bitField0_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof violet.action.common.proto_gen.action.MIsFollowRequest) {
        return mergeFrom((violet.action.common.proto_gen.action.MIsFollowRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(violet.action.common.proto_gen.action.MIsFollowRequest other) {
      if (other == violet.action.common.proto_gen.action.MIsFollowRequest.getDefaultInstance()) return this;
      if (other.getFromUserId() != 0L) {
        setFromUserId(other.getFromUserId());
      }
      if (!other.toUserIds_.isEmpty()) {
        if (toUserIds_.isEmpty()) {
          toUserIds_ = other.toUserIds_;
          bitField0_ = (bitField0_ & ~0x00000002);
        } else {
          ensureToUserIdsIsMutable();
          toUserIds_.addAll(other.toUserIds_);
        }
        onChanged();
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      violet.action.common.proto_gen.action.MIsFollowRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (violet.action.common.proto_gen.action.MIsFollowRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private long fromUserId_ ;
    /**
     * <code>int64 from_user_id = 1;</code>
     */
    public long getFromUserId() {
      return fromUserId_;
    }
    /**
     * <code>int64 from_user_id = 1;</code>
     */
    public Builder setFromUserId(long value) {
      
      fromUserId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 from_user_id = 1;</code>
     */
    public Builder clearFromUserId() {
      
      fromUserId_ = 0L;
      onChanged();
      return this;
    }

    private java.util.List<java.lang.Long> toUserIds_ = java.util.Collections.emptyList();
    private void ensureToUserIdsIsMutable() {
      if (!((bitField0_ & 0x00000002) == 0x00000002)) {
        toUserIds_ = new java.util.ArrayList<java.lang.Long>(toUserIds_);
        bitField0_ |= 0x00000002;
       }
    }
    /**
     * <code>repeated int64 to_user_ids = 2;</code>
     */
    public java.util.List<java.lang.Long>
        getToUserIdsList() {
      return java.util.Collections.unmodifiableList(toUserIds_);
    }
    /**
     * <code>repeated int64 to_user_ids = 2;</code>
     */
    public int getToUserIdsCount() {
      return toUserIds_.size();
    }
    /**
     * <code>repeated int64 to_user_ids = 2;</code>
     */
    public long getToUserIds(int index) {
      return toUserIds_.get(index);
    }
    /**
     * <code>repeated int64 to_user_ids = 2;</code>
     */
    public Builder setToUserIds(
        int index, long value) {
      ensureToUserIdsIsMutable();
      toUserIds_.set(index, value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 to_user_ids = 2;</code>
     */
    public Builder addToUserIds(long value) {
      ensureToUserIdsIsMutable();
      toUserIds_.add(value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 to_user_ids = 2;</code>
     */
    public Builder addAllToUserIds(
        java.lang.Iterable<? extends java.lang.Long> values) {
      ensureToUserIdsIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, toUserIds_);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 to_user_ids = 2;</code>
     */
    public Builder clearToUserIds() {
      toUserIds_ = java.util.Collections.emptyList();
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:action.MIsFollowRequest)
  }

  // @@protoc_insertion_point(class_scope:action.MIsFollowRequest)
  private static final violet.action.common.proto_gen.action.MIsFollowRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new violet.action.common.proto_gen.action.MIsFollowRequest();
  }

  public static violet.action.common.proto_gen.action.MIsFollowRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<MIsFollowRequest>
      PARSER = new com.google.protobuf.AbstractParser<MIsFollowRequest>() {
    public MIsFollowRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new MIsFollowRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<MIsFollowRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<MIsFollowRequest> getParserForType() {
    return PARSER;
  }

  public violet.action.common.proto_gen.action.MIsFollowRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

