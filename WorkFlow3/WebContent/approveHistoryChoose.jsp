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
.table {
	border-collapse: collapse;
	border: 1px black solid;
	align: center;
	margin: auto;
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
		<img src="https://www.homepage-tukurikata.com/image/hanikami.jpg"
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
	<div align="center">有給休暇取得申請システム 申請一覧画面</div>
	<br>
	<%
		if (session.getAttribute("statusError") != null) {
	%>
	<div align="center">
		<font color="red">申請が申請者によって<%=session.getAttribute("statusError")%>されました。
		</font>
	</div>
	<br>
	<%
		}
	%>
	<%
		if (!(session.getAttribute("statusError").equals(null))) {
	%>
	<div align="center">
		<font color="red">申請が承認者によって<%=session.getAttribute("statusError")%>されました。
		</font>
	</div>
	<%
		}
	%>
	<br>
	<table class="table">
		<tr height="30">
			<td align="left" valign="bottom" class="table">&nbsp;申請番号</td>
			<td align="left" valign="bottom" class="table">&nbsp;有給種別</td>
			<td align="left" valign="bottom" class="table">&nbsp;取得日時</td>
			<td align="left" valign="bottom" class="table">&nbsp;ステータス</td>
			<td align="left" valign="bottom" class="table" colspan="2">&nbsp;修正･取消</td>
		</tr>

		<!-- テーブルにリストのデータを入れる -->

		<%
			for (int i = 0; i < historysList.size(); i++) {
			String[] list = new String[8];
			for (int j = 0; j < historysList.get(i).size(); j++) {
				list[j] = historysList.get(i).get(j);
			}
		%>
		<tr class="table">
			<!-- 各列項目に入れるデータを取得 -->

			<td align="left" valign="top" class="table">&nbsp;<%=list[0]%>&nbsp;
			</td>
			<td align="left" valign="top" class="table">&nbsp;<%=Keyword.type(list[2])%>&nbsp;
			</td>
			<td align="left" valign="top" class="table">&nbsp;<%
				String from = list[3].substring(0, 4) + "年" + list[3].substring(4, 6) + "月"
					+ list[3].substring(6, 8) + "日 " + list[3].substring(8, 10) + "時"
					+ list[3].substring(10, 12) + "分";
			%><%=from%>&nbsp;～<br>&nbsp;&nbsp;&nbsp;<%
				String to = list[4].substring(0, 4) + "年" + list[4].substring(4, 6) + "月"
					+ list[4].substring(6, 8) + "日 " + list[4].substring(8, 10) + "時"
					+ list[4].substring(10, 12) + "分";
			%><%=to%>
			</td>
			<td align="center" valign="top" class="table">&nbsp;<%=list[6]%>&nbsp;<br>
				<form action="ApproveHistoryCreate" method="post">
					<input type="hidden" name="number" value=<%=list[1]%>> <input
						type="hidden" name="action" value="history"><input
						type="submit" value="<%=list[7]%>">
				</form></td>
			<td align="right" valign="top">
				<%
					if (list[7].equals("承認完了")) {
				} else {
				%><form action="ApproveHistoryCreate" method="post">
					<input type="hidden" name="number" value=<%=list[1]%>><input
						type="hidden" name="action" value="fix"> <input
						type="submit" value=" 修正 " class="btn">
				</form> &nbsp;&nbsp;&nbsp;<%
 	}
 %>
			</td>
			<td align="right" valign="top">
				<form action="ApproveHistoryCreate" method="post">
					<input type="hidden" name="number" value=<%=list[1]%>><input
						type="hidden" name="action" value="delete"><span
						style="margin-left: 10px"><input type="submit" value=" 取消 "
						class="btn"></span>
				</form>
			</td>
		</tr>
		<%
			}
		%>
	</table>
	<!-- ループ終了後に実行ボタン表示 -->
	<div align="center">
		<br> <span style="margin-right: 700px">
			<button type="button" onclick="history.back()">&nbsp;戻る&nbsp;</button>
		</span>
	</div>

	<%
		} catch (Exception e) {
		response.sendRedirect("login.jsp");
	}
	%>
</body>
</html>