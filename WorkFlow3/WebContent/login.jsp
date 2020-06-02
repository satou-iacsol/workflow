<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="java.util.Enumeration"%>
<%
// セッションにある全ての要素名を取得する
Enumeration<String> names = session.getAttributeNames();

// 取得した要素名をループ処理で全て削除する
while (names.hasMoreElements()) {
	String name = (String) names.nextElement();
	session.removeAttribute(name);
}
// csvのディレクトリを設定
session.setAttribute("referenceDirectory", "C:/Users/Yusuke/git/workflow/WorkFlow3/WebContent/");
%>
<html>
<head>
<meta charset="UTF-8"></meta>
<title>有給休暇取得申請システム</title>
<style>
table, td, th {
	border: 0px;
}

table {
	align: center;
	margin: 0 auto;
}
</style>
</head>
<body>
	<br>
	<form action="Login" method="post">
		<table>
			<tr>
				<td colspan="2">有給休暇取得申請システム ログイン</td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><font color="red"> <%
 	if (!(session.getAttribute("loginError") == null)) {
 %> <%=session.getAttribute("loginError")%> <%
 	session.setAttribute("loginError", "");
 }
 %>
				</font><br></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="center">社員番号:</td>
				<td align="center"><input type="text" name="id" id="id"
					class="form-control"></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="center">パスワード:</td>
				<td align="center"><input type="password" name="pass" id="pass"
					class="form-control"></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value=" ログイン " class="btn"></td>
			</tr>
		</table>
	</form>
</body>
</html>