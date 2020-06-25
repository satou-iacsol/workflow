<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ page import="java.util.ArrayList"%>
<%
	// Servletのデータ受け取り
request.setCharacterEncoding("UTF8");
final String referenceDirectory = (String) session.getAttribute("referenceDirectory");
@SuppressWarnings("unchecked")
ArrayList<ArrayList<String>> lists = (ArrayList<ArrayList<String>>) session.getAttribute("lists");
String fullname_M = (String) session.getAttribute("fullname_M");
String id_M = (String) session.getAttribute("id_M");
String pass_M = (String) session.getAttribute("pass_M");
String authority_M = (String) session.getAttribute("authority_M");
String username_M = (String) session.getAttribute("username_M");
String affiliationcode_M = (String) session.getAttribute("affiliationcode_M");
String flag_M = (String) session.getAttribute("flag_M");
String uploadResult = (String) session.getAttribute("uploadResult");
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
			<div class="deter_select">
			<form action="Employee_DB_Import" method="post" id="detClea">
				社員選択：<select class="select" name="select" id="select">
					<option value="">--- 更新・削除する場合は選択してください ---</option>
					<%
						for (int i = 0; i < lists.size(); i++) {
					%>
					<option value=<%=lists.get(i).get(3)%>><%=lists.get(i).get(3)%></option>
					<%
						}
					%>
				</select>
				<button type="submit" id="deterbtn" name="submitbtn"
					value="determination">決定</button>
				<button type="submit" id="cleabtn" name="submitbtn" value="clea">クリア</button>
			</form>
			</div>
			<p class="warnning_note">※新規登録の場合は未選択のまま、下記項目へ入力してください</p>
		</div>
		<form action="Employee_DB_Import" method="post" id="Employee_DB_Import"
			onsubmit="return check()">
			<div class="item">
				<div class="employee_number" id="employee_number">
					<div class="type">社員番号：</div>
					<input type="text" id="numberE" name="numberE" maxlength="4" onKeyup="this.value=this.value.replace(/[^0-9]+/i,'')"
						<%if (flag_M.equals("1")) {%> readonly <%}%>
						<%if (id_M != null) {%> value=<%=id_M%> <%}%>>
				</div>
				<div class="fullname" id="fullname">
					<div class="type">氏 名：</div>
					<input type="text" id="nameF" name="nameF" maxlength="10"
						<%if (fullname_M != null) {%> value=<%=fullname_M%> <%}%>>
				</div>
				<div class="password" id="passWord">
					<div class="type">パスワード：</div>
					<input type="text" id="password" name="password" maxlength="8"
						<%if (pass_M != null) {%> value=<%=pass_M%> <%}%>>
				</div>
				<div class="approval" id="approval">
					<div class="type">承認権限 ：</div>
					<select class="approval_select">
						<option value="0" <%if (authority_M != null && authority_M.equals("0")) {%> selected <%}%>>0(承認権限なし)</option>
						<option value="1" <%if (authority_M != null && authority_M.equals("1")) {%> selected <%}%>>1(承認権限あり)</option>
						<option value="2" <%if (authority_M != null && authority_M.equals("2")) {%> selected <%}%>>2(管理者権限)</option>
					</select>
				</div>
				<div class="affiliation" id="affiliation">
					<div class="type">所属コード：</div>
					<input type="text" id="affiliationC" name="affiliationC" onKeyup="this.value=this.value.replace(/[^0-9]+/i,'')"
						maxlength="4" <%if (affiliationcode_M != null) {%>
						value=<%=affiliationcode_M%> <%}%>>
				</div>
				<div class="user_name" id="user_name">
					<div class="type">ユーザー名：</div>
					<input type="text" id="userN" name="userN" maxlength="50"
						<%if (username_M != null) {%> value=<%=username_M%> <%}%>>
					<p class="slack_name">(slack)</p>
					<%
						if (uploadResult != null) {
					%><%=uploadResult%>
					<%
						}
					session.setAttribute("uploadResult", "");
					%>
				</div>
			</div>
		</form>
		<div class="csv">
			<form action="DownloadCSV" method="post">
				<div class="button">
					<button type="submit" id="buttonCSV" name="buttonCSV"
						value="employeeCSV" onClick="downloadCSV.submit();">.csvダウンロード</button>
				</div>
			</form>
			<form action="UploadEmployeeCSV" method="post"
				enctype="multipart/form-data">
				<div class="button">
					<input type="file" id="upFile" name="upCSV" />
					<button id="csvbtn" type="submit">.csvアップロード</button>
				</div>
			</form>
		</div>
		<div class="actionbtn">
			<div class="button">
				<button type="submit" id="newbtn" form="Employee_DB_Import"
					name="submitbtn" value="new">新規登録</button>
				<button type="submit" id="updatebtn" form="Employee_DB_Import"
					name="submitbtn" value="update">更新</button>
				<button type="submit" id="deletebtn" form="Employee_DB_Import"
					name="submitbtn" value="delete">削除</button>
				<button id="backbtn" onclick="location.href='menu.jsp'">もどる</button>
			</div>
		</div>
	</div>
	<script src="jquery-3.5.1.min.js"></script>
	<script>
		let num = document.getElementById("numberE").value;
		//社員選択にて、社員が選択された時、決定ボタンの背景色をピンクに変更
		$(document).on('change', '#select', function() {
			$('#deterbtn').css({
				'background-color' : 'pink'
			});
		});
		//すべての項目に対して、入力が行われたらクリアボタンを水色に変更
		$(document).on('change',
				'#numberE,#nameF,#password,#approvalP,#affiliationC,#userN',
				function() {
					$('#cleabtn').css({
						'background-color' : '#8BD1FA'
					});
				});
		//社員番号に文字が入力されていたらクリアボタンを水色に変更
		if (num != "") {
			document.getElementById('cleabtn').style.backgroundColor = "#8BD1FA";
		}

		//項目が未入力のまま、新規登録ボタンを押下された場合、アラート出力
		function check() {
			let num = document.getElementById("numberE").value;
			let name = document.getElementById("nameF").value;
			let pass = document.getElementById("password").value;
			let aff = document.getElementById("affiliationC").value;
			let user = document.getElementById("userN").value;

			if (num == "" || name == "" || pass == "" || aff == "" || user == "") {
				if (num == "") {
					document.getElementById("employee_number").style.color = "red";
				} else {
					document.getElementById("employee_number").style.color = "black";
				}
				if (name == "") {
					document.getElementById("fullname").style.color = "red";
				} else {
					document.getElementById("fullname").style.color = "black";
				}
				if (pass == "") {
					document.getElementById("passWord").style.color = "red";
				} else {
					document.getElementById("passWord").style.color = "black";
				}
				if (aff == "") {
					document.getElementById("affiliation").style.color = "red";
				} else {
					document.getElementById("affiliation").style.color = "black";
				}
				if (user == "") {
					document.getElementById("user_name").style.color = "red";
				} else {
					document.getElementById("user_name").style.color = "black";
				}

				alert("入力内容を確認してください。");
				return false;
			} else {
				return true;
			}
		}

		/*社員選択欄：未選択時のエラー処理*/
		$(document).on('click','#deterbtn',function(){
			let d = document.getElementById("detClea");
			d.setAttribute("onsubmit","return selectCheck()");
		});
		$(document).on('click','#cleabtn',function(){
			let d = document.getElementById("detClea");
			d.removeAttr("onsubmit");
		});

		function selectCheck(){
			let select = document.getElementById('select').value;
				if(select == ""){
					return false;
				}
				return true;
		}
	</script>
</body>
</html>