<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    
    	String st =request.getContextPath();
    	System.out.print(st);
    	
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<jsp:include page="info/banner.jsp" ></jsp:include>
	<jsp:include page="info/content.jsp" ></jsp:include>
	<a href="test/test.jsp">에니어그램 테스트</a>
	
	
	<jsp:include page="info/footer.jsp"></jsp:include>
</body>
</html>