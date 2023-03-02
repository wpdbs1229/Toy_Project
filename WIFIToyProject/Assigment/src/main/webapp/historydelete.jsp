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
		//요청 파라미터 id값을 받아서 해당 row를 삭제하고 다시 원래페이지로 반환
		HistoryService historyService = new HistoryService();
		
		String id = request.getParameter("id");
		historyService.deleteHistory(id);
		
		response.sendRedirect("history.jsp");
	%>

</body>
</html>