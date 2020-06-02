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
	<script type="text/javascript">
	<!--
		function logout() {
			if (confirm("ログアウトしますか？")) {
				return true;
			} else {
				return false;
			}
		}
	// -->
	</script>
	<%
		try {
		if (session.equals(null)) {
			throw new Exception();
		}
	%>
	<br>
	<form name="login_logout" action="login.jsp" method="post"
		onsubmit="return logout()">
		<div align="right">
			ログイン：<%=session.getAttribute("fullname")%>
			<input type="submit" value="ログアウト">
		</div>
	</form>
	<div class="content">
		<header>
			<h1>有給休暇取得申請システム メニュー画面</h1>
		</header>
		<br>
		<form action="<%=request.getContextPath()%>/Shinsei" method="post">
			<div class="hoge">
				<button type="submit">申請画面</button>
			</div>

			<br>

		</form>
		<%
			if (session.getAttribute("authority").equals("1")) {
		%>
		<form action="<%=request.getContextPath()%>/ApprovePick" method="post">
			<div class="hoge">
				<button type="submit">承認画面</button>
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
</body>
</html>