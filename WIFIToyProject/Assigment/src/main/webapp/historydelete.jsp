<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import = "db.HistoryService" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
</head>
<body>
	<%
		//��û �Ķ���� id���� �޾Ƽ� �ش� row�� �����ϰ� �ٽ� ������������ ��ȯ
		HistoryService historyService = new HistoryService();
		
		String id = request.getParameter("id");
		historyService.deleteHistory(id);
		
		response.sendRedirect("history.jsp");
	%>

</body>
</html>