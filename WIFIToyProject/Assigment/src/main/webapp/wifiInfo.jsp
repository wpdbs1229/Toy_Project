
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import="db.WifiService"%>
<%@page import="db.Wifi"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>���� ���� �������� �ҷ�������</title>
</head>
<body>
	<%! //���� ���� ����
		WifiService wifiService = new WifiService();
		List<Wifi> wifiList = new ArrayList<>();
	%>
	<%
		wifiList = wifiService.selectDB();
		//wifi���̺��� ä�����ִٸ� ���� x
		if(wifiList.size()==0)
		{//API�� ȣ��� �ִ� 1000���� �����Ͱ� �����Ƿ� �������� ��ü������ 23���� �ҷ���
		 	 for (int i = 1; i <23000 ; i+=1000) {
		      String jsonString = wifiService.getWifiString(i);
		      wifiService.insertDB(jsonString);
		  	}
		}
	%>
	<!-- Wifi ���̺� ����� ��ü ������ ǥ�� -->
	<h1><%=wifiService.countDB()%>���� WIFI ������ ���������� �����Ͽ����ϴ�.</h1>
	<a href="home.jsp">Ȩ���� ���ư���</a>
	
</body>
</html>