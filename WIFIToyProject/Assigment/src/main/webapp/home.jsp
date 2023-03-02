<%@page import ="db.WifiService" %>
<%@page import="db.Wifi"%>
<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
 <script src="https://code.jquery.com/jquery-3.6.0.slim.js" integrity="sha256-HwWONEZrpuoh951cQD1ov2HUK5zA5DwJ1DNUXaM6FsY=" crossorigin="anonymous"></script>
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
</head>
<body>
 <h1>와이파이 정보 구하기</h1>
  
  <nav>
    <ul>
      <li><a href="home.jsp">홈</a> |</li>
      <li><a href="history.jsp?">위치 히스토리 목록</a> |</li>
      <li><a href="wifiInfo.jsp">Open API 와이파이 정보 가져오기</a></li>
    </ul>
  </nav>

  
    LAT : <input type="text" value="0.0" id = "lat">
    LNT : <input type="text" value="0.0" id = "lnt">

    <button id="position" >내 위치 가져오기</button>
   <!-- Form을 통해 경도와 위도를 home2.jspㄹ로 전송 -->
  <form method = "GET" action="home2.jsp">
	  <input type="hidden" value="0.0" name="lat" id = "lat1">
	  <input type="hidden" value="0.0" name="lnt" id = "lnt1">
  	<button type ="submit">근처 와이파이 정보보기</button>
  </form>

	
	
	
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