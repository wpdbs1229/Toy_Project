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
<h1>�������� ���� ���ϱ�</h1>
  
  <nav>
    <ul>
      <li><a href="home.jsp">Ȩ</a> |</li>
      <li><a href="history.jsp">��ġ �����丮 ���</a> |</li>
      <li><a href="wifiInfo.jsp">Open API �������� ���� ��������</a></li>
    </ul>
  </nav>

  
    LAT : <input type="text" value="0.0" id = "lat">
    LNT : <input type="text" value="0.0" id = "lnt">

    <button id="position" >�� ��ġ ��������</button>
    
  <form method = "GET" action="home.jsp">
	  <input type="hidden" value="0.0" name="lat" id = "lat1">
	  <input type="hidden" value="0.0" name="lnt" id = "lnt1">
  	<button type ="submit">��ó �������� ��������</button>
  </form>
	<%! 
	// ���� ���� ����
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
		//���� request �Ķ���͸� double��ȯ�ѵ�, �̸� �� Wifi���� �Ÿ��� ���
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
	
		//�� ��ó WIFI���⸦ �������Ƿ� �ش� ����� history���̺� ����
		historyService.insertHistory(lat,lnt);
		
	%>
	
	<table>
		<thead>
			<tr>
				<th>�Ÿ�</th>
				<th>������ȣ</th>
				<th>��ġ��</th>
				<th>�������̸�</th>
				<th>���θ��ּ�</th>
				<th>���ּ�</th>
				<th>��ġ��ġ(��)</th>
				<th>��ġ����</th>
				<th>��ġ���</th>
				<th>���񽺱���</th>
				<th>������</th>
				<th>��ġ�⵵</th>
				<th>�ǳ��ܱ���</th>
				<th>WIFI����ȯ��</th>
				<th>X��ǥ</th>
				<th>Y��ǥ</th>
				<th>�۾�����</th>
			</tr>
		</thead>
		<tbody>
			<%
			//���� �ֽſ� �ҷ��� ���� �̹� ��ȸ�Ǿ��ִٸ�, ������ ���̺� ���� �״���̿�
			historyList = historyService.selectHistory();

			if(lat != historyList.get(historyList.size()-1).getY() && lnt != historyList.get(historyList.size()-1).getX() ){
				distanceWifiService.deleteDistanceWifi();
				distanceWifiService.insertDistanceWifi(distanceList);
			}
			
			//�� ��ó wifi 20����� ��ȸ
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