package com.ssafy.myfarm.util.chat.consumer;

import com.ssafy.myfarm.domain.chat.Message;
import com.ssafy.myfarm.repository.MessageRepository;
import com.ssafy.myfarm.util.chat.constants.KafkaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    MessageRepository messageRepository;

    @KafkaListener(
            topics = KafkaConstants.KAFKA_TOPIC,
            groupId = KafkaConstants.GROUP_ID
    )
    public void listen(Message message) {
        Message saveMessage = messageRepository.save(message);
        System.out.println("sending via kafka listener..");
        System.out.println("/topic/group/" + saveMessage.getRoomId());
        template.convertAndSend("/topic/group/" + saveMessage.getRoomId(), saveMessage);
    }
}
