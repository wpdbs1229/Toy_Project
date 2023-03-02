<%@ page import = "db.Wifi" %>
<%@ page import = "db.History" %>
<%@ page import = "db.HistoryService" %>
<%@ page import = "db.WifiService" %>
<%@ page import = "db.DistanceWifiService" %>
<%@ page import = "db.NearByWifi" %>
<%@ page import = "java.util.*" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
 <style>
      ul {
        list-style-type: none;
        margin: 0;
        padding: 0;
      }
      li {
        display: inline;
      }
      table, th, td {
        border: 1px solid;
      }
    </style>
<body>
<h1>와이파이 정보 구하기</h1>
  
  <nav>
    <ul>
      <li><a href="home.jsp">홈</a> |</li>
      <li><a href="history.jsp">위치 히스토리 목록</a> |</li>
      <li><a href="wifiInfo.jsp">Open API 와이파이 정보 가져오기</a></li>
    </ul>
  </nav>

  
    LAT : <input type="text" value="0.0" id = "lat">
    LNT : <input type="text" value="0.0" id = "lnt">

    <button id="position" >내 위치 가져오기</button>
    
  <form method = "GET" action="home.jsp">
	  <input type="hidden" value="0.0" name="lat" id = "lat1">
	  <input type="hidden" value="0.0" name="lnt" id = "lnt1">
  	<button type ="submit">근처 와이파이 정보보기</button>
  </form>
	<%! 
	// 변수 선언 영역
	Wifi wifi = new Wifi();
	WifiService wifiservice = new WifiService();
	List<Wifi> wifiList = new ArrayList<>();
	List<Double> distanceList = new ArrayList<>();
	DistanceWifiService distanceWifiService = new DistanceWifiService();
	List<NearByWifi> nearbywifilist = new ArrayList<>();	
	NearByWifi nearbywifi = new NearByWifi();
	HistoryService historyService = new HistoryService();
	List<History> historyList = new ArrayList();
	
	%>
	
	<%
		//받은 request 파라미터를 double반환한뒤, 이를 각 Wifi와의 거리를 계산
		String lntStr =request.getParameter("lnt");
		String latStr =request.getParameter("lat");
		double lat = Double.valueOf(latStr).doubleValue();
		double lnt = Double.valueOf(lntStr).doubleValue();
		Double distance = 0.0;
		
 		wifiList = wifiservice.selectDB();
		for(Wifi wifi : wifiList){
			double wifiLat =Double.valueOf(wifi.getLAT()).doubleValue();
			double wifiLnt =Double.valueOf(wifi.getLNT()).doubleValue();
			distance = Math.sqrt(Math.pow(wifiLat-lat,2)+ Math.pow(wifiLnt-lnt,2));
			distanceList.add(distance);
		} 
	
		//내 근처 WIFI보기를 눌렀으므로 해당 기록을 history테이블에 저장
		historyService.insertHistory(lat,lnt);
		
	%>
	
	<table>
		<thead>
			<tr>
				<th>거리</th>
				<th>관리번호</th>
				<th>자치구</th>
				<th>와이파이명</th>
				<th>도로명주소</th>
				<th>상세주소</th>
				<th>설치위치(층)</th>
				<th>설치유형</th>
				<th>설치기관</th>
				<th>서비스구분</th>
				<th>망종류</th>
				<th>설치년도</th>
				<th>실내외구분</th>
				<th>WIFI접속환경</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>작업일자</th>
			</tr>
		</thead>
		<tbody>
			<%
			//가장 최신에 불러온 값이 이미 조회되어있다면, 기존의 테이블 값을 그대로이용
			historyList = historyService.selectHistory();

			if(lat != historyList.get(historyList.size()-1).getY() && lnt != historyList.get(historyList.size()-1).getX() ){
				distanceWifiService.deleteDistanceWifi();
				distanceWifiService.insertDistanceWifi(distanceList);
			}
			
			//내 근처 wifi 20개목록 조회
			nearbywifilist = distanceWifiService.selectDistanceWifi();
			
			for(NearByWifi nearbywifi : nearbywifilist){
				out.print("<tr>");
				out.print("<td>"+ nearbywifi.getDistance()+"</td>");
				out.print("<td>"+ nearbywifi.getX_SWIFT_MGR_NO()+"</td>");
				out.print("<td>"+ nearbywifi.getX_SWIFI_WRDOFC()+"</td>");
				out.print("<td>"+ nearbywifi.getX_SWIFI_MAIN_NM()+"</td>");
				out.print("<td>"+ nearbywifi.getX_SWIFI_ADRES1()+"</td>");
				out.print("<td>"+ nearbywifi.getX_SWIFI_ADRES2()+"</td>");
				out.print("<td>"+ nearbywifi.getX_SWIFI_INSTL_FLOOR()+"</td>");
				out.print("<td>"+ nearbywifi.getX_SWIFI_INSTL_TY()+"</td>");
				out.print("<td>"+ nearbywifi.getX_SWIFI_INSTL_MBY()+"</td>");
				out.print("<td>"+ nearbywifi.getX_SWIFI_SVC_SE()+"</td>");
				out.print("<td>"+ nearbywifi.getX_SWIFI_CMCWR()+"</td>");
				out.print("<td>"+ nearbywifi.getX_SWIFI_CNSTC_YEAR()+"</td>");
				out.print("<td>"+ nearbywifi.getX_SWIFI_INOUT_DOOR()+"</td>");
				out.print("<td>"+ nearbywifi.getX_SWIFI_REMARS3()+"</td>");
				out.print("<td>"+ nearbywifi.getLAT()+"</td>");
				out.print("<td>"+ nearbywifi.getLNT()+"</td>");
				out.print("<td>"+ nearbywifi.getWORK_DTTM()+"</td>");
				out.print("</tr>");
			}	
			%>
		</tbody>
	</table>
	
	
</body>
 <script>
	/*나의 위치좌표를 받아오는 코드*/
    var lat = 0;
    var lnt = 0;
    if ('geolocation' in navigator) {
      console.log("위치 정보 사용 가능")
    } else {
      console.log("위치 정보 사용 불가능")
    }
    navigator.geolocation.getCurrentPosition((position) => {
      lat = position.coords.latitude;
      lnt = position.coords.longitude;
    });

    /*Lat, Lan의 input value값을 바꿔주는 js 함수 id가 position인 button을 누르면 함수가 실행*/
      var MyPosition = document.getElementById("lat");
      $(document).ready(function (){
        $('#position')
                .on('click',function(){
          $('#lat').val(lat);
          $('#lnt').val(lnt);
          $('#lat1').val(lat);
          $('#lnt1').val(lnt);
                });

      });
      
	

  </script>
</html>