<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
<link rel="stylesheet" href="login.css">
</head>

<body>
	<form action="<%=request.getContextPath()%>/servlet" method="post">
		<div class="content">
			<h1>ログイン画面</h1>
			<p>社員番号とパスワードを入力してください。</p>
			<div class="control">
				<label for="number">社員番号<span class="required">必須</span></label> <input
					id="number" type="text" name="number">
			</div>

			<div class="control">
				<label for="passcode">パスワード<span class="required">必須</span></label> <input
					id="passcode" type="password" name="passcode">
			</div>

			<div class="control">
				<button type="submit">ログイン</button>
			</div>

		</div>
	</form>
</body>

</html>