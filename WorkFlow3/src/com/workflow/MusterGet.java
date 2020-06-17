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
 * Servlet implementation class MusterGet
 */
@WebServlet("/MusterGet")
public class MusterGet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MusterGet() {
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

		// データベース・テーブルに接続する準備
		Connection con = null;
		PreparedStatement ps = null;
		Statement stmtEmployee = null;
		ResultSet resultEmployee = null;

		// 接続文字列の設定
		String url = Keyword.url();
		String user = Keyword.user();
		String password = Keyword.password();

		//作業用
		ArrayList<ArrayList<String>> lists = new ArrayList<>();

		try {
			// PostgreSQLに接続
			con = DriverManager.getConnection(url, user, password);
			con.setAutoCommit(false);

			//SELECT文の実行
			stmtEmployee = con.createStatement();
			String sqlEmployee = "SELECT * from employee_muster";
			resultEmployee = stmtEmployee.executeQuery(sqlEmployee);

			while (resultEmployee.next()) {
				ArrayList<String> list = new ArrayList<>();
				list.add((String) resultEmployee.getString("id"));
				list.add((String) resultEmployee.getString("pass"));
				list.add((String) resultEmployee.getString("authority"));
				list.add((String) resultEmployee.getString("fullname"));
				list.add((String) resultEmployee.getString("affiliationcode"));
				list.add((String) resultEmployee.getString("username"));
				lists.add(list);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// クローズ処理
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

		session.setAttribute("test", "test");
		session.setAttribute("lists", lists);
		response.sendRedirect("muster.jsp");
	}

}
