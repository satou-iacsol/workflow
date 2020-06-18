<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page session="true"%>
<%@ page import = "java.util.ArrayList" %>
<%
	// Servletのデータ受け取り
request.setCharacterEncoding("UTF8");
final String referenceDirectory = (String) session.getAttribute("referenceDirectory");
@SuppressWarnings("unchecked")
ArrayList<ArrayList<String>> lists = (ArrayList<ArrayList<String>>) session.getAttribute("lists");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>マスタ管理</title>
<link rel="stylesheet" href="menu.css">
</head>
<body>
	<%=session.getAttribute("test") %>
	<h1 class="h1">社員マスタメンテナンス</h1>
	<div class="content">
		<div class="selectionE">
			社員選択：<select class="select">
				<option value="">--- 更新・削除する場合は選択してください ---</option>
			</select>
			<p class="warnning_note">※新規登録の場合は未選択のまま、下記項目へ入力してください</p>
		</div>
		<form action="#" method="post">
			<div class="item">
				<div class="employee_number">
					社員番号　：<input type="text" id="numberE" name="numberE">
				</div>
				<div class="employee_number">
					氏　　　名：<input type="text" id="nameF" name="nameF">
				</div>
				<div class="employee_number">
					パスワード：<input type="text" id="password" name="password">
				</div>
				<div class="employee_number">
					承認権限　：<input type="text" id="approvalP" name="approvalP">
				</div>
				<div class="employee_number">
					所属コード：<input type="text" id="affiliationC" name="affiliationC">
				</div>
				<div class="employee_number">
					ユーザー名：<input type="text" id="userN" name="userN">
					<p class="slack_name">(slack)</p>
				</div>
			</div>
			<div class="button">
				<input type="submit" name="new" value="新規登録">
				<input type="submit" name="update" value="更新">
				<input type="submit" name="delete" value="削除">
			</div>
		</form>
		<button onclick="history.back()">もどる</button>
	</div>
</body>
</html>