<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page session="true"%>
<%
	// Servletのデータ受け取り
request.setCharacterEncoding("UTF8");
final String referenceDirectory = (String) session.getAttribute("referenceDirectory");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>マスタ管理</title>
<link rel="stylesheet" href="menu.css">
</head>
<body>
	<h1 class="h1">社員マスタメンテナンス</h1>
	<div class="content">
		<div class="selectionE">
			社員選択：<select class="select">
				<option value="">--- 更新・削除する場合は選択してください ---</option>
				<option value="">#</option>
			</select>
				<p class="warnning_note">※新規登録の場合は未選択のまま、下記項目へ入力してください</p>
		</div>

	</div>
</body>
</html>