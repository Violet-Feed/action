// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/im.proto

package violet.action.common.proto_gen.im;

public interface MessageGetByConversationResponseOrBuilder extends
        // @@protoc_insertion_point(interface_extends:im.MessageGetByConversationResponse)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated .im.MessageBody msg_bodies = 1;</code>
     */
    java.util.List<violet.action.common.proto_gen.im.MessageBody>
    getMsgBodiesList();

    /**
     * <code>repeated .im.MessageBody msg_bodies = 1;</code>
     */
    violet.action.common.proto_gen.im.MessageBody getMsgBodies(int index);

    /**
     * <code>repeated .im.MessageBody msg_bodies = 1;</code>
     */
    int getMsgBodiesCount();

    /**
     * <code>repeated .im.MessageBody msg_bodies = 1;</code>
     */
    java.util.List<? extends violet.action.common.proto_gen.im.MessageBodyOrBuilder>
    getMsgBodiesOrBuilderList();

    /**
     * <code>repeated .im.MessageBody msg_bodies = 1;</code>
     */
    violet.action.common.proto_gen.im.MessageBodyOrBuilder getMsgBodiesOrBuilder(
            int index);
}
