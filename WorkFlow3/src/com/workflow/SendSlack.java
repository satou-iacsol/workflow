package com.workflow;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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

import org.riversun.slacklet.SlackletService;

@WebServlet("/SendSlack")
public class SendSlack extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SendSlack() {
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

		String fromName = (String) session.getAttribute("fullname");
		String userName = "";
		String message = "有給休暇取得申請システム\n\n";

		String sendAction = (String) session.getAttribute("sendAction");

		if(((String) session.getAttribute("sendAction")).equals("申請")) {
			String number = (String) session.getAttribute("approvedNumber");
			String approverId = (String) session.getAttribute("approverNumber_1");

			if (((String) session.getAttribute("flag")).equals("1")) {
				approverId = (String) session.getAttribute("approverNumber_2");
			}

			message += "申請番号:" + number + "\n\n" + fromName + "により\n有給休暇が 申請 されました。\n"
					+ Keyword.webSiteURL();

			userName = userName_pick(approverId);

			sendSlack(userName, message);
		} else if (((String) session.getAttribute("sendAction")).equals("承認２")
				|| ((String) session.getAttribute("sendAction")).equals("差戻２")) {
			String number = (String) session.getAttribute("approvedNumber");
			String approvedId = (String) session.getAttribute("approvedId");

			message += "申請番号:" + number + "\n\n" + fromName + "により\n有給申請が " + sendAction.substring(0, 2) + " されました。\n"
					+ Keyword.webSiteURL();

			userName = userName_pick(approvedId);

			sendSlack(userName, message);
		} else if (sendAction.equals("承認")

				|| sendAction.equals("差戻")) {
			String number = (String) session.getAttribute("approvedNumber");
			String nextNumber = (String) session.getAttribute("nextNumber");

			message += "申請番号:" + number + "\n\n" + fromName + "により\n有給申請が " + sendAction
					+ " されました。\n申請番号:" + nextNumber + "\nへの操作をお願いいたします。\n"
					+ Keyword.webSiteURL();

			if (sendAction.equals("承認")) {
				userName = userName_pick((String) session.getAttribute("approverNumber_2"));

			} else if (sendAction.equals("差戻")) {
				userName = userName_pick((String) session.getAttribute("approverNumber_1"));

			}

			sendSlack(userName, message);

		} else {
			@SuppressWarnings("unchecked")
			ArrayList<String> historyList = (ArrayList<String>) session.getAttribute("historyList");
			String number = historyList.get(1);
			String approve1notification = (String) session.getAttribute("approve1notification");
			String approve2notification = (String) session.getAttribute("approve2notification");
			String fix_delete_comment = (String) session.getAttribute("fix_delete_comment");

			String message2 = "";

			if (sendAction.equals("修正")) {
				message2 = "新しい申請番号は\n" + historyList.get(1).substring(0, 14)
						+ String.format("%02d", Integer.parseInt(historyList.get(1).substring(14)) + 1) + "となります。\n";
			}

			message += "申請番号:" + number + "\n\n" + fromName + "により\n有給申請が " + sendAction + " されました。\n"
					+ message2 + sendAction + "コメント:\n" + fix_delete_comment + "\n" + Keyword.webSiteURL();

			if (approve1notification.equals("1")) {

				userName = userName_pick((String) session.getAttribute("approverName_1"));

				sendSlack(userName, message);

			}

			if (approve2notification.equals("1")) {

				userName = userName_pick((String) session.getAttribute("approverName_2"));

				sendSlack(userName, message);

			}
		}

		response.sendRedirect("menu.jsp");
	}

	private void sendSlack(String userName, String message) throws IOException {
		String botToken = "xoxb-888499428870-1207231347280-aOUFgZNOwUoLSmBLAMTub2rf";

		SlackletService slackService = new SlackletService(botToken);
		slackService.start();

		// ユーザーに対して、ダイレクトメッセージを送る
		slackService.sendDirectMessageTo(userName, message);

		// slackとの接続を終了
		slackService.stop();
	}

	private String userName_pick(String id) {

		String userName = "";

		// データベース・テーブルに接続する準備
		Connection con = null;
		Statement stmtEmployee = null;
		ResultSet resultEmployee = null;

		// 接続文字列の設定
		String url = Keyword.url();
		String user = Keyword.user();
		String password = Keyword.password();

		try {

			// PostgreSQLに接続
			con = DriverManager.getConnection(url, user, password);

			// SELECT文の作成・実行
			stmtEmployee = con.createStatement();
			String sqlEmployee = "SELECT * from employee_muster";
			resultEmployee = stmtEmployee.executeQuery(sqlEmployee);

			while (resultEmployee.next()) {

				if (resultEmployee.getString("id").equals(id)) {
					userName = resultEmployee.getString("userName");
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
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userName;
	}

}
