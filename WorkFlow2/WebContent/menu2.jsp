<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page session="true"%>

<%
	// Servletのデータ受け取り
	request.setCharacterEncoding("UTF8");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メニュー画面</title>
<link rel="stylesheet" href="menu.css">
</head>

<body>
	<div class="container">
		<header>
			<h1>有給休暇取得申請システム　メニュー画面</h1>
		</header>
	</div>
	<br>
	<form action="<%=request.getContextPath()%>/Shinsei" method="post">
		<div class="hoge">
			<button type="submit">申請画面</button>
		</div>
	
		<br>
	
	</form>
	<form action="<%=request.getContextPath()%>/approveChoose.jsp" method="post">
		<div class="hoge">
			<button type="submit">承認画面</button>
		</div>
	</form>
	
	

</body>
</html>