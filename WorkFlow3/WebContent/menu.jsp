<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page session="true"%>
<%
	// Servletのデータ受け取り
request.setCharacterEncoding("UTF8");
final String referenceDirectory = (String) session.getAttribute("referenceDirectory");
String authority = (String) session.getAttribute("authority");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メニュー画面</title>
<link rel="stylesheet" href="menu.css">
</head>

<body>
	<header>
		<img src="https://www.homepage-tukurikata.com/image/hanikami.jpg" alt="IACロゴ" title="IACロゴ" width="100px" height="25px">
		<form name="login_logout" action="login.jsp" method="post"
		onsubmit="return logout()">
		<div align="right">
			<div>
				<%=session.getAttribute("affiliationName") %>・
				<%=session.getAttribute("fullname")%>
				<input class="logoutbutton" type="submit" value="ログアウト">
			</div>
		</div>
	</form>
		<hr>
	</header>


	<%
		try {
		if (session.getAttribute("id").equals(null)) {
			throw new Exception();
		}
	%>
	<br>

	<div class="content">
		<h1>有給休暇取得申請システム メニュー画面</h1>
		<br>
		<form action="<%=request.getContextPath()%>/Shinsei" method="post">
			<div class="hoge">
				<button class="subbutton" type="submit">申請画面</button>
			</div>

		</form>
		<form action="<%=request.getContextPath()%>/ApproveHistoryPick" method="post">
			<div class="hoge">
				<button class="subbutton" type="submit">申請一覧</button>
			</div>
		</form>
		<%
			if (session.getAttribute("authority").equals("1")) {
		%>
		<form action="<%=request.getContextPath()%>/ApprovePick" method="post">
			<div class="hoge">
				<button class="subbutton" type="submit">承認画面</button>
			</div>
		</form>
		<%
			}
		%>
	</div>
	<%
		} catch (Exception e) {
		response.sendRedirect("login.jsp");
	}
	%>
	<script type="text/javascript" src="logout.js"></script>
</body>
</html>