package com.workflow;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ApproveFix")
public class ApproveFix extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ApproveFix() {
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

		@SuppressWarnings("unchecked")
		ArrayList<String> historyList = (ArrayList<String>) session.getAttribute("historyList");

		boolean didntChenge = true;
		boolean didntFix = false;

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

			// SELECT文の作成・実行
			stmtData = con.createStatement();
			String sqlData = "SELECT * from data for update nowait";
			resultData = stmtData.executeQuery(sqlData);

			String status = "";

			while (resultData.next()) {

				// 申請番号(最新)と一致するデータを取得
				if (resultData.getString("number").equals(historyList.get(1))) {
					// 選択時に空白で実行時に空白以外だと承認者が編集済
					status = resultData.getString("status");
					if (session.getAttribute("approvedFinish").equals("") && !status.equals("")) {
						didntChenge = false;
						break;
					}

					if (resultData.getString("fix_delete_comment").equals("") || session.getAttribute("status").equals("差戻") || session.getAttribute("approver_switch").equals("1")) {

						didntFix = true;

						if (resultData.getString("status").equals("")) {
							// SELECT文の作成・実行
							pstmtData = con.prepareStatement(
									"UPDATE data set status = ? WHERE number = ?");

							// 取消コメントの更新
							pstmtData.setString(1, "修正");
							// 更新する申請番号
							pstmtData.setString(2, historyList.get(1));
						}
					}
					break;
				}
			}
			if (didntChenge) {
				if (didntFix) {

					String nextNumber = historyList.get(1).substring(0, 14)
							+ String.format("%02d", Integer.parseInt(historyList.get(1).substring(14)) + 1);
					pstmtNextData = con.prepareStatement(
							"INSERT INTO data (number, id, type, date_1, date_2, date_3, date_4, comment, tellnumber, bikou, flag, approvernumber, approvercomment, approveddate, status, fix_delete_comment, delete_flag)SELECT ?, id, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '', '', '', ?, '0' FROM data WHERE number = ?");
					pstmtNextData.setString(1, nextNumber);
					pstmtNextData.setString(2, (String) session.getAttribute("fix_type"));
					pstmtNextData.setString(3, (String) session.getAttribute("fix_date_1"));
					pstmtNextData.setString(4, (String) session.getAttribute("fix_date_2"));
					pstmtNextData.setString(5, (String) session.getAttribute("fix_date_3"));
					pstmtNextData.setString(6, (String) session.getAttribute("fix_date_4"));
					pstmtNextData.setString(7, (String) session.getAttribute("fix_comment"));
					pstmtNextData.setString(8, (String) session.getAttribute("fix_tellnumber"));
					pstmtNextData.setString(9, (String) session.getAttribute("fix_bikou"));
					pstmtNextData.setString(10, (String) session.getAttribute("fix_flag"));
					pstmtNextData.setString(11, (String) session.getAttribute("fix_approverNumber"));
					pstmtNextData.setString(12, (String) session.getAttribute("fix_delete_comment"));
					pstmtNextData.setString(13, historyList.get(1));

					try {
						if (resultData.getString("status").equals("")) {
							pstmtData.executeUpdate();
						}
						pstmtNextData.executeUpdate();
						con.commit();

						String approve1notification = "0";
						String approve2notification = "0";

						if (session.getAttribute("approver_switch").equals("0")) {
							if (historyList.get(5).equals("1")
									&& historyList.get(11).equals("0")) {
								approve1notification = "1";
							} else if (historyList.get(5).equals("2")
									&& historyList.get(11).equals("1")) {
								approve2notification = "1";
							} else {
								approve1notification = "1";
								approve2notification = "1";
							}
						} else {
							approve1notification = "1";
							approve2notification = "1";
						}

						session.setAttribute("sendAction", "修正");
						session.setAttribute("approve1notification", approve1notification);
						session.setAttribute("approve2notification", approve2notification);
						request.getServletContext().getRequestDispatcher("/SendSlack").forward(request, response);
					} catch (Exception e) {
						con.rollback();
						session.setAttribute("statusError", "error");
						request.getServletContext().getRequestDispatcher("/ApproveHistoryPick").forward(request,
								response);
						e.printStackTrace();
					}
				} else {

					pstmtNextData = con.prepareStatement(
							"UPDATE data SET type = ?, date_1 = ?, date_2 = ?, date_3 = ?, date_4 = ?, comment = ?, tellnumber = ?, bikou = ?, flag = ?, approvernumber = ?, approvercomment = '', approveddate = '', status = '', fix_delete_comment = ?, delete_flag='0' WHERE number = ?");
					pstmtNextData.setString(1, (String) session.getAttribute("fix_type"));
					pstmtNextData.setString(2, (String) session.getAttribute("fix_date_1"));
					pstmtNextData.setString(3, (String) session.getAttribute("fix_date_2"));
					pstmtNextData.setString(4, (String) session.getAttribute("fix_date_3"));
					pstmtNextData.setString(5, (String) session.getAttribute("fix_date_4"));
					pstmtNextData.setString(6, (String) session.getAttribute("fix_comment"));
					pstmtNextData.setString(7, (String) session.getAttribute("fix_tellnumber"));
					pstmtNextData.setString(8, (String) session.getAttribute("fix_bikou"));
					pstmtNextData.setString(9, (String) session.getAttribute("fix_flag"));
					pstmtNextData.setString(10, (String) session.getAttribute("fix_approverNumber"));
					pstmtNextData.setString(11, (String) session.getAttribute("fix_delete_comment"));
					pstmtNextData.setString(12, historyList.get(1));

					try {
						pstmtNextData.executeUpdate();
						con.commit();
						response.sendRedirect("menu.jsp");
					} catch (Exception e) {
						con.rollback();
						session.setAttribute("statusError", "error");
						request.getServletContext().getRequestDispatcher("/ApproveHistoryPick").forward(request,
								response);
						e.printStackTrace();
					}
				}
			} else {
				// 上記処理を通らないため、排他を閉じるためコミット
				con.commit();
				session.setAttribute("statusError", status);
				request.getServletContext().getRequestDispatcher("/ApproveHistoryPick").forward(request, response);
			}
		} catch (SQLException e) {
			session.setAttribute("statusError", "error");
			request.getServletContext().getRequestDispatcher("/ApproveHistoryPick").forward(request, response);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// クローズ処理
			try {
				if (stmtData != null) {
					stmtData.close();
				}
				if (resultData != null) {
					resultData.close();
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
