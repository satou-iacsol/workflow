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

@WebServlet("/ApproveHistoryPick")
public class ApproveHistoryPick extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ApproveHistoryPick() {
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
		Statement stmtData1 = null;
		Statement stmtData2= null;
		Statement stmtData3= null;
		ResultSet resultData = null;
		ResultSet resultData1 = null;
		ResultSet resultData2 = null;
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
			ArrayList<ArrayList<String>> sortList = new ArrayList<>();
			while (resultData.next()) {
				Statement stmtEmployee = null;
				ResultSet resultEmployee = null;

				if (resultData.getString("id").equals(id)) {
					ArrayList<String> listSub = new ArrayList<>();

					listSub.add(resultData.getString("number"));
					listSub.add(resultData.getString("type"));
					listSub.add(resultData.getString("date_1") + resultData.getString("date_3"));

					stmtData1 = con.createStatement();
					String sqlData1 = "SELECT * from data";
					resultData1 = stmtData1.executeQuery(sqlData1);

					ArrayList<String> sort = new ArrayList<>();
					while (resultData1.next()) {
						if (resultData1.getString("number").substring(14).equals(listSub.get(0).substring(14))) {
							sort.add(resultData1.getString("number"));
							break;
						}
					}
					Collections.sort(sort);

					stmtData2 = con.createStatement();
					String sqlData2 = "SELECT * from data";
					resultData2 = stmtData2.executeQuery(sqlData2);

					String approverNumber;
					String status;
					while (resultData2.next()) {
						if (resultData2.getString("number").equals(sort.get(sort.size() - 1))) {
							approverNumber = resultData2.getString("approverNumber");
							status = resultData2.getString("status");
						break;
						}
					}

					stmtEmployee = con.createStatement();
					String sqlEmployee = "SELECT * from employee_muster";
					resultEmployee = stmtEmployee.executeQuery(sqlEmployee);
					while (resultEmployee.next()) {
						if (resultEmployee.getString("id").equals(resultData.getString("id"))) {
							listSub.add(resultEmployee.getString("fullname"));
							break;
						}
					}

					for (int i = 8; i <= 15; i++) {
						listSub.add(resultData.getString(i));
					}
					sortList.add(listSub);
				}

				if (stmtEmployee != null) {
					stmtEmployee.close();
				}
				if (resultEmployee != null) {
					resultEmployee.close();
				}
			}

			ArrayList<String> sort = new ArrayList<>();

			// 取得期間(FROM)を取得
			for (int i = 0; i < sortList.size(); i++) {
				sort.add(sortList.get(i).get(3));
			}

			session.setAttribute("approvedItems", sort.size());

			// 取得期間(FROM)順にソート
			Collections.sort(sort);

			// ソートしたデータをリストに入れる
			for (int i = 0; i < sort.size(); i++) {
				for (int j = 0; j < sortList.size(); j++) {
					if ((sortList.get(j).get(3)).equals(sort.get(i))) {
						list.add(sortList.get(j));
						sortList.remove(j);
						break;
					}
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