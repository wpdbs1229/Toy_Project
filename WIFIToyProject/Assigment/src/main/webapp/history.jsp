<%@ page import = "db.History" %>  
<%@ page import = "db.HistoryService" %>  
<%@ page import = "java.util.*" %>  
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>와이파이 조회 히스토리</title>
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
<h1>위치 히스토리 목록</h1>
  
  <nav>
    <ul>
      <li><a href="home2.jsp">홈</a> |</li>
      <li><a href="history.jsp">위치 히스토리 목록</a> |</li>
      <li><a href="wifiInfo.jsp">Open API 와이파이 정보 가져오기</a></li>
    </ul>
  </nav>
  <%!
  		//변수 선언
  		HistoryService historyService = new HistoryService();
  		List<History> historyList = new ArrayList();
  	%>
 <form id="delete">
  <table>
  	<thead>
  		<tr>
  			<th>ID</th>
  			<th>X</th>
  			<th>Y</th>
  			<th>조회일자</th>
  			<th>비고</th>
  		</tr>
  	</thead>
  	<tbody>
  	
  	<%
  		//historyService로부터 List를 받아 이를 테이블 형태로 출력
  		historyList = historyService.selectHistory();
  		for(History history : historyList){
   	%>
  	<tr>
			<td><%=history.getId()%></td>
			<td><%=history.getX()%></td>
			<td><%=history.getY()%></td>
			<td><%=history.getTime()%></td>
			<td>
				<!-- 삭제버튼을 생성, 삭제당하는 대상의 ID를 URL형식으로 전송 -->
				<a href ="historydelete.jsp?id=<%=history.getId()%>">삭제</a>	
			</td>
	</tr> 
	<%
  		}
	%>
  	</tbody>
  </table>
  </form>
</body>
</html>