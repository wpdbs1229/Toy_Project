# JDBC-toyProject

### 목적
공공데이터 포털의 OPEN API를 통해 서울시 공공 WIFI에 대한 데이터를 받아
JDBC을 이용해 DB에 저장하고, 가장 가까이에 있는 WIFI 목록 조회하기

### 환경
- Eclipse
- SQLite3
- Tomcat 8.5
- Maven (json-simple, okhttp3..)

### 설명
- Wifi 클래스 : DB에 WIFI table값을 담기위한 객체 클래스 
- WifiService : WIFI table에 대한 CRUD, JSONParsing 기능 담당
- DistanceWifi : DB에 distanceWifi table값을 담기위한 클래스 (distancewifi table의 경우 : 자신의 위치로 부터 각 와이파이 모델과의 거리 값이 담겨있음)
- DistanceWifiService : distancewifi의 CRUD 기능 담당
- NearByWifi : distanceWifi와 wifi를 와이파이 관리번호로 Join하여 distance와 wifi 테이블 속성을 갖는 객체 클래스
- History : 와이파이를 조회했을 때의 나의 위치, 시간을 저장하는 history 테이블을 다루기 위한 객체 클래스
- HistoryService : history의 CRUD 기능 담당

- home.jsp : 현재위치를 받아 home2.jsp로 위도와 경도를 넘겨줌
- home2.jsp : 받은 위도와 경도로 각 wifi와 현재위치에 거리를 계산, 가장가까운 wifi부터 20개까지 출력시켜줌
- wifiInfo.jsp : open API로부터 데이터를 받아서, 이를 wifi table에 저장
- history.jsp : 와이파이를 조회한 기록을 보고, 삭제 가능
- historydelete.jsp : history.jsp에서 삭제를 요청하면, 해당 페이지에서 삭제가 이루어짐

-> home.jsp에서 run server를 해야합니다.
