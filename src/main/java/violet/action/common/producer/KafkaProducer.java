package violet.action.common.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message)
                .addCallback(
                        success -> log.info("消息发送成功，topic: {}", success.getRecordMetadata().topic()),
                        failure -> log.error("消息发送失败：" + failure.getMessage())
                );
    }
}
