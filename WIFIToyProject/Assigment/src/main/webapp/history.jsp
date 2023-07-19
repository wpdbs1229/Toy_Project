<%@ page import = "db.History" %>  
<%@ page import = "db.HistoryService" %>  
<%@ page import = "java.util.*" %>  
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�������� ��ȸ �����丮</title>
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
<h1>��ġ �����丮 ���</h1>
  
  <nav>
    <ul>
      <li><a href="home2.jsp">Ȩ</a> |</li>
      <li><a href="history.jsp">��ġ �����丮 ���</a> |</li>
      <li><a href="wifiInfo.jsp">Open API �������� ���� ��������</a></li>
    </ul>
  </nav>
  <%!
  		//���� ����
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
  			<th>��ȸ����</th>
  			<th>���</th>
  		</tr>
  	</thead>
  	<tbody>
  	
  	<%
  		//historyService�κ��� List�� �޾� �̸� ���̺� ���·� ���
  		historyList = historyService.selectHistory();
  		for(History history : historyList){
   	%>
  	<tr>
			<td><%=history.getId()%></td>
			<td><%=history.getX()%></td>
			<td><%=history.getY()%></td>
			<td><%=history.getTime()%></td>
			<td>
				<!-- ������ư�� ����, �������ϴ� ����� ID�� URL�������� ���� -->
				<a href ="historydelete.jsp?id=<%=history.getId()%>">����</a>	
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