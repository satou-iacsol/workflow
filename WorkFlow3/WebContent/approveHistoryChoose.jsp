<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"
	import="java.util.Collections"%>
<%@ page session="true"%>
<%
// 社員番号取得
String id = (String) session.getAttribute("id");
// 申請一覧リスト取得
@SuppressWarnings("unchecked")
ArrayList<ArrayList<String>> historysList = (ArrayList<ArrayList<String>>) session.getAttribute("historysList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"></meta>
<title>有給休暇取得申請システム</title>
<style>
h2 {
	text-align: center;
}

table, td, th {
	border-collapse: collapse;
	border: 1px black solid;
}

table {
	align: center;
	margin: 0 auto;
	border: solid 1px;
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
	<div align="center">有給休暇取得申請システム 申請一覧画面</div>
	<br>
	<br>
	<table>
		<tr height="30">
			<td align="left" valign="bottom">&nbsp;申請番号</td>
			<td align="left" valign="bottom">&nbsp;有給種別</td>
			<td align="left" valign="bottom">&nbsp;取得日時</td>
			<td align="left" valign="bottom">&nbsp;ステータス</td>
			<td align="left" valign="bottom">&nbsp;修正･取消</td>
		</tr>

		<!-- テーブルにリストのデータを入れる -->

		<%
			for (int i = 0; i < historysList.size(); i++) {
				String[] list = new String[7];
				for (int j = 0; j < historysList.get(i).size(); j++){
					list[j] = historysList.get(i).get(j);
				}
		%>
		<tr>
			<!-- 各列項目に入れるデータを取得 -->

			<td align="left" valign="top">&nbsp;<%=list[0]%>&nbsp;
			</td>
			<td align="left" valign="top">&nbsp;<%=list[2]%>&nbsp;
			</td>
			<td align="left" valign="top">&nbsp;<%
				String from = list[3].substring(0, 4) + "年" + list[3].substring(4, 6) + "月"
					+ list[3].substring(6, 8) + "日 " + list[3].substring(8, 10) + "時"
					+ list[3].substring(10, 12) + "分";
			%><%=from%>&nbsp;～<br>&nbsp;&nbsp;&nbsp;<%
				String to = list[4].substring(0, 4) + "年" + list[4].substring(4, 6) + "月"
					+ list[4].substring(6, 8) + "日 " + list[4].substring(8, 10) + "時"
					+ list[4].substring(10, 12) + "分";
			%><%=to%>
			</td>
			<td align="center" valign="top">&nbsp;<%=list[5]%>&nbsp;<br>
				<form action="ApproveHistoryCreate" method="post">
					<input type="hidden" name="number" value=<%=list[1]%>> <input
						type="hidden" name="action" value="history"><input
						type="submit" value="<%=list[6]%>">
				</form></td>
			<td align="right" valign="top">
				<form action="ApproveHistoryCreate" method="post" id="delete">
					<input type="hidden" name="number" value=<%=list[1]%>><input
						type="hidden" name="action" value="delete">
				</form> <%
 	if (list[6].equals("承認完了")) {
 } else {
 %><form action="ApproveHistoryCreate" method="post" id="fix">
					<input type="hidden" name="number" value=<%=list[1]%>><input
						type="hidden" name="action" value="fix">
				</form> <input type="submit" value=" 修正 " form="fix">
				&nbsp;&nbsp;&nbsp;<%
					}
				%> <input type="submit" value=" 取消 " form="delete">
			</td>
		</tr>
		<%
			}
		%>
	</table>
	<!-- ループ終了後に実行ボタン表示 -->
	<div align="center">
		<br> <span style="margin-right:700px">
		<button type="button" onclick="history.back()">&nbsp;戻る&nbsp;</button></span>
	</div>

	<%
		} catch (Exception e) {
		response.sendRedirect("login_akashi.jsp");
	}
	%>
</body>
</html>