package com.workflow;

import java.io.IOException;
import java.io.PrintWriter;
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

/**
 * Servlet implementation class DownloadCSV
 */
@WebServlet("/DownloadCSV")
public class DownloadCSV extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DownloadCSV() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/csv;charset=Shift_JIS");

		String databaseName = "";
		String nameCSV = "";
		String column = "";
		if (request.getParameter("buttonCSV").equals("employeeCSV")) {
			databaseName = "employee_muster";
			nameCSV = "社員マスタ";
			column ="社員番号,パスワード,承認権限,氏名,所属コード,ユーザー名\r\n";
		} else if (request.getParameter("buttonCSV").equals("belongsCSV")) {
			databaseName = "belongs";
			nameCSV = "部署マスタ";
			column ="所属コード,所属,承認者１社員番号,承認者２社員番号\r\n";
		}

		ArrayList<ArrayList<String>> database = new ArrayList<>();

		// データベース・テーブルに接続する準備
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;

		// 接続文字列の設定
		String url = Keyword.url();
		String user = Keyword.user();
		String password = Keyword.password();

		try {
			// PostgreSQLに接続
			con = DriverManager.getConnection(url, user, password);

			// SELECT文の作成・実行
			stmt = con.createStatement();
			String sql = "SELECT * from " + databaseName;
			result = stmt.executeQuery(sql);

			while (result.next()) {
				ArrayList<String> databaseSub = new ArrayList<>();
				for (int i = 0; i < result.getMetaData().getColumnCount(); i++) {
					databaseSub.add(result.getString(i + 1));
				}
				database.add(databaseSub);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// クローズ処理
			try {
				if (result != null) {
					result.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		String fileName = new String((nameCSV + ".csv").getBytes("Shift_JIS"), "ISO-8859-1");

		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		PrintWriter writer = response.getWriter();

		writer.write(column);
		for (int i = 0; i < database.size(); i++) {
			for (int j = 0; j < database.get(i).size(); j++) {
				if (j == 0) {
					writer.write(database.get(i).get(j));
				} else {
					writer.write("," + database.get(i).get(j));
				}
			}
			writer.write("\r\n");
		}
		writer.close();
	}

}
