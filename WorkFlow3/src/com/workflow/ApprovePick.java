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

@WebServlet("/ApprovePick")
public class ApprovePick extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ApprovePick() {
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
			ArrayList<ArrayList<String>> sortList = new ArrayList<>();
			while (resultData.next()) {
				Statement stmtEmployee = null;
				ResultSet resultEmployee = null;
				Statement stmtBelongs = null;
				ResultSet resultBelongs = null;
				String fullname = "";
				String affiliationName = "";
				String approverId = "";

				stmtEmployee = con.createStatement();
				String sqlEmployee = "SELECT * from employee_muster";
				resultEmployee = stmtEmployee.executeQuery(sqlEmployee);
				while (resultEmployee.next()) {
					if (resultEmployee.getString("id").equals(resultData.getString("id"))) {
						fullname = resultEmployee.getString("fullname");
						break;
					}
				}

				stmtBelongs = con.createStatement();
				String sqlBelongs = "SELECT * from belongs";
				resultBelongs = stmtBelongs.executeQuery(sqlBelongs);
				while (resultBelongs.next()) {
					if (resultBelongs.getString("affiliationCode")
							.equals(resultEmployee.getString("affiliationCode"))) {
						affiliationName = resultBelongs.getString("affiliationName");
						switch (resultData.getString("approverNumber")) {
						case "1":
							approverId = resultBelongs.getString("approvernumber_1");
							break;
						case "2":
							approverId = resultBelongs.getString("approvernumber_2");
							break;
						}

						break;
					}
				}

				if (approverId.equals(id)
						&& resultData.getString("status").equals("")) {
					ArrayList<String> listSub = new ArrayList<>();

					for (int i = 1; i <= 5; i++) {
						listSub.add(resultData.getString(i));
					}

					listSub.add(fullname);
					listSub.add(affiliationName);

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
				if (stmtBelongs != null) {
					stmtBelongs.close();
				}
				if (resultBelongs != null) {
					resultBelongs.close();
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

			stmtData1 = con.createStatement();
			String sqlData1 = "SELECT * from data";
			resultData1 = stmtData1.executeQuery(sqlData1);

			while (resultData1.next()) {
				Statement stmtEmployee = null;
				ResultSet resultEmployee = null;
				Statement stmtBelongs = null;
				ResultSet resultBelongs = null;
				String fullname = "";
				String affiliationName = "";
				String approverId = "";

				stmtEmployee = con.createStatement();
				String sqlEmployee = "SELECT * from employee_muster";
				resultEmployee = stmtEmployee.executeQuery(sqlEmployee);
				while (resultEmployee.next()) {
					if (resultEmployee.getString("id").equals(resultData1.getString("id"))) {
						fullname = resultEmployee.getString("fullname");
						break;
					}
				}

				stmtBelongs = con.createStatement();
				String sqlBelongs = "SELECT * from belongs";
				resultBelongs = stmtBelongs.executeQuery(sqlBelongs);
				while (resultBelongs.next()) {
					if (resultBelongs.getString("affiliationCode")
							.equals(resultEmployee.getString("affiliationCode"))) {
						affiliationName = resultBelongs.getString("affiliationName");
						switch (resultData1.getString("approverNumber")) {
						case "1":
							approverId = resultBelongs.getString("approvernumber_1");
							break;
						case "2":
							approverId = resultBelongs.getString("approvernumber_2");
							break;
						}

						break;
					}
				}

				if (approverId.equals(id)
						&& resultData1.getString("status").equals("差戻")) {
					ArrayList<String> listSub = new ArrayList<>();

					for (int i = 1; i <= 5; i++) {
						listSub.add(resultData1.getString(i));
					}

					listSub.add(fullname);
					listSub.add(affiliationName);

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
				String fullname = "";
				String affiliationName = "";
				String approverId = "";

				stmtEmployee = con.createStatement();
				String sqlEmployee = "SELECT * from employee_muster";
				resultEmployee = stmtEmployee.executeQuery(sqlEmployee);
				while (resultEmployee.next()) {
					if (resultEmployee.getString("id").equals(resultData2.getString("id"))) {
						fullname = resultEmployee.getString("fullname");
						break;
					}
				}

				stmtBelongs = con.createStatement();
				String sqlBelongs = "SELECT * from belongs";
				resultBelongs = stmtBelongs.executeQuery(sqlBelongs);
				while (resultBelongs.next()) {
					if (resultBelongs.getString("affiliationCode")
							.equals(resultEmployee.getString("affiliationCode"))) {
						affiliationName = resultBelongs.getString("affiliationName");
						switch (resultData2.getString("approverNumber")) {
						case "1":
							approverId = resultBelongs.getString("approvernumber_1");
							break;
						case "2":
							approverId = resultBelongs.getString("approvernumber_2");
							break;
						}

						break;
					}
				}

				if (approverId.equals(id)
						&& resultData2.getString("status").equals("承認")) {
					ArrayList<String> listSub = new ArrayList<>();

					for (int i = 1; i <= 5; i++) {
						listSub.add(resultData2.getString(i));
					}

					listSub.add(fullname);
					listSub.add(affiliationName);

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
				if (resultData1 != null) {
					resultData1.close();
				}
				if (stmtData1 != null) {
					stmtData1.close();
				}
				if (resultData2 != null) {
					resultData2.close();
				}
				if (stmtData2 != null) {
					stmtData2.close();
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
