# 배포 과정 - ssafy

**SSAFY에서 준 instance → Ubuntu 20.04.5 LTS**

ssh -i I8B308T.pem [ubuntu@i8b308.p.ssafy.io](mailto:ubuntu@i8b308.p.ssafy.io) → ssh로 접근

- host EC2 내부에서 public ip에 대한 정보를 얻기 위한 명령어
    - `dig +short [myip.opendns.com](http://myip.opendns.com/) @resolver1.opendns.com`
        - 3.34.192.6 (EC2 public ip)

[대상 서버의 포트가 열려 있는지 확인하는 3가지 방법 - Google Search](https://www.google.com/search?q=%EB%8C%80%EC%83%81+%EC%84%9C%EB%B2%84%EC%9D%98+%ED%8F%AC%ED%8A%B8%EA%B0%80+%EC%97%B4%EB%A0%A4+%EC%9E%88%EB%8A%94%EC%A7%80+%ED%99%95%EC%9D%B8%ED%95%98%EB%8A%94+3%EA%B0%80%EC%A7%80+%EB%B0%A9%EB%B2%95&oq=%EB%8C%80%EC%83%81+%EC%84%9C%EB%B2%84%EC%9D%98+%ED%8F%AC%ED%8A%B8%EA%B0%80+%EC%97%B4%EB%A0%A4+%EC%9E%88%EB%8A%94%EC%A7%80+%ED%99%95%EC%9D%B8%ED%95%98%EB%8A%94+3%EA%B0%80%EC%A7%80+%EB%B0%A9%EB%B2%95&aqs=chrome..69i57j69i61.543j0j7&sourceid=chrome&ie=UTF-8)

- telnet, ping을 통해서 대상 서버와의 연결을 확인
- telnet 3.34.192.6 22

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/530779f1-c8c2-41fa-90c5-6bc282c0ed25/Untitled.png)

### EC2 초기 설정

- sudo apt update
- sudo apt upgrade
- sudo apt install build-essential

Docker로 Jenkins 설치하기

1. Jenkins 이미지 파일 내려받기(lts 버전)
$ docker pull jenkins/jenkins:lts
2. 내려받아진 이미지 확인
$ docker images
3. Jenkins 이미지를 Container로 실행

```cpp
docker run -d -p 8080:8080 -p 50000:50000 --name jenkins-server --restart=on-failure jenkins/jenkins:lts-jdk11
```

tmux 실행 → [https://freedeveloper.tistory.com/494](https://freedeveloper.tistory.com/494)

```
# 새로운 세션 생성
tmux new -s (session_name)

# 세션 다시 시작하기(다시 불러오기)
tmux attach -t session_number

# 틀 나누기
(ctrl + b) % #좌우로 나누기
(ctrl + b) " #위아래로 나누기

# 틀끼리 이동하기
(ctrl + b) 방향키
(ctrl + b) q
(ctrl + b) o #순서대로 이동
```

Jenkins 접속

1. 브라우저에서 [EC2 인스턴스 URL]:8080으로 접속
2. 암호 입력
/var/lib/jenkins/secrets/initialAdminPassword를 확인해야 하는데 Jenkins Container에 접속하여 얻어오거나 "docker logs jenkins" 명령어를 사용하면 된다.

// Container에 접속하지 않고 확인
$ docker logs jenkins

692b9842499c4c9db1340a7fb16041b7

// Container 접속
$ docker exec -it jenkins bash

// 암호 파일 확인
$ sudo cat /var/lib/jenkins/secrets/initialAdminPassword

계정 생성

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/63300a97-09c7-469f-b1b6-28a19cf2c6c3/Untitled.png)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/cd12202d-543c-4c46-93de-848602f17dce/Untitled.png)

→ jenkins를 8080으로도 열고 9090으로도 열어봤는데, 다 동작하는 걸로 봐선 포트가 네트워크 상에 다 열려 있는 것 같다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/1f1f0d93-abb4-44d8-b07d-1a94a979999b/Untitled.png)

기본적으로 openjdk11이 설치되어 있음.

그래서 jenkins의 global 설정에서 jdk를 설정하지 않아도 된다. 

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a913443b-1a4d-4d96-b981-dd42222aa671/Untitled.png)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/6792a752-ed16-49a2-8a46-420e21ba67b2/Untitled.png)

jenkins-server 내부에 프로젝트 아이템의 결과 폴더가 생성된다.

---

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/80b507d5-4835-43d3-a191-fd57cd0a92d9/Untitled.png)

jenkins-server에서 gitlab plugin를 설치하도록 하자. 

<aside>
⚠️ JDK를 안 깔아줘서 빌드 오류가 발생한다. /bin/java가 없다고 IOException이 발생
docker 에 jenkins를 사용하려면 JVM이 필요한데 이 때 openjdk11, 17버전을 사용한다고 한다. [https://luvstudy.tistory.com/66](https://luvstudy.tistory.com/66)

</aside>

다시 docker image & container 시작

- docker run -d -p 8080:8080 -p 50000:50000 --name jenkins-server --restart=on-failure -v jenkins_home:/var/jenkins_home jenkins/jenkins:lts-jdk11

[젠킨스 jdk 버전 11로 올리는 방법](https://www.blog.ecsimsw.com/entry/%EC%A0%A0%ED%82%A8%EC%8A%A4-jdk-%EB%B2%84%EC%A0%84-11%EB%A1%9C-%EC%98%AC%EB%A6%AC%EB%8A%94-%EB%B0%A9%EB%B2%95)

sudo command가 없다는 에러

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/0ab12001-0fc6-48a2-b0ae-be979b64ea7d/Untitled.png)

**1. docker 컨테이너 접속 / 루트 권한**

```bash
docker exec -itu 0 {container_id} /bin/bash
```

**2. openjdk 11 설치**

```bash
apt-get update
apt-get install openjdk-11-jdk
```

### gradle project 배포

My-Second-Project-Gradle 프로젝트 생성

- jenkins에서 추가로 gitlab plugin을 설치하도록 한다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/b2962a8d-9486-4312-b74a-2cc1837a9d17/Untitled.png)

→ gitlab URL 과 username, password(token) 설정

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f19272ff-7d6e-4421-aab5-36ac7bca252c/Untitled.png)

브랜치는 isak으로 했다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/eb236891-004b-458a-9a7b-060e384f1433/Untitled.png)

→ 일단 gitlab project token을 넣어서 빌드 버튼을 눌러서 진행하도록 함.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/473ee3b0-ab7f-4f9c-b614-f15649c140f0/Untitled.png)

빌드하고 jar 확인!

이번에는 invoke Gradle script로 빌드해보자

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/af3b10e7-89f2-4962-996d-c7c61f9d1caf/Untitled.png)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d2ab4ebd-0799-46cf-b929-f490c2b38b9c/Untitled.png)

빌드 완료 이후에 ssh를 통해서 해당 jar 파일을 docker-server에서 기동해야 한다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/100fc72a-ac27-4549-a8dd-9151444418c2/Untitled.png)

Publish Over SSH 플러그인을 설치한 뒤에 ssh-server를 등록하기 위해서 docker network에서 각 컨테이너의 private ip가 어떻게 되는지 확인

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/cce54f09-db65-4102-8889-ee757694fcc6/Untitled.png)

→ ssh-server로 연결하기 위해서는 host pc의 IP가 필요하다. 현재 ipconfig의 eh0의 inet은 172.26.2.9으로 설정되어 있음.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/4a895574-cd95-4d9f-b4a9-c88d52e8ac2d/Untitled.png)

docker network inspect birdge로 확인한 docker 내부 ip

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/ffb20b8f-c960-47fd-9e94-13401959d3a2/Untitled.png)

build step에서 SSH Publicshers 설정 진행

- 전달할 jar파일을 docker-server로 전송
- 파일 경로를 git repository에서 시작한다고 생각하고 진행
    - jar 파일만 필요하니 앞 폴더명은 지운다.
    - docker-server의 working directory(home)에 전송

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/90bc6052-0c6c-4053-9391-abf4bbced9a3/Untitled.png)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/b2044e3b-ecdc-4ee6-9919-86d2a35e1dec/Untitled.png)

docker-server에서 .jar 파일을 바로 실행해보자!

일단 mysql을 host에서 docker로 실행해야 함. 그러면 back-end에서 설정값을 변경해줘야 한다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/b43132b1-835f-4a30-820a-51e328980413/Untitled.png)

application.yml 파일에서 host ip를 알아내서 해당 포트를 입력해줘라.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c9a44c8a-1c23-49fb-a0e2-b05ebbc9a943/Untitled.png)

그리고 docker-server에 Dockerfile을 하나 생성한다. 여기서 jdk로 *.jar 파일을 실행할거다!

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e32a3a16-199d-4f10-a081-e9f98310087d/Untitled.png)

```bash
FROM anapsix/alpine-java
MAINTAINER isakggong

LABEL org.opencontainers.image.authors="isakggong@gmail.com"

COPY myFarm-0.0.1-SNAPSHOT.jar /home/myFarm-0.0.1-SNAPSHOT.jar

CMD ["java","-jar","/home/myFarm-0.0.1-SNAPSHOT.jar"]
```

→ 이미지 128MB 정도로 가벼움

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/3706b1e4-482d-4c1c-872a-34b026152b23/Untitled.png)

→ docker-server에서 해당 명령어를 실행하기 위해서 필요한 내용이다. 

현재 docker-server는 8081:8080로 포트 포워딩 되어 있으므로 빌드에 성공하면 웹 서버는 8081로 열리게 된다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/72a17040-8ccc-4b85-b6ad-db50b0e8fc32/Untitled.png)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/10c47217-c243-4e27-bef4-33655943cd61/Untitled.png)

→ mysql이 아니라 jenkins 서버에서 시간을 측정하나?

주의) mysql이 끊어지면 spring 서버에서도 데이터를 연결이 끊어진 것으로 본다. 다시 연결하지 않음 (어댑터 패턴을 사용하나?)

[[Spring Boot] MySql 연결 끊김 해결](https://marsland.tistory.com/427)

[[Spring Boot] JavaConfig로 Datasource 설정하기](https://blog.jiniworld.me/69)

→ spring에서 autoConnection 옵션을 true로 하면 된다고 한다. 

### Ansible 서버 붙이기

현재 jenkins - docker(tomcat) 으로 돌아가는 실정

dockerhub

- docker → isakggong/docker:v1
- ansible → isakggong/ansible:v1

docker network inspect bridge 확인

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/8f069159-ac26-4109-a74d-e042703ae876/Untitled.png)

My-Ansible-Project 생성 (기존 docker project를 copy)

- docker-server 정보를 변경하자
- exec command의 내용을 ansible-playbook 명령어로 변경한다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/3bcb724b-38ec-480d-a793-86894fd2e0f6/Untitled.png)

first-devops-playbook.yml

- 컨테이너 중지 - 삭제
- 이미지 삭제 (+none 이미지도 함께 삭제한다)
- /root/Dockerfile을 실행한다.
    - Dockerfile은 openjdk에 대한 이미지
- 생성된 이미지로 컨테이너 생성 (tomcat-server)

```bash
- hosts: all
#   become: true

  tasks:
  - name: stop current running container
    command: docker stop tomcat-server
    ignore_errors: yes

  - name: remove stopped container
    command: docker rm tomcat-server
    ignore_errors: yes

  - name: remove current docker image
    command: docker rmi cicd-project-ansible
    ignore_errors: yes

  - name: remove anonymous docker image
    command: docker rmi $(docker images -f "dangling=true" -q)
    ignore_errors: yes

  - name: build a docker image with deployed war file
    command: docker build . -f Dockerfile -t cicd-project-ansible
    args:
        chdir: /root
  - name: create a container using cicd-project-ansible image
    command: docker run -d -p 8080:8080 --name tomcat-server cicd-project-ansible:latest
```

hosts

- docker-server ip로 변경 (172.17.0.3으로 설정되어 있음)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/1cffa54b-77fb-4674-abe3-0b935898b766/Untitled.png)

docker-server에서 실행되는 것을 볼 수 있음!

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/b7d88369-9806-42c6-808b-7e5df4b9e852/Untitled.png)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/53f86738-1051-4ab0-a584-48b82a537763/Untitled.png)

### docker 이미지 push & pull

ansible-server

- 이미지 업로드

docker-server

- 이미지 다운로드
- 컨테이너 실행

*참고로 docker hub에 올릴 때, `docker login`을 진행해야 한다. id & password 입력

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/ba9f80dc-c4e0-4bed-a591-e392193dd62b/Untitled.png)

**create-cicd-project-image-playbook.yml**

```bash
- hosts: all # hosts: ansible 로 변경
#   become: true

  tasks:
  - name: create a docker image with deployed waf file
    command: docker build -t isakggong/cicd-project-ansible .
    args: 
        chdir: /root
    
  - name: push the image on Docker Hub
    command: docker push isakggong/cicd-project-ansible

  - name: remove the docker image from the ansible server
    command: docker rmi isakggong/cicd-project-ansible  
    ignore_errors: yes

  - name: remove anonymous docker image
    command: docker rmi $(docker images -f "dangling=true" -q)
    ignore_errors: yes
```

**create-cicd-devops-container-playbook.yml**

```bash
- hosts: all # hosts: docker 로 변경
#   become: true  

  tasks:
  - name: stop current running container
    command: docker stop my_cicd_project
    ignore_errors: yes

  - name: remove stopped cotainer
    command: docker rm my_cicd_project
    ignore_errors: yes

  - name: remove current docker image
    command: docker rmi isakggong/cicd-project-ansible
    ignore_errors: yes

  - name: remove anonymous docker image
    command: docker rmi $(docker images -f "dangling=true" -q)
    ignore_errors: yes

  - name: pull the newest docker image from Docker Hub
    command: docker pull isakggong/cicd-project-ansible

  - name: create a container using cicd-project-ansible image
    command: docker run -d --name tomcat-server -p 8080:8080 isakggong/cicd-project-ansible
```

ansible-playbook

1. ansible-server에서 이미지 생성 + docker hub push
2. docker-server에서 이미지 pull + 컨테이너 실행

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e6b00a5b-14e4-4972-ad50-8bafbf7c929c/Untitled.png)

→ 하지만 이미지 생성과 push하는 과정이 계속 빌드되어서 시간이 좀 오래 걸린다. 다만 image를 pull하고 컨테이너를 실행하는 과정은 조금 빠르다?

### jenkins pipeline 구축

My-Second-Pipeline

- ansible-server
    - docker network inspect bridge 확인
        
        ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e1ead301-656d-4fa1-b0fa-e78c28551703/Untitled.png)
        
    - create-cicd-devops-image-playbook.yml
    
    ```bash
    - hosts: ansible
    #   become: true
    
      tasks:
      - name: create a docker image with deployed waf file
        command: docker build -t isakggong/cicd-project-ansible .
        args:
            chdir: /root
    
      - name: push the image on Docker Hub
        command: docker push isakggong/cicd-project-ansible
    
      - name: remove the docker image from the ansible server
        command: docker rmi isakggong/cicd-project-ansible
        ignore_errors: yes
    
      - name: remove anonymous docker image
        command: docker rmi $(docker images -f "dangling=true" -q)
        ignore_errors: yes
    ```
    
    - create-cicd-devops-container-playbook.yml
    
    ```bash
    - hosts: docker
    #   become: true
    
      tasks:
      - name: stop current running container
        command: docker stop tomcat-server
        ignore_errors: yes
    
      - name: remove stopped cotainer
        command: docker rm tomcat-server
        ignore_errors: yes
    
      - name: remove current docker image
        command: docker rmi isakggong/cicd-project-ansible
        ignore_errors: yes
    
      - name: remove anonymous docker image
        command: docker rmi $(docker images -f "dangling=true" -q)
        ignore_errors: yes
    
      - name: pull the newest docker image from Docker Hub
        command: docker pull isakggong/cicd-project-ansible
    
      - name: create a container using cicd-project-ansible image
        command: docker run -d --name tomcat-server -p 8080:8080 isakggong/cicd-project-ansible
    ```
    

- ansible-server와 docker-server
    - docker running 중인지 확인
    

```bash
pipeline {
    agent any
    tools{
        gradle 'gradle-7.6'
    }
    stages {
        stage('Gitlab clone'){
            steps{
                git branch: 'isak', credentialsId: 'gitlab-personal-token', url: 'https://lab.ssafy.com/s08-webmobile2-sub2/S08P12B308.git'
                sh "chmod +x -R ${env.WORKSPACE}"
            }
        }
        stage('build') {
            steps {
                sh '''
                    echo build start
                    cd BackEnd/myFarm
                    gradle clean build --exclude-task test
                '''
                echo "Compiled successfully!";
            }
        }
        stage('ssh publisher'){
            steps {
                sshPublisher(publishers: [sshPublisherDesc(configName: 'ansible-server', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: '''ansible-playbook -i hosts create-cicd-devops-image-playbook.yml
ansible-playbook -i hosts create-cicd-devops-container-playbook.yml''', execTimeout: 300000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '.', remoteDirectorySDF: false, removePrefix: 'BackEnd/myFarm/build/libs', sourceFiles: 'BackEnd/myFarm/build/libs/*.jar')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
            }
        }

        // stage('JUnit') {
        //     steps {
        //         echo "JUnit passed successfully!";
        //         sh './unit.sh'
        //     }
        // }

        // stage('Code Analysis') {
        //     steps {
        //         echo "Code Analysis completed successfully!";
        //         sh './quality.sh'
        //     }
        // }

        // stage('Deploy') {
        //     steps {
        //         echo "Deployed successfully!";
        //         sh './deploy.sh'
        //     }
        // }
    }

    post {
      always {
        echo "This will always run"
      }
      success {
        echo "This will run when the run finished successfully"
      }
      failure {
        echo "This will run if failed"
      }
      unstable {
        echo "This will run when the run was marked as unstable"
      }
      changed {
        echo "This will run when the state of the pipeline has changed"
      }
    }
}
```

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/7009c095-932f-4949-98e3-01e80f190078/Untitled.png)

→ 파이프라인 빌드 성공! 웹 서버도 잘 동작하고 있음.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f1b9ff0c-3d69-4d44-ac68-dc0aab7f6f0d/Untitled.png)

### sonarqube

- `docker run --rm -p 9000:9000 -name sonarqube sonarqube`
    - 만약에 [docker.io](http://docker.io) error 발생
    
    ```bash
    docker logout
    docker login
    > username : isakggong
    > password : <personal-token>
    ```
    

sonarqube에 접속

- admin, admin → 비번 변경 admin123

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/24ef2894-c23e-4f45-81af-70601e8c4a8f/Untitled.png)

My Account - Security에서 User-Token 생성 (만료 기한 없음)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/4d446e5d-e2e7-4a1b-b21d-da4c8e69aaca/Untitled.png)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/39c7135f-4cb7-4ddb-b330-7a8d782ad7b6/Untitled.png)

jenkins 관리 - Security 영역 - Manage credentials 를 접근

sonarqube-token을 등록하도록 하자. → Secret text 로 생성한다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/45bc1ab2-7d3a-4b6b-8d1c-a56f05684fb2/Untitled.png)

원래는 docker network inspect bridge 내부 ip를 사용해서 Server URL를 갖게 된다. 

하지만 host에서도 sonarqube docker가 보이니까. 한 번 해봄.

왜냐면 매번 docker network에서 ip가 변경되기 때문에 고정적으로 변하지 않는 host가 맞지 않을까…

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d4a7419e-ef17-49f2-8f9f-3543ecff6a79/Untitled.png)

### SSAFY Sonarqube

[교수명세서_sonarqube_박찬국.pdf](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/357237fe-65ff-4102-8aa5-45525a1343c2/%EA%B5%90%EC%88%98%EB%AA%85%EC%84%B8%EC%84%9C_sonarqube_%EB%B0%95%EC%B0%AC%EA%B5%AD.pdf)

project-key : myFarm

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/fca0e4c4-2ee0-4ac8-9db5-b6e32e904dde/Untitled.png)

spring plugin을 추가해야 한다. (sonarqube에서 프로젝트를 만들 때 어떤 버전의 플러그인을 할지 알려준다.)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/42caf33b-039f-40cb-9ee4-633ba00a1bbb/Untitled.png)

token을 설정하기 위한 SonarQube server를 지정한다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/362b3a44-299b-436e-aeae-8d61f58dc925/Untitled.png)

credential

- secret text로 지정
    - myFarm project token을 secret key로 넣는다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/fb2fecac-7d89-46e3-84db-c3272773f4b6/Untitled.png)

[SonarQube 정적분석 및 Jenkins CI/CD 통합](https://waspro.tistory.com/596)

pipeline

- git clone → isak branch의 내용을 끌어온다.
    - gradle에 실행할 수 있도록 권한을 변경 → 안하면 permission denied
- withSonarQubeEnv(<sonarqube server name>) 이름 지정한다.
- gradle init → 안하면 gradle init를 하라고 알려줌

```bash
./gradlew sonarqube 
-Dsonar.projectKey=myFarm # sonarqube 내의 project name
-Dsonar.host.url=https://sonarqube.ssafy.com # sonarqube URL
-Dsonar.login=24c218be6d503f55615181ae32ba8768a70ea043 # token
```

```bash
pipeline {
    agent any
    tools{
        gradle 'gradle-7.6'
    }
    
    environment {
        GIT_BUSINESS_CD = 'isak'
        GIT_CREDENTIAL_ID = 'gitlab-personal-token'
        GITLAB_URL = 'https://lab.ssafy.com/s08-webmobile2-sub2/S08P12B308.git'
    }
    
    stages {
        stage('Gitlab clone'){
            steps{
                git branch: "${env.GIT_BUSINESS_CD}", credentialsId: "${env.GIT_CREDENTIAL_ID}", url: "${env.GITLAB_URL}"
                sh "chmod +x -R ${env.WORKSPACE}"
            }
        }
     
        stage('SonarQube analysis'){
            steps {
                withSonarQubeEnv('sonarqube-server'){
                    sh '''
                        echo SonarQube bug reporting...
                        cd BackEnd/myFarm
                        gradle init
                        ./gradlew sonarqube -Dsonar.projectKey=myFarm -Dsonar.host.url=https://sonarqube.ssafy.com -Dsonar.login=24c218be6d503f55615181ae32ba8768a70ea043
                    '''
                }
             }
        }
    }

    // post {
    //   always {
    //     echo "This will always run"
    //   }
    //   success {
    //     echo "This will run when the run finished successfully"
    //   }
    //   failure {
    //     echo "This will run if failed"
    //   }
    //   unstable {
    //     echo "This will run when the run was marked as unstable"
    //   }
    //   changed {
    //     echo "This will run when the state of the pipeline has changed"
    //   }
    // }
}
```

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a26d00ee-5d60-48b7-b906-1f08867ebfb2/Untitled.png)

sonarqube에서 보여주는 버그 리포팅

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a567761e-b07a-4b17-8752-81e25788e274/Untitled.png)

### Webhook (Gitlab + Jenkins)

[Gitlab Webhook으로 Jenkins 빌드 자동화](https://toast1307.tistory.com/m/46)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f8306ef7-97a2-4b25-8627-77d2fb50c5e2/Untitled.png)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f8e4c19d-6902-4fc6-ac61-e95ee4912c4f/Untitled.png)

중요한 부분은 git push event가 발생하는 branch를 선정하는 부분과 Secret token을 잘 넣으면 된다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/44cd88fa-4933-4413-bb6e-fbccfaa0d3d6/Untitled.png)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e238d06b-3c70-46ff-ba7b-ee833017f4f9/Untitled.png)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/7797681f-0542-463c-ac06-028b9c714cf2/Untitled.png)

해당 변경점이 gitlab의 webhook으로 Trigger되었다고 나옴.

### MSA 배포

Delpoyed Services - IP address, Ports

| Sevice(Container name) | IP address  | Port |
| --- | --- | --- |
| jenkins | 172.18.0.2/16 | 50000:50000
8081:8080 |
| ansible | 172.18.0.3/16 | 20022:22
8088:8080 |
| rabbitmq | 172.18.0.4/16 | 5671:5671 |
| config-service | 172.18.0.5/16 | 8888:8888 |
| discovery-service | 172.18.0.6/16 | 8761:8761 |
| apigateway-service | 172.18.0.7/16 | 8000:8000 |
| mysqldb | 172.18.0.8/16 | 3307:3306 |
| kafka-docker_zookeeper_1 | 172.18.0.100/16 | 2181:2181 |
| kafka-docker_kafka_1 | 172.18.0.101/16 | 9092:9092 |
| zipkin | 172.18.0.9/16 | 9411:9411 |
| prometheus | 172.18.0.10/16 | 9090:9090 |
| grafana | 172.18.0.11/16 | 3000:3000 |
| user-service | 172.18.0.12/16 |  |
| plant-service | 172.18.0.13/16 |  |
| notify-service | 172.18.0.14/16 |  |
| chat-service | 172.18.0.15/16 |  |
| calendar-service | 172.18.0.16/16 |  |
| board-service | 172.18.0.17/16 |  |

*apigateway-service로부터 마이크로 서비스를 할당하기 때문에 IP, Port는 중요하지 않음.

### ⚠️ Ansible-server에서 Host PC로 ssh키 보내기

1. ssh-keygen으로 키생성 (passphrase는 없음)
2. `vi /root/.ssh/id_rsa.pub` → host에서 `vi .ssh/authorized_keys` 에 public key를 추가하자. (ssh-copy-id 로도 가능)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/2ce7a696-1283-440d-abac-c76ac239452a/Untitled.png)

현재 사용자가 ubuntu이기 때문에 `ssh root@172.26.2.9` 로 하지 마라❗

→ 비밀번호가 맞아도 Permission denied가 발생함.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e6ff9597-a027-4f2a-8618-d6b09e4018cc/Untitled.png)

*jenkins containers를 포트를 변경하기 위한 방법

1. docker commit으로 이미지를 생성해서 포트를 변경한다.
2. container의 정보 hostconfig.json을 찾아서 내용을 변경한다.
    1. sudo find / -name hostconfig.json 명령을 사용

[How do I assign a port mapping to an existing Docker container?](https://stackoverflow.com/questions/19335444/how-do-i-assign-a-port-mapping-to-an-existing-docker-container)

## 부록

---

### AWS 시스템 시간 변경 하기

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/ac2fc971-d81c-4e90-b7a3-95cf120dc55b/Untitled.png)

[https://anggeum.tistory.com/entry/AWS-EC2-서버-시간-동기화-및-타임존-설정](https://anggeum.tistory.com/entry/AWS-EC2-%EC%84%9C%EB%B2%84-%EC%8B%9C%EA%B0%84-%EB%8F%99%EA%B8%B0%ED%99%94-%EB%B0%8F-%ED%83%80%EC%9E%84%EC%A1%B4-%EC%84%A4%EC%A0%95)

→ AWS 위치 시간 설정 (한국으로 설정하기)

Dockerfile에서 시간 설정하는 방법

```
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
```

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/0db52c53-20cf-44ef-b6ae-88b6fd5265ae/Untitled.png)

Dockerfile 생성

[Run jar file in docker image](https://stackoverflow.com/questions/35061746/run-jar-file-in-docker-image)

Docker (+서울 로컬타임) 

```bash
FROM ubuntu
MAINTAINER isakggong

RUN apt-get update
RUN apt-get install -y openjdk-8-jdk

ENV TZ=Asia/Seoul
RUN apt-get update && apt-get install -y tzdata && \
    echo $TZ > /etc/timezone && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime
LABEL org.opencontainers.image.authors="isakggong@gmail.com"

COPY myFarm-0.0.1-SNAPSHOT.jar /home/myFarm-0.0.1-SNAPSHOT.jar

CMD ["java","-jar","/home/myFarm-0.0.1-SNAPSHOT.jar"]
```

→ ubuntu + openjdk가 무거워서 700MB 정도 크기

but, mysql과 tomcat 실행되는 시스템에 시간 축을 변경해봐도 안됨.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/2d540525-0432-471c-bd65-b1fbcae5ad6d/Untitled.png)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/ccffa21d-a343-40b1-9acd-34ed5e5449bd/Untitled.png)

→ Swagger 응답 헤더의 date가 어딘지 모름

### ansible key pub (rsa)

```bash
ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQC1VQXhBGXJD/tcvViP1j/gy/CQWHkzitF6TkxyK6Ryw07CiRxZOwmXo1ig3WkDw48fIy4nyV9QZDgi8q+dNyEnle3FRuLd5pDyTeK3bxFhXd0bso0GSJSseIVl1Fomsrhe6KpY8Fay8nfnM09JwyuffNA7/r91MnedWzFLZepgsi4IZhk+zXnh+2LApQTaeaLrmXBySAQaGCUeiAYFRDiozBsjxAYDi3bQSDNyY8PQ6nJW6Yj/37kU/cD4Ngq/8V+GFS6iVAvQLUkMsmgY4C7jFnvXlLmPCNnIg5YJe48w2odMTfa/W8KUPEDv7H7AvpqLXAbYH2YBBXigEGFV4WOFN83292znxVNfeJQLZ7D9baF2RS3h1DZBue68QDCXcLYjB2BacKPxEkEXIghjjOML0cbrghyAkSvzi3tAq0xqVyv0nPz1MjHkMvSlEZQdcKruvl4+0RWrisqThn56sDJ3ATvnCPJTU4uoGllxuffuwbFxAlVtJDttMThgZ/sJXSU= root@6264b488e430
```

→ docker에 ssh로 넘겨주기

### Kubernetest 설치

---

[쿠버네티스(Kubernetes) 설치 및 환경 구성하기](https://medium.com/finda-tech/overview-8d169b2a54ff)

[](https://velog.io/@koo8624/Kubernetes-AWS-EC2-%EC%9D%B8%EC%8A%A4%ED%84%B4%EC%8A%A4%EC%97%90-Kubernetes-%ED%81%B4%EB%9F%AC%EC%8A%A4%ED%84%B0-%EA%B5%AC%EC%B6%95%ED%95%98%EA%B8%B0)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/816bdc0e-469c-496b-8db2-50bf037b8a22/Untitled.png)

→ [docker.io](http://docker.io) 설치 + containerd 설치

[안녕하세요, kubeadm init 에러가 발생합니다. - 인프런 | 질문 & 답변](https://www.inflearn.com/questions/589535/%EC%95%88%EB%85%95%ED%95%98%EC%84%B8%EC%9A%94-kubeadm-init-%EC%97%90%EB%9F%AC%EA%B0%80-%EB%B0%9C%EC%83%9D%ED%95%A9%EB%8B%88%EB%8B%A4)

[[Kubernetes] - docker.io : Depends: containerd (>= 1.2.6-0ubuntu1~)](https://lifetutorial.tistory.com/114)

→ 실패

[](https://confluence.curvc.com/pages/releaseview.action?pageId=98048155)

docker + kubernetes를 천천히 실행하자.

이번에도 kubeadm init에서 막혔지만 error 잘 보면 root 권한이 필요하다는 의미였음.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/45843630-0b41-4cee-9c34-ac4226e09156/Untitled.png)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/06178e33-1df9-40ee-9105-5475a0078477/Untitled.png)

성공!

```bash
To start using your cluster, you need to run the following as a regular user:

  mkdir -p $HOME/.kube
  sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
  sudo chown $(id -u):$(id -g) $HOME/.kube/config

Alternatively, if you are the root user, you can run:

  export KUBECONFIG=/etc/kubernetes/admin.conf

You should now deploy a pod network to the cluster.
Run "kubectl apply -f [podnetwork].yaml" with one of the options listed at:
  https://kubernetes.io/docs/concepts/cluster-administration/addons/

Then you can join any number of worker nodes by running the following on each as root:

kubeadm join 172.26.2.9:6443 --token n14uj5.4v6c2tq7z6g4uxgr \
        --discovery-token-ca-cert-hash sha256:bd640a4202dd12604e6d700cdce91e2630f8fdf07dbfb7871a969db40f10e3f0
```

→ EC2가 여러개(최소 2개)가 있어야 실제 쿠버네티스 클러스터가 구현이 가능하다.

그러므로 배포 구현을 축소하고 minikube를 진행하도록 하자. 자율 때 실제로 쿠버네티스를 구현해도 늦지 않다.

[Minikube 설치](https://www.notion.so/Minikube-96ed72b122734caa825b9cda9caeaf0e)