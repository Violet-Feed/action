// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/im.proto

package violet.action.common.proto_gen.im;

public interface AddConversationMembersRequestOrBuilder extends
        // @@protoc_insertion_point(interface_extends:im.AddConversationMembersRequest)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 con_short_id = 1;</code>
     */
    long getConShortId();

    /**
     * <code>string con_id = 2;</code>
     */
    java.lang.String getConId();

    /**
     * <code>string con_id = 2;</code>
     */
    com.google.protobuf.ByteString
    getConIdBytes();

    /**
     * <code>repeated int64 members = 3;</code>
     */
    java.util.List<java.lang.Long> getMembersList();

    /**
     * <code>repeated int64 members = 3;</code>
     */
    int getMembersCount();

    /**
     * <code>repeated int64 members = 3;</code>
     */
    long getMembers(int index);

    /**
     * <code>int64 operator = 4;</code>
     */
    long getOperator();
}
