<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page session="true"%>
<%
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
	
	String type = (String) session.getAttribute("type");
	String date_1 = (String) session.getAttribute("date_1");
	String date_2 = (String) session.getAttribute("date_2");
	String date_3 = (String) session.getAttribute("date_3");
	String time_1 = (String) session.getAttribute("time_1");
	String date_4 = (String) session.getAttribute("date_4");
	String time_2 = (String) session.getAttribute("time_2");
	String comment = (String) session.getAttribute("comment");
	String tellnumber = (String) session.getAttribute("tellnumber");
	String bikou = (String) session.getAttribute("bikou");
	String approver = (String) session.getAttribute("approver");
	String approver_1_2 = (String) session.getAttribute("approver_1_2");
	String flag = (String) session.getAttribute("flag");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>確認画面</title>
<link rel="stylesheet" href="shinsei.css">
</head>
<body>
	<form action="<%=request.getContextPath()%>/Output" method="post">
		<div class="hoge">
			<h1>有給休暇取得申請システム　申請確認画面</h1>
		</div>
		<br>
		<div class="margin">
		<p>所属:　<%=affiliationName%></p>
		<p>氏名:　<%=fullname%></p>
		
		有給種別:　<%=type%>
		<br>
		取得期間:　<%=date_1 %>　 ～ 　<%=date_2 %>
		<br>
		取得日時:　<%=date_3 %>/<%=time_1 %>　～　<%=date_4 %>/<%=time_2 %>
		<br>
		取得事由:　<%=comment %>
		<br>
		連絡先　:　<%=tellnumber %>
		<br>
		備考　　:　<%=bikou %>　　　　
		<br>
		承認者　:　<%=approver_1_2 %>
		<br>
		一次承認者スキップ:　<%=flag %>
		<br>
		<br>
		<button type="submit">申請</button>
		<input value="もどる" onclick="history.back();" type="button">
		</div>
	</form>

</body>
</html>