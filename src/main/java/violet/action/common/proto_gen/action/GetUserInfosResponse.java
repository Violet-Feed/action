// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/action.proto

package violet.action.common.proto_gen.action;

/**
 * Protobuf type {@code action.GetUserInfosResponse}
 */
public  final class GetUserInfosResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:action.GetUserInfosResponse)
    GetUserInfosResponseOrBuilder {
  // Use GetUserInfosResponse.newBuilder() to construct.
  private GetUserInfosResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private GetUserInfosResponse() {
    userInfos_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private GetUserInfosResponse(
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
          case 10: {
            if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
              userInfos_ = new java.util.ArrayList<violet.action.common.proto_gen.action.UserInfo>();
              mutable_bitField0_ |= 0x00000001;
            }
            userInfos_.add(
                input.readMessage(violet.action.common.proto_gen.action.UserInfo.parser(), extensionRegistry));
            break;
          }
          case 2042: {
            violet.action.common.proto_gen.action.BaseResp.Builder subBuilder = null;
            if (baseResp_ != null) {
              subBuilder = baseResp_.toBuilder();
            }
            baseResp_ = input.readMessage(violet.action.common.proto_gen.action.BaseResp.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(baseResp_);
              baseResp_ = subBuilder.buildPartial();
            }

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
        userInfos_ = java.util.Collections.unmodifiableList(userInfos_);
      }
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return violet.action.common.proto_gen.action.Action.internal_static_action_GetUserInfosResponse_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return violet.action.common.proto_gen.action.Action.internal_static_action_GetUserInfosResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            violet.action.common.proto_gen.action.GetUserInfosResponse.class, violet.action.common.proto_gen.action.GetUserInfosResponse.Builder.class);
  }

  private int bitField0_;
  public static final int USER_INFOS_FIELD_NUMBER = 1;
  private java.util.List<violet.action.common.proto_gen.action.UserInfo> userInfos_;
  /**
   * <code>repeated .action.UserInfo user_infos = 1;</code>
   */
  public java.util.List<violet.action.common.proto_gen.action.UserInfo> getUserInfosList() {
    return userInfos_;
  }
  /**
   * <code>repeated .action.UserInfo user_infos = 1;</code>
   */
  public java.util.List<? extends violet.action.common.proto_gen.action.UserInfoOrBuilder> 
      getUserInfosOrBuilderList() {
    return userInfos_;
  }
  /**
   * <code>repeated .action.UserInfo user_infos = 1;</code>
   */
  public int getUserInfosCount() {
    return userInfos_.size();
  }
  /**
   * <code>repeated .action.UserInfo user_infos = 1;</code>
   */
  public violet.action.common.proto_gen.action.UserInfo getUserInfos(int index) {
    return userInfos_.get(index);
  }
  /**
   * <code>repeated .action.UserInfo user_infos = 1;</code>
   */
  public violet.action.common.proto_gen.action.UserInfoOrBuilder getUserInfosOrBuilder(
      int index) {
    return userInfos_.get(index);
  }

  public static final int BASERESP_FIELD_NUMBER = 255;
  private violet.action.common.proto_gen.action.BaseResp baseResp_;
  /**
   * <code>.action.BaseResp baseResp = 255;</code>
   */
  public boolean hasBaseResp() {
    return baseResp_ != null;
  }
  /**
   * <code>.action.BaseResp baseResp = 255;</code>
   */
  public violet.action.common.proto_gen.action.BaseResp getBaseResp() {
    return baseResp_ == null ? violet.action.common.proto_gen.action.BaseResp.getDefaultInstance() : baseResp_;
  }
  /**
   * <code>.action.BaseResp baseResp = 255;</code>
   */
  public violet.action.common.proto_gen.action.BaseRespOrBuilder getBaseRespOrBuilder() {
    return getBaseResp();
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
    for (int i = 0; i < userInfos_.size(); i++) {
      output.writeMessage(1, userInfos_.get(i));
    }
    if (baseResp_ != null) {
      output.writeMessage(255, getBaseResp());
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < userInfos_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, userInfos_.get(i));
    }
    if (baseResp_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(255, getBaseResp());
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
    if (!(obj instanceof violet.action.common.proto_gen.action.GetUserInfosResponse)) {
      return super.equals(obj);
    }
    violet.action.common.proto_gen.action.GetUserInfosResponse other = (violet.action.common.proto_gen.action.GetUserInfosResponse) obj;

    boolean result = true;
    result = result && getUserInfosList()
        .equals(other.getUserInfosList());
    result = result && (hasBaseResp() == other.hasBaseResp());
    if (hasBaseResp()) {
      result = result && getBaseResp()
          .equals(other.getBaseResp());
    }
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (getUserInfosCount() > 0) {
      hash = (37 * hash) + USER_INFOS_FIELD_NUMBER;
      hash = (53 * hash) + getUserInfosList().hashCode();
    }
    if (hasBaseResp()) {
      hash = (37 * hash) + BASERESP_FIELD_NUMBER;
      hash = (53 * hash) + getBaseResp().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static violet.action.common.proto_gen.action.GetUserInfosResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static violet.action.common.proto_gen.action.GetUserInfosResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.GetUserInfosResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static violet.action.common.proto_gen.action.GetUserInfosResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.GetUserInfosResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static violet.action.common.proto_gen.action.GetUserInfosResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.GetUserInfosResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static violet.action.common.proto_gen.action.GetUserInfosResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.GetUserInfosResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static violet.action.common.proto_gen.action.GetUserInfosResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.GetUserInfosResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static violet.action.common.proto_gen.action.GetUserInfosResponse parseFrom(
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
  public static Builder newBuilder(violet.action.common.proto_gen.action.GetUserInfosResponse prototype) {
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
   * Protobuf type {@code action.GetUserInfosResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:action.GetUserInfosResponse)
      violet.action.common.proto_gen.action.GetUserInfosResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return violet.action.common.proto_gen.action.Action.internal_static_action_GetUserInfosResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return violet.action.common.proto_gen.action.Action.internal_static_action_GetUserInfosResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              violet.action.common.proto_gen.action.GetUserInfosResponse.class, violet.action.common.proto_gen.action.GetUserInfosResponse.Builder.class);
    }

    // Construct using violet.action.common.proto_gen.action.GetUserInfosResponse.newBuilder()
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
        getUserInfosFieldBuilder();
      }
    }
    public Builder clear() {
      super.clear();
      if (userInfosBuilder_ == null) {
        userInfos_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        userInfosBuilder_.clear();
      }
      if (baseRespBuilder_ == null) {
        baseResp_ = null;
      } else {
        baseResp_ = null;
        baseRespBuilder_ = null;
      }
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return violet.action.common.proto_gen.action.Action.internal_static_action_GetUserInfosResponse_descriptor;
    }

    public violet.action.common.proto_gen.action.GetUserInfosResponse getDefaultInstanceForType() {
      return violet.action.common.proto_gen.action.GetUserInfosResponse.getDefaultInstance();
    }

    public violet.action.common.proto_gen.action.GetUserInfosResponse build() {
      violet.action.common.proto_gen.action.GetUserInfosResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public violet.action.common.proto_gen.action.GetUserInfosResponse buildPartial() {
      violet.action.common.proto_gen.action.GetUserInfosResponse result = new violet.action.common.proto_gen.action.GetUserInfosResponse(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (userInfosBuilder_ == null) {
        if (((bitField0_ & 0x00000001) == 0x00000001)) {
          userInfos_ = java.util.Collections.unmodifiableList(userInfos_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.userInfos_ = userInfos_;
      } else {
        result.userInfos_ = userInfosBuilder_.build();
      }
      if (baseRespBuilder_ == null) {
        result.baseResp_ = baseResp_;
      } else {
        result.baseResp_ = baseRespBuilder_.build();
      }
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
      if (other instanceof violet.action.common.proto_gen.action.GetUserInfosResponse) {
        return mergeFrom((violet.action.common.proto_gen.action.GetUserInfosResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(violet.action.common.proto_gen.action.GetUserInfosResponse other) {
      if (other == violet.action.common.proto_gen.action.GetUserInfosResponse.getDefaultInstance()) return this;
      if (userInfosBuilder_ == null) {
        if (!other.userInfos_.isEmpty()) {
          if (userInfos_.isEmpty()) {
            userInfos_ = other.userInfos_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureUserInfosIsMutable();
            userInfos_.addAll(other.userInfos_);
          }
          onChanged();
        }
      } else {
        if (!other.userInfos_.isEmpty()) {
          if (userInfosBuilder_.isEmpty()) {
            userInfosBuilder_.dispose();
            userInfosBuilder_ = null;
            userInfos_ = other.userInfos_;
            bitField0_ = (bitField0_ & ~0x00000001);
            userInfosBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getUserInfosFieldBuilder() : null;
          } else {
            userInfosBuilder_.addAllMessages(other.userInfos_);
          }
        }
      }
      if (other.hasBaseResp()) {
        mergeBaseResp(other.getBaseResp());
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
      violet.action.common.proto_gen.action.GetUserInfosResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (violet.action.common.proto_gen.action.GetUserInfosResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<violet.action.common.proto_gen.action.UserInfo> userInfos_ =
      java.util.Collections.emptyList();
    private void ensureUserInfosIsMutable() {
      if (!((bitField0_ & 0x00000001) == 0x00000001)) {
        userInfos_ = new java.util.ArrayList<violet.action.common.proto_gen.action.UserInfo>(userInfos_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        violet.action.common.proto_gen.action.UserInfo, violet.action.common.proto_gen.action.UserInfo.Builder, violet.action.common.proto_gen.action.UserInfoOrBuilder> userInfosBuilder_;

    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    public java.util.List<violet.action.common.proto_gen.action.UserInfo> getUserInfosList() {
      if (userInfosBuilder_ == null) {
        return java.util.Collections.unmodifiableList(userInfos_);
      } else {
        return userInfosBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    public int getUserInfosCount() {
      if (userInfosBuilder_ == null) {
        return userInfos_.size();
      } else {
        return userInfosBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    public violet.action.common.proto_gen.action.UserInfo getUserInfos(int index) {
      if (userInfosBuilder_ == null) {
        return userInfos_.get(index);
      } else {
        return userInfosBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    public Builder setUserInfos(
        int index, violet.action.common.proto_gen.action.UserInfo value) {
      if (userInfosBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureUserInfosIsMutable();
        userInfos_.set(index, value);
        onChanged();
      } else {
        userInfosBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    public Builder setUserInfos(
        int index, violet.action.common.proto_gen.action.UserInfo.Builder builderForValue) {
      if (userInfosBuilder_ == null) {
        ensureUserInfosIsMutable();
        userInfos_.set(index, builderForValue.build());
        onChanged();
      } else {
        userInfosBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    public Builder addUserInfos(violet.action.common.proto_gen.action.UserInfo value) {
      if (userInfosBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureUserInfosIsMutable();
        userInfos_.add(value);
        onChanged();
      } else {
        userInfosBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    public Builder addUserInfos(
        int index, violet.action.common.proto_gen.action.UserInfo value) {
      if (userInfosBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureUserInfosIsMutable();
        userInfos_.add(index, value);
        onChanged();
      } else {
        userInfosBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    public Builder addUserInfos(
        violet.action.common.proto_gen.action.UserInfo.Builder builderForValue) {
      if (userInfosBuilder_ == null) {
        ensureUserInfosIsMutable();
        userInfos_.add(builderForValue.build());
        onChanged();
      } else {
        userInfosBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    public Builder addUserInfos(
        int index, violet.action.common.proto_gen.action.UserInfo.Builder builderForValue) {
      if (userInfosBuilder_ == null) {
        ensureUserInfosIsMutable();
        userInfos_.add(index, builderForValue.build());
        onChanged();
      } else {
        userInfosBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    public Builder addAllUserInfos(
        java.lang.Iterable<? extends violet.action.common.proto_gen.action.UserInfo> values) {
      if (userInfosBuilder_ == null) {
        ensureUserInfosIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, userInfos_);
        onChanged();
      } else {
        userInfosBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    public Builder clearUserInfos() {
      if (userInfosBuilder_ == null) {
        userInfos_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        userInfosBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    public Builder removeUserInfos(int index) {
      if (userInfosBuilder_ == null) {
        ensureUserInfosIsMutable();
        userInfos_.remove(index);
        onChanged();
      } else {
        userInfosBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    public violet.action.common.proto_gen.action.UserInfo.Builder getUserInfosBuilder(
        int index) {
      return getUserInfosFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    public violet.action.common.proto_gen.action.UserInfoOrBuilder getUserInfosOrBuilder(
        int index) {
      if (userInfosBuilder_ == null) {
        return userInfos_.get(index);  } else {
        return userInfosBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    public java.util.List<? extends violet.action.common.proto_gen.action.UserInfoOrBuilder> 
         getUserInfosOrBuilderList() {
      if (userInfosBuilder_ != null) {
        return userInfosBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(userInfos_);
      }
    }
    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    public violet.action.common.proto_gen.action.UserInfo.Builder addUserInfosBuilder() {
      return getUserInfosFieldBuilder().addBuilder(
          violet.action.common.proto_gen.action.UserInfo.getDefaultInstance());
    }
    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    public violet.action.common.proto_gen.action.UserInfo.Builder addUserInfosBuilder(
        int index) {
      return getUserInfosFieldBuilder().addBuilder(
          index, violet.action.common.proto_gen.action.UserInfo.getDefaultInstance());
    }
    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    public java.util.List<violet.action.common.proto_gen.action.UserInfo.Builder> 
         getUserInfosBuilderList() {
      return getUserInfosFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        violet.action.common.proto_gen.action.UserInfo, violet.action.common.proto_gen.action.UserInfo.Builder, violet.action.common.proto_gen.action.UserInfoOrBuilder> 
        getUserInfosFieldBuilder() {
      if (userInfosBuilder_ == null) {
        userInfosBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            violet.action.common.proto_gen.action.UserInfo, violet.action.common.proto_gen.action.UserInfo.Builder, violet.action.common.proto_gen.action.UserInfoOrBuilder>(
                userInfos_,
                ((bitField0_ & 0x00000001) == 0x00000001),
                getParentForChildren(),
                isClean());
        userInfos_ = null;
      }
      return userInfosBuilder_;
    }

    private violet.action.common.proto_gen.action.BaseResp baseResp_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        violet.action.common.proto_gen.action.BaseResp, violet.action.common.proto_gen.action.BaseResp.Builder, violet.action.common.proto_gen.action.BaseRespOrBuilder> baseRespBuilder_;
    /**
     * <code>.action.BaseResp baseResp = 255;</code>
     */
    public boolean hasBaseResp() {
      return baseRespBuilder_ != null || baseResp_ != null;
    }
    /**
     * <code>.action.BaseResp baseResp = 255;</code>
     */
    public violet.action.common.proto_gen.action.BaseResp getBaseResp() {
      if (baseRespBuilder_ == null) {
        return baseResp_ == null ? violet.action.common.proto_gen.action.BaseResp.getDefaultInstance() : baseResp_;
      } else {
        return baseRespBuilder_.getMessage();
      }
    }
    /**
     * <code>.action.BaseResp baseResp = 255;</code>
     */
    public Builder setBaseResp(violet.action.common.proto_gen.action.BaseResp value) {
      if (baseRespBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        baseResp_ = value;
        onChanged();
      } else {
        baseRespBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.action.BaseResp baseResp = 255;</code>
     */
    public Builder setBaseResp(
        violet.action.common.proto_gen.action.BaseResp.Builder builderForValue) {
      if (baseRespBuilder_ == null) {
        baseResp_ = builderForValue.build();
        onChanged();
      } else {
        baseRespBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.action.BaseResp baseResp = 255;</code>
     */
    public Builder mergeBaseResp(violet.action.common.proto_gen.action.BaseResp value) {
      if (baseRespBuilder_ == null) {
        if (baseResp_ != null) {
          baseResp_ =
            violet.action.common.proto_gen.action.BaseResp.newBuilder(baseResp_).mergeFrom(value).buildPartial();
        } else {
          baseResp_ = value;
        }
        onChanged();
      } else {
        baseRespBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.action.BaseResp baseResp = 255;</code>
     */
    public Builder clearBaseResp() {
      if (baseRespBuilder_ == null) {
        baseResp_ = null;
        onChanged();
      } else {
        baseResp_ = null;
        baseRespBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.action.BaseResp baseResp = 255;</code>
     */
    public violet.action.common.proto_gen.action.BaseResp.Builder getBaseRespBuilder() {
      
      onChanged();
      return getBaseRespFieldBuilder().getBuilder();
    }
    /**
     * <code>.action.BaseResp baseResp = 255;</code>
     */
    public violet.action.common.proto_gen.action.BaseRespOrBuilder getBaseRespOrBuilder() {
      if (baseRespBuilder_ != null) {
        return baseRespBuilder_.getMessageOrBuilder();
      } else {
        return baseResp_ == null ?
            violet.action.common.proto_gen.action.BaseResp.getDefaultInstance() : baseResp_;
      }
    }
    /**
     * <code>.action.BaseResp baseResp = 255;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        violet.action.common.proto_gen.action.BaseResp, violet.action.common.proto_gen.action.BaseResp.Builder, violet.action.common.proto_gen.action.BaseRespOrBuilder> 
        getBaseRespFieldBuilder() {
      if (baseRespBuilder_ == null) {
        baseRespBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            violet.action.common.proto_gen.action.BaseResp, violet.action.common.proto_gen.action.BaseResp.Builder, violet.action.common.proto_gen.action.BaseRespOrBuilder>(
                getBaseResp(),
                getParentForChildren(),
                isClean());
        baseResp_ = null;
      }
      return baseRespBuilder_;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:action.GetUserInfosResponse)
  }

  // @@protoc_insertion_point(class_scope:action.GetUserInfosResponse)
  private static final violet.action.common.proto_gen.action.GetUserInfosResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new violet.action.common.proto_gen.action.GetUserInfosResponse();
  }

  public static violet.action.common.proto_gen.action.GetUserInfosResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<GetUserInfosResponse>
      PARSER = new com.google.protobuf.AbstractParser<GetUserInfosResponse>() {
    public GetUserInfosResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new GetUserInfosResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<GetUserInfosResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<GetUserInfosResponse> getParserForType() {
    return PARSER;
  }

  public violet.action.common.proto_gen.action.GetUserInfosResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

