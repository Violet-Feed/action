// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/action.proto

package violet.action.common.proto_gen.action;

/**
 * Protobuf type {@code action.MGetFollowCountRequest}
 */
public  final class MGetFollowCountRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:action.MGetFollowCountRequest)
    MGetFollowCountRequestOrBuilder {
  // Use MGetFollowCountRequest.newBuilder() to construct.
  private MGetFollowCountRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private MGetFollowCountRequest() {
    userIds_ = java.util.Collections.emptyList();
    needFollowing_ = false;
    needFollower_ = false;
    needFriend_ = false;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private MGetFollowCountRequest(
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
            if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
              userIds_ = new java.util.ArrayList<java.lang.Long>();
              mutable_bitField0_ |= 0x00000001;
            }
            userIds_.add(input.readInt64());
            break;
          }
          case 10: {
            int length = input.readRawVarint32();
            int limit = input.pushLimit(length);
            if (!((mutable_bitField0_ & 0x00000001) == 0x00000001) && input.getBytesUntilLimit() > 0) {
              userIds_ = new java.util.ArrayList<java.lang.Long>();
              mutable_bitField0_ |= 0x00000001;
            }
            while (input.getBytesUntilLimit() > 0) {
              userIds_.add(input.readInt64());
            }
            input.popLimit(limit);
            break;
          }
          case 16: {

            needFollowing_ = input.readBool();
            break;
          }
          case 24: {

            needFollower_ = input.readBool();
            break;
          }
          case 32: {

            needFriend_ = input.readBool();
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
      if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
        userIds_ = java.util.Collections.unmodifiableList(userIds_);
      }
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return violet.action.common.proto_gen.action.Action.internal_static_action_MGetFollowCountRequest_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return violet.action.common.proto_gen.action.Action.internal_static_action_MGetFollowCountRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            violet.action.common.proto_gen.action.MGetFollowCountRequest.class, violet.action.common.proto_gen.action.MGetFollowCountRequest.Builder.class);
  }

  private int bitField0_;
  public static final int USER_IDS_FIELD_NUMBER = 1;
  private java.util.List<java.lang.Long> userIds_;
  /**
   * <code>repeated int64 user_ids = 1;</code>
   */
  public java.util.List<java.lang.Long>
      getUserIdsList() {
    return userIds_;
  }
  /**
   * <code>repeated int64 user_ids = 1;</code>
   */
  public int getUserIdsCount() {
    return userIds_.size();
  }
  /**
   * <code>repeated int64 user_ids = 1;</code>
   */
  public long getUserIds(int index) {
    return userIds_.get(index);
  }
  private int userIdsMemoizedSerializedSize = -1;

  public static final int NEED_FOLLOWING_FIELD_NUMBER = 2;
  private boolean needFollowing_;
  /**
   * <code>bool need_following = 2;</code>
   */
  public boolean getNeedFollowing() {
    return needFollowing_;
  }

  public static final int NEED_FOLLOWER_FIELD_NUMBER = 3;
  private boolean needFollower_;
  /**
   * <code>bool need_follower = 3;</code>
   */
  public boolean getNeedFollower() {
    return needFollower_;
  }

  public static final int NEED_FRIEND_FIELD_NUMBER = 4;
  private boolean needFriend_;
  /**
   * <code>bool need_friend = 4;</code>
   */
  public boolean getNeedFriend() {
    return needFriend_;
  }

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
    if (getUserIdsList().size() > 0) {
      output.writeUInt32NoTag(10);
      output.writeUInt32NoTag(userIdsMemoizedSerializedSize);
    }
    for (int i = 0; i < userIds_.size(); i++) {
      output.writeInt64NoTag(userIds_.get(i));
    }
    if (needFollowing_ != false) {
      output.writeBool(2, needFollowing_);
    }
    if (needFollower_ != false) {
      output.writeBool(3, needFollower_);
    }
    if (needFriend_ != false) {
      output.writeBool(4, needFriend_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    {
      int dataSize = 0;
      for (int i = 0; i < userIds_.size(); i++) {
        dataSize += com.google.protobuf.CodedOutputStream
          .computeInt64SizeNoTag(userIds_.get(i));
      }
      size += dataSize;
      if (!getUserIdsList().isEmpty()) {
        size += 1;
        size += com.google.protobuf.CodedOutputStream
            .computeInt32SizeNoTag(dataSize);
      }
      userIdsMemoizedSerializedSize = dataSize;
    }
    if (needFollowing_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(2, needFollowing_);
    }
    if (needFollower_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(3, needFollower_);
    }
    if (needFriend_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(4, needFriend_);
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
    if (!(obj instanceof violet.action.common.proto_gen.action.MGetFollowCountRequest)) {
      return super.equals(obj);
    }
    violet.action.common.proto_gen.action.MGetFollowCountRequest other = (violet.action.common.proto_gen.action.MGetFollowCountRequest) obj;

    boolean result = true;
    result = result && getUserIdsList()
        .equals(other.getUserIdsList());
    result = result && (getNeedFollowing()
        == other.getNeedFollowing());
    result = result && (getNeedFollower()
        == other.getNeedFollower());
    result = result && (getNeedFriend()
        == other.getNeedFriend());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (getUserIdsCount() > 0) {
      hash = (37 * hash) + USER_IDS_FIELD_NUMBER;
      hash = (53 * hash) + getUserIdsList().hashCode();
    }
    hash = (37 * hash) + NEED_FOLLOWING_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getNeedFollowing());
    hash = (37 * hash) + NEED_FOLLOWER_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getNeedFollower());
    hash = (37 * hash) + NEED_FRIEND_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getNeedFriend());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static violet.action.common.proto_gen.action.MGetFollowCountRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static violet.action.common.proto_gen.action.MGetFollowCountRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.MGetFollowCountRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static violet.action.common.proto_gen.action.MGetFollowCountRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.MGetFollowCountRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static violet.action.common.proto_gen.action.MGetFollowCountRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.MGetFollowCountRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static violet.action.common.proto_gen.action.MGetFollowCountRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.MGetFollowCountRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static violet.action.common.proto_gen.action.MGetFollowCountRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.MGetFollowCountRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static violet.action.common.proto_gen.action.MGetFollowCountRequest parseFrom(
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
  public static Builder newBuilder(violet.action.common.proto_gen.action.MGetFollowCountRequest prototype) {
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
   * Protobuf type {@code action.MGetFollowCountRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:action.MGetFollowCountRequest)
      violet.action.common.proto_gen.action.MGetFollowCountRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return violet.action.common.proto_gen.action.Action.internal_static_action_MGetFollowCountRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return violet.action.common.proto_gen.action.Action.internal_static_action_MGetFollowCountRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              violet.action.common.proto_gen.action.MGetFollowCountRequest.class, violet.action.common.proto_gen.action.MGetFollowCountRequest.Builder.class);
    }

    // Construct using violet.action.common.proto_gen.action.MGetFollowCountRequest.newBuilder()
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
      userIds_ = java.util.Collections.emptyList();
      bitField0_ = (bitField0_ & ~0x00000001);
      needFollowing_ = false;

      needFollower_ = false;

      needFriend_ = false;

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return violet.action.common.proto_gen.action.Action.internal_static_action_MGetFollowCountRequest_descriptor;
    }

    public violet.action.common.proto_gen.action.MGetFollowCountRequest getDefaultInstanceForType() {
      return violet.action.common.proto_gen.action.MGetFollowCountRequest.getDefaultInstance();
    }

    public violet.action.common.proto_gen.action.MGetFollowCountRequest build() {
      violet.action.common.proto_gen.action.MGetFollowCountRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public violet.action.common.proto_gen.action.MGetFollowCountRequest buildPartial() {
      violet.action.common.proto_gen.action.MGetFollowCountRequest result = new violet.action.common.proto_gen.action.MGetFollowCountRequest(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        userIds_ = java.util.Collections.unmodifiableList(userIds_);
        bitField0_ = (bitField0_ & ~0x00000001);
      }
      result.userIds_ = userIds_;
      result.needFollowing_ = needFollowing_;
      result.needFollower_ = needFollower_;
      result.needFriend_ = needFriend_;
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
      if (other instanceof violet.action.common.proto_gen.action.MGetFollowCountRequest) {
        return mergeFrom((violet.action.common.proto_gen.action.MGetFollowCountRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(violet.action.common.proto_gen.action.MGetFollowCountRequest other) {
      if (other == violet.action.common.proto_gen.action.MGetFollowCountRequest.getDefaultInstance()) return this;
      if (!other.userIds_.isEmpty()) {
        if (userIds_.isEmpty()) {
          userIds_ = other.userIds_;
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          ensureUserIdsIsMutable();
          userIds_.addAll(other.userIds_);
        }
        onChanged();
      }
      if (other.getNeedFollowing() != false) {
        setNeedFollowing(other.getNeedFollowing());
      }
      if (other.getNeedFollower() != false) {
        setNeedFollower(other.getNeedFollower());
      }
      if (other.getNeedFriend() != false) {
        setNeedFriend(other.getNeedFriend());
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
      violet.action.common.proto_gen.action.MGetFollowCountRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (violet.action.common.proto_gen.action.MGetFollowCountRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<java.lang.Long> userIds_ = java.util.Collections.emptyList();
    private void ensureUserIdsIsMutable() {
      if (!((bitField0_ & 0x00000001) == 0x00000001)) {
        userIds_ = new java.util.ArrayList<java.lang.Long>(userIds_);
        bitField0_ |= 0x00000001;
       }
    }
    /**
     * <code>repeated int64 user_ids = 1;</code>
     */
    public java.util.List<java.lang.Long>
        getUserIdsList() {
      return java.util.Collections.unmodifiableList(userIds_);
    }
    /**
     * <code>repeated int64 user_ids = 1;</code>
     */
    public int getUserIdsCount() {
      return userIds_.size();
    }
    /**
     * <code>repeated int64 user_ids = 1;</code>
     */
    public long getUserIds(int index) {
      return userIds_.get(index);
    }
    /**
     * <code>repeated int64 user_ids = 1;</code>
     */
    public Builder setUserIds(
        int index, long value) {
      ensureUserIdsIsMutable();
      userIds_.set(index, value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 user_ids = 1;</code>
     */
    public Builder addUserIds(long value) {
      ensureUserIdsIsMutable();
      userIds_.add(value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 user_ids = 1;</code>
     */
    public Builder addAllUserIds(
        java.lang.Iterable<? extends java.lang.Long> values) {
      ensureUserIdsIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, userIds_);
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 user_ids = 1;</code>
     */
    public Builder clearUserIds() {
      userIds_ = java.util.Collections.emptyList();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }

    private boolean needFollowing_ ;
    /**
     * <code>bool need_following = 2;</code>
     */
    public boolean getNeedFollowing() {
      return needFollowing_;
    }
    /**
     * <code>bool need_following = 2;</code>
     */
    public Builder setNeedFollowing(boolean value) {
      
      needFollowing_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool need_following = 2;</code>
     */
    public Builder clearNeedFollowing() {
      
      needFollowing_ = false;
      onChanged();
      return this;
    }

    private boolean needFollower_ ;
    /**
     * <code>bool need_follower = 3;</code>
     */
    public boolean getNeedFollower() {
      return needFollower_;
    }
    /**
     * <code>bool need_follower = 3;</code>
     */
    public Builder setNeedFollower(boolean value) {
      
      needFollower_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool need_follower = 3;</code>
     */
    public Builder clearNeedFollower() {
      
      needFollower_ = false;
      onChanged();
      return this;
    }

    private boolean needFriend_ ;
    /**
     * <code>bool need_friend = 4;</code>
     */
    public boolean getNeedFriend() {
      return needFriend_;
    }
    /**
     * <code>bool need_friend = 4;</code>
     */
    public Builder setNeedFriend(boolean value) {
      
      needFriend_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool need_friend = 4;</code>
     */
    public Builder clearNeedFriend() {
      
      needFriend_ = false;
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


    // @@protoc_insertion_point(builder_scope:action.MGetFollowCountRequest)
  }

  // @@protoc_insertion_point(class_scope:action.MGetFollowCountRequest)
  private static final violet.action.common.proto_gen.action.MGetFollowCountRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new violet.action.common.proto_gen.action.MGetFollowCountRequest();
  }

  public static violet.action.common.proto_gen.action.MGetFollowCountRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<MGetFollowCountRequest>
      PARSER = new com.google.protobuf.AbstractParser<MGetFollowCountRequest>() {
    public MGetFollowCountRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new MGetFollowCountRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<MGetFollowCountRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<MGetFollowCountRequest> getParserForType() {
    return PARSER;
  }

  public violet.action.common.proto_gen.action.MGetFollowCountRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

