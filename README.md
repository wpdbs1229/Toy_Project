# weatherDiary
- 일기를 쓰려는 날짜의 날씨 데이터를 받아서, 날씨와 사용자가 쓴 일기내용을 저장하는 서버를 구축

-----
### 요구사항
- 일기
  - 일기 작성
  - 한 날짜에 여러 개의 일기 작성 가능
- 날씨
  - 해당 날짜 날씨 받아오기

### 기술 스택
- SpringBoot 2.7.9
- JDK 11
- Gradle
- MySql
- JPA
- Lombok


### 구현기능 (API)
- 일기
  -  일기 작성
  -  일기 삭제
  -  일기 조회

### Open API
- [OpenWeatherAPI](https://openweathermap.org/) 에서 Json을 받아 파싱해서 DB에 저장
- 하루 무료 API 호출 수가 정해져있기 때문에, 새벽 1시가되면, 날짜 데이터를 받아 DB에 저장 후 사용
