<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" import="java.util.Collections"%>
<%@ page session="true"%>
<%
try {
if (session.equals(null)) {
	throw new Exception();
}
	request.setCharacterEncoding("UTF-8");
response.setContentType("text/html;charset=UTF-8");
// 社員番号取得
String id = (String) session.getAttribute("id");
// 申請詳細リスト取得
@SuppressWarnings("unchecked")
ArrayList<String> historyList = (ArrayList<String>) session.getAttribute("historyList");

StringBuilder fromDate = new StringBuilder();
fromDate.append(historyList.get(3).substring(0, 4) + "-");
fromDate.append(historyList.get(3).substring(4, 6) + "-");
fromDate.append(historyList.get(3).substring(6, 8));

StringBuilder toDate = new StringBuilder();
toDate.append(historyList.get(4).substring(0, 4) + "-");
toDate.append(historyList.get(4).substring(4, 6) + "-");
toDate.append(historyList.get(4).substring(6, 8));

StringBuilder fromTime = new StringBuilder();
fromTime.append(historyList.get(3).substring(8, 10) + ":");
fromTime.append(historyList.get(3).substring(10, 12));

StringBuilder toTime = new StringBuilder();
toTime.append(historyList.get(4).substring(8, 10) + ":");
toTime.append(historyList.get(4).substring(10, 12));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"></meta>
<title>有給休暇取得申請システム</title>
<script src="https://code.jquery.com/jquery-3.5.1.js"
	integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
	crossorigin="anonymous"></script>
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
	<form action="approveFixCheck.jsp" method="post"
		onsubmit="return check()">
		<table style="border: 0">
			<tr>
				<td colspan="4" align="center">有給休暇取得申請システム 申請修正画面</td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="left">申請番号:</td>
				<td colspan="3"><%=historyList.get(1)%></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="left">有給種別:</td>
				<td colspan="3"><select name="fix_type">
						<option value="01" <%if (historyList.get(2).equals("1.有給休暇")) {%>
							selected <%}%>>有給休暇</option>
						<option value="02" <%if (historyList.get(2).equals("2.代休")) {%>
							selected <%}%>>代休</option>
						<option value="03" <%if (historyList.get(2).equals("3.生理休暇")) {%>
							selected <%}%>>生理休暇</option>
						<option value="04" <%if (historyList.get(2).equals("4.慶弔休暇")) {%>
							selected <%}%>>慶弔休暇</option>
						<option value="05" <%if (historyList.get(2).equals("5.特別休暇")) {%>
							selected <%}%>>特別休暇</option>
						<option value="06" <%if (historyList.get(2).equals("6.罹災休暇")) {%>
							selected <%}%>>罹災休暇</option>
						<option value="07" <%if (historyList.get(2).equals("7.半休")) {%>
							selected <%}%>>半休</option>
						<option value="08" <%if (historyList.get(2).equals("8.結婚休暇")) {%>
							selected <%}%>>結婚休暇</option>
						<option value="09" <%if (historyList.get(2).equals("9.出産休暇")) {%>
							selected <%}%>>出産休暇</option>
						<option value="10"
							<%if (historyList.get(2).equals("10.忌引き休暇")) {%> selected <%}%>>忌引き休暇</option>
						<option value="11" <%if (historyList.get(2).equals("11.隔離休暇")) {%>
							selected <%}%>>隔離休暇</option>
						<option value="12" <%if (historyList.get(2).equals("12.一周忌")) {%>
							selected <%}%>>一周忌</option>
						<option value="13" <%if (historyList.get(2).equals("13.受験休暇")) {%>
							selected <%}%>>受験休暇</option>
						<option value="14"
							<%if (historyList.get(2).equals("14.産前産後休暇")) {%> selected <%}%>>産前産後休暇</option>
				</select></td>
			</tr>
			<tr>
				<td align="left" id="date_col">取得期間:</td>
				<td><label class="date-edit"><input type="date"
						id="date_1" name="fix_date_1" value=<%=fromDate%>
						required="required"></label></td>
				<td>&nbsp;～&nbsp;</td>
				<td><label class="date-edit"><input type="date"
						id="date_2" name="fix_date_2" value=<%=toDate%>
						required="required"></label></td>
			</tr>
			<tr>
				<td align="left" id="time_col">取得時間:</td>
				<td><input type="time" id="time_1" name="fix_time_1"
					value=<%=fromTime%> required="required"></td>
				<td>&nbsp;～&nbsp;</td>
				<td><input type="time" id="time_2" name="fix_time_2"
					value=<%=toTime%> required="required"></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="left" id="reason_col">取得事由:</td>
				<td colspan="3"><textarea id="fix_comment" name="fix_comment"
						rows="1" cols="38" maxlength="30" required="required"><%=historyList.get(8)%></textarea></td>
			</tr>
			<tr>
				<td align="left" id="tell_col">連絡先:</td>
				<td colspan="3"><input maxlength="11" id="fix_tel" type="tel"
					name="fix_tellnumber" id="number" value=<%=historyList.get(9)%>
					onKeyup="this.value=this.value.replace(/[^0-9]+/i,'')"
					required="required" /></td>
			</tr>
			<tr>
				<td align="left" id="bikou">備考:</td>
				<td colspan="3"><textarea name="fix_bikou" rows="1" cols="38"
						maxlength="30"><%=historyList.get(10)%></textarea></td>
			</tr>
			<tr>
				<td align="left" id="fix_col">修正コメント:</td>
				<td colspan="3"><textarea id="fix_delete_comment"
						name="fix_delete_comment" rows="1" cols="38" maxlength="30"
						required="required"></textarea></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td colspan="5">一次承認者スキップ:<select name="fix_flag">

						<option value="0" <%if (historyList.get(11).equals("0")) {%>
							selected <%}%>>0(無)</option>

						<option value="1" <%if (historyList.get(11).equals("1")) {%>
							selected <%}%>>1(有)</option>
				</select></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="left" colspan="4">承認者１コメント:<%=historyList.get(13)%></td>
			</tr>
			<tr>
				<td align="left" colspan="4">承認者２コメント:<%=historyList.get(14)%></td>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td colspan="4" align="right"><span style="margin-right: 25px"><input
						type="submit" value=" 確認 " class="btn"></span> <span
					style="margin-right: 40px"><button type="button"
							onclick="history.back()">&nbsp;戻る&nbsp;</button> </span></td>
			</tr>
		</table>
	</form>
	<%
		} catch (Exception e) {
		response.sendRedirect("login.jsp");
	}
	%>
	<script type="text/javascript">
		$(document).ready(
				function() {
					var dColor = '#999999'; //ヒント（初期値）の文字色
					var fColor = '#000000'; //通常入力時の文字色
					var dValue = '必須'; //ヒント（初期値）文字列

					//初期状態設定
					if ($('#fix_comment').val() == ""
							|| $('#fix_comment').val() == dValue) {
						$('#fix_comment').val(dValue);
						$('#fix_comment').css('color', dColor);
					}

					//フォーカスされたときの処理
					$('#fix_comment').focus(function() {
						if ($(this).val() == dValue) {
							$(this).val('');
							$(this).css('color', fColor);
						}
					})
					//フォーカスが外れたときの処理
					.blur(function() {
						if ($(this).val() == dValue || $(this).val() == '') {
							$(this).val(dValue);
							$(this).css('color', dColor);
						}
						;
					});
				});
	</script>

	<script type="text/javascript">
		$(document).ready(
				function() {
					var dColor = '#999999'; //ヒント（初期値）の文字色
					var fColor = '#000000'; //通常入力時の文字色
					var dValue = '必須'; //ヒント（初期値）文字列

					//初期状態設定
					if ($('#fix_delete_comment').val() == ""
							|| $('#fix_delete_comment').val() == dValue) {
						$('#fix_delete_comment').val(dValue);
						$('#fix_delete_comment').css('color', dColor);
					}

					//フォーカスされたときの処理
					$('#fix_delete_comment').focus(function() {
						if ($(this).val() == dValue) {
							$(this).val('');
							$(this).css('color', fColor);
						}
					})
					//フォーカスが外れたときの処理
					.blur(function() {
						if ($(this).val() == dValue || $(this).val() == '') {
							$(this).val(dValue);
							$(this).css('color', dColor);
						}
						;
					});
				});
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			var dColor = '#999999'; //ヒント（初期値）の文字色
			var fColor = '#000000'; //通常入力時の文字色
			var dValue = '必須'; //ヒント（初期値）文字列

			//初期状態設定
			if ($('#fix_tel').val() == "" || $('#fix_tel').val() == dValue) {
				$('#fix_tel').val(dValue);
				$('#fix_tel').css('color', dColor);
			}

			//フォーカスされたときの処理
			$('#fix_tel').focus(function() {
				if ($(this).val() == dValue) {
					$(this).val('');
					$(this).css('color', fColor);
				}
			})
			//フォーカスが外れたときの処理
			.blur(function() {
				if ($(this).val() == dValue || $(this).val() == '') {
					$(this).val(dValue);
					$(this).css('color', dColor);
				}
				;
			});
		});
	</script>

	<script>
		//入力内容の整合性チェック（submitボタン押下時に実行）
		function check() {
			let date1 = document.getElementById("date_1").value;
			let date2 = document.getElementById("date_2").value;
			let time1 = document.getElementById("time_1").value;
			let time2 = document.getElementById("time_2").value;
			let date_time1 = date1 + time1;
			let date_time2 = date2 + time2;

			let reason = document.getElementById("fix_comment").value;
			let tell_number = document.getElementById("fix_tel").value;
			let fix = document.getElementById("fix_delete_comment").value;

			if (date_time1 > date_time2 || reason == "必須"
					|| tell_number == "必須" || fix == "必須") {
				//入力内容に不整合がある場合、その項目の文字色を変化
				if (date_time1 > date_time2) {
					document.getElementById("date_col").style.color = "red";
					document.getElementById("time_col").style.color = "red";
				} else {
					document.getElementById("date_col").style.color = "black";
					document.getElementById("time_col").style.color = "black";
				}
				if (reason == "必須") {
					document.getElementById("reason_col").style.color = "red";
				} else {
					document.getElementById("reason_col").style.color = "black";
				}
				if (tell_number == "必須") {
					document.getElementById("tell_col").style.color = "red";
				} else {
					document.getElementById("tell_col").style.color = "black";
				}
				if (fix == "必須") {
					document.getElementById("fix_col").style.color = "red";
				} else {
					document.getElementById("fix_col").style.color = "black";
				}
				alert("入力内容を確認してください。");
				return false;
			} else {
				return true;
			}

		}
	</script>
</body>
</html>