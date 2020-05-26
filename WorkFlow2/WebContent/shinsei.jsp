<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page session="true"%>

<%
	// Shinsei.javaのデータ受け取り
request.setCharacterEncoding("UTF8");
String user = (String) session.getAttribute("user");
String authority = (String) session.getAttribute("authority");
String fullname = (String) session.getAttribute("fullname");
String affiliationCode = (String) session.getAttribute("affiliationCode");
String mail = (String) session.getAttribute("mail");
String affiliationName = (String) session.getAttribute("affiliationName");
String approverNumber_1 = (String) session.getAttribute("approverNumber_1");
String approverName_1 = (String) session.getAttribute("approverName_1");
String approverNumber_2 = (String) session.getAttribute("approverNumber_2");
String approverName_2 = (String) session.getAttribute("approverName_2");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>申請画面</title>
<link rel="stylesheet" href="shinsei.css">
</head>
<body>
	<div class="hoge">
		<h1>有給休暇取得申請システム 申請画面</h1>
	</div>

	<br>
	<div class="margin">
		<form action="<%=request.getContextPath()%>/Confirmation"
			method="post" id="Confirmation">
			<p>
				所属:<%=affiliationName%></p>
			<p>
				氏名:<%=fullname%></p>

			有給種別: <select name="type">
				<option value=有給休暇>有給休暇</option>
				<option value=代休>代休</option>
				<option value=生理休暇>生理休暇</option>
				<option value=慶弔休暇>慶弔休暇</option>
				<option value=特別休暇>特別休暇</option>
				<option value=罹災休暇>罹災休暇</option>
				<option value=半休>半休</option>
				<option value=結婚休暇>結婚休暇</option>
				<option value=出産休暇>出産休暇</option>
				<option value=忌引き休暇>忌引き休暇</option>
				<option value=隔離休暇>隔離休暇</option>
				<option value=一周忌>一周忌</option>
				<option value=受験休暇>受験休暇</option>
				<option value=産前産後休暇>産前産後休暇</option>
			</select> <br> 取得期間: <label class="date-edit"><input type="date"
				name="date_1" required="required"></label> ～ <label
				class="date-edit"><input type="date" name="date_2"
				required="required"></label> <br> 取得日時: <label
				class="date-edit"><input type="date" name="date_3"
				required="required"></label> <input type="time" name="time_1"
				required="required"> ～ <label class="date-edit"><input
				type="date" name="date_4" required="required"></label> <input
				type="time" name="time_2" required="required"> <br> <br>

			取得事由:
			<textarea maxlength="50" name="comment" cols="50" rows="1" required="required"></textarea>
			<br> 連絡先　: <input maxlength="11" type="tel" name="tellnumber" id="number" onKeyup="this.value=this.value.replace(/[^0-9]+/i,'')"
				required="required" /> <br> 備考　　:
			<textarea maxlength="50" name="bikou" cols="50" rows="1"></textarea>
			<br> 承認者　: <select name="approver">
				<option value="<%=approverName_1%>"><%=approverName_1%></option>
				<option value="<%=approverName_2%>"><%=approverName_2%></option>
			</select> 一次承認者スキップ: <select name="flag">
				<option value="0">0(無)</option>
				<option value="1">1(有)</option>
			</select> <br> <br>

		</form>
				<button type="submit" form="Confirmation">確認</button>
				<button onclick="history.back();">キャンセル</button>
		</div>

</body>
</html>