<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.workflow.Keyword"%>
<%@ page session="true"%>
<%
	try {
	if (session.equals(null)) {
		throw new Exception();
	}
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
	String id = (String) session.getAttribute("id");
	session.setAttribute("approvedComment", request.getParameter("comment"));
	session.setAttribute("approvedAction", request.getParameter("action"));
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
		<img src="./imge/apple-touch-icon-120x120.png" alt="IACロゴ"
			title="IACロゴ" width="100px" height="25px">
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
	<form action="Approve" method="post">
		<table style="border: 0">
			<tr>
				<td colspan="4" align="center">有給休暇取得申請システム 承認明細確認画面</td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="left">申請番号:</td>
				<td colspan="3"><%=session.getAttribute("approvedNumber")%></td>
			</tr>
			<tr>
				<td align="left">所属:</td>
				<td colspan="3"><%=session.getAttribute("approvedBelongs")%></td>
			</tr>
			<tr>
				<td align="left">氏名:</td>
				<td colspan="3"><%=session.getAttribute("approvedName")%></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="left">有給種別:</td>
				<td colspan="3"><%=Keyword.type((String) session.getAttribute("approvedType"))%></td>
			</tr>
			<tr>
				<td align="left">取得期間:</td>
				<td><%=session.getAttribute("approvedFromDate")%></td>
				<td>&nbsp;～&nbsp;</td>
				<td><%=session.getAttribute("approvedToDate")%></td>
			</tr>
			<tr>
				<td align="left">取得時間:</td>
				<td><%=session.getAttribute("approvedFromTime")%></td>
				<td>&nbsp;～&nbsp;</td>
				<td><%=session.getAttribute("approvedToTime")%></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="left">取得事由:</td>
				<td colspan="3"><%=session.getAttribute("approvedReason")%></td>
			</tr>
			<tr>
				<td align="left">連絡先:</td>
				<td colspan="3"><%=session.getAttribute("approvedAddress")%></td>
			</tr>
			<tr>
				<td align="left">備考:</td>
				<td colspan="3"><%=session.getAttribute("approvedRemarks")%></td>
			</tr>
			<%
				if (!session.getAttribute("approvedFixComment").equals("")) {
			%>
			<tr>
				<td align="left">修正コメント:</td>
				<td colspan="3"><%=session.getAttribute("approvedFixComment")%></td>
			</tr>
			<%
				}
			%>
			<tr>
				<td><br></td>
			</tr>

			<%
				if (!session.getAttribute("id").equals(session.getAttribute("approve2Id"))) {
			%>

			<tr>
				<td align="left">承認者:</td>
				<td colspan="3"><%=session.getAttribute("approve2Name")%></td>
			</tr>
			<tr>
				<td align="left" colspan="4">承認者１コメント:<%=session.getAttribute("approvedComment")%></td>
			</tr>
			<tr>
				<td align="left" colspan="4">承認者２コメント:<%=session.getAttribute("approve2Comment")%></td>
			</tr>

			<%
				} else {
			%>

			<tr>
				<td align="left" colspan="4">承認者１コメント:<%=session.getAttribute("approve1Comment")%></td>
			</tr>
			<tr>
				<td align="left" colspan="4">承認者２コメント:<%=session.getAttribute("approvedComment")%></td>
			</tr>

			<%
				}
			%>

			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit"
					value=" <%=session.getAttribute("approvedAction")%> " class="btn">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="button" onclick="history.back()">&nbsp;戻る&nbsp;</button>
				</td>
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