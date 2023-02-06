package com.ssafy.maryfarmconsumer.kafka.consumer.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.maryfarmconsumer.kafka.consumer.board.DetailArticleView.DetailArticleDTO;
import com.ssafy.maryfarmconsumer.kafka.consumer.board.TotalArticleView.ArticleDTO;
import com.ssafy.maryfarmconsumer.kafka.consumer.board.TotalArticleView.SearchArticleByTypeDTO;
import com.ssafy.maryfarmconsumer.mongo.MongoCRUD;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class BoardListener {
    private final MongoCRUD mongoCRUD;
    @KafkaListener(
            topics = "boarddb-article",
            groupId = "articleGroupByType"
    )
    public void articleGroupByType_Listen(String message) {
        log.info("Kafka Message: ->" + message);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(message, new TypeReference<Map<Object, Object>>() {});
            log.info(map.toString());
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        Map<Object, Object> payload = (Map<Object, Object>) map.get("payload");
        String type = (String) payload.get("type");
        List<Document> documents = mongoCRUD.findData("type", type, "articleGroupByType");
        ArticleDTO articleDTO = new ArticleDTO();
        fillArticleDTO(articleDTO,payload);
        if(documents.isEmpty()) {
            List<ArticleDTO> list = new ArrayList<>();
            list.add(articleDTO);
            mongoCRUD.saveData(new SearchArticleByTypeDTO(type, list),"articleGroupByType");
        } else {
            Document document = documents.get(0);
            List<ArticleDTO> list = (List<ArticleDTO>) document.get("articles");
            list.add(articleDTO);
            mongoCRUD.removeData("type",type,"articleGroupByType");
            mongoCRUD.saveData(new SearchArticleByTypeDTO(type, list),"articleGroupByType");
        }
    }

    @KafkaListener(
            topics = "boarddb-article",
            groupId = "detailArticle"
    )
    public void detailArticle_Listen(String message) {
        log.info("Kafka Message: ->" + message);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(message, new TypeReference<Map<Object, Object>>() {
            });
            log.info(map.toString());
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        Map<Object, Object> payload = (Map<Object, Object>) map.get("payload");
        DetailArticleDTO detailArticleDTO = new DetailArticleDTO();
        fillDetailArticleDTO(detailArticleDTO, payload);
        mongoCRUD.saveData(detailArticleDTO, "detailArticle");
    }

    private void fillDetailArticleDTO(DetailArticleDTO detailArticleDTO, Map<Object, Object> payload) {
        detailArticleDTO.setArticleId((String) payload.get("article_id"));
        detailArticleDTO.setUserId((String) payload.get("user_id"));
        detailArticleDTO.setUserName((String) payload.get("user_name"));
        detailArticleDTO.setType((String) payload.get("type"));
        detailArticleDTO.setTitle((String) payload.get("title"));
        detailArticleDTO.setContent((String) payload.get("content"));
        detailArticleDTO.setViews(0);
        detailArticleDTO.setLikes(0);
        detailArticleDTO.setCommentCount(0);
        LocalDateTime createdDate = LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) payload.get("created_date")), TimeZone.getDefault().toZoneId());
        LocalDateTime lastModifiedDate = LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) payload.get("last_modified_date")), TimeZone.getDefault().toZoneId());
        detailArticleDTO.setCreatedDate(createdDate);
        detailArticleDTO.setLastModifiedDate(lastModifiedDate);
    }

    private void fillArticleDTO(ArticleDTO articleDTO, Map<Object, Object> payload) {
        articleDTO.setArticleId((String) payload.get("article_id"));
        articleDTO.setUserId((String) payload.get("user_id"));
        articleDTO.setUserName((String) payload.get("user_name"));
        articleDTO.setTitle((String) payload.get("title"));
        articleDTO.setContent((String) payload.get("content"));
        LocalDateTime createdDate = LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) payload.get("created_date")), TimeZone.getDefault().toZoneId());
        LocalDateTime lastModifiedDate = LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) payload.get("last_modified_date")), TimeZone.getDefault().toZoneId());
        articleDTO.setCreatedDate(createdDate);
        articleDTO.setLastModifiedDate(lastModifiedDate);
    }


}
