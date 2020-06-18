<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page session="true"%>
<%
	request.setCharacterEncoding("UTF8");
final String referenceDirectory = (String) session.getAttribute("referenceDirectory");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>確認画面</title>
<link rel="icon" href="./imge/favicon.ico">
<link rel="stylesheet" href="shinsei.css">
</head>
<body>
	<header>
		<img src="./imge/apple-touch-icon-120x120.png" alt="IACロゴ" title="IACロゴ" width="100px" height="25px">
		<form name="login_logout" action="login.jsp" method="post"
		onsubmit="return logout()">
		<div align="right">
			<div class="header_right">
				<%=session.getAttribute("affiliationName") %>・
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
	<form action="<%=request.getContextPath()%>/Output" method="post">
		<div class="hoge">
			<h1>有給休暇取得申請システム 申請確認画面</h1>
		</div>
		<br>
		<div class="margin">
			<p>
				所属:
				<%=session.getAttribute("affiliationName")%></p>
			<p>
				氏名:
				<%=session.getAttribute("fullname")%></p>

			有給種別:
			<%=session.getAttribute("type")%>
			<br> 取得期間:
			<%=session.getAttribute("date_1")%>
			～
			<%=session.getAttribute("date_2")%>
			<br> 取得時間:
			<%=session.getAttribute("time_1")%>
			～
			<%=session.getAttribute("time_2")%>
			<br> 取得事由:
			<%=session.getAttribute("comment")%>
			<br> 連絡先　:
			<%=session.getAttribute("tellnumber")%>
			<br> 備考　　:
			<%=session.getAttribute("bikou")%>
			<br> 承認者　:
			<%=session.getAttribute("approver_1_2")%>
			<br> 一次承認者スキップ:
			<%=session.getAttribute("flag")%>
			<br> <br>
			<button type="submit">申請</button>
			<input value="もどる" onclick="history.back();" type="button">
		</div>
	</form>
	<%
		} catch (Exception e) {
		response.sendRedirect("login.jsp");
	}
	%>
	<script type="text/javascript" src="logout.js"></script>
</body>
</html>