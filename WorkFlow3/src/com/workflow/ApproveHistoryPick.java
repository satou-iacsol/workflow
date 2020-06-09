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
		Statement stmtData2 = null;
		Statement stmtData3 = null;
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
				String number01 = "";
				String numberNow = "";
				String type = "";
				String fromDateTime = "";
				String toDateTime = "";
				String flow = "";
				String result = "";


				Statement stmtEmployee = null;
				ResultSet resultEmployee = null;

				if (resultData.getString("id").equals(id) && resultData.getString("delete_flag").equals("0")
						&& resultData.getString("number").substring(14).equals("01")) {
					ArrayList<String> listSub = new ArrayList<>();

					number01 = resultData.getString("number");


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

					String approverNumber = "";
					String status = "";
					while (resultData2.next()) {
						if (resultData2.getString("number").equals(sort.get(sort.size() - 1))) {
							numberNow = resultData2.getString("number");

							switch (Integer.parseInt(resultData2.getString("type"))) {
							case 1:
								type = "1.有給休暇";
								break;
							case 2:
								type = "2.代休";
								break;
							case 3:
								type = "3.生理休暇";
								break;
							case 4:
								type = "4.慶弔休暇";
								break;
							case 5:
								type = "5.特別休暇";
								break;
							case 6:
								type = "6.罹災休暇";
								break;
							case 7:
								type = "7.半休";
								break;
							case 8:
								type = "8.結婚休暇";
								break;
							case 9:
								type = "9.出産休暇";
								break;
							case 10:
								type = "10.忌引き休暇";
								break;
							case 11:
								type = "11.隔離休暇";
								break;
							case 12:
								type = "12.一周忌";
								break;
							case 13:
								type = "13.受験休暇";
								break;
							case 14:
								type = "14.産前産後休暇";
								break;
							}

							fromDateTime = resultData2.getString("date_1") + resultData2.getString("date_3");
							toDateTime = resultData2.getString("date_2") + resultData2.getString("date_4");
							approverNumber = resultData2.getString("approverNumber");
							status = resultData2.getString("status");
							break;
						}
					}

					if (status.equals("承認")) {
						stmtEmployee = con.createStatement();
						String sqlEmployee = "SELECT * from employee_muster";
						resultEmployee = stmtEmployee.executeQuery(sqlEmployee);
						while (resultEmployee.next()) {
							if (resultEmployee.getString("id").equals(approverNumber)) {
								flow = resultEmployee.getString("fullname");
								break;
							}
						}
						result = "承認完了";
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
					listSub.add(number01);
					listSub.add(numberNow);
					listSub.add(type);
					listSub.add(fromDateTime);
					listSub.add(toDateTime);
					listSub.add(flow);
					listSub.add(result);

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
		response.sendRedirect("approveHistoryChoose.jsp");
	}
}