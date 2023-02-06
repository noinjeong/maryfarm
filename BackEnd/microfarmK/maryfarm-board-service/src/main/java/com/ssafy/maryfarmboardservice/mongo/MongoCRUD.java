package com.ssafy.maryfarmboardservice.mongo;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MongoCRUD {
    private final MongoTemplate mongoTemplate;
    public void removeData(String key, String value, String collectionName) {
        Criteria criteria = new Criteria(key);
        criteria.is(value);
        Query query = new Query(criteria);
        mongoTemplate.remove(query, collectionName);
    }
    public List<Document> findData(String key, String value, String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where(key).is(value));
        return mongoTemplate.find(query, Document.class, collectionName);
    }

    public void saveData(Object o, String collectionName) {
        mongoTemplate.save(o,collectionName);
    }
}
