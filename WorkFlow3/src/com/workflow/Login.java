package com.workflow;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
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

		String id = request.getParameter("id");
		String pass = request.getParameter("pass");

		String code = "";
		String authority = "";
		String fullname = "";
		String affiliationCode = "";
		String userName = "";
		String affiliationName = "";
		String approverNumber_1 = "";
		String approverName_1 = "";
		String approverNumber_2 = "";
		String approverName_2 = "";

		// データベース・テーブルに接続する準備
		Connection con = null;
		Statement stmtEmployee = null;
		ResultSet resultEmployee = null;
		Statement stmtBelongs = null;
		ResultSet resultBelongs = null;
		Statement stmtApprover_1 = null;
		ResultSet resultApprover_1 = null;
		Statement stmtApprover_2 = null;
		ResultSet resultApprover_2 = null;

		// 接続文字列の設定
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "0978781";


		try {
			// PostgreSQLに接続
			con = DriverManager.getConnection(url, user, password);

			// SELECT文の作成・実行
			stmtEmployee = con.createStatement();
			String sqlEmployee = "SELECT * from employee_muster";
			resultEmployee = stmtEmployee.executeQuery(sqlEmployee);


			while (resultEmployee.next()) {
				if (resultEmployee.getString("id").equals(id) && resultEmployee.getString("pass").equals(pass)) {
					code = resultEmployee.getString("affiliationcode");
					authority = resultEmployee.getString("authority");
					fullname = resultEmployee.getString("fullname");
					affiliationCode = resultEmployee.getString("affiliationCode");
					userName = resultEmployee.getString("userName");
					break;
				}
			}

			stmtBelongs = con.createStatement();
			String sqlBelongs = "SELECT * from belongs";
			resultBelongs = stmtBelongs.executeQuery(sqlBelongs);

			while (resultBelongs.next()) {
				if (resultBelongs.getString("affiliationcode").equals(code)) {
					affiliationName = resultBelongs.getString("affiliationName");
					approverNumber_1 = resultBelongs.getString("approverNumber_1");
					approverNumber_2 = resultBelongs.getString("approverNumber_2");
					break;
				}
			}

			stmtApprover_1 = con.createStatement();
			String sqlApprover_1 = "SELECT * from employee_muster";
			resultApprover_1 = stmtApprover_1.executeQuery(sqlApprover_1);

			while (resultApprover_1.next()) {
				if (resultApprover_1.getString("id").equals(approverNumber_1)) {
					approverName_1 = resultApprover_1.getString("fullname");
					break;
				}
			}

			stmtApprover_2 = con.createStatement();
			String sqlApprover_2 = "SELECT * from employee_muster";
			resultApprover_2 = stmtApprover_2.executeQuery(sqlApprover_2);

			while (resultApprover_2.next()) {
				if (resultApprover_2.getString("id").equals(approverNumber_2)) {
					approverName_2 = resultApprover_2.getString("fullname");
					break;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// クローズ処理
			try {
				if (resultEmployee != null) {
					resultEmployee.close();
				}
				if (stmtEmployee != null) {
					stmtEmployee.close();
				}
				if (resultBelongs != null) {
					resultBelongs.close();
				}
				if (stmtBelongs != null) {
					stmtBelongs.close();
				}
				if (resultApprover_1 != null) {
					resultApprover_1.close();
				}
				if (stmtApprover_1 != null) {
					stmtApprover_1.close();
				}
				if (resultApprover_2 != null) {
					resultApprover_2.close();
				}
				if (stmtApprover_2 != null) {
					stmtApprover_2.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (code.equals("")) {
				session.setAttribute("loginError", "ユーザーID／パスワードが間違っています。");
				response.sendRedirect("login.jsp");
			} else {
				session.setAttribute("id", id);
				session.setAttribute("authority", authority);
				session.setAttribute("fullname", fullname);
				session.setAttribute("affiliationCode", affiliationCode);
				session.setAttribute("userName", userName);
				session.setAttribute("affiliationName", affiliationName);
				session.setAttribute("approverNumber_1", approverNumber_1);
				session.setAttribute("approverName_1",  approverName_1);
				session.setAttribute("approverNumber_2", approverNumber_2);
				session.setAttribute("approverName_2", approverName_2);
				response.sendRedirect("menu.jsp");
			}
		}
	}
}