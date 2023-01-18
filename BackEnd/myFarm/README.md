# 마리팜 Backend

<!-- 필수 항목 -->

## 소개

My Little Farm 프로젝트 백엔드 버전 및 설명 스크립트

<br>
<!-- 필수 항목 -->

## 기술스택 및 라이브러리

| Project      | Version | Description |
| -------      | ------- | ----------- |
| Java         | 1.8     |             |
| Spring Boot  | 1.8     |             |
| Tomcat       | 1.8     |             |
| Gradle       | x.x.x   | Build Tool  |
| MySQL        | x.x.x   |             |
|              |         |             |

<!-- 필수 항목 -->

## 개발 환경 구성

Windows 기준 개발 환경 구성 설명

1. OpenJDK 설치
   1. JDK 다운로드 사이트에서 1.8.x 설치 파일 다운로드 및 실행
      - Zulu OpenJDK: https://www.azul.com/downloads/?version=java-8-lts&package=jdk
      - OJDK Build: https://github.com/ojdkbuild/ojdkbuild
   2. 설치 후 명령 프롬프트(cmd) 확인
      ```
      > java -version
      ```
      출력 예)
      ```
      openjdk version "1.8.0_192"
      OpenJDK Runtime Environment (Zulu 8.33.0.1-win64) (build 1.8.0_192-b01)
      OpenJDK 64-Bit Server VM (Zulu 8.33.0.1-win64) (build 25.192-b01, mixed mode)
      ```

2. Docker 및 데이터베이스 구성 *(이미 설치되어 있거나 원격 DB를 사용하는 경우 설치 부분 생략)*
   
   1. Docker 사이트를 참조하여 Docker For Windows 설치
   
      - https://docs.docker.com/docker-for-windows/install/
   
   2. MySQL 설치
      - cmd에서 docker run 커맨드 실행
         ```
         > docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=1234 --name farm-mysql mysql
         ```
      
   3. DB 및 계정 생성
      - cmd에서 docker exec 커맨드 실행
         ```
         > docker exec -it farm-mysql mysql -u root -p
         ```
      - root 비밀번호 입력
         ```
         > Enter passowrd : 1234
         ```
      - DB 생성
         ```sql
         mysql> create database IF NOT EXISTS `ssafy_sns`;
         ```

4. 프로젝트 다운로드 및 설정

   1. 프로젝트 다운로드
      ```
      git clone <repo URL>
      ```

   4. src/main/resources/application.properties 수정

      ```
      spring.datasource.username=<사용자 계정> -> root
      spring.datasource.password=<비밀번호> -> 1234
   ``` 
   

## 디렉토리 구조

```
.
└─src
    ├─main
```

