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
