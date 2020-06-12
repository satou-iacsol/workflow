<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.BufferedReader"
	import="java.io.FileNotFoundException" import="java.io.IOException"
	import="java.nio.charset.Charset" import="java.nio.file.Files"
	import="java.nio.file.Paths" import="com.workflow.Keyword"%>
<%@ page session="true"%>
<%
try {
if (session.equals(null)) {
	throw new Exception();
}
	request.setCharacterEncoding("UTF-8");
response.setContentType("text/html;charset=UTF-8");
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

header {
	position: fixed;
	left: 0;
	width: 100%;
}

img {
	float: left;
}

.logoutbutton {
	margin-left: 20px;
	margin-right: 20px;
}
</style>
</head>
<body>
	<header>
		<img src="https://www.homepage-tukurikata.com/image/hanikami.jpg"
			alt="IACロゴ" title="IACロゴ" width="100px" height="25px">
		<form name="login_logout" action="login.jsp" method="post"
			onsubmit="return logout()">
			<div align="right">
				<div>
					<%=session.getAttribute("affiliationName")%>・
					<%=session.getAttribute("fullname")%>
					<input class="logoutbutton" type="submit" value="ログアウト">
				</div>
			</div>
		</form>
		<hr>
	</header>
	<script type="text/javascript">
	<!--
		function formCheck() {
			if (document.approveForm.action.value == "差戻") {
				if (document.approveForm.comment.value == "") {
					document.getElementById('notice').style.display = "block";
					return false;
				} else {
					document.getElementById('notice').style.display = "none";
					return true;
				}
			} else {
				document.getElementById('notice').style.display = "none";
				return true;
			}
		}
		function logout() {
			if (confirm("ログアウトしますか？")) {
				return true;
			} else {
				return false;
			}
		}
	// -->
	</script>
	<br>
	<br>
	<form name="approveForm" action="approveCheck.jsp" method="post"
		onsubmit="return formCheck()">
		<table style="border: 0">
			<tr>
				<td colspan="4" align="center">有給休暇取得申請システム 承認明細画面</td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="left">申請番号:</td>
				<td colspan="3"><%=session.getAttribute("approvedNumber")%></td>
			</tr>
			<tr>
				<td align="left">所属:</td>
				<td colspan="3"><%=session.getAttribute("approvedBelongs")%></td>
			</tr>
			<tr>
				<td align="left">氏名:</td>
				<td colspan="3"><%=session.getAttribute("approvedName")%></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="left">有給種別:</td>
				<td colspan="3"><%=Keyword.type((String)session.getAttribute("approvedType"))%></td>
			</tr>
			<tr>
				<td align="left">取得期間:</td>
				<td><%=session.getAttribute("approvedFromDate")%></td>
				<td>&nbsp;～&nbsp;</td>
				<td><%=session.getAttribute("approvedToDate")%></td>
			</tr>
			<tr>
				<td align="left">取得時間:</td>
				<td><%=session.getAttribute("approvedFromTime")%></td>
				<td>&nbsp;～&nbsp;</td>
				<td><%=session.getAttribute("approvedToTime")%></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="left">取得事由:</td>
				<td colspan="3"><%=session.getAttribute("approvedReason")%></td>
			</tr>
			<tr>
				<td align="left">連絡先:</td>
				<td colspan="3"><%=session.getAttribute("approvedAddress")%></td>
			</tr>
			<tr>
				<td align="left">備考:</td>
				<td colspan="3"><%=session.getAttribute("approvedRemarks")%></td>
			</tr>
			<tr>
				<td colspan="5" align="center"><br>
					<noscript>
						<a><font color="red">差戻時はコメントが必須です</font></a>
					</noscript> <a id="notice" style="display: none; color: red;">
						差戻時はコメントが必須です</a></td>
			</tr>

			<!-- 承認者２ではない時表示 -->
			<%
				if (!session.getAttribute("id").equals(session.getAttribute("approve2Id"))) {
			%>

			<tr>
				<td align="left">承認者:</td>
				<td colspan="3"><%=session.getAttribute("approve2Name")%></td>
			</tr>
			<tr>
				<td align="left" colspan="4">承認者１コメント: <%
					if (session.getAttribute("approvedStatus").equals("")) {
				%><textarea name="comment" rows="1" cols="28" maxlength="50"></textarea>
					<%
						} else {
					%> <%=session.getAttribute("approvedOverComment")%> <%
 	}
 %>
				</td>
			</tr>

			<!-- 申請データの連番が01の時に非表示 -->
			<%
				if (!((String) session.getAttribute("approvedNumber")).substring(14).equals("01")) {
			%>
			<tr>
				<td align="left" colspan="4">承認者２コメント:<%=session.getAttribute("preComment")%></td>
			</tr>
			<%
				}
			} else {
			%>

			<!-- 承認者２の時表示 -->
			<tr>
				<td align="left" colspan="4">承認者１コメント:<%=session.getAttribute("preComment")%>
					<%%></td>
			</tr>
			<tr>
				<td align="left" colspan="4">承認者２コメント: <%
					if (session.getAttribute("approvedStatus").equals("")) {
				%> <textarea name="comment" rows="1" cols="30" maxlength="50"></textarea>
					<%
						} else {
					%> <%=session.getAttribute("approvedOverComment")%> <%
 	}
 %>
				</td>
			</tr>

			<%
				}
			%>

			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td colspan="4">
					<!-- ステータスが空白の時ラジオボタンと確認ボタン表示 --> <%
 	if (session.getAttribute("approvedStatus").equals("")) {
 %> <input type="radio" name="action" id="承認" value="承認" checked><label
					for="承認">&nbsp;承認&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input
					type="radio" name="action" id="差戻" value="差戻"><label
					for="差戻">&nbsp;差戻</label>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" value=" 確認 " class="btn"> <%
 	}
 %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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