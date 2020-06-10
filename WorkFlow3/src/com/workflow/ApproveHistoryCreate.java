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

@WebServlet("/ApproveHistoryCreate")
public class ApproveHistoryCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ApproveHistoryCreate() {
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

		ArrayList<String> historyList = new ArrayList<>();
		for (ArrayList<String> list : (ArrayList<ArrayList<String>>)session.getAttribute("historysList")) {
			if(list.get(1).equals(request.getParameter("number"))) {
				historyList = list;
				break;
			}
		}

		ArrayList<String> list = new ArrayList<>();

		for (String s : historyList) {
			list.add(s);
		}
		// データベース・テーブルに接続する準備
		Connection con = null;
		Statement stmtData = null;
		Statement stmtData1 = null;
		ResultSet resultData = null;
		ResultSet resultData1 = null;

		// 接続文字列の設定
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "0978781";

		String number = list.get(1);
		try {
			String comment = "";
			String tellnumber = "";
			String bikou = "";
			String flag = "";
			String fix_delete_comment = "";
			String approverComment = "";
			String approve1Comment = "";
			String approve2Comment = "";

			// PostgreSQLに接続
			con = DriverManager.getConnection(url, user, password);

			// SELECT文の作成・実行
			stmtData = con.createStatement();
			String sqlData = "SELECT * from data";
			resultData = stmtData.executeQuery(sqlData);

			while (resultData.next()) {

				if (resultData.getString("number").equals(number)) {
					comment = resultData.getString("comment");
					tellnumber = resultData.getString("tellnumber");
					bikou = resultData.getString("bikou");
					flag = resultData.getString("flag");
					fix_delete_comment = resultData.getString("fix_delete_comment");
					approverComment = resultData.getString("approverComment");
					break;
				}
			}

			if (flag.equals("0")) {
				approve1Comment = approverComment;

				// SELECT文の作成・実行
				stmtData1 = con.createStatement();
				String sqlData1 = "SELECT * from data";
				resultData1 = stmtData.executeQuery(sqlData1);

				while (resultData1.next()) {

					if (resultData1.getString("number").equals(number.substring(0, 14)
							+ String.format("%02d", Integer.parseInt(number.substring(14)) - 1))) {
						approve2Comment = resultData1.getString("approverComment");
						break;
					}
				}
			} else if (flag.equals("1")) {
				approve1Comment = "承認者１をスキップしました。";
				approve2Comment = approverComment;
			}

			list.add(comment);
			list.add(tellnumber);
			list.add(bikou);
			list.add(flag);
			list.add(fix_delete_comment);
			list.add(approve1Comment);
			list.add(approve2Comment);

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
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		session.setAttribute("historyList", list);

		if (request.getParameter("action").equals("history")) {
			session.setAttribute("flowList", flowListCreate(number.substring(0, 14)));
			response.sendRedirect("approveHistory.jsp");
		} else if (request.getParameter("action").equals("fix")) {
			response.sendRedirect("approveFixAction.jsp");
		} else if (request.getParameter("action").equals("delete")) {
			response.sendRedirect("approveDeleteAction.jsp");
		}
	}

	protected ArrayList<ArrayList<String>> flowListCreate(String number14) {
		ArrayList<ArrayList<String>> sortList = new ArrayList<>();
		ArrayList<ArrayList<String>> flowList = new ArrayList<>();

		// データベース・テーブルに接続する準備
		Connection con = null;
		Statement stmtData = null;
		ResultSet resultData = null;

		// 接続文字列の設定
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "0978781";

		try {
			String number = "";
			String approver = "";
			String approverComment = "";
			String approvedDate = "";
			String status = "";

			// PostgreSQLに接続
			con = DriverManager.getConnection(url, user, password);

			// SELECT文の作成・実行
			stmtData = con.createStatement();
			String sqlData = "SELECT * from data";
			resultData = stmtData.executeQuery(sqlData);

			while (resultData.next()) {
				ArrayList<String> listSub = new ArrayList<>();
				if (resultData.getString("number").substring(0, 14).equals(number14)) {
					number = resultData.getString("number").substring(14);

					Statement stmtEmployee = null;
					ResultSet resultEmployee = null;

					stmtEmployee = con.createStatement();
					String sqlEmployee = "SELECT * from employee_muster";
					resultEmployee = stmtEmployee.executeQuery(sqlEmployee);

					while (resultEmployee.next()) {

						if (resultEmployee.getString("id").equals(resultData.getString("approverNumber"))) {
							approver = resultEmployee.getString("fullname");
							break;
						}
					}

					if (resultEmployee != null) {
						resultEmployee.close();
					}
					if (stmtEmployee != null) {
						stmtEmployee.close();
					}

					approverComment = resultData.getString("approverComment");
					approvedDate = resultData.getString("approvedDate");
					status = resultData.getString("status");

					listSub.add(number);
					listSub.add(approver);
					listSub.add(approverComment);
					listSub.add(approvedDate);
					listSub.add(status);

					sortList.add(listSub);
				}

			}
			ArrayList<String> sort = new ArrayList<>();

			for (int i = 0; i < sortList.size(); i++) {
				sort.add(sortList.get(i).get(0));
			}

			Collections.sort(sort);

			// ソートしたデータをリストに入れる
			for (int i = 0; i < sort.size(); i++) {
				for (int j = 0; j < sortList.size(); j++) {
					if ((sortList.get(j).get(0)).equals(sort.get(i))) {
						flowList.add(sortList.get(j));
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
		return flowList;
	}
}