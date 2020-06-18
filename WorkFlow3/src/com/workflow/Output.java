package com.workflow;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Output
 */
@WebServlet("/Output")
public class Output extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Output() {
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

		String id = (String) session.getAttribute("id");
		String authority = (String) session.getAttribute("authority");
		String fullname = (String) session.getAttribute("fullname");
		String affiliationCode = (String) session.getAttribute("affiliationCode");
		String mail = (String) session.getAttribute("mail");
		String affiliationName = (String) session.getAttribute("affiliationName");
		String approverNumber_1 = (String) session.getAttribute("approverNumber_1");
		String approverName_1 = (String) session.getAttribute("approverName_1");
		String approverNumber_2 = (String) session.getAttribute("approverNumber_2");
		String approverName_2 = (String) session.getAttribute("approverName_2");

		String type = (String) session.getAttribute("type");
		String date_1 = (String) session.getAttribute("date_1");
		String date_2 = (String) session.getAttribute("date_2");
		String time_1 = (String) session.getAttribute("time_1");
		String time_2 = (String) session.getAttribute("time_2");
		String comment = (String) session.getAttribute("comment");
		String tellnumber = (String) session.getAttribute("tellnumber");
		String bikou = (String) session.getAttribute("bikou");
		//		String approver = (String) session.getAttribute("approver");
		String approver_1_2 = (String) session.getAttribute("approver_1_2");
		String flag = (String) session.getAttribute("flag");

		String type_1 = "01", type_2 = "02", type_3 = "03", type_4 = "04", type_5 = "05", type_6 = "06", type_7 = "07",
				type_8 = "08", type_9 = "09", type_10 = "10", type_11 = "11", type_12 = "12", type_13 = "13",
				type_14 = "14";

		//各変数に含まれる"時"と"分"を削除
		date_1 = date_1.replace("年", "");
		date_2 = date_2.replace("年", "");
		date_1 = date_1.replace("月", "");
		date_2 = date_2.replace("月", "");
		date_1 = date_1.replace("日", "");
		date_2 = date_2.replace("日", "");
		time_1 = time_1.replace("時", "");
		time_2 = time_2.replace("時", "");
		time_1 = time_1.replace("分", "");
		time_2 = time_2.replace("分", "");

		//date_1を年月日から月日へ変換
		String date_1_1 = date_1.substring(date_1.length() - 4);

		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

		String approvednumber = sdf.format(c.getTime()) + id + date_1_1 + "01";

		// データベース・テーブルに接続する準備
		Connection con = null;
		PreparedStatement ps = null;

		// 接続文字列の設定
		String url = Keyword.url();
		String user = Keyword.user();
		String password = Keyword.password();

		// SQL文
		String sql = "INSERT INTO data values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			// PostgreSQLに接続
			con = DriverManager.getConnection(url, user, password);
			con.setAutoCommit(false);

			//実行するSQL文とパラメータを指定する
			ps = con.prepareStatement(sql);
			ps.setString(1, approvednumber);
			ps.setString(2, id);
			if (type.equals("有給休暇")) {
				ps.setString(3, type_1);
			} else if (type.equals("代休")) {
				ps.setString(3, type_2);
			} else if (type.equals("生理休暇")) {
				ps.setString(3, type_3);
			} else if (type.equals("慶弔休暇")) {
				ps.setString(3, type_4);
			} else if (type.equals("特別休暇")) {
				ps.setString(3, type_5);
			} else if (type.equals("罹災休暇")) {
				ps.setString(3, type_6);
			} else if (type.equals("半休")) {
				ps.setString(3, type_7);
			} else if (type.equals("結婚休暇")) {
				ps.setString(3, type_8);
			} else if (type.equals("出産休暇")) {
				ps.setString(3, type_9);
			} else if (type.equals("忌引き休暇")) {
				ps.setString(3, type_10);
			} else if (type.equals("隔離休暇")) {
				ps.setString(3, type_11);
			} else if (type.equals("一周忌")) {
				ps.setString(3, type_12);
			} else if (type.equals("受験休暇")) {
				ps.setString(3, type_13);
			} else if (type.equals("産前産後休暇")) {
				ps.setString(3, type_14);
			}
			ps.setString(4, date_1);
			ps.setString(5, date_2);
			ps.setString(6, time_1);
			ps.setString(7, time_2);
			ps.setString(8, comment);
			ps.setString(9, tellnumber);
			if(bikou != null) {
				ps.setString(10, bikou);
			}else {
				ps.setString(10, "");
			}
			ps.setString(11, flag);
			if (approver_1_2.equals(approverName_1)) {
				ps.setString(12, approverNumber_1);
			} else if (approver_1_2.equals(approverName_2)) {
				ps.setString(12, approverNumber_2);
			}
			ps.setString(13, "");
			ps.setString(14, "");
			ps.setString(15, "");
			ps.setString(16, "");
			ps.setString(17, "0");

			//INSERT文を実行
			ps.executeUpdate();

			//コミット
			con.commit();

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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		session.setAttribute("id", id);
		session.setAttribute("authority", authority);
		session.setAttribute("fullname", fullname);
		session.setAttribute("affiliationCode", affiliationCode);
		session.setAttribute("mail", mail);
		session.setAttribute("affiliationName", affiliationName);
		session.setAttribute("approverNumber_1", approverNumber_1);
		session.setAttribute("approverName_1", approverName_1);
		session.setAttribute("approverNumber_2", approverNumber_2);
		session.setAttribute("approverName_2", approverName_2);
		session.setAttribute("sendAction", "申請");
		session.setAttribute("approvedNumber", approvednumber);

		request.getServletContext().getRequestDispatcher("/SendSlack").forward(request, response);
	}
}