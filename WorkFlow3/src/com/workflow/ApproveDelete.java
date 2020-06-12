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

@WebServlet("/ApproveDelete")
public class ApproveDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ApproveDelete() {
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

		String status = historyList.get(7);

		// データベース・テーブルに接続する準備
		Connection con = null;
		Statement stmtData = null;
		ResultSet resultData = null;
		PreparedStatement pstmtData = null;
		// 接続文字列の設定
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "0978781";

		// 申請データの更新
		try {
			// PostgreSQLに接続

			con = DriverManager.getConnection(url, user, password);
			con.setAutoCommit(false);

			// SELECT文の作成・実行
			stmtData = con.createStatement();
			String sqlData = "SELECT * from data";
			resultData = stmtData.executeQuery(sqlData);

			while (resultData.next()) {

				if (resultData.getString("number").substring(0, 14).equals(historyList.get(0).substring(0, 14))) {
					// 上14桁が一致するデータを取得
					String number = resultData.getString("number");

					if (status.equals("承認待ち")) {
						// SELECT文の作成・実行
						pstmtData = con.prepareStatement(
								"UPDATE data set status = ?,fix_delete_comment = ?,delete_flag = ? WHERE number = ?");

						// 取消コメントの更新
						pstmtData.setString(1, (String) session.getAttribute("取消"));
						// 取消コメントの更新
						pstmtData.setString(2, (String) session.getAttribute("fix_delete_comment"));
						// 削除フラグをたてる
						pstmtData.setString(3, (String) "1");
						// 更新する申請番号
						pstmtData.setString(4, number);
					} else {
						// SELECT文の作成・実行
						pstmtData = con.prepareStatement(
								"UPDATE data set fix_delete_comment = ?,delete_flag = ? WHERE number = ?");

						// 取消コメントの更新
						pstmtData.setString(1, (String) session.getAttribute("fix_delete_comment"));
						// 削除フラグをたてる
						pstmtData.setString(2, (String) "1");
						// 更新する申請番号
						pstmtData.setString(3, number);
					}
				}
				try {
					pstmtData.executeUpdate();
					con.commit();
				} catch (Exception e) {
					con.rollback();
				}
			}
			if (status.equals("差戻")) {
				response.sendRedirect("menu.jsp");
			} else {
				String approve1notification = "0";
				String approve2notification = "0";

				if (historyList.get(5).equals(session.getAttribute("approverNumber_1"))) {
					approve1notification = "1";
				} else {
					if (historyList.get(11).equals("1")) {
						approve2notification = "1";
					} else {
						approve1notification = "1";
						approve2notification = "1";
					}
				}

				session.setAttribute("approvedResult", "取消");
				session.setAttribute("approve1notification", approve1notification);
				session.setAttribute("approve2notification", approve2notification);
				request.getServletContext().getRequestDispatcher("/SendMail").forward(request, response);
			}
		} catch (

		Exception e) {
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
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
