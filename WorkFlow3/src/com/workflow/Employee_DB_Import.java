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
@WebServlet("/Employee_DB_Import")
public class Employee_DB_Import extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Employee_DB_Import() {
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
		//各ボタンのvalue値を取得
		String submitbtn = request.getParameter("submitbtn");
		//各項目の入力情報を取得
		String numberE = request.getParameter("numberE");
		String nameF = request.getParameter("nameF");
		String pass = request.getParameter("password");
		String approvalP = request.getParameter("approvalP");
		String affiliationC = request.getParameter("affiliationC");
		String userN = request.getParameter("userN");
		String sel = request.getParameter("select");
		ArrayList<String> emp = new ArrayList<>();
		//通知用
		String uploadResult = "";
		//データベース・テーブルに接続する準備
		Connection con = null;
		PreparedStatement ps = null;
		Statement stmtEmployee = null;
		ResultSet resultEmployee = null;
		//接続文字列の設定
		String url = Keyword.url();
		String user = Keyword.user();
		String password = Keyword.password();

		//名前一覧出力用
		ArrayList<ArrayList<String>> lists = new ArrayList<>();
		//flag用
		String flag_M = "";

		try {
			//PostgreSQLに接続
			con = DriverManager.getConnection(url, user, password);
			con.setAutoCommit(false);
			//SELECT文の実行
			stmtEmployee = con.createStatement();
			String sqlEmployee = "SELECT * from employee_muster";
			resultEmployee = stmtEmployee.executeQuery(sqlEmployee);
			//★決定ボタン押下時の処理★
			if (submitbtn.equals("determination")) {
				while (resultEmployee.next()) {
					String ver = resultEmployee.getString("id");
					if (ver.equals(select)) {
						emp.add(resultEmployee.getString("fullname"));
						emp.add(resultEmployee.getString("id"));
						emp.add(resultEmployee.getString("pass"));
						emp.add(resultEmployee.getString("authority"));
						emp.add(resultEmployee.getString("affiliationcode"));
						emp.add(resultEmployee.getString("username"));
						break;
					}
				}
				flag_M = "1";
			}
			//★クリアボタン押下時の処理★
			if (submitbtn.contentEquals("clea")) {
				flag_M = "0";
			}
			//★新規登録ボタン押下時の処理★
			if (submitbtn.equals("new")) {
				//idの重複チェック
				resultEmployee = stmtEmployee.executeQuery(sqlEmployee);
				while (resultEmployee.next()) {
					String ver = resultEmployee.getString("id");
					if (numberE.equals(ver)) {
						uploadResult = "idが重複しています。";
						break;
					}
				}
				//idが重複していなければ以下の処理を実行
				if (uploadResult == "") {
					//SQL文
					String sql = "INSERT INTO employee_muster values(?,?,?,?,?,?)";
					//実行するSQL文とパラメータを指定する
					ps = con.prepareStatement(sql);
					ps.setString(1, numberE);
					ps.setString(2, pass);
					ps.setString(3, approvalP);
					ps.setString(4, nameF);
					ps.setString(5, affiliationC);
					ps.setString(6, userN);
					//INSERT文を実行
					ps.executeUpdate();
					//コミット
					con.commit();

					uploadResult = "登録が完了しました。";
				}
			}
			//★更新ボタン押下時の処理★
			if (submitbtn.equals("update")) {
				//存在するidかチェック
				resultEmployee = stmtEmployee.executeQuery(sqlEmployee);
				while (resultEmployee.next()) {
					String ver = resultEmployee.getString("id");
					if (numberE.equals(ver)) {
						//SQL文
						String sql = "UPDATE employee_muster SET pass=?, authority=?, fullname=?, affiliationcode=?, username=? WHERE id=?";
						ps = con.prepareStatement(sql);
						ps.setString(1, pass);
						ps.setString(2, approvalP);
						ps.setString(3, nameF);
						ps.setString(4, affiliationC);
						ps.setString(5, userN);
						ps.setString(6, numberE);
						//UPDATE文を実行
						ps.executeUpdate();
						//コミット
						con.commit();

						uploadResult = "更新が完了しました。";
						break;
					}
				}
				if(uploadResult == "") {uploadResult = "idが存在しません。";}
			}
			//★削除ボタン押下時の処理★
			if (submitbtn.equals("delete")) {
				//存在するidかチェック
				resultEmployee = stmtEmployee.executeQuery(sqlEmployee);
				while (resultEmployee.next()) {
					String ver = resultEmployee.getString("id");
					if (numberE.equals(ver)) {
						//SQL文
						String sql = "DELETE FROM employee_muster WHERE id = ?";
						ps = con.prepareStatement(sql);
						ps.setString(1, numberE);
						//DELETE文を実行
						ps.executeUpdate();
						//コミット
						con.commit();

						uploadResult = "削除が完了しました。";
					}
				}
				if(uploadResult == "") {uploadResult = "idが存在しません。";}
			}

			//名前一覧出力用
			resultEmployee = stmtEmployee.executeQuery(sqlEmployee);
			while (resultEmployee.next()) {
				ArrayList<String> list = new ArrayList<>();
				list.add(resultEmployee.getString("id"));
				list.add(resultEmployee.getString("pass"));
				list.add(resultEmployee.getString("authority"));
				list.add(resultEmployee.getString("fullname"));
				list.add(resultEmployee.getString("affiliationcode"));
				list.add(resultEmployee.getString("username"));
				lists.add(list);
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
		if (submitbtn.equals("determination")) {
			session.setAttribute("fullname_M", emp.get(0));
			session.setAttribute("id_M", emp.get(1));
			session.setAttribute("pass_M", emp.get(2));
			session.setAttribute("authority_M", emp.get(3));
			session.setAttribute("affiliationcode_M", emp.get(4));
			session.setAttribute("username_M", emp.get(5));
			session.setAttribute("flag_M", flag_M);
		}
		if (submitbtn.equals("clea")) {
			session.setAttribute("fullname_M", "");
			session.setAttribute("id_M", "");
			session.setAttribute("pass_M", "");
			session.setAttribute("authority_M", "");
			session.setAttribute("affiliationcode_M", "");
			session.setAttribute("username_M", "");
			session.setAttribute("flag_M", flag_M);
		}
		if (submitbtn.equals("update")) {
			session.setAttribute("fullname_M", nameF);
			session.setAttribute("id_M", numberE);
			session.setAttribute("pass_M", pass);
			session.setAttribute("authority_M", approvalP);
			session.setAttribute("affiliationcode_M", affiliationC);
			session.setAttribute("username_M", userN);
		}
		session.setAttribute("uploadResult", uploadResult);
		session.setAttribute("lists", lists);
		session.setAttribute("sel", sel);
		response.sendRedirect("musterEmp.jsp");

	}
}
