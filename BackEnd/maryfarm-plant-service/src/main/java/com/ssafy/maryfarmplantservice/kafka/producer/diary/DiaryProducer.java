<<<<<<< HEAD
<<<<<<<< HEAD:BackEnd/microfarmK/maryfarm-board-service/src/main/java/com/ssafy/maryfarmboardservice/kafka/producer/article/ArticleProducer.java
package com.ssafy.maryfarmboardservice.kafka.producer.article;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.maryfarmboardservice.domain.board.Article;
import com.ssafy.maryfarmboardservice.kafka.dto.Status;
import com.ssafy.maryfarmboardservice.kafka.dto.article.KafkaArticleDTO;
========
=======
>>>>>>> 3413e9b185e651b91a370b6adee82037c2fd68a8
package com.ssafy.maryfarmplantservice.kafka.producer.diary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.maryfarmplantservice.domain.diary.Diary;
import com.ssafy.maryfarmplantservice.kafka.dto.Status;
import com.ssafy.maryfarmplantservice.kafka.dto.diary.KafkaDiaryDTO;
<<<<<<< HEAD
>>>>>>>> back:BackEnd/maryfarm-plant-service/src/main/java/com/ssafy/maryfarmplantservice/kafka/producer/diary/DiaryProducer.java
=======
>>>>>>> 3413e9b185e651b91a370b6adee82037c2fd68a8
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, Diary diary) {
//        KafkaDiaryDTO kafkaDiaryDTO = new KafkaDiaryDTO(status, diary);
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(diary);
        } catch(JsonProcessingException ex) {
            ex.printStackTrace();
        }
        kafkaTemplate.send(topic, jsonInString);
    }
}
