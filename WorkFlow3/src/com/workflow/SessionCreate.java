package com.workflow;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SessionCreate")
public class SessionCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SessionCreate() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession();

		String number = request.getParameter("radio");

		String approvedId = "";
		String type = "";
		String flag = "";
		String approvedName = "";
		String approvedBelongsCode = "";
		String approvedBelongs = "";
		String approve1Id = "";
		String approve2Id = "";
		String approve1Name = "";
		String approve2Name = "";
		String date_1 = "";
		String date_2 = "";
		String date_3 = "";
		String date_4 = "";
		String approvedReason = "";
		String approvedAddress = "";
		String approvedRemarks = "";
		String approvedOverComment = "";
		String approvedStatus = "";
		String preComment = "";

		// データベース・テーブルに接続する準備
		Connection con = null;
		Statement stmtData = null;
		ResultSet resultData = null;
		Statement stmtEmployee = null;
		ResultSet resultEmployee = null;
		Statement stmtBelongs = null;
		ResultSet resultBelongs = null;
		Statement stmtApprove1 = null;
		ResultSet resultApprove1 = null;
		Statement stmtApprove2 = null;
		ResultSet resultApprove2 = null;
		Statement stmtPreData = null;
		ResultSet resultPreData = null;

		// 接続文字列の設定
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "0978781";

		try {
			// PostgreSQLに接続
			con = DriverManager.getConnection(url, user, password);

			// SELECT文の作成・実行
			stmtData = con.createStatement();
			String sqlData = "SELECT * from data";
			resultData = stmtData.executeQuery(sqlData);

			while (resultData.next()) {
				if (resultData.getString("number").equals(number)) {
					approvedId = resultData.getString("id");
					type = resultData.getString("type");
					flag = resultData.getString("flag");
					date_1 = resultData.getString("date_1");
					date_2 = resultData.getString("date_2");
					date_3 = resultData.getString("date_3");
					date_4 = resultData.getString("date_4");
					approvedReason = resultData.getString("comment");
					approvedAddress = resultData.getString("tellnumber");
					approvedRemarks = resultData.getString("bikou");
					approvedOverComment = resultData.getString("approverComment");
					approvedStatus = resultData.getString("status");
					break;
				}
			}

			stmtEmployee = con.createStatement();
			String sqlEmployee = "SELECT * from employee_muster";
			resultEmployee = stmtEmployee.executeQuery(sqlEmployee);

			while (resultEmployee.next()) {
				if (resultEmployee.getString("id").equals(approvedId)) {
					approvedName = resultEmployee.getString("fullname");
					approvedBelongsCode = resultEmployee.getString("affiliationCode");
					break;
				}
			}

			stmtBelongs = con.createStatement();
			String sqlBelongs = "SELECT * from belongs";
			resultBelongs = stmtBelongs.executeQuery(sqlBelongs);

			while (resultBelongs.next()) {
				if (resultBelongs.getString("affiliationcode").equals(approvedBelongsCode)) {
					approvedBelongs = resultBelongs.getString("affiliationName");
					approve1Id = resultBelongs.getString("approverNumber_1");
					approve2Id = resultBelongs.getString("approverNumber_2");
					break;
				}
			}

			stmtApprove1 = con.createStatement();
			String sqlApprove1 = "SELECT * from employee_muster";
			resultApprove1 = stmtApprove1.executeQuery(sqlApprove1);

			while (resultApprove1.next()) {
				if (resultApprove1.getString("id").equals(approve1Id)) {
					approve1Name = resultApprove1.getString("fullname");
					break;
				}
			}

			stmtApprove2 = con.createStatement();
			String sqlApprove2 = "SELECT * from employee_muster";
			resultApprove2 = stmtApprove2.executeQuery(sqlApprove2);

			while (resultApprove2.next()) {
				if (resultApprove2.getString("id").equals(approve2Id)) {
					approve2Name = resultApprove2.getString("fullname");
					break;
				}
			}

			// 申請データの連番 -1 の連番を作成
			String preNumber = number.substring(0, 14) + String.format("%02d",Integer.parseInt(number.substring(14)) - 1);

			stmtPreData = con.createStatement();
			String sqlPreData = "SELECT * from data";
			resultPreData = stmtPreData.executeQuery(sqlPreData);

			while (resultPreData.next()) {
				if (resultPreData.getString("number").equals(preNumber)) {
					preComment = resultPreData.getString("approverComment");
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// クローズ処理
			try {
				if (resultData != null) {
					resultData.close();
				}
				if (stmtData != null) {
					stmtData.close();
				}
				if (resultEmployee != null) {
					resultEmployee.close();
				}
				if (stmtEmployee != null) {
					stmtEmployee.close();
				}
				if (resultBelongs != null) {
					resultBelongs.close();
				}
				if (stmtBelongs != null) {
					stmtBelongs.close();
				}
				if (resultApprove1 != null) {
					resultApprove1.close();
				}
				if (stmtApprove1 != null) {
					stmtApprove1.close();
				}
				if (resultApprove2 != null) {
					resultApprove2.close();
				}
				if (stmtApprove2 != null) {
					stmtApprove2.close();
				}
				if (resultPreData != null) {
					resultPreData.close();
				}
				if (stmtPreData != null) {
					stmtPreData.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		switch (Integer.parseInt(type)) {
		case 1:
			session.setAttribute("approvedType", "1.有給休暇");
			break;
		case 2:
			session.setAttribute("approvedType", "2.代休");
			break;
		case 3:
			session.setAttribute("approvedType", "3.生理休暇");
			break;
		case 4:
			session.setAttribute("approvedType", "4.慶弔休暇");
			break;
		case 5:
			session.setAttribute("approvedType", "5.特別休暇");
			break;
		case 6:
			session.setAttribute("approvedType", "6.罹災休暇");
			break;
		case 7:
			session.setAttribute("approvedType", "7.半休");
			break;
		case 8:
			session.setAttribute("approvedType", "8.結婚休暇");
			break;
		case 9:
			session.setAttribute("approvedType", "9.出産休暇");
			break;
		case 10:
			session.setAttribute("approvedType", "10.忌引き休暇");
			break;
		case 11:
			session.setAttribute("approvedType", "11.隔離休暇");
			break;
		case 12:
			session.setAttribute("approvedType", "12.一周忌");
			break;
		case 13:
			session.setAttribute("approvedType", "13.受験休暇");
			break;
		case 14:
			session.setAttribute("approvedType", "14.産前産後休暇");
			break;

		}

		// 日付関連のデータの形式の編集
		StringBuilder fromDate = new StringBuilder();
		fromDate.append(date_1.substring(0, 4) + "/");
		fromDate.append(date_1.substring(4, 6) + "/");
		fromDate.append(date_1.substring(6, 8));

		StringBuilder toDate = new StringBuilder();
		toDate.append(date_2.substring(0, 4) + "/");
		toDate.append(date_2.substring(4, 6) + "/");
		toDate.append(date_2.substring(6, 8));

		StringBuilder fromTime = new StringBuilder();
		fromTime.append(date_3.substring(0, 4) + "/");
		fromTime.append(date_3.substring(4, 6) + "/");
		fromTime.append(date_3.substring(6, 8) + " ");
		fromTime.append(date_3.substring(8, 10) + ":");
		fromTime.append(date_3.substring(10, 12));

		StringBuilder toTime = new StringBuilder();
		toTime.append(date_4.substring(0, 4) + "/");
		toTime.append(date_4.substring(4, 6) + "/");
		toTime.append(date_4.substring(6, 8) + " ");
		toTime.append(date_4.substring(8, 10) + ":");
		toTime.append(date_4.substring(10, 12));

		session.setAttribute("approvedNumber", request.getParameter("radio"));
		session.setAttribute("approvedId", approvedId);
		session.setAttribute("approvedName", approvedName);
		session.setAttribute("approvedBelongsCode", approvedBelongsCode);
		session.setAttribute("approvedBelongs", approvedBelongs);
		session.setAttribute("approve1Id", approve1Id);
		session.setAttribute("approve2Id", approve2Id);
		session.setAttribute("approve1Name", approve1Name);
		session.setAttribute("approve2Name", approve2Name);
		session.setAttribute("approvedFromDate", fromDate);
		session.setAttribute("approvedToDate", toDate);
		session.setAttribute("approvedDate", fromDate + " ～ " + toDate);
		session.setAttribute("approvedFromTime", fromTime);
		session.setAttribute("approvedToTime", toTime);
		session.setAttribute("approvedTime", fromTime + " ～ " + toTime);
		session.setAttribute("approvedReason", approvedReason);
		session.setAttribute("approvedAddress", approvedAddress);
		session.setAttribute("approvedRemarks", approvedRemarks);
		session.setAttribute("approvedOverComment", approvedOverComment);
		session.setAttribute("approvedStatus", approvedStatus);
		if (flag.equals("1")) {
			session.setAttribute("preComment", "承認者１はスキップされました。");
		} else {
			session.setAttribute("preComment", preComment);
		}

		response.sendRedirect("approveAction.jsp");
	}
}
