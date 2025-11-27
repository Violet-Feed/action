**Caffeine：**

热点用户following_list,follower_list,following_map

following_count,follower_count,friend_count,follower_list,following_map,hit

**Redis：**

following_list,follower_list,friend_list,following_map,(following_count,follower_count)

**接口逻辑：**

following_list：热点Caffeine，非热点Redis->Neo4j

follower_list：热点Caffeine，非热点Caffeine->Redis->Neo4j

friend_list：Redis->Neo4j

follow_count：Caffeine->Neo4j/count_service

friend_count：Caffeine->list(Redis->Neo4j)

is_following：热点Caffeine，非热点获取count->获取list(Redis->Neo4j)(count<=2000||size>20)/获取hash(Redis->Neo4j)

is_follower：获取list(Redis->Neo4j)(count<=1000||count<=5000&&size>20)/获取hash(热点Caffeine，非热点Caffeine->list?(
Redis->Neo4j)(hit)/Redis+Neo4j)

is_friend：Neo4j