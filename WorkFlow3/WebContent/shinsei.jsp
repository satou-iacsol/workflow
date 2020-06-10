<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page session="true"%>
<%
	// Shinsei.javaのデータ受け取り
request.setCharacterEncoding("UTF8");
final String referenceDirectory = (String) session.getAttribute("referenceDirectory");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>申請画面</title>
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<link rel="stylesheet" href="shinsei.css">
</head>
<body>
		<header>
		<img src="https://www.homepage-tukurikata.com/image/hanikami.jpg" alt="IACロゴ" title="IACロゴ" width="100px" height="25px">
		<form name="login_logout" action="login.jsp" method="post"
		onsubmit="return logout()">
		<div align="right">
			<div>
				<%=session.getAttribute("affiliationName") %>・
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
	<%
		try {
		if (session.equals(null)) {
			throw new Exception();
		}
	%>
	<br>
	<div class="hoge">
		<h1>有給休暇取得申請システム 申請画面</h1>
	</div>

	<br>
	<div class="margin">
		<form action="<%=request.getContextPath()%>/Confirmation"
			method="post" id="Confirmation" onsubmit="return check()">
			<div class="top">
				所属:<%=session.getAttribute("affiliationName")%></div>
			<div>
				氏名:<%=session.getAttribute("fullname")%></div>
			<br>
			<div class="interval">有給種別: <select name="type">
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
			</select></div>
				<div class="interval"><div id="date_col">取得期間: </div><label class="date-edit"><input type="date"  id="date_1"
				name="date_1" value="" required="required"></label> ～ <label
				class="date-edit"><input type="date" name="date_2"  id="date_2"
				required="required"></label> </div>
				<div class="interval"><div id="time_col">取得時間: </div><input type="time" id="time_1" name="time_1" value="09:00"
				required="required"> ～ <input type="time" id="time_2" name="time_2" value="18:00" required="required"></div>


			<div class="interval"><div id="reason_col">取得事由:</div>
			<textarea maxlength="50" id="hoge_text" name="comment" cols="50" rows="1"required="required"></textarea>
			</div>
			<div class="interval"><div id="tell_col">連絡先　: </div><input maxlength="11" id="hoge_tel" type="tel" name="tellnumber"
				id="number" onKeyup="this.value=this.value.replace(/[^0-9]+/i,'')"
				required="required" /></div>
			<div class="interval">備考　　:
			<textarea maxlength="50" name="bikou" cols="50" rows="1"></textarea>
			</div>
			 一次承認者スキップ: <select name="flag">
				<option value="0">0(無)</option>
				<option value="1">1(有)</option>
			</select> <br> <br>

		</form>
		<button type="submit" form="Confirmation">確認</button>
		<button onclick="history.back();">キャンセル</button>
	</div>
	<%
		} catch (Exception e) {
		response.sendRedirect("login.jsp");
	}
	%>
	 <script type="text/javascript">
      $(document).ready(function(){
          var dColor = '#999999';    //ヒント（初期値）の文字色
          var fColor = '#000000';    //通常入力時の文字色
          var dValue = '必須';    //ヒント（初期値）文字列

          //初期状態設定
          if($('#hoge_text').val() == "" || $('#hoge_text').val() == dValue){
              $('#hoge_text').val(dValue);
              $('#hoge_text').css('color',dColor);
          }

          //フォーカスされたときの処理
          $('#hoge_text').focus(function(){
              if($(this).val() == dValue){
                  $(this).val('');
                  $(this).css('color', fColor);
              }
          })
          //フォーカスが外れたときの処理
          .blur(function(){
              if($(this).val() == dValue || $(this).val() == ''){
                  $(this).val(dValue);
                  $(this).css('color',dColor);
              };
          });
      });
 	 </script>
	 <script type="text/javascript">
      $(document).ready(function(){
          var dColor = '#999999';    //ヒント（初期値）の文字色
          var fColor = '#000000';    //通常入力時の文字色
          var dValue = '必須';    //ヒント（初期値）文字列

          //初期状態設定
          if($('#hoge_tel').val() == "" || $('#hoge_tel').val() == dValue){
              $('#hoge_tel').val(dValue);
              $('#hoge_tel').css('color',dColor);
          }

          //フォーカスされたときの処理
          $('#hoge_tel').focus(function(){
              if($(this).val() == dValue){
                  $(this).val('');
                  $(this).css('color', fColor);
              }
          })
          //フォーカスが外れたときの処理
          .blur(function(){
              if($(this).val() == dValue || $(this).val() == ''){
                  $(this).val(dValue);
                  $(this).css('color',dColor);
              };
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

	      	let reason = document.getElementById("hoge_text").value;
	      	let tell_number = document.getElementById("hoge_tel").value;

	      	if(date_time1 > date_time2 || reason == "必須" || tell_number == "必須"){
	      		//入力内容に不整合がある場合、その項目の文字色を変化
	      		if(date_time1 > date_time2){
	      			document.getElementById("date_col").style.color="red";
	      			document.getElementById("time_col").style.color="red";
	      		}else{
	      			document.getElementById("date_col").style.color="black";
	      			document.getElementById("time_col").style.color="black";
	      		}
				if(reason == "必須"){
					document.getElementById("reason_col").style.color="red";
				}else{
					document.getElementById("reason_col").style.color="black";
				}
				if(tell_number == "必須"){
					document.getElementById("tell_col").style.color="red";
				}else{
					document.getElementById("tell_col").style.color="black";
				}
	      	    alert("入力内容を確認してください。");
	      	    return false;
	      	}else{
	      		return true;
	      	}

	     }


      </script>
</body>
</html>