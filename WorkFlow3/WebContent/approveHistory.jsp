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
	// 社員番号取得
	String id = (String) session.getAttribute("id");
	// 申請詳細リスト取得
	@SuppressWarnings("unchecked")
	ArrayList<String> historyList = (ArrayList<String>) session.getAttribute("historyList");
	// フローリスト取得
	@SuppressWarnings("unchecked")
	ArrayList<ArrayList<String>> flowList = (ArrayList<ArrayList<String>>) session.getAttribute("flowList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"></meta>
<title>有給休暇取得申請システム</title>
<style>
.table1 {
	border-collapse: collapse;
	border: 0px;
	align: center;
	margin: auto;
}

.table2 {
	border: 1px black solid;
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
.header_right{
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
		<img src="https://www.homepage-tukurikata.com/image/hanikami.jpg" alt="IACロゴ" title="IACロゴ" width="100px" height="25px">
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
	<br>
	<br>
	<table class="table1">
		<tr>
			<td colspan="4" align="center">有給休暇取得申請システム 申請履歴画面</td>
		</tr>
		<tr>
			<td><br></td>
		</tr>
		<tr>
			<td align="left">申請番号:</td>
			<td colspan="3"><%=historyList.get(1)%></td>
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
			<%
				if (!historyList.get(11).equals("")) {
			%>
			<td align="left">修正コメント:</td>
			<td colspan="3"><%=historyList.get(12)%></td>
			<%
				}
			%>
		</tr>
	</table>
	<table class="table1">
		<tr>
			<td colspan="5"><hr></td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;＜承認履歴＞</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr height="30" class="table2">
			<td align="left" valign="bottom" class="table2">&nbsp;申請番号</td>
			<td align="center" valign="bottom" class="table2">承認者</td>
			<td align="left" valign="bottom" class="table2">&nbsp;承認者コメント&nbsp;</td>
			<td align="left" valign="bottom" class="table2">&nbsp;承認日時</td>
			<td align="left" valign="bottom" class="table2">&nbsp;ステータス&nbsp;</td>
		</tr>
		<%
			for (ArrayList<String> list : flowList) {
		%>
		<tr>
			<td align="center" class="table2">&nbsp;<%=list.get(0)%></td>
			<td class="table2">&nbsp;<%=list.get(1)%></td>
			<td class="table2">&nbsp;<%=list.get(2)%></td>
			<td class="table2">
				<%
					String approvedDate = "";
				if (!list.get(3).equals("")) {
					approvedDate = list.get(3).substring(0, 4) + "年" + list.get(3).substring(4, 6) + "月"
					+ list.get(3).substring(6, 8) + "日 " + list.get(3).substring(8, 10) + "時" + list.get(3).substring(10, 12)
					+ "分";
				}
				%>&nbsp;<%=approvedDate%></td>
			<td class="table2">
				<%
					if (list.get(4).equals("")) {
				%>&nbsp;承認待ち<%
					} else {
				%>&nbsp;<%=list.get(4)%> <%
 	}
 %>
			</td>
		</tr>
		<%
			}
		%>
	</table>
	<br>
	<table class="table1">
		<tr>
			<td><form action="approveFixAction.jsp" method="post">
					<span style="margin-right: 50px"> <%
 	if (!historyList.get(7).equals("承認完了")) {
 %> <input type="submit" value=" 修正 " class="btn"> <%
 	}
 %>
					</span>
				</form></td>
			<td>
				<form action="approveDeleteAction.jsp" method="post">
					<span style="margin-right: 100px"><input type="submit"
						value=" 取消 " class="btn"></span>
				</form>
			</td>
			<td>
				<button onclick="history.back();">&nbsp;戻る&nbsp;</button>
			</td>

			<%
				} catch (Exception e) {
				response.sendRedirect("login.jsp");
			}
			%>
		</tr>
	</table>
</body>
</html>