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

/**
 * Servlet implementation class Muster_DB_Import
 */
@WebServlet("/Muster_DB_Import")
public class Muster_DB_Import extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Muster_DB_Import() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();

		String select = request.getParameter("select");
		ArrayList<String> emp = new ArrayList<>();

		//データベース・テーブルに接続する準備
		Connection con = null;
		PreparedStatement ps = null;
		Statement stmtEmployee = null;
		ResultSet resultEmployee = null;

		//接続文字列の設定
		String url = Keyword.url();
		String user = Keyword.user();
		String password = Keyword.password();

		try {
			//PostgreSQLに接続
			con = DriverManager.getConnection(url, user, password);
			con.setAutoCommit(false);

			//SELECT文の実行
			stmtEmployee = con.createStatement();
			String sqlEmployee = "SELECT * from employee_muster";
			resultEmployee = stmtEmployee.executeQuery(sqlEmployee);

			while (resultEmployee.next()) {

				emp.add(resultEmployee.getString("fullname"));

				if (emp.get(0).equals(select)) {
					emp.add(resultEmployee.getString("id"));
					emp.add(resultEmployee.getString("pass"));
					emp.add(resultEmployee.getString("authority"));
					emp.add(resultEmployee.getString("affiliationcode"));
					emp.add(resultEmployee.getString("username"));
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//クローズ処理
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
				if (stmtEmployee != null) {
					stmtEmployee.close();
				}
				if (resultEmployee != null) {
					resultEmployee.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		session.setAttribute("test", select);
		session.setAttribute("fullname_M", emp.get(0));
		session.setAttribute("id_M", emp.get(1));
		session.setAttribute("pass_M", emp.get(2));
		session.setAttribute("authority_M", emp.get(3));
		session.setAttribute("affiliationcode_M", emp.get(4));
		session.setAttribute("username_M", emp.get(5));
		response.sendRedirect("muster.jsp");
	}

}