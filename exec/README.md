# Porting Menual

## Deploy Document

### Version

- openjdk "1.8.0_262"

- spring boot 2.5.2 
- IntelliJ IDEA 2021.1.3 x64
- Visual Studio Code 1.58
- Node.js 14.17.3
- mysql 8.0

## Deploy Description

### Backend

#### Java 8 버전 설치

```
sudo apt-get install openjdk-8-jdk
```

#### build

```
// backend root directory
$ sudo ./gradlew build
```

#### 실행

```
$ nohup java -jar build/libs/[project_jar_file].jar &
```

#### 종료

```
$ kill -9 $(lsof -t -i:[port_number])
```



### Frontend

#### Nginx 설치

```
$ sudo apt-get update
$ sudo apt-get upgrade
$ sudo apt-get install nginx
```

#### letsencrypt  설치

```
$ sudo apt-get update -y & sudo apt-get install letsencrypt -y
```

#### 인증서 발급

```
$ sudo letsencrypt certonly --standalone -d [your_domain_name]
```

#### conf 파일 설정

```
$ cd /etc/nginx/sites-availables/
$ vi default
```

![image](https://user-images.githubusercontent.com/77473524/129824538-9e1d54c6-33c1-46f7-beb3-427a00d19bc5.png)

![image](https://user-images.githubusercontent.com/77473524/129824595-b0caa795-3f5c-4320-be4e-425f2586b9e8.png)

#### Nginx에 vue 프로젝트 업로드

```
// frontend root directory
$ npm install
$ npm run build
$ sudo mv dist /var/www/html/
```

#### Nginx 실행

```
$ sudo service nginx start
```



## Necessary

### Backend

- application.properties

### Frontend

- drf.js
- awss3.js
- SendBird.js



## Prerequisites

### Twilio

- account ID
- auth token
- phone number

### AWS S3

- bucket name
- bucket region
- identity pollID

### SendBird

- appkey
- sendbird url
- sendbird api token

### Kakao Map

- appkey

