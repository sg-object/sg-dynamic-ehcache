# sg-dynamic-ehcache

## 주요 기능
* Rest API를 사용한 Ehcache 조작
* CSV File 기반 cache 초기화

## Version
* Spring Boot : 2.2.6.RELEASE
* Ehcache : 2.10.6
* Swagger : 2.9.2

## 사전 설정
* ehcache.xml 수정  
Path : /sg-dynamic-ehcache/src/main/resources/config/ehcache.xml  
수정 : cache data가 물리적 파일로 저장되는 diskStore Path 수정
* application.properties 수정  
Path : /sg-dynamic-ehcache/src/main/resources/application.properties  
수정 : CSV File이 임시 저장 되는 ehcache.csv.path 수정

## 테스트
Application 구동 후 Swagger URL 접속  
URL : http://localhost:8080/swagger-ui.html

## ScreenShot
![K-001](https://user-images.githubusercontent.com/49360550/81472577-06e75500-9234-11ea-9f69-7672cd188752.jpg)

![K-002](https://user-images.githubusercontent.com/49360550/81472607-2da58b80-9234-11ea-9779-2beb6724ea04.jpg)

![K-003](https://user-images.githubusercontent.com/49360550/81472627-53329500-9234-11ea-8e3a-a4ad4f68e653.jpg)
