// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/action.proto

package violet.action.common.proto_gen.action;

/**
 * Protobuf type {@code MIsFollowResponse}
 */
public  final class MIsFollowResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:MIsFollowResponse)
    MIsFollowResponseOrBuilder {
  // Use MIsFollowResponse.newBuilder() to construct.
  private MIsFollowResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private MIsFollowResponse() {
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private MIsFollowResponse(
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
              isFollowing_ = com.google.protobuf.MapField.newMapField(
                  IsFollowingDefaultEntryHolder.defaultEntry);
              mutable_bitField0_ |= 0x00000001;
            }
            com.google.protobuf.MapEntry<java.lang.Long, java.lang.Boolean>
            isFollowing__ = input.readMessage(
                IsFollowingDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
            isFollowing_.getMutableMap().put(
                isFollowing__.getKey(), isFollowing__.getValue());
            break;
          }
          case 18: {
            if (!((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
              isFollower_ = com.google.protobuf.MapField.newMapField(
                  IsFollowerDefaultEntryHolder.defaultEntry);
              mutable_bitField0_ |= 0x00000002;
            }
            com.google.protobuf.MapEntry<java.lang.Long, java.lang.Boolean>
            isFollower__ = input.readMessage(
                IsFollowerDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
            isFollower_.getMutableMap().put(
                isFollower__.getKey(), isFollower__.getValue());
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
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return violet.action.common.proto_gen.action.Action.internal_static_MIsFollowResponse_descriptor;
  }

  @SuppressWarnings({"rawtypes"})
  protected com.google.protobuf.MapField internalGetMapField(
      int number) {
    switch (number) {
      case 1:
        return internalGetIsFollowing();
      case 2:
        return internalGetIsFollower();
      default:
        throw new RuntimeException(
            "Invalid map field number: " + number);
    }
  }
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return violet.action.common.proto_gen.action.Action.internal_static_MIsFollowResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            violet.action.common.proto_gen.action.MIsFollowResponse.class, violet.action.common.proto_gen.action.MIsFollowResponse.Builder.class);
  }

  private int bitField0_;
  public static final int IS_FOLLOWING_FIELD_NUMBER = 1;
  private static final class IsFollowingDefaultEntryHolder {
    static final com.google.protobuf.MapEntry<
        java.lang.Long, java.lang.Boolean> defaultEntry =
            com.google.protobuf.MapEntry
            .<java.lang.Long, java.lang.Boolean>newDefaultInstance(
                violet.action.common.proto_gen.action.Action.internal_static_MIsFollowResponse_IsFollowingEntry_descriptor, 
                com.google.protobuf.WireFormat.FieldType.INT64,
                0L,
                com.google.protobuf.WireFormat.FieldType.BOOL,
                false);
  }
  private com.google.protobuf.MapField<
      java.lang.Long, java.lang.Boolean> isFollowing_;
  private com.google.protobuf.MapField<java.lang.Long, java.lang.Boolean>
  internalGetIsFollowing() {
    if (isFollowing_ == null) {
      return com.google.protobuf.MapField.emptyMapField(
          IsFollowingDefaultEntryHolder.defaultEntry);
    }
    return isFollowing_;
  }

  public int getIsFollowingCount() {
    return internalGetIsFollowing().getMap().size();
  }
  /**
   * <code>map&lt;int64, bool&gt; is_following = 1;</code>
   */

  public boolean containsIsFollowing(
      long key) {
    
    return internalGetIsFollowing().getMap().containsKey(key);
  }
  /**
   * Use {@link #getIsFollowingMap()} instead.
   */
  @java.lang.Deprecated
  public java.util.Map<java.lang.Long, java.lang.Boolean> getIsFollowing() {
    return getIsFollowingMap();
  }
  /**
   * <code>map&lt;int64, bool&gt; is_following = 1;</code>
   */

  public java.util.Map<java.lang.Long, java.lang.Boolean> getIsFollowingMap() {
    return internalGetIsFollowing().getMap();
  }
  /**
   * <code>map&lt;int64, bool&gt; is_following = 1;</code>
   */

  public boolean getIsFollowingOrDefault(
      long key,
      boolean defaultValue) {
    
    java.util.Map<java.lang.Long, java.lang.Boolean> map =
        internalGetIsFollowing().getMap();
    return map.containsKey(key) ? map.get(key) : defaultValue;
  }
  /**
   * <code>map&lt;int64, bool&gt; is_following = 1;</code>
   */

  public boolean getIsFollowingOrThrow(
      long key) {
    
    java.util.Map<java.lang.Long, java.lang.Boolean> map =
        internalGetIsFollowing().getMap();
    if (!map.containsKey(key)) {
      throw new java.lang.IllegalArgumentException();
    }
    return map.get(key);
  }

  public static final int IS_FOLLOWER_FIELD_NUMBER = 2;
  private static final class IsFollowerDefaultEntryHolder {
    static final com.google.protobuf.MapEntry<
        java.lang.Long, java.lang.Boolean> defaultEntry =
            com.google.protobuf.MapEntry
            .<java.lang.Long, java.lang.Boolean>newDefaultInstance(
                violet.action.common.proto_gen.action.Action.internal_static_MIsFollowResponse_IsFollowerEntry_descriptor, 
                com.google.protobuf.WireFormat.FieldType.INT64,
                0L,
                com.google.protobuf.WireFormat.FieldType.BOOL,
                false);
  }
  private com.google.protobuf.MapField<
      java.lang.Long, java.lang.Boolean> isFollower_;
  private com.google.protobuf.MapField<java.lang.Long, java.lang.Boolean>
  internalGetIsFollower() {
    if (isFollower_ == null) {
      return com.google.protobuf.MapField.emptyMapField(
          IsFollowerDefaultEntryHolder.defaultEntry);
    }
    return isFollower_;
  }

  public int getIsFollowerCount() {
    return internalGetIsFollower().getMap().size();
  }
  /**
   * <code>map&lt;int64, bool&gt; is_follower = 2;</code>
   */

  public boolean containsIsFollower(
      long key) {
    
    return internalGetIsFollower().getMap().containsKey(key);
  }
  /**
   * Use {@link #getIsFollowerMap()} instead.
   */
  @java.lang.Deprecated
  public java.util.Map<java.lang.Long, java.lang.Boolean> getIsFollower() {
    return getIsFollowerMap();
  }
  /**
   * <code>map&lt;int64, bool&gt; is_follower = 2;</code>
   */

  public java.util.Map<java.lang.Long, java.lang.Boolean> getIsFollowerMap() {
    return internalGetIsFollower().getMap();
  }
  /**
   * <code>map&lt;int64, bool&gt; is_follower = 2;</code>
   */

  public boolean getIsFollowerOrDefault(
      long key,
      boolean defaultValue) {
    
    java.util.Map<java.lang.Long, java.lang.Boolean> map =
        internalGetIsFollower().getMap();
    return map.containsKey(key) ? map.get(key) : defaultValue;
  }
  /**
   * <code>map&lt;int64, bool&gt; is_follower = 2;</code>
   */

  public boolean getIsFollowerOrThrow(
      long key) {
    
    java.util.Map<java.lang.Long, java.lang.Boolean> map =
        internalGetIsFollower().getMap();
    if (!map.containsKey(key)) {
      throw new java.lang.IllegalArgumentException();
    }
    return map.get(key);
  }

  public static final int BASERESP_FIELD_NUMBER = 255;
  private violet.action.common.proto_gen.action.BaseResp baseResp_;
  /**
   * <code>.BaseResp baseResp = 255;</code>
   */
  public boolean hasBaseResp() {
    return baseResp_ != null;
  }
  /**
   * <code>.BaseResp baseResp = 255;</code>
   */
  public violet.action.common.proto_gen.action.BaseResp getBaseResp() {
    return baseResp_ == null ? violet.action.common.proto_gen.action.BaseResp.getDefaultInstance() : baseResp_;
  }
  /**
   * <code>.BaseResp baseResp = 255;</code>
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
    com.google.protobuf.GeneratedMessageV3
      .serializeLongMapTo(
        output,
        internalGetIsFollowing(),
        IsFollowingDefaultEntryHolder.defaultEntry,
        1);
    com.google.protobuf.GeneratedMessageV3
      .serializeLongMapTo(
        output,
        internalGetIsFollower(),
        IsFollowerDefaultEntryHolder.defaultEntry,
        2);
    if (baseResp_ != null) {
      output.writeMessage(255, getBaseResp());
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (java.util.Map.Entry<java.lang.Long, java.lang.Boolean> entry
         : internalGetIsFollowing().getMap().entrySet()) {
      com.google.protobuf.MapEntry<java.lang.Long, java.lang.Boolean>
      isFollowing__ = IsFollowingDefaultEntryHolder.defaultEntry.newBuilderForType()
          .setKey(entry.getKey())
          .setValue(entry.getValue())
          .build();
      size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, isFollowing__);
    }
    for (java.util.Map.Entry<java.lang.Long, java.lang.Boolean> entry
         : internalGetIsFollower().getMap().entrySet()) {
      com.google.protobuf.MapEntry<java.lang.Long, java.lang.Boolean>
      isFollower__ = IsFollowerDefaultEntryHolder.defaultEntry.newBuilderForType()
          .setKey(entry.getKey())
          .setValue(entry.getValue())
          .build();
      size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, isFollower__);
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
    if (!(obj instanceof violet.action.common.proto_gen.action.MIsFollowResponse)) {
      return super.equals(obj);
    }
    violet.action.common.proto_gen.action.MIsFollowResponse other = (violet.action.common.proto_gen.action.MIsFollowResponse) obj;

    boolean result = true;
    result = result && internalGetIsFollowing().equals(
        other.internalGetIsFollowing());
    result = result && internalGetIsFollower().equals(
        other.internalGetIsFollower());
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
    if (!internalGetIsFollowing().getMap().isEmpty()) {
      hash = (37 * hash) + IS_FOLLOWING_FIELD_NUMBER;
      hash = (53 * hash) + internalGetIsFollowing().hashCode();
    }
    if (!internalGetIsFollower().getMap().isEmpty()) {
      hash = (37 * hash) + IS_FOLLOWER_FIELD_NUMBER;
      hash = (53 * hash) + internalGetIsFollower().hashCode();
    }
    if (hasBaseResp()) {
      hash = (37 * hash) + BASERESP_FIELD_NUMBER;
      hash = (53 * hash) + getBaseResp().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static violet.action.common.proto_gen.action.MIsFollowResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static violet.action.common.proto_gen.action.MIsFollowResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.MIsFollowResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static violet.action.common.proto_gen.action.MIsFollowResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.MIsFollowResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static violet.action.common.proto_gen.action.MIsFollowResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.MIsFollowResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static violet.action.common.proto_gen.action.MIsFollowResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.MIsFollowResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static violet.action.common.proto_gen.action.MIsFollowResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static violet.action.common.proto_gen.action.MIsFollowResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static violet.action.common.proto_gen.action.MIsFollowResponse parseFrom(
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
  public static Builder newBuilder(violet.action.common.proto_gen.action.MIsFollowResponse prototype) {
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
   * Protobuf type {@code MIsFollowResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:MIsFollowResponse)
      violet.action.common.proto_gen.action.MIsFollowResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return violet.action.common.proto_gen.action.Action.internal_static_MIsFollowResponse_descriptor;
    }

    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMapField(
        int number) {
      switch (number) {
        case 1:
          return internalGetIsFollowing();
        case 2:
          return internalGetIsFollower();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMutableMapField(
        int number) {
      switch (number) {
        case 1:
          return internalGetMutableIsFollowing();
        case 2:
          return internalGetMutableIsFollower();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return violet.action.common.proto_gen.action.Action.internal_static_MIsFollowResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              violet.action.common.proto_gen.action.MIsFollowResponse.class, violet.action.common.proto_gen.action.MIsFollowResponse.Builder.class);
    }

    // Construct using violet.action.common.proto_gen.action.MIsFollowResponse.newBuilder()
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
      internalGetMutableIsFollowing().clear();
      internalGetMutableIsFollower().clear();
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
      return violet.action.common.proto_gen.action.Action.internal_static_MIsFollowResponse_descriptor;
    }

    public violet.action.common.proto_gen.action.MIsFollowResponse getDefaultInstanceForType() {
      return violet.action.common.proto_gen.action.MIsFollowResponse.getDefaultInstance();
    }

    public violet.action.common.proto_gen.action.MIsFollowResponse build() {
      violet.action.common.proto_gen.action.MIsFollowResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public violet.action.common.proto_gen.action.MIsFollowResponse buildPartial() {
      violet.action.common.proto_gen.action.MIsFollowResponse result = new violet.action.common.proto_gen.action.MIsFollowResponse(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      result.isFollowing_ = internalGetIsFollowing();
      result.isFollowing_.makeImmutable();
      result.isFollower_ = internalGetIsFollower();
      result.isFollower_.makeImmutable();
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
      if (other instanceof violet.action.common.proto_gen.action.MIsFollowResponse) {
        return mergeFrom((violet.action.common.proto_gen.action.MIsFollowResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(violet.action.common.proto_gen.action.MIsFollowResponse other) {
      if (other == violet.action.common.proto_gen.action.MIsFollowResponse.getDefaultInstance()) return this;
      internalGetMutableIsFollowing().mergeFrom(
          other.internalGetIsFollowing());
      internalGetMutableIsFollower().mergeFrom(
          other.internalGetIsFollower());
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
      violet.action.common.proto_gen.action.MIsFollowResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (violet.action.common.proto_gen.action.MIsFollowResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private com.google.protobuf.MapField<
        java.lang.Long, java.lang.Boolean> isFollowing_;
    private com.google.protobuf.MapField<java.lang.Long, java.lang.Boolean>
    internalGetIsFollowing() {
      if (isFollowing_ == null) {
        return com.google.protobuf.MapField.emptyMapField(
            IsFollowingDefaultEntryHolder.defaultEntry);
      }
      return isFollowing_;
    }
    private com.google.protobuf.MapField<java.lang.Long, java.lang.Boolean>
    internalGetMutableIsFollowing() {
      onChanged();;
      if (isFollowing_ == null) {
        isFollowing_ = com.google.protobuf.MapField.newMapField(
            IsFollowingDefaultEntryHolder.defaultEntry);
      }
      if (!isFollowing_.isMutable()) {
        isFollowing_ = isFollowing_.copy();
      }
      return isFollowing_;
    }

    public int getIsFollowingCount() {
      return internalGetIsFollowing().getMap().size();
    }
    /**
     * <code>map&lt;int64, bool&gt; is_following = 1;</code>
     */

    public boolean containsIsFollowing(
        long key) {
      
      return internalGetIsFollowing().getMap().containsKey(key);
    }
    /**
     * Use {@link #getIsFollowingMap()} instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.Long, java.lang.Boolean> getIsFollowing() {
      return getIsFollowingMap();
    }
    /**
     * <code>map&lt;int64, bool&gt; is_following = 1;</code>
     */

    public java.util.Map<java.lang.Long, java.lang.Boolean> getIsFollowingMap() {
      return internalGetIsFollowing().getMap();
    }
    /**
     * <code>map&lt;int64, bool&gt; is_following = 1;</code>
     */

    public boolean getIsFollowingOrDefault(
        long key,
        boolean defaultValue) {
      
      java.util.Map<java.lang.Long, java.lang.Boolean> map =
          internalGetIsFollowing().getMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }
    /**
     * <code>map&lt;int64, bool&gt; is_following = 1;</code>
     */

    public boolean getIsFollowingOrThrow(
        long key) {
      
      java.util.Map<java.lang.Long, java.lang.Boolean> map =
          internalGetIsFollowing().getMap();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
    }

    public Builder clearIsFollowing() {
      internalGetMutableIsFollowing().getMutableMap()
          .clear();
      return this;
    }
    /**
     * <code>map&lt;int64, bool&gt; is_following = 1;</code>
     */

    public Builder removeIsFollowing(
        long key) {
      
      internalGetMutableIsFollowing().getMutableMap()
          .remove(key);
      return this;
    }
    /**
     * Use alternate mutation accessors instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.Long, java.lang.Boolean>
    getMutableIsFollowing() {
      return internalGetMutableIsFollowing().getMutableMap();
    }
    /**
     * <code>map&lt;int64, bool&gt; is_following = 1;</code>
     */
    public Builder putIsFollowing(
        long key,
        boolean value) {
      
      
      internalGetMutableIsFollowing().getMutableMap()
          .put(key, value);
      return this;
    }
    /**
     * <code>map&lt;int64, bool&gt; is_following = 1;</code>
     */

    public Builder putAllIsFollowing(
        java.util.Map<java.lang.Long, java.lang.Boolean> values) {
      internalGetMutableIsFollowing().getMutableMap()
          .putAll(values);
      return this;
    }

    private com.google.protobuf.MapField<
        java.lang.Long, java.lang.Boolean> isFollower_;
    private com.google.protobuf.MapField<java.lang.Long, java.lang.Boolean>
    internalGetIsFollower() {
      if (isFollower_ == null) {
        return com.google.protobuf.MapField.emptyMapField(
            IsFollowerDefaultEntryHolder.defaultEntry);
      }
      return isFollower_;
    }
    private com.google.protobuf.MapField<java.lang.Long, java.lang.Boolean>
    internalGetMutableIsFollower() {
      onChanged();;
      if (isFollower_ == null) {
        isFollower_ = com.google.protobuf.MapField.newMapField(
            IsFollowerDefaultEntryHolder.defaultEntry);
      }
      if (!isFollower_.isMutable()) {
        isFollower_ = isFollower_.copy();
      }
      return isFollower_;
    }

    public int getIsFollowerCount() {
      return internalGetIsFollower().getMap().size();
    }
    /**
     * <code>map&lt;int64, bool&gt; is_follower = 2;</code>
     */

    public boolean containsIsFollower(
        long key) {
      
      return internalGetIsFollower().getMap().containsKey(key);
    }
    /**
     * Use {@link #getIsFollowerMap()} instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.Long, java.lang.Boolean> getIsFollower() {
      return getIsFollowerMap();
    }
    /**
     * <code>map&lt;int64, bool&gt; is_follower = 2;</code>
     */

    public java.util.Map<java.lang.Long, java.lang.Boolean> getIsFollowerMap() {
      return internalGetIsFollower().getMap();
    }
    /**
     * <code>map&lt;int64, bool&gt; is_follower = 2;</code>
     */

    public boolean getIsFollowerOrDefault(
        long key,
        boolean defaultValue) {
      
      java.util.Map<java.lang.Long, java.lang.Boolean> map =
          internalGetIsFollower().getMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }
    /**
     * <code>map&lt;int64, bool&gt; is_follower = 2;</code>
     */

    public boolean getIsFollowerOrThrow(
        long key) {
      
      java.util.Map<java.lang.Long, java.lang.Boolean> map =
          internalGetIsFollower().getMap();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
    }

    public Builder clearIsFollower() {
      internalGetMutableIsFollower().getMutableMap()
          .clear();
      return this;
    }
    /**
     * <code>map&lt;int64, bool&gt; is_follower = 2;</code>
     */

    public Builder removeIsFollower(
        long key) {
      
      internalGetMutableIsFollower().getMutableMap()
          .remove(key);
      return this;
    }
    /**
     * Use alternate mutation accessors instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.Long, java.lang.Boolean>
    getMutableIsFollower() {
      return internalGetMutableIsFollower().getMutableMap();
    }
    /**
     * <code>map&lt;int64, bool&gt; is_follower = 2;</code>
     */
    public Builder putIsFollower(
        long key,
        boolean value) {
      
      
      internalGetMutableIsFollower().getMutableMap()
          .put(key, value);
      return this;
    }
    /**
     * <code>map&lt;int64, bool&gt; is_follower = 2;</code>
     */

    public Builder putAllIsFollower(
        java.util.Map<java.lang.Long, java.lang.Boolean> values) {
      internalGetMutableIsFollower().getMutableMap()
          .putAll(values);
      return this;
    }

    private violet.action.common.proto_gen.action.BaseResp baseResp_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        violet.action.common.proto_gen.action.BaseResp, violet.action.common.proto_gen.action.BaseResp.Builder, violet.action.common.proto_gen.action.BaseRespOrBuilder> baseRespBuilder_;
    /**
     * <code>.BaseResp baseResp = 255;</code>
     */
    public boolean hasBaseResp() {
      return baseRespBuilder_ != null || baseResp_ != null;
    }
    /**
     * <code>.BaseResp baseResp = 255;</code>
     */
    public violet.action.common.proto_gen.action.BaseResp getBaseResp() {
      if (baseRespBuilder_ == null) {
        return baseResp_ == null ? violet.action.common.proto_gen.action.BaseResp.getDefaultInstance() : baseResp_;
      } else {
        return baseRespBuilder_.getMessage();
      }
    }
    /**
     * <code>.BaseResp baseResp = 255;</code>
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
     * <code>.BaseResp baseResp = 255;</code>
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
     * <code>.BaseResp baseResp = 255;</code>
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
     * <code>.BaseResp baseResp = 255;</code>
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
     * <code>.BaseResp baseResp = 255;</code>
     */
    public violet.action.common.proto_gen.action.BaseResp.Builder getBaseRespBuilder() {
      
      onChanged();
      return getBaseRespFieldBuilder().getBuilder();
    }
    /**
     * <code>.BaseResp baseResp = 255;</code>
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
     * <code>.BaseResp baseResp = 255;</code>
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


    // @@protoc_insertion_point(builder_scope:MIsFollowResponse)
  }

  // @@protoc_insertion_point(class_scope:MIsFollowResponse)
  private static final violet.action.common.proto_gen.action.MIsFollowResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new violet.action.common.proto_gen.action.MIsFollowResponse();
  }

  public static violet.action.common.proto_gen.action.MIsFollowResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<MIsFollowResponse>
      PARSER = new com.google.protobuf.AbstractParser<MIsFollowResponse>() {
    public MIsFollowResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new MIsFollowResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<MIsFollowResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<MIsFollowResponse> getParserForType() {
    return PARSER;
  }

  public violet.action.common.proto_gen.action.MIsFollowResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

