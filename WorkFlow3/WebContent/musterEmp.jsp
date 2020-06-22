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
String flag_M = (String)session.getAttribute("flag_M");
String uploadResult = (String)session.getAttribute("uploadResult");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<link rel="icon" href="./imge/favicon.ico">
<title>マスタ管理</title>
<link rel="stylesheet" href="menu.css">
</head>
<body>
	<h1 class="h1">社員マスタメンテナンス</h1>
	<div class="content">
		<div class="selectionE">
			<form action="Muster_DB_Import" method="post">
			社員選択：<select class="select" name="select" id="select">
				<option value="">--- 更新・削除する場合は選択してください ---</option>
				<% for(int i = 0;i < lists.size();i++){ %>
					<option value=<%=lists.get(i).get(3)%>><%=lists.get(i).get(3) %></option>
				<%} %>
			</select>
			<button type="submit" id="deterbtn" name="submitbtn" value="determination">決定</button>
			<button type="submit" id="cleabtn" name="submitbtn" value="clea">クリア</button>
			</form>
			<p class="warnning_note">※新規登録の場合は未選択のまま、下記項目へ入力してください</p>
		</div>
		<form action="Muster_DB_Import" method="post" onsubmit="return check()">
			<div class="item">
				<div class="employee_number" id="employee_number">
					社員番号　：<input type="text" id="numberE" name="numberE" maxlength="4" <%if(flag_M.equals("1")){ %>disabled<%} %>
					<%if(id_M != null){ %>value=<%=id_M %><%} %>>
				</div>
				<div class="fullname" id="fullname">
					氏　　　名：<input type="text" id="nameF" name="nameF" maxlength="10"
					<%if(fullname_M != null){ %>value=<%=fullname_M %><%} %>>
				</div>
				<div class="password" id="passWord">
					パスワード：<input type="text" id="password" name="password" maxlength="8"
					<%if(pass_M != null){ %>value=<%=pass_M %><%} %>>
				</div>
				<div class="approval" id="approval">
					承認権限　：<input type="text" id="approvalP" name="approvalP" maxlength="1"
					<%if(authority_M != null){ %>value=<%=authority_M %><%} %>>
				</div>
				<div class="affiliation" id="affiliation">
					所属コード：<input type="text" id="affiliationC" name="affiliationC" maxlength="4"
					<%if(affiliationcode_M != null){ %>value=<%=affiliationcode_M %><%} %>>
				</div>
				<div class="user_name" id="user_name">
					ユーザー名：<input type="text" id="userN" name="userN" maxlength="50"
					<%if(username_M != null){ %>value=<%=username_M %><%} %>>
					<p class="slack_name">(slack)</p>
					<%if(uploadResult != null){ %><%=uploadResult%><%} %>
				</div>
			</div>
			<div class="button">
				<button type="submit" id="newbtn" name="submitbtn" value="new">新規登録</button>
				<button type="submit" id="updatebtn" name="submitbtn" value="update">更新</button>
				<button type="submit" id="deletebtn" name="submitbtn" value="delete">削除</button>
			</div>
		</form>
		<form action="DownloadCSV" method="post">
			<div class="button">
				<button type="submit"  name="buttonCSV" value="employeeCSV" onClick="downloadCSV.submit();">.csvダウンロード</button>
			</div>
		</form>
		<form action="UploadEmployeeCSV" method="post" enctype="multipart/form-data">
			<div class="button">
				<input type="file" name="upCSV" /><button type="submit">.csvアップロード</button>
			</div>
		</form>

		<button onclick="location.href='menu.jsp'">もどる</button>
	</div>
	<script src="jquery-3.5.1.min.js"></script>
	<script>
		let num = document.getElementById("numberE").value;
		let name = document.getElementById("nameF").value;
		let pass = document.getElementById("password").value;
		let app = document.getElementById("approvalP").value;
		let aff = document.getElementById("affiliationC").value;
		let user = document.getElementById("userN").value;
		//社員選択にて、社員が選択された時、決定ボタンの背景色をピンクに変更
		$(document).on('change','#select',function(){
			$('#deterbtn').css({'background-color':'pink'});
		});
		//すべての項目に対して、入力が行われたらクリアボタンを水色に変更
		$(document).on('change','#numberE,#nameF,#password,#approvalP,#affiliationC,#userN',function(){
			$('#cleabtn').css({'background-color':'#8BD1FA'});
		});
		//社員番号に文字が入力されていたらクリアボタンを水色に変更
		if(num != ""){
			console.log("test");
			document.getElementById('cleabtn').style.backgroundColor="#8BD1FA";
		}

		//項目が未入力のまま、新規登録ボタンを押下された場合、アラート出力
		function check(){

			if(num == "" || name == "" || pass == "" || app == "" || app != "0" || "1" || "2"|| aff == "" || user == ""){
				if(num == ""){
					document.getElementById("employee_number").style.color="red";
				}else{
					document.getElementById("employee_number").style.color="black";
				}
				if(name == ""){
					document.getElementById("fullname").style.color="red";
				}else{
					document.getElementById("fullname").style.color="black";
				}
				if(pass == ""){
					document.getElementById("passWord").style.color="red";
				}else{
					document.getElementById("passWord").style.color="black";
				}
				if(app == "" || app != "0" || "1" || "2"){
					document.getElementById("approval").style.color="red";
				}else{
					document.getElementById("approval").style.color="black";
				}
				if(aff == ""){
					document.getElementById("affiliation").style.color="red";
				}else{
					document.getElementById("affiliation").style.color="black";
				}
				if(user == ""){
					document.getElementById("user_name").style.color="red";
				}else{
					document.getElementById("user_name").style.color="black";
				}

				alert("入力内容を確認してください。");
				return false;
			}else{
				return true;
			}
		}

	</script>
</body>
</html>