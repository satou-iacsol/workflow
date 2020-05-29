<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.BufferedReader"
	import="java.io.FileNotFoundException" import="java.io.IOException"
	import="java.nio.charset.Charset" import="java.nio.file.Files"
	import="java.nio.file.Paths"%>
<%@ page session="true"%>
<%
	final String referenceDirectory = (String) session.getAttribute("referenceDirectory");

String num = (String) session.getAttribute("id");

BufferedReader brEmployee = null;
BufferedReader brBelongs = null;
BufferedReader brEmployeeBelongs2 = null;
BufferedReader brEmployeeBelongs3 = null;
String[] employee = new String[6];
String[] belongs = new String[4];
String[] employeeBelongs2 = new String[6];
String[] employeeBelongs3 = new String[6];
try {
	brEmployee = Files.newBufferedReader(Paths.get(referenceDirectory + "employee_muster.csv"),
	Charset.forName("UTF-8"));
	String lineEmployee = "";

	while ((lineEmployee = brEmployee.readLine()) != null) {
		employee = lineEmployee.split(",", -1);
		if (employee[0].equals(num)) {
	break;
		}
	}
	brBelongs = Files.newBufferedReader(Paths.get(referenceDirectory + "belongs.csv"),
	Charset.forName("UTF-8"));
	String lineBelongs = "";

	while ((lineBelongs = brBelongs.readLine()) != null) {
		belongs = lineBelongs.split(",", -1);
		if (belongs[0].equals(employee[4])) {
	break;
		}
	}
	brEmployeeBelongs2 = Files.newBufferedReader(Paths.get(referenceDirectory + "employee_muster.csv"),
	Charset.forName("UTF-8"));
	String lineEmployeeBelongs2 = "";

	while ((lineEmployeeBelongs2 = brEmployeeBelongs2.readLine()) != null) {
		employeeBelongs2 = lineEmployeeBelongs2.split(",", -1);
		if (employeeBelongs2[0].equals(belongs[2])) {
	break;
		}
	}
	brEmployeeBelongs3 = Files.newBufferedReader(Paths.get(referenceDirectory + "employee_muster.csv"),
	Charset.forName("UTF-8"));
	String lineEmployeeBelongs3 = "";

	while ((lineEmployeeBelongs3 = brEmployeeBelongs3.readLine()) != null) {
		employeeBelongs3 = lineEmployeeBelongs3.split(",", -1);
		if (employeeBelongs3[0].equals(belongs[3])) {
	break;
		}
	}
} catch (FileNotFoundException e) {
	e.printStackTrace();
} catch (IOException e) {
	e.printStackTrace();
} finally {
	try {
		brEmployee.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"></meta>
<title>SignUp</title>
<style>
table, td, th {
	border: 0px;
}

table {
	margin: 0 auto;
}
</style>
</head>
<body>
	<br>
	<form action="Shinsei" method="post">
		<table style="border: 0">
			<tr>
				<td colspan="2" class="text-right">有給休暇取得申請システム 申請画面 <br>
					<br></td>
			</tr>
			<tr>
				<td align="left" valign="top">所属:</td>
				<td><%=belongs[1]%></td>
			</tr>
			<tr>
				<td align="left" valign="top">氏名:</td>
				<td><%=employee[3]%></td>
			</tr>
			<tr>
				<td><br></td>
				<td></td>
			</tr>
			<tr>
				<td align="left" valign="top">有給種別:</td>
				<td><select name="type">
						<option value="01">1.有給休暇
						<option value="02">2.代休
						<option value="03">3.生理休暇
						<option value="04">4.慶弔休暇
						<option value="05">5.特別休暇
						<option value="06">6.罹災休暇
						<option value="07">7.半休
						<option value="08">8.結婚休暇
						<option value="09">9.出産休暇
						<option value="10">10.忌引き休暇
						<option value="11">11.隔離休暇
						<option value="12">12.一周忌
						<option value="13">13.受験休暇
						<option value="14">14.産前産後休暇
				</select></td>
			</tr>
			<tr>
				<td align="left" valign="top">取得期間:</td>
				<td>20<select name="fromYear">
						<option value="20">20
						<option value="21">21
						<option value="22">22
						<option value="23">23
						<option value="24">24
						<option value="25">25
						<option value="26">26
						<option value="27">27
						<option value="28">28
						<option value="29">29
						<option value="30">30
						<option value="31">31
						<option value="32">32
						<option value="33">33
						<option value="34">34
						<option value="35">35
						<option value="36">36
						<option value="37">37
						<option value="38">38
						<option value="39">39
						<option value="40">40
						<option value="41">41
						<option value="42">42
						<option value="43">43
						<option value="44">44
						<option value="45">45
						<option value="46">46
						<option value="47">47
						<option value="48">48
						<option value="49">49
						<option value="50">50
						<option value="51">51
						<option value="52">52
						<option value="53">53
						<option value="54">54
						<option value="55">55
						<option value="56">56
						<option value="57">57
						<option value="58">58
						<option value="59">59
						<option value="60">60
						<option value="61">61
						<option value="62">62
						<option value="63">63
						<option value="64">64
						<option value="65">65
						<option value="66">66
						<option value="67">67
						<option value="68">68
						<option value="69">69
						<option value="70">70
						<option value="71">71
						<option value="72">72
						<option value="73">73
						<option value="74">74
						<option value="75">75
						<option value="76">76
						<option value="77">77
						<option value="78">78
						<option value="79">79
						<option value="80">80
						<option value="81">81
						<option value="82">82
						<option value="83">83
						<option value="84">84
						<option value="85">85
						<option value="86">86
						<option value="87">87
						<option value="88">88
						<option value="89">89
						<option value="90">90
						<option value="91">91
						<option value="92">92
						<option value="93">93
						<option value="94">94
						<option value="95">95
						<option value="96">96
						<option value="97">97
						<option value="98">98
						<option value="99">99
				</select> / <select name="fromMonth">
						<option value="01">01
						<option value="02">02
						<option value="03">03
						<option value="04">04
						<option value="05">05
						<option value="06">06
						<option value="07">07
						<option value="08">08
						<option value="09">09
						<option value="10">10
						<option value="11">11
						<option value="12">12
				</select> / <select name="fromDay">
						<option value="01">01
						<option value="02">02
						<option value="03">03
						<option value="04">04
						<option value="05">05
						<option value="06">06
						<option value="07">07
						<option value="08">08
						<option value="09">09
						<option value="10">10
						<option value="11">11
						<option value="12">12
						<option value="13">13
						<option value="14">14
						<option value="15">15
						<option value="16">16
						<option value="17">17
						<option value="18">18
						<option value="19">19
						<option value="20">20
						<option value="21">21
						<option value="22">22
						<option value="23">23
						<option value="24">24
						<option value="25">25
						<option value="26">26
						<option value="27">27
						<option value="28">28
						<option value="29">29
						<option value="30">30
						<option value="31">31
				</select>&nbsp;&nbsp;&nbsp;<select name="fromTime">
						<option value="00">00
						<option value="01">01
						<option value="02">02
						<option value="03">03
						<option value="04">04
						<option value="05">05
						<option value="06">06
						<option value="07">07
						<option value="08">08
						<option value="09">09
						<option value="10">10
						<option value="11">11
						<option value="12">12
						<option value="13">13
						<option value="14">14
						<option value="15">15
						<option value="16">16
						<option value="17">17
						<option value="18">18
						<option value="19">19
						<option value="20">20
						<option value="21">21
						<option value="22">22
						<option value="23">23
				</select> : <select name="fromMinutes">
						<option value="00">00
						<option value="01">01
						<option value="02">02
						<option value="03">03
						<option value="04">04
						<option value="05">05
						<option value="06">06
						<option value="07">07
						<option value="08">08
						<option value="09">09
						<option value="10">10
						<option value="11">11
						<option value="12">12
						<option value="13">13
						<option value="14">14
						<option value="15">15
						<option value="16">16
						<option value="17">17
						<option value="18">18
						<option value="19">19
						<option value="20">20
						<option value="21">21
						<option value="22">22
						<option value="23">23
						<option value="24">24
						<option value="25">25
						<option value="26">26
						<option value="27">27
						<option value="28">28
						<option value="29">29
						<option value="30">30
						<option value="31">31
						<option value="32">32
						<option value="33">33
						<option value="34">34
						<option value="35">35
						<option value="36">36
						<option value="37">37
						<option value="38">38
						<option value="39">39
						<option value="40">40
						<option value="41">41
						<option value="42">42
						<option value="43">43
						<option value="44">44
						<option value="45">45
						<option value="46">46
						<option value="47">47
						<option value="48">48
						<option value="49">49
						<option value="50">50
						<option value="51">51
						<option value="52">52
						<option value="53">53
						<option value="54">54
						<option value="55">55
						<option value="56">56
						<option value="57">57
						<option value="58">58
						<option value="59">59
				</select>&nbsp;&nbsp;&nbsp;～
				</td>
			</tr>
			<tr>
				<td></td>
				<td align="right">20<select name="toYear">
						<option value="20">20
						<option value="21">21
						<option value="22">22
						<option value="23">23
						<option value="24">24
						<option value="25">25
						<option value="26">26
						<option value="27">27
						<option value="28">28
						<option value="29">29
						<option value="30">30
						<option value="31">31
						<option value="32">32
						<option value="33">33
						<option value="34">34
						<option value="35">35
						<option value="36">36
						<option value="37">37
						<option value="38">38
						<option value="39">39
						<option value="40">40
						<option value="41">41
						<option value="42">42
						<option value="43">43
						<option value="44">44
						<option value="45">45
						<option value="46">46
						<option value="47">47
						<option value="48">48
						<option value="49">49
						<option value="50">50
						<option value="51">51
						<option value="52">52
						<option value="53">53
						<option value="54">54
						<option value="55">55
						<option value="56">56
						<option value="57">57
						<option value="58">58
						<option value="59">59
						<option value="60">60
						<option value="61">61
						<option value="62">62
						<option value="63">63
						<option value="64">64
						<option value="65">65
						<option value="66">66
						<option value="67">67
						<option value="68">68
						<option value="69">69
						<option value="70">70
						<option value="71">71
						<option value="72">72
						<option value="73">73
						<option value="74">74
						<option value="75">75
						<option value="76">76
						<option value="77">77
						<option value="78">78
						<option value="79">79
						<option value="80">80
						<option value="81">81
						<option value="82">82
						<option value="83">83
						<option value="84">84
						<option value="85">85
						<option value="86">86
						<option value="87">87
						<option value="88">88
						<option value="89">89
						<option value="90">90
						<option value="91">91
						<option value="92">92
						<option value="93">93
						<option value="94">94
						<option value="95">95
						<option value="96">96
						<option value="97">97
						<option value="98">98
						<option value="99">99
				</select> / <select name="toMonth">
						<option value="01">01
						<option value="02">02
						<option value="03">03
						<option value="04">04
						<option value="05">05
						<option value="06">06
						<option value="07">07
						<option value="08">08
						<option value="09">09
						<option value="10">10
						<option value="11">11
						<option value="12">12
				</select> / <select name="toDay">
						<option value="01">01
						<option value="02">02
						<option value="03">03
						<option value="04">04
						<option value="05">05
						<option value="06">06
						<option value="07">07
						<option value="08">08
						<option value="09">09
						<option value="10">10
						<option value="11">11
						<option value="12">12
						<option value="13">13
						<option value="14">14
						<option value="15">15
						<option value="16">16
						<option value="17">17
						<option value="18">18
						<option value="19">19
						<option value="20">20
						<option value="21">21
						<option value="22">22
						<option value="23">23
						<option value="24">24
						<option value="25">25
						<option value="26">26
						<option value="27">27
						<option value="28">28
						<option value="29">29
						<option value="30">30
						<option value="31">31
				</select>&nbsp;&nbsp;&nbsp;<select name="toTime">
						<option value="00">00
						<option value="01">01
						<option value="02">02
						<option value="03">03
						<option value="04">04
						<option value="05">05
						<option value="06">06
						<option value="07">07
						<option value="08">08
						<option value="09">09
						<option value="10">10
						<option value="11">11
						<option value="12">12
						<option value="13">13
						<option value="14">14
						<option value="15">15
						<option value="16">16
						<option value="17">17
						<option value="18">18
						<option value="19">19
						<option value="20">20
						<option value="21">21
						<option value="22">22
						<option value="23">23
				</select> : <select name="toMinutes">
						<option value="00">00
						<option value="01">01
						<option value="02">02
						<option value="03">03
						<option value="04">04
						<option value="05">05
						<option value="06">06
						<option value="07">07
						<option value="08">08
						<option value="09">09
						<option value="10">10
						<option value="11">11
						<option value="12">12
						<option value="13">13
						<option value="14">14
						<option value="15">15
						<option value="16">16
						<option value="17">17
						<option value="18">18
						<option value="19">19
						<option value="20">20
						<option value="21">21
						<option value="22">22
						<option value="23">23
						<option value="24">24
						<option value="25">25
						<option value="26">26
						<option value="27">27
						<option value="28">28
						<option value="29">29
						<option value="30">30
						<option value="31">31
						<option value="32">32
						<option value="33">33
						<option value="34">34
						<option value="35">35
						<option value="36">36
						<option value="37">37
						<option value="38">38
						<option value="39">39
						<option value="40">40
						<option value="41">41
						<option value="42">42
						<option value="43">43
						<option value="44">44
						<option value="45">45
						<option value="46">46
						<option value="47">47
						<option value="48">48
						<option value="49">49
						<option value="50">50
						<option value="51">51
						<option value="52">52
						<option value="53">53
						<option value="54">54
						<option value="55">55
						<option value="56">56
						<option value="57">57
						<option value="58">58
						<option value="59">59
				</select></td>
			</tr>
			<tr>
				<td align="left" valign="top">取得事由:</td>
				<td><textarea name="reason"></textarea></td>
			</tr>
			<tr>
				<td align="left" valign="top">連絡先:</td>
				<td>(不在の連絡先)<br>TEL<input type="number" name="address" /></td>
			</tr>
			<tr>
				<td align="left" valign="top">備考:</td>
				<td><textarea name="remarks"></textarea></td>
			</tr>
			<tr>
				<td><br></td>
				<td></td>
			</tr>
			<tr>
				<td align="left" valign="top">承認者:</td>
				<td><select name="authorizer">
						<option value=<%=belongs[2]%>><%=employeeBelongs2[3]%>
						<option value=<%=belongs[3]%>><%=employeeBelongs3[3]%>
				</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一次承認者スキップ<select
					name="skip" id="skip" class="form-control">
						<option value="0">なし
						<option value="1">あり
				</select></td>
			</tr>
			<tr>
				<td><br></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value=" 申請 " class="btn"></td>
		</table>
	</form>


</body>
</html>