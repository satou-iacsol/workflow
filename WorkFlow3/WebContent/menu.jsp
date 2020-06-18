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
<link rel="icon" href="./imge/favicon.ico">
<link rel="stylesheet" href="menu.css">
</head>

<body>
	<header>
		<img src="./imge/apple-touch-icon-120x120.png"
			alt="IACロゴ" title="IACロゴ" width="100px" height="25px">
		<form name="login_logout" action="login.jsp" method="post"
			onsubmit="return logout()">
			<div align="right">
				<div class="header_right">
					<%=session.getAttribute("affiliationName")%>・
					<%=session.getAttribute("fullname")%>
					<input class="logoutbutton" type="submit" value="ログアウト">
				</div>
			</div>
		</form>
	</header>

	<%
		try {
		if (session.getAttribute("id").equals(null)) {
			throw new Exception();
		}
	%>
	<br>

	<div class="h1">
		<h1>有給休暇取得申請システム メニュー画面</h1>
	</div>
	<div class="content">
		<div class="block">
			<br>
			<div class="hoge">
				<input type="button" class="subbtn"
					onclick="location.href='./shinsei.jsp'" value="申請画面">
			</div>

			<form action="<%=request.getContextPath()%>/ApproveHistoryPick"
				method="post">
				<div class="hoge">
					<button class="subbtn" type="submit">申請一覧</button>
				</div>
			</form>
			<%
				if (session.getAttribute("authority").equals("1") || session.getAttribute("authority").equals("2")) {
			%>
			<form action="<%=request.getContextPath()%>/ApprovePick"
				method="post">
				<div class="hoge">
					<button class="subbtn" type="submit">承認画面</button>
				</div>
			</form>
			<%
				}
			%>
		</div>
		<%
			if (session.getAttribute("authority").equals("2")) {
		%>
		<form action="MusterTransition" method="post">
			<div class="muster">
				<h2 class="muster_box">マスタメンテ</h2>
				<button class="musterEbtn" type="submit">社員マスタ</button>
			</div>
		</form>
				<input type="button" class="musterBbtn"
					onclick="location.href='./muster.jsp'" value="部署マスタ">
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