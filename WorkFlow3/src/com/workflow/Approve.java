package com.workflow;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
		String[] preData = new String[15];

		// データベース・テーブルに接続する準備
		Connection con = null;
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

			try {
				pstmtData.executeUpdate();
				con.commit();
			} catch (Exception e) {
				con.rollback();
			}

			if (((String) session.getAttribute("approvedAction")).equals("承認")) {
				if (((String) session.getAttribute("id")).equals((String) session.getAttribute("approve2Id"))) {
					session.setAttribute("approvedResult", "承認２");
					request.getServletContext().getRequestDispatcher("/SendMail").forward(request, response);
				} else {
					String nextNumber = number.substring(0, 14) + String.format("%02d",Integer.parseInt(number.substring(14)) + 1);
					pstmtNextData = con.prepareStatement(
							"INSERT INTO data (number, id, type, date_1, date_2, date_3, date_4, comment, tellnumber, bikou, flag, approvernumber, approvercomment, approveddate, status, fix_delete_comment, delete_flag)SELECT ?, id, type, date_1, date_2, date_3, date_4, comment, tellnumber, bikou, flag, ?, '', '', '', '', delete_flag FROM data WHERE number = ?");
					pstmtNextData.setString(1, nextNumber);
					pstmtNextData.setString(2, (String) session.getAttribute("approve2Id"));
					pstmtNextData.setString(3, number);

					try {
						pstmtNextData.executeUpdate();
						con.commit();
					} catch (Exception e) {
						con.rollback();
					}

					session.setAttribute("approvedNumber", nextNumber);
					session.setAttribute("approvedResult", (String) session.getAttribute("approvedAction"));
					request.getServletContext().getRequestDispatcher("/SendMail").forward(request, response);
				}
			} else if (((String) session.getAttribute("approvedAction")).equals("差戻")) {
				if (((String) session.getAttribute("id")).equals((String) session.getAttribute("approve1Id"))) {
					session.setAttribute("approvedResult", "差戻２");
					request.getServletContext().getRequestDispatcher("/SendMail").forward(request, response);
				} else if (preData[10].equals("1")) {
					session.setAttribute("approvedResult", "差戻２");
					request.getServletContext().getRequestDispatcher("/SendMail").forward(request, response);
				} else {
					String nextNumber = number.substring(0, 14) + String.format("%02d",Integer.parseInt(number.substring(14)) + 1);
					pstmtNextData = con.prepareStatement(
							"INSERT INTO data (number, id, type, date_1, date_2, date_3, date_4, comment, tellnumber, bikou, flag, approvernumber, approvercomment, approveddate, status, fix_delete_comment, delete_flag)SELECT ?, id, type, date_1, date_2, date_3, date_4, comment, tellnumber, bikou, flag, ?, '', '', '', '', delete_flag FROM data WHERE number = ?");
					pstmtNextData.setString(1, nextNumber);
					pstmtNextData.setString(2, (String) session.getAttribute("approve2Id"));
					pstmtNextData.setString(3, number);

					try {
						pstmtNextData.executeUpdate();
						con.commit();
					} catch (Exception e) {
						con.rollback();
					}

					session.setAttribute("approvedNumber", nextNumber);
					session.setAttribute("approvedResult", (String) session.getAttribute("approvedAction"));
					request.getServletContext().getRequestDispatcher("/SendMail").forward(request, response);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// クローズ処理
			try {
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
