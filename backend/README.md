# Backend
- IntelliJ IDEA 2021.1.3 x64
- MySQL Workbench
## Install
### application.properties 파일 생성


```
#server port
server.port=[your_port]

#SMS
ACCOUNT_SID=[your_twilio_ACCOUNT_SID]
AUTH_TOKEN=[your_twilio_AUTH_TOKEN]

#database
spring.jpa.hibernate.naming.implicit-strategy=org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy

spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL57Dialect
spring.data.web.pageable.one-indexed-parameters=true
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://[your_db_url]/[your_db_name]?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul&zeroDateTimeBehavior=convertToNull&rewriteBatchedStatements=true
spring.datasource.hikari.username=[your_db_id]
spring.datasource.hikari.password=[your_db_pw]

# mybatis
mybatis.config-location=classpath:/config/mybatis-config.xml
```
## Package Diagram

![image-20210817142854544](https://user-images.githubusercontent.com/77473524/129685431-eb2c8f69-3232-4427-b7b9-26be40a247ab.png)

## Project Structure

```
backend
│  .gitignore
│  build.gradle
│  gradlew
│  gradlew.bat
│  HELP.md
│  settings.gradle
│       
└─src
    └─main
        ├─java
        │  └─com
        │      └─myapp
        │          └─backend
        │              │  BackendApplication.java
        │              │  
        │              ├─common					공통파일
        │              │      LoggerAspect.java
        │              │      LoginInterceptor.java
        │              │      
        │              ├─config					설정파일
        │              │      JPAConfig.java
        │              │      WebMvcConfig.java
        │              │      WebSocketConfig.java
        │              │      
        │              ├─controller				컨트롤러
        │              │      AlbumController.java
        │              │      AttendController.java
        │              │      ...
        │              │      
        │              ├─dao					MyBatis Dao
        │              │      DosageDao.java
        │              │      MenuDao.java
        │              │      ...
        │              │      
        │              ├─domain
        │              │  ├─dto					요청/응답용 클래스
        │              │  │  │  Result.java
        │              │  │  │  
        │              │  │  ├─admin
        │              │  │  │      AdminUserDto.java
        │              │  │  │      
        │              │  │  ├─album
        │              │  │  │      AlbumDetailDto.java
        │              │  │  │      AlbumDto.java
        │              │  │  └─     ...
        │              │  │          
        │              │  └─entity				JPA 엔티티
        │              │      │  Album.java
        │              │      │  AlbumImg.java
        │              │      │  ...
        │              │      │  
        │              │      └─key
        │              │              AttendKey.java
        │              │              
        │              ├─handler				WS핸들러
        │              │      WebSocketGPSHandler.java
        │              │      
        │              ├─repository				JPA 레포지토리
        │              │      AlbumImgRepository.java
        │              │      AlbumRepository.java
        │              │      ...
        │              │      
        │              └─service				서비스
        │                      AlbumService.java
        │                      AlbumServiceImpl.java
        │                      ...
        │                      
        └─resources
            │  application.properties
            │  
            ├─config					MyBatis 설정
            │      config.txt
            │      mybatis-config.xml
            │      
            └─mapper					MyBatis 파일
                    dosage_query.xml
                    mapper.txt
                    ...
```

