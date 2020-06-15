<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="java.util.Enumeration"%>
<%
	//csvのディレクトリを設定
String referenceDirectory = "C:\\Users\\seiya_saitou\\git\\workflow\\WorkFlow3\\WebContent\\";
%>
<html>
<head>
<meta charset="UTF-8"></meta>
<title>有給休暇取得申請システム</title>
<link rel="icon" href="./imge/favicon.ico">
<style>
body{
	margin:8px 0 0 0;
}
table, td, th {
	border: 0px;
}

table {
	align: center;
	margin: 0 auto;
}
header{
	left:0;
}
img{
	margin-left:0px;
}
.btn{
  display       : inline-block;
  border-radius : 5%;          /* 角丸       */
  font-size     : 18pt;        /* 文字サイズ */
  text-align    : center;      /* 文字位置   */
  cursor        : pointer;     /* カーソル   */
  padding       : 12px 12px;   /* 余白       */
  background    : #000066;     /* 背景色     */
  color         : #ffffff;     /* 文字色     */
  line-height   : 1em;         /* 1行の高さ  */
  transition    : .3s;         /* なめらか変化 */
  box-shadow    : 6px 6px 3px #666666;  /* 影の設定 */
  border        : 2px solid #000066;    /* 枠の指定 */
}
.btn:hover {
  box-shadow    : none;        /* カーソル時の影消去 */
  color         : #000066;     /* 背景色     */
  background    : #ffffff;     /* 文字色     */
}
</style>
</head>
<body>
	<header>
		<img src="https://www.homepage-tukurikata.com/image/hanikami.jpg" alt="IACロゴ" title="IACロゴ" width="100px" height="25px">
	</header>
	<br>
	<form action="Login" method="post">
		<table>
			<tr>
				<td colspan="2">有給休暇取得申請システム ログイン</td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><font color="red"> <%
 	if (!(session.getAttribute("loginError") == null)) {
 %> <%=session.getAttribute("loginError")%> <%
 	session.setAttribute("loginError", "");
 }

 //セッションにある全ての要素名を取得する
 Enumeration<String> names = session.getAttributeNames();

 //取得した要素名をループ処理で全て削除する
 while (names.hasMoreElements()) {
 	String name = (String) names.nextElement();
 	session.removeAttribute(name);
 }
 session.setAttribute("referenceDirectory", referenceDirectory);
 %>
				</font><br></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="center">社員番号:</td>
				<td align="center"><input type="text" name="id" id="id"
					class="form-control"></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td align="center">パスワード:</td>
				<td align="center"><input type="password" name="pass" id="pass"
					class="form-control"></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value=" ログイン " class="btn"></td>
			</tr>
		</table>
	</form>
</body>
</html>