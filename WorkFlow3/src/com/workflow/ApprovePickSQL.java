package com.workflow;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ApprovePickSQL")
public class ApprovePickSQL extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ApprovePickSQL() {
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

		String id = (String) session.getAttribute("id");

		ArrayList<ArrayList<String>> list = new ArrayList<>();

		// データベース・テーブルに接続する準備
		Connection con = null;
		Statement stmtData = null;
		ResultSet resultData = null;
		Statement stmtData1 = null;
		ResultSet resultData1 = null;
		Statement stmtData2 = null;
		ResultSet resultData2 = null;
		Statement stmtData3 = null;
		ResultSet resultData3 = null;

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

			ArrayList<String> sort = new ArrayList<>();
			while (resultData.next()) {
				sort.add(resultData.getString("date_1"));
			}

			// 取得期間(FROM)を取得
			session.setAttribute("approvedItems", sort.size());

			// 取得期間(FROM)順にソート
			Collections.sort(sort);

			stmtData1 = con.createStatement();
			String sqlData1 = "SELECT * from data";
			resultData1 = stmtData1.executeQuery(sqlData1);
			int count = 0;

			while (resultData1.next()) {
				Statement stmtEmployee = null;
				ResultSet resultEmployee = null;
				Statement stmtBelongs = null;
				ResultSet resultBelongs = null;

				if (resultData1.getString("date_1").equals(sort.get(count++))
						&& resultData1.getString("approverNumber").equals(id)
						&& resultData1.getString("status").equals("")) {
					ArrayList<String> listSub = new ArrayList<>();

					for (int i = 1; i <= 5; i++) {
						listSub.add(resultData1.getString(i));
					}
					stmtEmployee = con.createStatement();
					String sqlEmployee = "SELECT * from data";
					resultEmployee = stmtEmployee.executeQuery(sqlEmployee);
					while (resultEmployee.next()) {
						if (resultEmployee.getString("id").equals(resultData1.getString("approverNumber"))) {
							listSub.add(resultData1.getString("fullname"));
							break;
						}
					}
					stmtBelongs = con.createStatement();
					String sqlBelongs = "SELECT * from data";
					resultBelongs = stmtBelongs.executeQuery(sqlBelongs);
					while (resultBelongs.next()) {
						if (resultBelongs.getString("affiliationCode")
								.equals(resultEmployee.getString("affiliationCode"))) {
							listSub.add(resultBelongs.getString("affiliationName"));
							break;
						}
					}
					for (int i = 8; i <= 15; i++) {
						listSub.add(resultData1.getString(i));
					}
					list.add(listSub);
				}

				if (stmtEmployee != null) {
					stmtEmployee.close();
				}
				if (resultEmployee != null) {
					resultEmployee.close();
				}
				if (stmtBelongs != null) {
					stmtBelongs.close();
				}
				if (resultBelongs != null) {
					resultBelongs.close();
				}
			}

			stmtData2 = con.createStatement();
			String sqlData2 = "SELECT * from data";
			resultData2 = stmtData2.executeQuery(sqlData2);

			while (resultData2.next()) {
				Statement stmtEmployee = null;
				ResultSet resultEmployee = null;
				Statement stmtBelongs = null;
				ResultSet resultBelongs = null;

				if (resultData2.getString("approverNumber").equals(id)
						&& resultData2.getString("status").equals("差戻")) {
					ArrayList<String> listSub = new ArrayList<>();

					for (int i = 1; i <= 5; i++) {
						listSub.add(resultData2.getString(i));
					}
					stmtEmployee = con.createStatement();
					String sqlEmployee = "SELECT * from data";
					resultEmployee = stmtEmployee.executeQuery(sqlEmployee);
					while (resultEmployee.next()) {
						if (resultEmployee.getString("id").equals(resultData2.getString("approverNumber"))) {
							listSub.add(resultData2.getString("fullname"));
							break;
						}
					}
					stmtBelongs = con.createStatement();
					String sqlBelongs = "SELECT * from data";
					resultBelongs = stmtBelongs.executeQuery(sqlBelongs);
					while (resultBelongs.next()) {
						if (resultBelongs.getString("affiliationCode")
								.equals(resultEmployee.getString("affiliationCode"))) {
							listSub.add(resultBelongs.getString("affiliationName"));
							break;
						}
					}
					for (int i = 8; i <= 15; i++) {
						listSub.add(resultData2.getString(i));
					}
					list.add(listSub);
				}

				if (stmtEmployee != null) {
					stmtEmployee.close();
				}
				if (resultEmployee != null) {
					resultEmployee.close();
				}
				if (stmtBelongs != null) {
					stmtBelongs.close();
				}
				if (resultBelongs != null) {
					resultBelongs.close();
				}
			}

			stmtData3 = con.createStatement();
			String sqlData3 = "SELECT * from data";
			resultData3 = stmtData3.executeQuery(sqlData3);

			while (resultData3.next()) {
				Statement stmtEmployee = null;
				ResultSet resultEmployee = null;
				Statement stmtBelongs = null;
				ResultSet resultBelongs = null;

				if (resultData3.getString("approverNumber").equals(id)
						&& resultData3.getString("status").equals("承認")) {
					ArrayList<String> listSub = new ArrayList<>();

					for (int i = 1; i <= 5; i++) {
						listSub.add(resultData3.getString(i));
					}
					stmtEmployee = con.createStatement();
					String sqlEmployee = "SELECT * from data";
					resultEmployee = stmtEmployee.executeQuery(sqlEmployee);
					while (resultEmployee.next()) {
						if (resultEmployee.getString("id").equals(resultData3.getString("approverNumber"))) {
							listSub.add(resultData3.getString("fullname"));
							break;
						}
					}
					stmtBelongs = con.createStatement();
					String sqlBelongs = "SELECT * from data";
					resultBelongs = stmtBelongs.executeQuery(sqlBelongs);
					while (resultBelongs.next()) {
						if (resultBelongs.getString("affiliationCode")
								.equals(resultEmployee.getString("affiliationCode"))) {
							listSub.add(resultBelongs.getString("affiliationName"));
							break;
						}
					}
					for (int i = 8; i <= 15; i++) {
						listSub.add(resultData3.getString(i));
					}
					list.add(listSub);
				}

				if (stmtEmployee != null) {
					stmtEmployee.close();
				}
				if (resultEmployee != null) {
					resultEmployee.close();
				}
				if (stmtBelongs != null) {
					stmtBelongs.close();
				}
				if (resultBelongs != null) {
					resultBelongs.close();
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

				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		session.setAttribute("list", list);
		response.sendRedirect("approveChoose.jsp");
	}

}