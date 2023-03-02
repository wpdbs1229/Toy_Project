
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import="db.WifiService"%>
<%@page import="db.Wifi"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>서울 전역 와이파이 불러오는중</title>
</head>
<body>
	<%! //변수 선언 영역
		WifiService wifiService = new WifiService();
		List<Wifi> wifiList = new ArrayList<>();
	%>
	<%
		wifiList = wifiService.selectDB();
		//wifi테이블이 채워져있다면 삽입 x
		if(wifiList.size()==0)
		{//API가 호출당 최대 1000개의 데이터가 들어오므로 데이터의 전체갯수인 23번을 불럽줌
		 	 for (int i = 1; i <23000 ; i+=1000) {
		      String jsonString = wifiService.getWifiString(i);
		      wifiService.insertDB(jsonString);
		  	}
		}
	%>
	<!-- Wifi 테이블 저장된 전체 갯수를 표시 -->
	<h1><%=wifiService.countDB()%>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
	<a href="home.jsp">홈으로 돌아가기</a>
	
</body>
</html>