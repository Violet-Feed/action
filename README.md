# Violet Action Service

本仓库是“用户行为与关系”服务，提供用户注册登录、关系链路（关注/粉丝/互关）、互动行为（点赞、评论、回复）、行为事件投递与通知等能力。核心关系与互动数据存储在 Nebula 图数据库，配合多级缓存与消息队列，支撑高并发访问与热点用户查询。

## 业务架构
- 用户与账号：注册、登录、批量用户信息查询，基于 Milvus 的用户名向量搜索。
- 关系链路：关注/取关、关注/粉丝/互关列表与计数、多用户关系判定（mIsFollowing/mIsFollower）。
- 互动行为：对创作或评论点赞、取消点赞；评论与回复创建；评论列表、回复列表与计数。
- 行为事件：点击、点赞、评论等事件异步写入 Kafka，并由消费者生成通知。
- 外部协作：通过 gRPC 与 IM 服务（通知）、AIGC 服务（创作信息）协作。

### 缓存与接口策略

**Caffeine（热点用户本地缓存）**  
following_list, follower_list, following_map, following_count, follower_count, friend_count, hit

**Redis（分布式缓存）**  
following_list, follower_list, friend_list, following_map, following_count, follower_count

**接口逻辑摘要（含阈值/命中条件）**  
- following_list：热点用户命中 Caffeine；非热点 Redis 未命中时回源 Nebula。
- follower_list：热点用户命中 Caffeine；非热点 Caffeine 未命中 -> Redis -> Nebula。
- friend_list：Redis 未命中 -> Nebula。
- follow_count：Caffeine 未命中 -> Nebula/count_service。
- friend_count：Caffeine 未命中 -> list(Redis -> Nebula)。
- is_following：热点用户 Caffeine；非热点先取 following_count，然后：
  - count <= 2000 或 查询 size > 20：走 list(Redis -> Nebula)。
  - 否则走 hash(Redis -> Nebula)。
- is_follower：先取 follower_count，然后：
  - count <= 1000 或 (count <= 5000 且 size > 20)：走 list(Redis -> Nebula)。
  - 否则走 hash：
    - 热点用户：Caffeine hash。
    - 非热点用户：基于 hit 统计，命中则 Caffeine hash，未命中则 Redis + Nebula 回源并回填。
- is_friend：Nebula。
