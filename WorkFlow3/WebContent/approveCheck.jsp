<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%
	request.setCharacterEncoding("UTF-8");
response.setContentType("text/html;charset=UTF-8");
final String referenceDirectory = (String) session.getAttribute("referenceDirectory");
String id = (String) session.getAttribute("id");
session.setAttribute("approvedComment", request.getParameter("comment"));
session.setAttribute("approvedAction", request.getParameter("action"));
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
	<script type="text/javascript">
	<!--
		function logout() {
			if (confirm("ログアウトしますか？")) {
				return true;
			} else {
				return false;
			}
		}
	// -->
	</script>
	<%
		try {
		if (session.equals(null)) {
			throw new Exception();
		}
	%>
	<br>
	<form name="login_logout" action="login.jsp" method="post"
		onsubmit="return logout()">
		<div align="right">
			ログイン：<%=session.getAttribute("fullname")%>
			<input type="submit" value="ログアウト">
		</div>
	</form>
	<form action="Approve" method="post">
		<table style="border: 0">
			<tr>
				<td colspan="2" align="center">有給休暇取得申請システム 承認明細確認画面</td>
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
				<td><br></td>
			</tr>

			<%
				if (!session.getAttribute("id").equals(session.getAttribute("approve2Id"))) {
			%>

			<tr>
				<td align="left">承認者:</td>
				<td><%=session.getAttribute("approve2Name")%></td>
			</tr>
			<tr>
				<td align="left">承認者１コメント:</td>
				<td><%=session.getAttribute("approvedComment")%></td>
			</tr>
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

			<tr>
				<td align="left">承認者１コメント:</td>
				<td><%=session.getAttribute("preComment")%></td>
			</tr>
			<tr>
				<td align="left">承認者２コメント:</td>
				<td><%=session.getAttribute("approvedComment")%></td>
			</tr>

			<%
				}
			%>

			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit"
					value=" <%=session.getAttribute("approvedAction")%> " class="btn">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="button" onclick="history.back()">&nbsp;戻る&nbsp;</button>
				</td>
			</tr>
		</table>
	</form>
	<%
		} catch (Exception e) {
		response.sendRedirect("login.jsp");
	}
	%>
</body>
</html>