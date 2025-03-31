// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/action.proto

package violet.action.common.proto_gen.action;

public interface MGetFollowCountResponseOrBuilder extends
        // @@protoc_insertion_point(interface_extends:action.MGetFollowCountResponse)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <code>map&lt;int64, int64&gt; following_count = 1;</code>
     */
    int getFollowingCountCount();

    /**
     * <code>map&lt;int64, int64&gt; following_count = 1;</code>
     */
    boolean containsFollowingCount(
            long key);

    /**
     * Use {@link #getFollowingCountMap()} instead.
     */
    @java.lang.Deprecated
    java.util.Map<java.lang.Long, java.lang.Long>
    getFollowingCount();

    /**
     * <code>map&lt;int64, int64&gt; following_count = 1;</code>
     */
    java.util.Map<java.lang.Long, java.lang.Long>
    getFollowingCountMap();

    /**
     * <code>map&lt;int64, int64&gt; following_count = 1;</code>
     */

    long getFollowingCountOrDefault(
            long key,
            long defaultValue);

    /**
     * <code>map&lt;int64, int64&gt; following_count = 1;</code>
     */

    long getFollowingCountOrThrow(
            long key);

    /**
     * <code>map&lt;int64, int64&gt; follower_count = 2;</code>
     */
    int getFollowerCountCount();

    /**
     * <code>map&lt;int64, int64&gt; follower_count = 2;</code>
     */
    boolean containsFollowerCount(
            long key);

    /**
     * Use {@link #getFollowerCountMap()} instead.
     */
    @java.lang.Deprecated
    java.util.Map<java.lang.Long, java.lang.Long>
    getFollowerCount();

    /**
     * <code>map&lt;int64, int64&gt; follower_count = 2;</code>
     */
    java.util.Map<java.lang.Long, java.lang.Long>
    getFollowerCountMap();

    /**
     * <code>map&lt;int64, int64&gt; follower_count = 2;</code>
     */

    long getFollowerCountOrDefault(
            long key,
            long defaultValue);

    /**
     * <code>map&lt;int64, int64&gt; follower_count = 2;</code>
     */

    long getFollowerCountOrThrow(
            long key);

    /**
     * <code>map&lt;int64, int64&gt; friend_count = 3;</code>
     */
    int getFriendCountCount();

    /**
     * <code>map&lt;int64, int64&gt; friend_count = 3;</code>
     */
    boolean containsFriendCount(
            long key);

    /**
     * Use {@link #getFriendCountMap()} instead.
     */
    @java.lang.Deprecated
    java.util.Map<java.lang.Long, java.lang.Long>
    getFriendCount();

    /**
     * <code>map&lt;int64, int64&gt; friend_count = 3;</code>
     */
    java.util.Map<java.lang.Long, java.lang.Long>
    getFriendCountMap();

    /**
     * <code>map&lt;int64, int64&gt; friend_count = 3;</code>
     */

    long getFriendCountOrDefault(
            long key,
            long defaultValue);

    /**
     * <code>map&lt;int64, int64&gt; friend_count = 3;</code>
     */

    long getFriendCountOrThrow(
            long key);

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
