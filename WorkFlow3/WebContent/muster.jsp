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
String fullname_M = (String)session.getAttribute("fullname_M");
String id_M = (String)session.getAttribute("id_M");
String pass_M = (String)session.getAttribute("pass_M");
String authority_M = (String)session.getAttribute("authority_M");
String username_M = (String)session.getAttribute("username_M");
String affiliationcode_M = (String)session.getAttribute("affiliationcode_M");

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
			<form action="Muster_DB_Import" method="post">
			社員選択：<select class="select" name="select">
				<option value="">--- 更新・削除する場合は選択してください ---</option>
				<% for(int i = 0;i < lists.size();i++){ %>
					<option value=<%=lists.get(i).get(3)%>><%=lists.get(i).get(3) %></option>

				<%} %>
			</select>
			<button type="submit" name="submitbtn" value="determination">決定</button>
			</form>
			<p class="warnning_note">※新規登録の場合は未選択のまま、下記項目へ入力してください</p>
		</div>
		<form action="Muster_DB_Import" method="post">
			<div class="item">
				<div class="employee_number">
					社員番号　：<input type="text" id="numberE" name="numberE"
					<%if(id_M != null){ %>value=<%=id_M %><%} %>>
				</div>
				<div class="fullname">
					氏　　　名：<input type="text" id="nameF" name="nameF"
					<%if(fullname_M != null){ %>value=<%=fullname_M %><%} %>>
				</div>
				<div class="password">
					パスワード：<input type="text" id="password" name="password"
					<%if(pass_M != null){ %>value=<%=pass_M %><%} %>>
				</div>
				<div class="approval">
					承認権限　：<input type="text" id="approvalP" name="approvalP"
					<%if(authority_M != null){ %>value=<%=authority_M %><%} %>>
				</div>
				<div class="affiliation">
					所属コード：<input type="text" id="affiliationC" name="affiliationC"
					<%if(affiliationcode_M != null){ %>value=<%=affiliationcode_M %><%} %>>
				</div>
				<div class="user_name">
					ユーザー名：<input type="text" id="userN" name="userN"
					<%if(username_M != null){ %>value=<%=username_M %><%} %>>
					<p class="slack_name">(slack)</p>
				</div>
			</div>
			<div class="button">
				<button type="submit" name="submitbtn" value="new">新規登録</button>
				<button type="submit" name="submitbtn" value="update">更新</button>
				<button type="submit" name="submitbtn" value="delete">削除</button>
			</div>
		</form>
		<button onclick="location.href='menu.jsp'">もどる</button>
	</div>
</body>
</html>