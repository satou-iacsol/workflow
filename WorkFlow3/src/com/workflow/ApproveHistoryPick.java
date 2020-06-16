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
				String number01 = "";
				String numberNow = "";
				String type = "";
				String fromDateTime = "";
				String toDateTime = "";
				String approverNumber = "";
				String flow = "";
				String result = "";

				if (resultData.getString("id").equals(id) && resultData.getString("delete_flag").equals("0")
						&& resultData.getString("number").substring(14).equals("01")) {
					ArrayList<String> listSub = new ArrayList<>();

					number01 = resultData.getString("number");

					stmtData1 = con.createStatement();
					String sqlData1 = "SELECT * from data";
					resultData1 = stmtData1.executeQuery(sqlData1);

					ArrayList<String> sort = new ArrayList<>();
					while (resultData1.next()) {
						if (resultData1.getString("number").substring(0, 14).equals(number01.substring(0, 14))) {
							sort.add(resultData1.getString("number"));
						}
					}
					Collections.sort(sort);

					stmtData2 = con.createStatement();
					String sqlData2 = "SELECT * from data";
					resultData2 = stmtData2.executeQuery(sqlData2);

					String status = "";
					while (resultData2.next()) {
						if (resultData2.getString("number").equals(sort.get(sort.size() - 1))) {
							numberNow = resultData2.getString("number");
							type = resultData2.getString("type");
							fromDateTime = resultData2.getString("date_1") + resultData2.getString("date_3");
							toDateTime = resultData2.getString("date_2") + resultData2.getString("date_4");
							approverNumber = resultData2.getString("approverNumber");
							status = resultData2.getString("status");
							break;
						}
					}

					if (status.equals("承認")) {
						if (session.getAttribute("approve1Id").equals(approverNumber)) {
							flow = (String) session.getAttribute("approve1Name");
						} else if (session.getAttribute("approve2Id").equals(approverNumber)) {
							flow = (String) session.getAttribute("approve2Name");
						}
						result = "承認完了";
					} else if (status.equals("差戻")) {
						if (session.getAttribute("approve1Id").equals(approverNumber)) {
							flow = (String) session.getAttribute("approve1Name");
						} else if (session.getAttribute("approve2Id").equals(approverNumber)) {
							flow = (String) session.getAttribute("approve2Name");
						}
						result = "差戻";
					} else {
						String beforeApprover = "";
						String afterApprover = "";

						if (numberNow.substring(14).equals("01")
								|| !(resultData.getString("fix_delete_comment").equals(""))) {
							beforeApprover = (String) session.getAttribute("approvedName");
						} else {
							String BeforeApproverNumber = "";

							stmtData3 = con.createStatement();
							String sqlData3 = "SELECT * from data";
							resultData3 = stmtData3.executeQuery(sqlData3);

							while (resultData3.next()) {
								if (resultData3.getString("number").equals(numberNow.substring(0, 14)
										+ String.format("%02d", Integer.parseInt(numberNow.substring(14)) - 1))) {
									BeforeApproverNumber = resultData3.getString("approverNumber");
									break;
								}
							}
							if (session.getAttribute("approve1Id").equals(BeforeApproverNumber)) {
								beforeApprover = (String) session.getAttribute("approve1Name");
							} else if (session.getAttribute("approve2Id").equals(BeforeApproverNumber)) {
								beforeApprover = (String) session.getAttribute("approve2Name");
							}
						}
						if (session.getAttribute("approve1Id").equals(approverNumber)) {
							afterApprover = (String) session.getAttribute("approve1Name");
						} else if (session.getAttribute("approve2Id").equals(approverNumber)) {
							afterApprover = (String) session.getAttribute("approve2Name");
						}
						flow = beforeApprover + "  →  " + afterApprover;
						result = "承認待ち";
					}
					listSub.add(number01);
					listSub.add(numberNow);
					listSub.add(type);
					listSub.add(fromDateTime);
					listSub.add(toDateTime);
					listSub.add(approverNumber);
					listSub.add(flow);
					listSub.add(result);

					sortList.add(listSub);

				}
			}

			ArrayList<String> sort = new ArrayList<>();

			// 取得期間(FROM)を取得
			for (int i = 0; i < sortList.size(); i++) {
				sort.add(sortList.get(i).get(3));
			}

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
				if (resultData3 != null) {
					resultData3.close();
				}
				if (stmtData3 != null) {
					stmtData3.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		session.setAttribute("historysList", list);
		response.sendRedirect("approveHistoryChoose.jsp");
	}
}