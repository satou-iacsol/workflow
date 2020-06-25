<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ page import = "java.util.ArrayList" %>
<%
	// Servletのデータ受け取り
request.setCharacterEncoding("UTF8");
final String referenceDirectory = (String) session.getAttribute("referenceDirectory");
@SuppressWarnings("unchecked")
ArrayList<ArrayList<String>> belongs_lists = (ArrayList<ArrayList<String>>) session.getAttribute("belongs_lists");
String belongsN = (String)session.getAttribute("belongsN");
String belongsC = (String)session.getAttribute("belongsC");
String app1 = (String)session.getAttribute("app1");
String app2 = (String)session.getAttribute("app2");
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
	<h1 class="h1">部署マスタメンテナンス</h1>
	<div class="content">
		<div class="selectionE">
			<form action="<%=request.getContextPath()%>/Belongs_DB_Import" method="post" id="detClea">
				部署選択：<select class="select" name="select" id="select">
				<option value="">--- 更新・削除する場合は選択してください ---</option>
				<% for(int i = 0;i < belongs_lists.size();i++){ %>
					<option value=<%=belongs_lists.get(i).get(1)%>><%=belongs_lists.get(i).get(1) %></option>
				<%} %>
			</select>
			<button type="submit" id="deterbtn" name="submitbtn" value="determination">決定</button>
			<button type="submit" id="cleabtn" name="submitbtn" value="clea">クリア</button>
			</form>
			<p class="warnning_note">※新規登録の場合は未選択のまま、下記項目へ入力してください</p>
		</div>
			<div class="item">
		<form action="<%=request.getContextPath()%>/Belongs_DB_Import" method="post" id="Belongs_DB_Import" onsubmit="return check()">
				<div class="belongs_code" id="belongs_code">
					<div class="type">部署コード：</div><input type="text" id="belongsC" name="belongsC" maxlength="4" <%if(flag_M.equals("1")){ %>readonly<%} %>
					<%if(belongsC != null){ %>value=<%=belongsC%><%} %>>
				</div>
				<div class="belongs_name" id="belongs_name">
					<div class="type">部署名：</div><input type="text" id="belongsN" name="belongsN" maxlength="50"
					<%if(belongsN != null){ %>value=<%=belongsN %><%} %>>
				</div>
				<div class="approver_1" id="approver_1">
					<div class="type">承認者1(社員番号)：</div><input type="text" id="app1" name="app1" maxlength="4"
					<%if(app1 != null){ %>value=<%=app1 %><%} %>>
				</div>
				<div class="approver_2" id="approver_2">
					<div class="type">承認者2(社員番号)：</div><input type="text" id="app2" name="app2" maxlength="4"
					<%if(app2 != null){ %>value=<%=app2 %><%} %>>
				</div>
					<%if(uploadResult != null){ %><%=uploadResult%><%} %>
		</form>
			</div>
		<div class="csv">
			<form action="DownloadCSV" method="post">
				<div class="button">
					<button type="submit" id="buttonCSV" name="buttonCSV" value="belongsCSV" onClick="downloadCSV.submit();">.csvダウンロード</button>
				</div>
			</form>
			<form action="UploadBelongsCSV" method="post" enctype="multipart/form-data">
				<div class="button">
					<input type="file" id="upFile" name="upCSV" /><button id="csvbtn"type="submit">.csvアップロード</button>
				</div>
			</form>
		</div>
			<div class="actionbtn">
			<div class="button">
				<button type="submit" id="newbtn" name="submitbtn" form="Belongs_DB_Import" value="new">新規登録</button>
				<button type="submit" id="updatebtn" name="submitbtn" form="Belongs_DB_Import" value="update">更新</button>
				<button type="submit" id="deletebtn" name="submitbtn" form="Belongs_DB_Import" value="delete">削除</button>
				<button id="backbtn" onclick="location.href='menu.jsp'">もどる</button>
			</div>
			</div>
	</div><!-- content -->
	<script src="jquery-3.5.1.min.js"></script>
	<script>
		let code = document.getElementById("belongsC").value;
		let name = document.getElementById("belongsN").value;
		let app1 = document.getElementById("app1").value;
		let app2 = document.getElementById("app2").value;
		//社員選択にて、社員が選択された時、決定ボタンの背景色をピンクに変更
		$(document).on('change','#select',function(){
			$('#deterbtn').css({'background-color':'pink'});
		});
		//すべての項目に対して、入力が行われたらクリアボタンを水色に変更
		$(document).on('change','#belongsC,#belongsN,#app1,#app2',function(){
			$('#cleabtn').css({'background-color':'#8BD1FA'});
		});
		//社員番号に文字が入力されていたらクリアボタンを水色に変更
		if(code != ""){
			document.getElementById('cleabtn').style.backgroundColor="#8BD1FA";
		}

		//項目が未入力のまま、新規登録ボタンを押下された場合、アラート出力
		function check(){
			let code = document.getElementById("belongsC").value;
			let name = document.getElementById("belongsN").value;
			let app1 = document.getElementById("app1").value;
			let app2 = document.getElementById("app2").value;

			if(code == "" || name == "" || app1 == "" || app2 == ""){
				if(code == ""){
					document.getElementById("belongs_code").style.color="red";
				}else{
					document.getElementById("belongs_code").style.color="black";
				}
				if(name == ""){
					document.getElementById("belongs_name").style.color="red";
				}else{
					document.getElementById("belongs_name").style.color="black";
				}
				if(app1 == ""){
					document.getElementById("approver_1").style.color="red";
				}else{
					document.getElementById("approver_1").style.color="black";
				}
				if(app2 == ""){
					document.getElementById("approver_2").style.color="red";
				}else{
					document.getElementById("approver_2").style.color="black";
				}

				alert("入力内容を確認してください。");
				return false;
			}else{
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