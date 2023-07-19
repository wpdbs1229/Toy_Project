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
 <h1>�������� ���� ���ϱ�</h1>
  
  <nav>
    <ul>
      <li><a href="home.jsp">Ȩ</a> |</li>
      <li><a href="history.jsp?">��ġ �����丮 ���</a> |</li>
      <li><a href="wifiInfo.jsp">Open API �������� ���� ��������</a></li>
    </ul>
  </nav>

  
    LAT : <input type="text" value="0.0" id = "lat">
    LNT : <input type="text" value="0.0" id = "lnt">

    <button id="position" >�� ��ġ ��������</button>
   <!-- Form�� ���� �浵�� ������ home2.jsp���� ���� -->
  <form method = "GET" action="home2.jsp">
	  <input type="hidden" value="0.0" name="lat" id = "lat1">
	  <input type="hidden" value="0.0" name="lnt" id = "lnt1">
  	<button type ="submit">��ó �������� ��������</button>
  </form>

	
	
	
</body>
 <script>
	
	/*���� ��ġ��ǥ�� �޾ƿ��� �ڵ�*/
    var lat = 0;
    var lnt = 0;
    if ('geolocation' in navigator) {
      console.log("��ġ ���� ��� ����")
    } else {
      console.log("��ġ ���� ��� �Ұ���")
    }
    navigator.geolocation.getCurrentPosition((position) => {
      lat = position.coords.latitude;
      lnt = position.coords.longitude;
    });

    /*Lat, Lan�� input value���� �ٲ��ִ� js �Լ� id�� position�� button�� ������ �Լ��� ����*/
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