<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.BufferedReader"
	import="java.io.FileNotFoundException" import="java.io.IOException"
	import="java.nio.charset.Charset" import="java.nio.file.Files"
	import="java.nio.file.Paths"%>
<%@ page session="true"%>
<%
	final String referenceDirectory = (String) session.getAttribute("referenceDirectory");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"></meta>
<title>有給休暇取得申請システム</title>
<style>
table, td, th {
	border: 0px black solid;
}

table {
	align: center;
	margin: 0 auto;
}
</style>
</head>
<body>
	<br>
	<table>
		<tr>
			<td>有給休暇取得申請システム メニュー画面</td>
		</tr>
		<tr>
			<td><br></td>
		</tr>
		<tr>
			<td><br></td>
		</tr>
		<tr>
			<td>
				<form action="shinseisho.jsp" method="post">
					<input type="submit" value=" 申請画面 " class="btn">
				</form> <br> <br> <br>
			<form action="approveChoose.jsp" method="post">
					<input type="submit" value=" 承認画面 " class="btn">
				</form>
			</td>
		</tr>
	</table>
</body>
</html>