<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.BufferedReader"
	import="java.io.FileNotFoundException" import="java.io.IOException"
	import="java.nio.charset.Charset" import="java.nio.file.Files"
	import="java.nio.file.Paths" import="java.util.ArrayList"%>
<%@ page session="true"%>
<%
final String referenceDirectory = "C:/Users/明石佑介/git/workflow/workflow_akashi/WebContent/";

// 社員番号取得
String id = (String) session.getAttribute("id");

ArrayList<ArrayList<String>> list = new ArrayList<>();
BufferedReader brData = null;
BufferedReader brEmployee = null;
BufferedReader brBelongs = null;
String[] employee = new String[6];
String[] belongs = new String[4];

try {

	brData = Files.newBufferedReader(Paths.get(referenceDirectory + "data.csv"),
	Charset.forName("UTF-8"));
	String lineData = "";

	while ((lineData = brData.readLine()) != null) {
		String[] data = lineData.split(",", -1);
		ArrayList<String> listSub = new ArrayList<>();
		for (int i = 0; i < data.length; i++) {
	listSub.add(data[i]);
		}
		// 申請データの承認者が一致したデータをリストに入れる
		if (data[11].equals(id)) {
	list.add(listSub);
		}
	}
} catch (FileNotFoundException e) {
	e.printStackTrace();
} catch (IOException e) {
	e.printStackTrace();
} finally {
	try {
		brData.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"></meta>
<title>有給休暇取得申請システム</title>
<style>
h2 {
	text-align: center;
}

table, td, th {
	border-collapse: collapse;
	border: 1px black solid;
}

table {
	align: center;
	margin: 0 auto;
	border: solid 1px;
}
</style>
</head>
<body>
	<br>
	<div align="center">有給休暇取得申請システム 承認画面</div>
	<br>
	<form action="SessionCreate" method="post">
		<table>
			<tr height="30">
				<td align="left" valign="bottom">チェック</td>
				<td align="left" valign="bottom">申請番号</td>
				<td align="left" valign="bottom">所属</td>
				<td align="left" valign="bottom">氏名</td>
				<td align="left" valign="bottom">有給種別</td>
				<td align="left" valign="bottom">ステータス</td>
			</tr>

			<!-- テーブルにリストのデータを入れる -->

			<%
				for (int i = 0; i < list.size(); i++) {
				String number = list.get(i).get(0);
			%>
			<tr>
				<td align="center">
					<!-- １番目のデータをデフォルトにする --> <%
 	if (i == 0) {
 %> <input type="radio" name="radio" id="radio" value="<%=number%>"
					class="form-control" checked> <%
 	} else {
 %> <input type="radio" name="radio" id="radio" value="<%=number%>"
					class="form-control"> <%
 	}
 %>
				</td>
				<td align="left">&nbsp;<%=number%>&nbsp;
				</td>
				<!-- 各列項目に入れるデータを取得 -->
				<%
					try {

					brEmployee = Files.newBufferedReader(Paths.get(referenceDirectory + "employee_muster.csv"),
					Charset.forName("UTF-8"));
					String lineEmployee = "";

					while ((lineEmployee = brEmployee.readLine()) != null) {
						employee = lineEmployee.split(",", -1);
						if (employee[0].equals(list.get(i).get(1))) {
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
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						brData.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				%>
				<td align="left">&nbsp;<%=belongs[1]%>&nbsp;
				</td>
				<td align="left">&nbsp;<%=employee[3]%>&nbsp;
				</td>
				<td align="left">
					<%
						switch (Integer.parseInt(list.get(i).get(2))) {
					case 1:
					%> 1.有給休暇<%
						break;
								case 2:
					%> 2.代休<%
						break;
											case 3:
					%> 3.生理休暇<%
						break;
														case 4:
					%> 4.慶弔休暇<%
						break;
																	case 5:
					%> 5.特別休暇<%
						break;
																						case 6:
					%> 6.罹災休暇<%
						break;
																						case 7:
					%> 7.半休<%
						break;
																						case 8:
					%> 8.結婚休暇<%
						break;
																						case 9:
					%> 9.出産休暇<%
						break;
																						case 10:
					%> 10.忌引き休暇<%
						break;
																						case 11:
					%> 11.隔離休暇<%
						break;
																						case 12:
					%> 12.一周忌<%
						break;
																						case 13:
					%> 13.受験休暇<%
						break;
																						case 14:
					%> 14.産前産後休暇<%
						break;

																						}
					%>&nbsp;
				</td>
				<td align="left">&nbsp;<%=list.get(i).get(14)%></td>
			</tr>
			<%
				}
			%>
		</table>
		<!-- ループ終了後に実行ボタン表示 -->
		<div align="center">
			<br> <span style="margin-right: 200px"><input
				type="submit" value=" 実行 " class="btn"></span>
		</div>
	</form>
</body>
</html>