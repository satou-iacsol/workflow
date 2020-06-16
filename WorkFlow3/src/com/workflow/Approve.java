package com.workflow;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Approve")
public class Approve extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Approve() {
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

		String number = (String) session.getAttribute("approvedNumber");
		String type = (String) session.getAttribute("approvedType");
		String date_1 = (String) session.getAttribute("approvedDate_1");
		String date_2 = (String) session.getAttribute("approvedDate_2");
		String date_3 = (String) session.getAttribute("approvedDate_3");
		String date_4 = (String) session.getAttribute("approvedDate_4");
		String reason = (String) session.getAttribute("approvedReason");
		String address = (String) session.getAttribute("approvedAddress");
		String remarks = (String) session.getAttribute("approvedRemarks");

		// データベース・テーブルに接続する準備
		Connection con = null;
		Statement stmtData = null;
		ResultSet resultData = null;
		PreparedStatement pstmtData = null;
		PreparedStatement pstmtNextData = null;
		// 接続文字列の設定
		String url = Keyword.url();
		String user = Keyword.user();
		String password = Keyword.password();

		// 申請データの更新
		try {
			// PostgreSQLに接続
			con = DriverManager.getConnection(url, user, password);
			con.setAutoCommit(false);

			String status = "";

			// SELECT文の作成・実行
			stmtData = con.createStatement();
			String sqlData = "SELECT * from employee_muster for update nowait";
			resultData = stmtData.executeQuery(sqlData);

			while (resultData.next()) {
				if (resultData.getString("number").equals(number)) {
					status = resultData.getString("status");
					if (!(type.equals(resultData.getString("type"))) || !(date_1.equals(resultData.getString("date_1")))
							|| !(date_2.equals(resultData.getString("date_2")))
							|| !(date_3.equals(resultData.getString("date_3")))
							|| !(date_4.equals(resultData.getString("date_4")))
							|| !(reason.equals(resultData.getString("comment")))
							|| !(address.equals(resultData.getString("tellnumber")))
							|| !(remarks.equals(resultData.getString("bikou")))) {
						status = "修正２";
					}
					break;
				}
			}

			// ステータスが空白以外だと承認者が編集済
			if (status.equals("")) {
				// SELECT文の作成・実行
				pstmtData = con.prepareStatement(
						"UPDATE data set approverComment = ?,approvedDate = ?,status = ? WHERE number = ?");

				// コメントの更新
				pstmtData.setString(1, (String) session.getAttribute("approvedComment"));
				// 承認差戻日を当日時のデータに更新
				LocalDateTime localDateTime = LocalDateTime.now();
				pstmtData.setString(2, DateTimeFormatter.ofPattern("yyyyMMddHHmm").format(localDateTime));
				// ステータスを実行内容で更新
				pstmtData.setString(3, (String) session.getAttribute("approvedAction"));
				// 更新する申請番号
				pstmtData.setString(4, number);

				String nextNumber = "";
				String sendAction = "";
				if (((String) session.getAttribute("approvedAction")).equals("承認")) {
					if (((String) session.getAttribute("id")).equals((String) session.getAttribute("approve2Id"))) {
						sendAction = "承認２";
					} else {
						nextNumber = number.substring(0, 14)
								+ String.format("%02d", Integer.parseInt(number.substring(14)) + 1);
						pstmtNextData = con.prepareStatement(
								"INSERT INTO data (number, id, type, date_1, date_2, date_3, date_4, comment, tellnumber, bikou, flag, approvernumber, approvercomment, approveddate, status, fix_delete_comment, delete_flag)SELECT ?, id, type, date_1, date_2, date_3, date_4, comment, tellnumber, bikou, flag, ?, '', '', '', '', delete_flag FROM data WHERE number = ?");
						pstmtNextData.setString(1, nextNumber);
						pstmtNextData.setString(2, (String) session.getAttribute("approve2Id"));
						pstmtNextData.setString(3, number);

						sendAction = (String) session.getAttribute("approvedAction");
					}
				} else if (((String) session.getAttribute("approvedAction")).equals("差戻")) {
					if (((String) session.getAttribute("id")).equals((String) session.getAttribute("approve1Id"))) {
						sendAction = "差戻２";
					} else if (session.getAttribute("approvedSkip").equals("1")) {
						sendAction = "差戻２";
					} else {
						nextNumber = number.substring(0, 14)
								+ String.format("%02d", Integer.parseInt(number.substring(14)) + 1);
						pstmtNextData = con.prepareStatement(
								"INSERT INTO data (number, id, type, date_1, date_2, date_3, date_4, comment, tellnumber, bikou, flag, approvernumber, approvercomment, approveddate, status, fix_delete_comment, delete_flag)SELECT ?, id, type, date_1, date_2, date_3, date_4, comment, tellnumber, bikou, flag, ?, '', '', '', '', delete_flag FROM data WHERE number = ?");
						pstmtNextData.setString(1, nextNumber);
						pstmtNextData.setString(2, (String) session.getAttribute("approve1Id"));
						pstmtNextData.setString(3, number);

						sendAction = (String) session.getAttribute("approvedAction");
					}
				}

				try {
					pstmtData.executeUpdate();
					pstmtNextData.executeUpdate();
					con.commit();
				} catch (Exception e) {
					con.rollback();
				}

				session.setAttribute("nextNumber", nextNumber);
				session.setAttribute("sendAction", sendAction);
				request.getServletContext().getRequestDispatcher("/SendSlack").forward(request, response);

			} else {
				con.commit();
				session.setAttribute("statusError", status);
				response.sendRedirect("approveChoose.jsp");
			}

		} catch (SQLException e) {
			session.setAttribute("statusError", "error");
			response.sendRedirect("approveChoose.jsp");
			e.printStackTrace();
		} catch (Exception e) {
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
				if (pstmtData != null) {
					pstmtData.close();
				}
				if (pstmtNextData != null) {
					pstmtNextData.close();
				}

				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
