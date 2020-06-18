<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" import="java.util.Collections"
	import="com.workflow.Keyword"%>
<%@ page session="true"%>
<%
	try {
	if (session.equals(null)) {
		throw new Exception();
	}
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
	// 社員番号取得
	String id = (String) session.getAttribute("id");
	// 申請詳細リスト取得
	@SuppressWarnings("unchecked")
	ArrayList<String> historyList = (ArrayList<String>) session.getAttribute("historyList");
	// 取消コメントをセッションに渡す
	session.setAttribute("fix_delete_comment", request.getParameter("fix_delete_comment"));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"></meta>
<title>有給休暇取得申請システム</title>
<link rel="icon" href="./imge/favicon.ico">
<style>
table, td, th {
	border: 0px;
}

table {
	align: center;
	margin: 0 auto;
}

body {
	background-color: #fafafa;
	margin: 0;
}

header {
	position: fixed;
	left: 0;
	width: 100%;
	height: 42px;
	background: #36D1DC; /* fallback for old browsers */
	background: -webkit-linear-gradient(to top, #5B86E5, #36D1DC);
	/* Chrome 10-25, Safari 5.1-6 */
	background: linear-gradient(to top, #5B86E5, #36D1DC);
	/* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
}

img {
	float: left;
	width: 100px;
	position: absolute;
	top: 50%;
	left: 0;
	transform: translateY(-50%);
}

.header_right {
	position: absolute;
	top: 50%;
	transform: translateY(-50%);
	right: 20px;
	font-size: 16px;
}
</style>
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
	<br>
	<br>
	<form action="ApproveDelete" method="post">
		<table style="border: 0">
			<tr>
				<td colspan="4" align="center">有給休暇取得申請システム 申請取消確認画面</td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="left">申請番号:</td>
				<td colspan="3"><%=historyList.get(1)%></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="left">有給種別:</td>
				<td colspan="3"><%=Keyword.type((String) historyList.get(2))%></td>
			</tr>
			<tr>
				<td align="left">取得期間:</td>
				<td>
					<%
						String fromDate = historyList.get(3).substring(0, 4) + "年" + historyList.get(3).substring(4, 6) + "月"
							+ historyList.get(3).substring(6, 8) + "日";
					%><%=fromDate%></td>
				<td>&nbsp;～&nbsp;</td>
				<td>
					<%
						String toDate = historyList.get(4).substring(0, 4) + "年" + historyList.get(4).substring(4, 6) + "月"
							+ historyList.get(4).substring(6, 8) + "日";
					%><%=toDate%></td>
			</tr>
			<tr>
				<td align="left">取得時間:</td>
				<td>
					<%
						String fromTime = historyList.get(3).substring(8, 10) + "時" + historyList.get(3).substring(10, 12) + "分";
					%><%=fromTime%></td>
				<td>&nbsp;～&nbsp;</td>
				<td>
					<%
						String toTime = historyList.get(4).substring(8, 10) + "時" + historyList.get(4).substring(10, 12) + "分";
					%><%=toTime%></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="left">取得事由:</td>
				<td colspan="3"><%=historyList.get(8)%></td>
			</tr>
			<tr>
				<td align="left">連絡先:</td>
				<td colspan="3"><%=historyList.get(9)%></td>
			</tr>
			<tr>
				<td align="left">備考:</td>
				<td colspan="3"><%=historyList.get(10)%></td>
			</tr>
			<tr>
				<td align="left" id="delete_col">取消コメント:</td>
				<td colspan="3"><%=session.getAttribute("fix_delete_comment")%></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="left" colspan="4">承認者１コメント:<%=historyList.get(13)%></td>
			</tr>
			<tr>
				<td align="left" colspan="4">承認者２コメント:<%=historyList.get(14)%></td>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td colspan="4" align="right"><span style="margin-right: 25px"><input
						type="submit" value=" 取消 " class="btn"></span> <span
					style="margin-right: 40px"><button type="button"
							onclick="history.back()">&nbsp;戻る&nbsp;</button> </span></td>
			</tr>
		</table>
	</form>
	<%
		} catch (Exception e) {
		response.sendRedirect("login.jsp");
	}
	%>
	<script src="jquery-3.5.1.min.js"></script>
	<script type="text/javascript" src="logout.js"></script>
</body>
</html>