<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.BufferedReader"
	import="java.io.FileNotFoundException" import="java.io.IOException"
	import="java.nio.charset.Charset" import="java.nio.file.Files"
	import="java.nio.file.Paths" import="java.util.ArrayList"
	import="java.util.Collections" import="com.workflow.Keyword"%>
<%@ page session="true"%>
<%
	try {
	if (session.equals(null)) {
		throw new Exception();
	}
	// 社員番号取得
	String id = (String) session.getAttribute("id");
	@SuppressWarnings("unchecked")
	ArrayList<ArrayList<String>> list = (ArrayList<ArrayList<String>>) session.getAttribute("list");
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
	<div align="center">有給休暇取得申請システム 承認画面</div>
	<br>
	<%
		if (session.getAttribute("statusError") != null) {
	%>
	<div align="center">
		<font color="red">申請が申請者によって<%=session.getAttribute("statusError")%>されました。<br>新しい申請番号よりもう一度操作をお願いいたします。
		</font>
	</div>
	<br>
	<%
		}
	%>
	<div align="center">
		<font color="red">未処理の申請<%=session.getAttribute("approvedItems")%>件
		</font>
	</div>
	<br>
	<form action="SessionCreate" method="post">
		<table>
			<tr height="30">
				<td align="left" valign="bottom">チェック</td>
				<td align="left" valign="bottom">申請番号</td>
				<td align="left" valign="bottom">所属</td>
				<td align="left" valign="bottom">氏名</td>
				<td align="left" valign="bottom">有給種別</td>
				<td align="left" valign="bottom">ステータス</td>
			</tr>

			<!-- テーブルにリストのデータを入れる -->

			<%
				for (int i = 0; i < list.size(); i++) {
				String number = list.get(i).get(0);
			%>
			<tr>
				<td align="center">
					<!-- １番目のデータをデフォルトにする --> <%
 	if (i == 0) {
 %> <input type="radio" name="radio" id="radio" value="<%=number%>"
					class="form-control" checked> <%
 	} else {
 %> <input type="radio" name="radio" id="radio" value="<%=number%>"
					class="form-control"> <%
 	}
 %>
				</td>
				<td align="left">&nbsp;<%=number%>&nbsp;
				</td>
				<!-- 各列項目に入れるデータを取得 -->

				<td align="left">&nbsp;<%=list.get(i).get(6)%>&nbsp;
				</td>
				<td align="left">&nbsp;<%=list.get(i).get(5)%>&nbsp;
				</td>
				<td align="left"><%=Keyword.type((String) list.get(i).get(2))%>&nbsp;
				</td>
				<td align="left">&nbsp;<%=list.get(i).get(14)%></td>
			</tr>
			<%
				}
			%>
		</table>
		<!-- ループ終了後に実行ボタン表示 -->
		<div align="center">
			<br> <span style="margin-right: 200px"><input
				type="submit" value=" 実行 " class="btn"></span>
			<button type="button" onclick="history.back()">&nbsp;戻る&nbsp;</button>
		</div>
	</form>
	<%
		} catch (Exception e) {
		response.sendRedirect("login.jsp");
	}
	%>
</body>
</html>