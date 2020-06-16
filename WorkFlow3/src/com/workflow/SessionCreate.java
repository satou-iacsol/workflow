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
		String approvedType = "";
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
		String approvedSkip = "";
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
		String url = Keyword.url();
		String user = Keyword.user();
		String password = Keyword.password();

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
					approvedType = resultData.getString("type");
					flag = resultData.getString("flag");
					date_1 = resultData.getString("date_1");
					date_2 = resultData.getString("date_2");
					date_3 = resultData.getString("date_3");
					date_4 = resultData.getString("date_4");
					approvedReason = resultData.getString("comment");
					approvedAddress = resultData.getString("tellnumber");
					approvedRemarks = resultData.getString("bikou");
					approvedSkip = resultData.getString("flag");
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

		// 日付関連のデータの形式の編集
		StringBuilder fromDate = new StringBuilder();
		fromDate.append(date_1.substring(0, 4) + "年");
		fromDate.append(date_1.substring(4, 6) + "月");
		fromDate.append(date_1.substring(6, 8) + "日");

		StringBuilder toDate = new StringBuilder();
		toDate.append(date_2.substring(0, 4) + "年");
		toDate.append(date_2.substring(4, 6) + "月");
		toDate.append(date_2.substring(6, 8) + "日");

		StringBuilder fromTime = new StringBuilder();
		fromTime.append(date_3.substring(0, 2) + "時");
		fromTime.append(date_3.substring(2, 4) + "分");

		StringBuilder toTime = new StringBuilder();
		toTime.append(date_4.substring(0, 2) + "時");
		toTime.append(date_4.substring(2, 4) + "分");


		session.setAttribute("approvedNumber", request.getParameter("radio"));
		session.setAttribute("approvedId", approvedId);
		session.setAttribute("approvedType", approvedType);
		session.setAttribute("approvedName", approvedName);
		session.setAttribute("approvedBelongsCode", approvedBelongsCode);
		session.setAttribute("approvedBelongs", approvedBelongs);
		session.setAttribute("approve1Id", approve1Id);
		session.setAttribute("approve2Id", approve2Id);
		session.setAttribute("approve1Name", approve1Name);
		session.setAttribute("approve2Name", approve2Name);
		session.setAttribute("approveDate_1", date_1);
		session.setAttribute("approveDate_2", date_2);
		session.setAttribute("approveDate_3", date_3);
		session.setAttribute("approveDate_4", date_4);
		session.setAttribute("approvedFromDate", fromDate);
		session.setAttribute("approvedToDate", toDate);
		session.setAttribute("approvedFromTime", fromTime);
		session.setAttribute("approvedToTime", toTime);
		session.setAttribute("approvedReason", approvedReason);
		session.setAttribute("approvedAddress", approvedAddress);
		session.setAttribute("approvedRemarks", approvedRemarks);
		session.setAttribute("approvedSkip", approvedSkip);
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
