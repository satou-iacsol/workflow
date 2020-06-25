package com.workflow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * Servlet implementation class DownloadCSV
 */
@WebServlet("/UploadEmployeeCSV")
// location = "" をcsvを保存する場所に変更してください。
@MultipartConfig(location = "/usr/libexec/tomcat9/webapps/tmp/")
public class UploadEmployeeCSV extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UploadEmployeeCSV() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// location = "" をcsvを保存する場所に変更してください。
		String location = Keyword.directry();

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession();

		String databaseName = "employee_muster";
		String nameCSV = "社員マスタ";
		LocalDateTime localDateTime = LocalDateTime.now();
		String dateTime = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss_").format(localDateTime);

		Part part = request.getPart("upCSV");
		part.write(location + dateTime + nameCSV + ".csv");

		ArrayList<ArrayList<String>> database = new ArrayList<>();

		BufferedReader brDatabase = null;

		String uploadError = "";
		Boolean asUploadError = false;

		try {
			// 申請データの取得
			brDatabase = Files.newBufferedReader(
					Paths.get(location + dateTime + nameCSV + ".csv"),
					Charset.forName("Shift_JIS"));
			String lineDatabase = "";

			if (brDatabase.readLine() == null) {
				uploadError = "ファイルをアップロードしてください。";
				asUploadError = true;
			}

			while ((lineDatabase = brDatabase.readLine()) != null) {
				String[] databaseStr = lineDatabase.split(",", -1);
				ArrayList<String> databaseSub = new ArrayList<>();

				try {
					Integer.parseInt(databaseStr[0]);
					Integer.parseInt(databaseStr[2]);
					Integer.parseInt(databaseStr[4]);
				} catch (Exception e) {
					asUploadError = true;
				}

				if (databaseStr[0].length() == 4) {
					databaseSub.add(databaseStr[0]);
				} else {
					asUploadError = true;
				}

				if (databaseStr[1].length() == 8) {
					databaseSub.add(databaseStr[1]);
				} else {
					asUploadError = true;
				}

				if (databaseStr[2].length() == 1) {
					databaseSub.add(databaseStr[2]);
				} else {
					asUploadError = true;
				}

				if (databaseStr[3].length() <= 10) {
					databaseSub.add(databaseStr[3]);
				} else {
					asUploadError = true;
				}

				if (databaseStr[4].length() == 4) {
					databaseSub.add(databaseStr[4]);
				} else {
					asUploadError = true;
				}

				if (databaseStr[5].length() <= 50) {
					databaseSub.add(databaseStr[5]);
				} else {

					asUploadError = true;
				}

				if (asUploadError) {
					uploadError = "csv内の情報に間違いがあります。";
					break;
				}

				database.add(databaseSub);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				brDatabase.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (asUploadError) {
			session.setAttribute("uploadResult", uploadError);
			File delete = new File(location + dateTime + nameCSV + ".csv");
			delete.delete();
			response.sendRedirect("musterEmp.jsp");
		}
		if (!asUploadError) {
			// データベース・テーブルに接続する準備
			Connection con = null;
			Statement stmt = null;
			PreparedStatement pstmt = null;

			// 接続文字列の設定
			String url = Keyword.url();
			String user = Keyword.user();
			String password = Keyword.password();

			try {
				// PostgreSQLに接続
				con = DriverManager.getConnection(url, user, password);
				con.setAutoCommit(false);

				// TRUNCATE文の作成・実行
				stmt = con.createStatement();
				String sql = "TRUNCATE TABLE " + databaseName;
				stmt.executeUpdate(sql);

				for (int i = 0; i < database.size(); i++) {
					//実行するSQL文とパラメータを指定する
					String psql = "INSERT INTO " + databaseName + " values(?,?,?,?,?,?)";

					pstmt = con.prepareStatement(psql);
					pstmt.setString(1, database.get(i).get(0));
					pstmt.setString(2, database.get(i).get(1));
					pstmt.setString(3, database.get(i).get(2));
					pstmt.setString(4, database.get(i).get(3));
					pstmt.setString(5, database.get(i).get(4));
					pstmt.setString(6, database.get(i).get(5));

					//INSERT文を実行
					pstmt.executeUpdate();
				}

				//コミット
				try {
					con.commit();
				} catch (Exception e) {
					con.rollback();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				// クローズ処理
				try {
					if (stmt != null) {
						stmt.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			session.setAttribute("uploadResult", "csvのアップロードが完了しました。");
			response.sendRedirect("musterEmp.jsp");
		}
	}
}
