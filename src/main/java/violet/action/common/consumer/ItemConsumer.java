package violet.action.common.consumer;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import violet.action.common.mapper.EntityMapper;
import violet.action.common.pojo.Entity;
import violet.action.common.proto_gen.action.EntityType;
import violet.action.common.proto_gen.market.ItemInfo;

@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "action", topic = "item")
public class ItemConsumer implements RocketMQListener<String> {
    @Autowired
    private EntityMapper entityMapper;

    @Override
    public void onMessage(String data) {
        log.info("ItemConsumer received message: {}", data);
        ItemInfo itemInfo = JSONObject.parseObject(data, ItemInfo.class);
        entityMapper.save(new Entity(null, EntityType.Item_VALUE, itemInfo.getItemId(), ""));
    }
}
