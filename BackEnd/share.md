#1 백승범 01 13  
docker를 통해 mysql 컨테이너 만듦.

```
docker run -d -p 3307:3306 -e MYSQL_ROOT_PASSWORD=1234 --name farm-mysql mysql
docker exec -it farm-mysql bash
mysql -u root -p
Enter passowrd : 1234
mysql> create database farm;
```

#2 백승범 01 28  
mysql 컨테이너 안에 userdb,plantdb,notifydb,chatdb,calendardb,boardb 를 추가해줌.
kafka 에는 article, calendar, message, room, diary, plant, follow, user 토픽을 생성해줘야함.
#3 백승범 01 28  
notify user plant chat calendar board 순으로 MSA 실행해야 FeignClient 관련 오류 안뜸.  
#4 백승범 01 28  
docker를 통해 bus-refresh를 위한 rabbitmq 컨테이너를 만듦.

```
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 --restart=unless-stopped rabbitmq:management
```

#5 백승범 01 28
docker를 통해 분산 추적 모니터링을 위한 zipkin 컨테이너를 만듦.

```
docker run -d -p 9411:9411 openzipkin/zipkin
```

#5 백승범 01 29

```
1. Kafka와 Zookeeper, mysql를 같이 실행하는 docker-compose.yml 파일을 실행 / `docker-compose up -d`
2. mysql의 내부에 farm, user, notify, plant, calendar, char, board db 생성 / 위의 mysql container 참고
2. kafka container에 들어가 `/opt/kafka_2.13-2.8.1` 경로에 connectors dir 생성
3. connectors dir에 jdbc-connector.zip 전송후 압축해제 `docker cp confluentinc-kafka-connect-jdbc-10.6.0.zip kafka:/opt/kafka_2.13-2.8.1/connectors/`
3. kafka container 내부에서 apt-get update 후 apt-get 으로 unzip, vim 설치
4. `/opt/kafka_2.13-2.8.1/config`의 connect-distributed.properties 파일 맨 밑에 `plugin.path=/opt/kafka_2.13-2.8.1/connectors`추가
5. `connect-distributed.sh /opt/kafka/config/connect-distributed.properties`로 kafka connect 실행
7. `curl --location --request GET 'localhost:8083/connector-plugins'` 로 jdbcSinkConnector가 커넥트에 들어갔는지 확인
8. `docker cp mysql-connector-java-8.0.27.jar kafka:/opt/kafka_2.13-2.8.1/connectors/confluentinc-kafka-connect-jdbc-10.6.0/lib/`를 통해 mysql driver를 전송
```

Topic 생성 명령어

```
bin/kafka-topics.sh \
      --create \
      --bootstrap-server 127.0.0.1:9092 \
      --partitions 3 \
      --replication-factor 1 \
      --config retention.ms=60000 \
      --topic user
```

커넥터 생성 명령어

```
echo '

{

"name":"user-sink-connector",

"config":{

"connector.class":"io.confluent.connect.jdbc.JdbcSinkConnector",

"connection.url":"jdbc:mysql://mysql:3306/userdb",

"connection.user":"root",

"connection.password":"1234",

"auto.create":"true",

"auto.evolve":"true",

"delete.enabled":"false",

"tasks.max":"1",

"topics":"user"

}

}

'| curl -X POST -d @- http://localhost:8083/connectors --header "content-Type:application/json"
```

#6 백승범 02 02
`docker run -d -p 6379:6379 --name redis redis` 를 통해 redis container 생성

#7 백승범 02 04
`docker run -d -p 27017:27017 --name mongo -d mongo` 를 통해 mongodb container 생성

#8 백승범 02 05
wurstmeister kafka는 `connect-distributed.properties` 에 `topic.creation.enable=true` 값을 넣어줘야 source-connector 사용시 자동 topic 생성이 가능해짐.

```
curl --location --request POST 'http://localhost:8083/connectors' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "userdb-source-connector",
    "config": {
        "connector.class" : "io.confluent.connect.jdbc.JdbcSourceConnector",
        "connection.url" : "jdbc:mysql://mysql:3306/userdb",
        "connection.user" : "root",
        "connection.password" : "1234",
        "topic.prefix" : "userdb-",
        "poll.interval.ms" : 2000,
        "table.whitelist" : "user,user_badge,recommend,follow,badge",
        "mode" : "timestamp",
        "timestamp.column.name" : "created_date",
        "topic.creation.default.replication.factor" : 1,
        "topic.creation.default.partitions" : 3
    }
}'
```
