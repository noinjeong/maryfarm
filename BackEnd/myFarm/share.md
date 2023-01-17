#1 백승범 01 13  
docker를 통해 mysql 컨테이너 만듦.
````
docker run -d -p 3307:3306 -e MYSQL_ROOT_PASSWORD=1234 --name farm-mysql mysql
docker exec -it farm-mysql bash
mysql -u root -p
Enter passowrd : 1234
mysql> create database farm;
````
