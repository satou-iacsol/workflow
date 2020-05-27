<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.BufferedReader"
	import="java.io.FileNotFoundException" import="java.io.IOException"
	import="java.nio.charset.Charset" import="java.nio.file.Files"
	import="java.nio.file.Paths"%>
<%@ page session="true"%>
<%
	request.setCharacterEncoding("UTF-8");
response.setContentType("text/html;charset=UTF-8");
final String referenceDirectory = (String) session.getAttribute("referenceDirectory");
%>
<!DOCTYPE html>
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
	<form action="ApproveActionCheck" method="post">
		<table style="border: 0">
			<tr>
				<td colspan="2" align="center">有給休暇取得申請システム 承認明細画面</td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="left">申請番号:</td>
				<td><%=session.getAttribute("approvedNumber")%></td>
			</tr>
			<tr>
				<td align="left">所属:</td>
				<td><%=session.getAttribute("approvedBelongs")%></td>
			</tr>
			<tr>
				<td align="left">氏名:</td>
				<td><%=session.getAttribute("approvedName")%></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="left">有給種別:</td>
				<td><%=session.getAttribute("approvedType")%></td>
			</tr>
			<tr>
				<td align="left">取得期間:</td>
				<td><%=session.getAttribute("approvedDate")%></td>
			</tr>
			<tr>
				<td align="left">取得日時:</td>
				<td><%=session.getAttribute("approvedTime")%></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="left">取得事由:</td>
				<td><%=session.getAttribute("approvedReason")%></td>
			</tr>
			<tr>
				<td align="left">連絡先:</td>
				<td><%=session.getAttribute("approvedAddress")%></td>
			</tr>
			<tr>
				<td align="left">備考:</td>
				<td><%=session.getAttribute("approvedRemarks")%></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><font color="red"> <%
 	if (!(session.getAttribute("approvedCommentError") == null)) {
 %> <%=session.getAttribute("approvedCommentError")%> <%
 	}
 %>
				</font><br></td>
			</tr>

			<!-- 承認者２ではない時表示 -->
			<%
				if (!session.getAttribute("id").equals(session.getAttribute("approve2Id"))) {
			%>

			<tr>
				<td align="left">承認者:</td>
				<td><%=session.getAttribute("approve2Name")%></td>
			</tr>
			<tr>
				<td align="left">承認者１コメント:</td>
				<td><textarea name="comment" rows="1" cols="28" maxlength="50"></textarea></td>
			</tr>

			<!-- 申請データの連番が01の時に非表示 -->
			<%
				if (!((String) session.getAttribute("approvedNumber")).substring(14).equals("01")) {
			%>
			<tr>
				<td align="left">承認者２コメント:</td>
				<td><%=session.getAttribute("preComment")%></td>
			</tr>
			<%
				}
			} else {
			%>

			<!-- 承認者２の時表示 -->
			<tr>
				<td align="left">承認者１コメント:</td>
				<td><%=session.getAttribute("preComment")%> <%

 %></td>
			</tr>
			<tr>
				<td align="left">承認者２コメント:</td>
				<td><textarea name="comment" rows="1" cols="28" maxlength="50"></textarea></td>
			</tr>

			<%
				}
			%>

			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td colspan="2">
					<!-- ステータスが空白の時ラジオボタンと確認ボタン表示 --> <%
 	if (session.getAttribute("approvedStatus").equals("")) {
 	if (session.getAttribute("approvedCommentError") == null || session.getAttribute("approvedCommentError").equals("")) {
 %> <input type="radio" name="action" id="承認" value="承認" checked><label
					for="承認">&nbsp;承認&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input
					type="radio" name="action" id="差戻" value="差戻"><label
					for="差戻">&nbsp;差戻</label> <%
 	} else {
 %> <input type="radio" name="action" id="承認" value="承認"><label
					for="承認">&nbsp;承認&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input
					type="radio" name="action" id="差戻" value="差戻" checked><label
					for="差戻">&nbsp;差戻</label> <%
 	session.setAttribute("approvedCommentError", "");
 }
 %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
					type="submit" value=" 確認 " class="btn"> <%
 	}
 %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="button" onclick="history.back()">&nbsp;戻る&nbsp;</button>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>