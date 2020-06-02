package com.workflow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ApproveSQL")
public class ApproveSQL extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ApproveSQL() {
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
		final String referenceDirectory = (String) session.getAttribute("referenceDirectory");

		String number = (String) session.getAttribute("approvedNumber");
		String[] preData = new String[15];
		BufferedReader brData = null;

		// データベース・テーブルに接続する準備
		Connection con = null;
		PreparedStatement pstmtData = null;
		// 接続文字列の設定
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "0978781";
		try {
			// PostgreSQLに接続
			con = DriverManager.getConnection(url, user, password);
			// SELECT文の作成・実行
			pstmtData = con.prepareStatement("UPDATE data set approverComment=?,approvedDate=?,status=? WHERE number=?");

			pstmtData.setString(1, (String)session.getAttribute("approvedComment"));
			LocalDate localDateTime = LocalDate.now();
			pstmtData.setString(2, DateTimeFormatter.ofPattern("yyyyMMdd").format(localDateTime));
			pstmtData.setString(3, (String)session.getAttribute("approvedAction"));
			pstmtData.setString(4, (String)session.getAttribute("approvedNumber"));
			con.setAutoCommit(false);
			try {
				pstmtData.executeUpdate();
				con.commit();
			} catch (Exception e) {
				con.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// クローズ処理
			try {
				if (pstmtData != null) {
					pstmtData.close();
				}

				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 申請データの更新
		try {
			ArrayList<String> list = new ArrayList<>();
			String[] data = new String[15];
			brData = Files.newBufferedReader(
					Paths.get(referenceDirectory + "data.csv"),
					Charset.forName("UTF-8"));

			String lineData = "";
			// 使用する申請データの抽出
			while ((lineData = brData.readLine()) != null) {
				data = lineData.split(",", -1);
				if (data[0].equals(number)) {
					preData = data;
					// コメントの更新
					data[12] = (String) session.getAttribute("approvedComment");
					// ステータスを実行内容で更新
					data[14] = (String) session.getAttribute("approvedAction");
					// 承認差戻日を当日のデータに更新
					LocalDate localDateTime = LocalDate.now();
					data[13] = DateTimeFormatter.ofPattern("yyyyMMdd").format(localDateTime);
					// 更新内容を反映したデータの作成
					String newData = data[0];
					for (int i = 1; i <= 14; i++) {
						newData += "," + data[i];
					}
					// 更新した申請番号のデータ
					list.add(newData);
				} else {
					// 更新のない申請番号のデータ
					list.add(lineData);
				}
			}
			// 申請データの書き込み
			BufferedWriter writer = Files
					.newBufferedWriter(Paths.get(referenceDirectory + "data.csv"));
			for (int i = 0; i < list.size(); i++) {
				writer.write(list.get(i));
				writer.newLine();
			}
			writer.flush();
			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				brData.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (((String) session.getAttribute("approvedAction")).equals("承認")) {
			if (((String) session.getAttribute("id")).equals((String) session.getAttribute("approve2Id"))) {
				session.setAttribute("approvedResult", "承認２");
				request.getServletContext().getRequestDispatcher("/SendMail").forward(request, response);
			} else {
				// 次申請番号の作成
				String[] nextData = new String[15];
				nextData[0] = number.substring(0, 15)
						+ String.valueOf(Integer.parseInt(number.substring(15)) + 1);
				for (int i = 1; i < 11; i++) {
					nextData[i] = preData[i];
				}
				nextData[11] = (String) session.getAttribute("approve2Id");

				String nextDataLine = nextData[0];

				for (int i = 1; i < 12; i++) {
					nextDataLine += "," + nextData[i];
				}
				nextDataLine += ",,,";

				BufferedWriter writer = Files
						.newBufferedWriter(Paths.get(referenceDirectory + "data.csv"),
								StandardOpenOption.APPEND);
				writer.write(nextDataLine);
				writer.newLine();
				writer.flush();
				writer.close();
				session.setAttribute("approvedNumber", nextData[0]);
				session.setAttribute("approvedResult", (String) session.getAttribute("approvedAction"));
				request.getServletContext().getRequestDispatcher("/SendMail").forward(request, response);
			}
		} else if (((String) session.getAttribute("approvedAction")).equals("差戻")) {
			if (((String) session.getAttribute("id")).equals((String) session.getAttribute("approve1Id"))) {
				session.setAttribute("approvedResult", "差戻２");
				request.getServletContext().getRequestDispatcher("/SendMail").forward(request, response);
			} else if (preData[10].equals("1")) {
				session.setAttribute("approvedResult", "差戻２");
				request.getServletContext().getRequestDispatcher("/SendMail").forward(request, response);
			} else {
				// 次申請番号の作成
				String[] nextData = new String[15];
				nextData[0] = number.substring(0, 15)
						+ String.valueOf(Integer.parseInt(number.substring(15)) + 1);
				for (int i = 1; i < 11; i++) {
					nextData[i] = preData[i];
				}
				nextData[11] = (String) session.getAttribute("approve1Id");

				String nextDataLine = nextData[0];

				for (int i = 1; i < 12; i++) {
					nextDataLine += "," + nextData[i];
				}
				nextDataLine += ",,,";

				BufferedWriter writer = Files
						.newBufferedWriter(Paths.get(referenceDirectory + "data.csv"),
								StandardOpenOption.APPEND);
				writer.write(nextDataLine);
				writer.newLine();
				writer.flush();
				writer.close();
				session.setAttribute("approvedNumber", nextData[0]);
				session.setAttribute("approvedResult", (String) session.getAttribute("approvedAction"));
				request.getServletContext().getRequestDispatcher("/SendMail").forward(request, response);
			}
		}
	}
}
