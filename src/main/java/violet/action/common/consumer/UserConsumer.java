package violet.action.common.consumer;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.milvus.v2.client.MilvusClientV2;
import io.milvus.v2.service.vector.request.InsertReq;
import io.milvus.v2.service.vector.response.InsertResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import violet.action.common.mapper.RelationMapper;
import violet.action.common.pojo.User;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class UserConsumer {
    @Autowired
    private MilvusClientV2 milvusClient;
    @Autowired
    private RelationMapper relationMapper;

    @KafkaListener(topics = "mysql.violet.user", groupId = "action_consumer")
    public void listen(ConsumerRecord<String, String> record) {
        log.info("Received message: key = {}, value = {}, partition = {}, offset = {}", record.key(), record.value(), record.partition(), record.offset());
        JSONObject json = JSON.parseObject(record.value());
        User user = JSON.parseObject(json.getString("payload"), User.class, JSONReader.Feature.SupportSmartMatch);
        log.info("Parsed user: {}", user.getAvatar());
        List<JsonObject> data = Collections.singletonList(new Gson().fromJson(String.format(
                "{\"user_id\": %d, \"username\": %s, \"avatar\": %s}",
                user.getUserId(), user.getUsername(), user.getAvatar().isEmpty() ? null : user.getAvatar()
        ), JsonObject.class));
        InsertReq insertReq = InsertReq.builder()
                .collectionName("user")
                .data(data)
                .build();
        InsertResp insertResp = milvusClient.insert(insertReq);
        log.info("Inserted user_id {} into Milvus with cnt: {}", user.getUserId(), insertResp.getInsertCnt());

        //按理应该放在另一个消费者组
        relationMapper.createUser(user);
    }
}
