<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" import="java.util.Collections"%>
<%@ page session="true"%>
<%
	request.setCharacterEncoding("UTF-8");
response.setContentType("text/html;charset=UTF-8");
// 社員番号取得
String id = (String) session.getAttribute("id");
// 申請詳細リスト取得
@SuppressWarnings("unchecked")
ArrayList<String> historyList = (ArrayList<String>) session.getAttribute("historyList");

session.setAttribute("fix_type", request.getParameter("fix_type"));
session.setAttribute("fix_date_1", request.getParameter("fix_date_1").substring(0, 4)
		+ request.getParameter("fix_date_1").substring(5, 7) + request.getParameter("fix_date_1").substring(8, 10));
session.setAttribute("fix_date_2", request.getParameter("fix_date_2").substring(0, 4)
		+ request.getParameter("fix_date_2").substring(5, 7) + request.getParameter("fix_date_2").substring(8, 10));
session.setAttribute("fix_date_3",
		request.getParameter("fix_time_1").substring(0, 2) + request.getParameter("fix_time_1").substring(3, 5));
session.setAttribute("fix_date_4",
		request.getParameter("fix_time_2").substring(0, 2) + request.getParameter("fix_time_2").substring(3, 5));
session.setAttribute("fix_comment", request.getParameter("fix_comment"));
session.setAttribute("fix_tellnumber", request.getParameter("fix_tellnumber"));
session.setAttribute("fix_bikou", request.getParameter("fix_bikou"));
session.setAttribute("fix_delete_comment", request.getParameter("fix_delete_comment"));
session.setAttribute("fix_flag", request.getParameter("fix_flag"));

StringBuilder fromDate = new StringBuilder();
fromDate.append(((String) session.getAttribute("fix_date_1")).substring(0, 4) + "年");
fromDate.append(((String) session.getAttribute("fix_date_1")).substring(4, 6) + "月");
fromDate.append(((String) session.getAttribute("fix_date_1")).substring(6, 8) + "日");

StringBuilder toDate = new StringBuilder();
toDate.append(((String) session.getAttribute("fix_date_2")).substring(0, 4) + "年");
toDate.append(((String) session.getAttribute("fix_date_2")).substring(4, 6) + "月");
toDate.append(((String) session.getAttribute("fix_date_2")).substring(6, 8) + "日");

StringBuilder fromTime = new StringBuilder();
fromTime.append(((String) session.getAttribute("fix_date_3")).substring(0, 2) + "時");
fromTime.append(((String) session.getAttribute("fix_date_3")).substring(2, 4) + "分");

StringBuilder toTime = new StringBuilder();
toTime.append(((String) session.getAttribute("fix_date_4")).substring(0, 2) + "時");
toTime.append(((String) session.getAttribute("fix_date_4")).substring(2, 4) + "分");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"></meta>
<title>有給休暇取得申請システム</title>
<script src="https://code.jquery.com/jquery-3.5.1.js"
	integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
	crossorigin="anonymous"></script>
<style>
table, td, th {
	border: 0px;
}

table {
	align: center;
	margin: 0 auto;
}

header {
	position: fixed;
	left: 0;
	width: 100%;
}

img {
	float: left;
}

.logoutbutton {
	margin-left: 20px;
	margin-right: 20px;
}
</style>
</head>
<body>
	<header>
		<img src="https://www.homepage-tukurikata.com/image/hanikami.jpg"
			alt="IACロゴ" title="IACロゴ" width="100px" height="25px">
		<form name="login_logout" action="login.jsp" method="post"
			onsubmit="return logout()">
			<div align="right">
				<div>
					<%=session.getAttribute("affiliationName")%>・
					<%=session.getAttribute("fullname")%>
					<input class="logoutbutton" type="submit" value="ログアウト">
				</div>
			</div>
		</form>
		<hr>
	</header>
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
	<br>
	<form action="ApproveFix" method="post" onsubmit="return check()">
		<table style="border: 0">
			<tr>
				<td colspan="4" align="center">有給休暇取得申請システム 申請修正確認画面</td>
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
				<td colspan="3"><%=session.getAttribute("fix_type")%></td>
			</tr>
			<tr>
				<td align="left">取得期間:</td>
				<td><%=fromDate%></td>
				<td>&nbsp;～&nbsp;</td>
				<td><%=toDate%></td>
			</tr>
			<tr>
				<td align="left">取得時間:</td>
				<td><%=fromTime%></td>
				<td>&nbsp;～&nbsp;</td>
				<td><%=toTime%></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="left">取得事由:</td>
				<td colspan="3"><%=session.getAttribute("fix_comment")%></td>
			</tr>
			<tr>
				<td align="left">連絡先:</td>
				<td colspan="3"><%=session.getAttribute("fix_tellnumber")%></td>
			</tr>
			<tr>
				<td align="left">備考:</td>
				<td colspan="3"><%=session.getAttribute("fix_bikou")%></td>
			</tr>
			<tr>
				<td align="left">修正コメント:</td>
				<td colspan="3"><%=session.getAttribute("fix_delete_comment")%></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="left">承認者:</td>
				<td align="left">
					<%
						if (session.getAttribute("fix_flag").equals("0")) {
					%> <%=session.getAttribute("approverName_1")%> <%
 	} else {
 %><%=session.getAttribute("approverName_2")%> <%
 	}
 %>
				</td>

				<td colspan="2">一次承認者スキップ:<%=session.getAttribute("fix_flag")%></td>

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
						type="submit" value=" 修正 " class="btn"></span> <span
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
</body>
</html>