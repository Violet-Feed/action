// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/action.proto

package violet.action.common.proto_gen.action;

public interface SearchUsersResponseOrBuilder extends
        // @@protoc_insertion_point(interface_extends:action.SearchUsersResponse)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    java.util.List<violet.action.common.proto_gen.action.UserInfo>
    getUserInfosList();

    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    violet.action.common.proto_gen.action.UserInfo getUserInfos(int index);

    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    int getUserInfosCount();

    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    java.util.List<? extends violet.action.common.proto_gen.action.UserInfoOrBuilder>
    getUserInfosOrBuilderList();

    /**
     * <code>repeated .action.UserInfo user_infos = 1;</code>
     */
    violet.action.common.proto_gen.action.UserInfoOrBuilder getUserInfosOrBuilder(
            int index);

    /**
     * <code>.common.BaseResp baseResp = 255;</code>
     */
    boolean hasBaseResp();

    /**
     * <code>.common.BaseResp baseResp = 255;</code>
     */
    violet.action.common.proto_gen.common.BaseResp getBaseResp();

    /**
     * <code>.common.BaseResp baseResp = 255;</code>
     */
    violet.action.common.proto_gen.common.BaseRespOrBuilder getBaseRespOrBuilder();
}
