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
@WebServlet("/Belongs_DB_Import")
public class Belongs_DB_Import extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Belongs_DB_Import() {
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
		String belongsC = request.getParameter("belongsC");
		String belongsN = request.getParameter("belongsN");
		String app1 = request.getParameter("app1");
		String app2 = request.getParameter("app2");
		String sel = request.getParameter("select");
		ArrayList<String> bel = new ArrayList<>();
		//データベース・テーブルに接続する準備
		Connection con = null;
		PreparedStatement ps = null;
		Statement stmtBelongs = null;
		ResultSet resultBelongs = null;
		//接続文字列の設定
		String url = Keyword.url();
		String user = Keyword.user();
		String password = Keyword.password();

		//部署名一覧出力用
		ArrayList<ArrayList<String>> belongs_lists = new ArrayList<>();
		//flag用
		String flag_M = "";

		try {
			//PostgreSQLに接続
			con = DriverManager.getConnection(url, user, password);
			con.setAutoCommit(false);
			//★決定ボタン押下時の処理★
			if (submitbtn.equals("determination")) {
				//SELECT文の実行
				stmtBelongs = con.createStatement();
				String sqlBelongs = "SELECT * from belongs";
				resultBelongs = stmtBelongs.executeQuery(sqlBelongs);
				while (resultBelongs.next()) {
					String ver = resultBelongs.getString("affiliationname");
					if (ver.equals(select)) {
						bel.add(resultBelongs.getString("affiliationname"));
						bel.add(resultBelongs.getString("affiliationcode"));
						bel.add(resultBelongs.getString("approvernumber_1"));
						bel.add(resultBelongs.getString("approvernumber_2"));
						break;
					}
				}
				flag_M = "1";
			}
			//★クリアボタン押下時の処理★
			if(submitbtn.contentEquals("clea")) {
				flag_M = "0";
			}
			//★新規登録ボタン押下時の処理★
			if (submitbtn.equals("new")) {
				//SQL文
				String sql = "INSERT INTO belongs values(?,?,?,?)";
				//実行するSQL文とパラメータを指定する
				ps = con.prepareStatement(sql);
				ps.setString(1, belongsC);
				ps.setString(2, belongsN);
				ps.setString(3, app1);
				ps.setString(4, app2);
				//INSERT文を実行
				ps.executeUpdate();
				//コミット
				con.commit();
			}
			//★更新ボタン押下時の処理★
			if (submitbtn.equals("update")) {
				//SQL文
				String sql = "UPDATE belongs SET affiliationname=?, approvernumber_1=?, approvernumber_2=? WHERE affiliationcode=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, belongsN);
				ps.setString(2, app1);
				ps.setString(3, app2);
				ps.setString(4, belongsC);
				//UPDATE文を実行
				ps.executeUpdate();
				//コミット
				con.commit();

			}
			//★削除ボタン押下時の処理★
			if (submitbtn.equals("delete")) {
				//SQL文
				String sql = "DELETE FROM belongs WHERE affiliationcode = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, belongsC);
				//DELETE文を実行
				ps.executeUpdate();
				//コミット
				con.commit();
			}

			//名前一覧出力用
			stmtBelongs = con.createStatement();
			String sqlEmployee = "SELECT * from belongs";
			resultBelongs = stmtBelongs.executeQuery(sqlEmployee);
			while (resultBelongs.next()) {
				ArrayList<String> list = new ArrayList<>();
				list.add(resultBelongs.getString("affiliationcode"));
				list.add(resultBelongs.getString("affiliationname"));
				list.add(resultBelongs.getString("approvernumber_1"));
				list.add(resultBelongs.getString("approvernumber_2"));
				belongs_lists.add(list);
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
				if (stmtBelongs != null) {
					stmtBelongs.close();
				}
				if (resultBelongs != null) {
					resultBelongs .close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (submitbtn.equals("determination")) {
			session.setAttribute("belongsN", bel.get(0));
			session.setAttribute("belongsC", bel.get(1));
			session.setAttribute("app1", bel.get(2));
			session.setAttribute("app2", bel.get(3));
			session.setAttribute("flag_M", flag_M);
		}
		if(submitbtn.equals("clea")) {
			session.setAttribute("belongsN", "");
			session.setAttribute("belongsC", "");
			session.setAttribute("app1", "");
			session.setAttribute("app2", "");
			session.setAttribute("flag_M", flag_M);
		}
		session.setAttribute("belongs_lists", belongs_lists);
		session.setAttribute("sel", sel);
		response.sendRedirect("musterApp.jsp");
	}
}
