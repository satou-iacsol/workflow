package com.workflow;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SessionCreate")
public class SessionCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SessionCreate() {
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

		String number = request.getParameter("radio");

		BufferedReader brData = null;
		BufferedReader brEmployee = null;
		BufferedReader brBelongs = null;
		BufferedReader brApprove1 = null;
		BufferedReader brApprove2 = null;

		String[] data = new String[15];
		String[] employee = new String[6];
		String[] belongs = new String[6];
		String[] approve1 = new String[6];
		String[] approve2 = new String[6];

		try {
			// 申請データの取得
			brData = Files.newBufferedReader(
					Paths.get("C:/Users/Yusuke/git/workflow/workflow_akashi/WebContent/data.csv"),
					Charset.forName("UTF-8"));
			String lineData = "";

			while ((lineData = brData.readLine()) != null) {
				data = lineData.split(",", -1);
				if (data[0].equals(number)) {
					break;
				}
			}

			// 社員マスタの取得
			brEmployee = Files.newBufferedReader(Paths.get("C:/Users/Yusuke/git/workflow/workflow_akashi/WebContent/employee_muster.csv"),
					Charset.forName("UTF-8"));
			String lineEmployee = "";

			while ((lineEmployee = brEmployee.readLine()) != null) {
				employee = lineEmployee.split(",", -1);
				if (employee[0].equals(data[1])) {
					break;
				}
			}

			// 承認者マスタの取得
			brBelongs = Files.newBufferedReader(Paths.get("C:/Users/Yusuke/git/workflow/workflow_akashi/WebContent/belongs.csv"),
					Charset.forName("UTF-8"));
			String lineBelongs = "";

			while ((lineBelongs = brBelongs.readLine()) != null) {
				belongs = lineBelongs.split(",", -1);
				if (belongs[0].equals(employee[4])) {
					break;
				}
			}

			// 承認者１の社員マスタの取得
			brApprove1 = Files.newBufferedReader(Paths.get("C:/Users/Yusuke/git/workflow/workflow_akashi/WebContent/employee_muster.csv"),
					Charset.forName("UTF-8"));
			String lineApprove1 = "";

			while ((lineApprove1 = brApprove1.readLine()) != null) {
				approve1 = lineApprove1.split(",", -1);
				if (approve1[0].equals(belongs[2])) {
					break;
				}
			}

			// 承認者２の社員マスタの取得
			brApprove2 = Files.newBufferedReader(Paths.get("C:/Users/Yusuke/git/workflow/workflow_akashi/WebContent/employee_muster.csv"),
					Charset.forName("UTF-8"));
			String lineApprove2 = "";

			while ((lineApprove2 = brApprove2.readLine()) != null) {
				approve2 = lineApprove2.split(",", -1);
				if (approve2[0].equals(belongs[3])) {
					break;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				brData.close();
				brEmployee.close();
				brBelongs.close();
				brApprove1.close();
				brApprove2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		switch (Integer.parseInt(data[2])) {
		case 1:
			session.setAttribute("approvedType", "1.有給休暇");
			break;
		case 2:
			session.setAttribute("approvedType", "2.代休");
			break;
		case 3:
			session.setAttribute("approvedType", "3.生理休暇");
			break;
		case 4:
			session.setAttribute("approvedType", "4.慶弔休暇");
			break;
		case 5:
			session.setAttribute("approvedType", "5.特別休暇");
			break;
		case 6:
			session.setAttribute("approvedType", "6.罹災休暇");
			break;
		case 7:
			session.setAttribute("approvedType", "7.半休");
			break;
		case 8:
			session.setAttribute("approvedType", "8.結婚休暇");
			break;
		case 9:
			session.setAttribute("approvedType", "9.出産休暇");
			break;
		case 10:
			session.setAttribute("approvedType", "10.忌引き休暇");
			break;
		case 11:
			session.setAttribute("approvedType", "11.隔離休暇");
			break;
		case 12:
			session.setAttribute("approvedType", "12.一周忌");
			break;
		case 13:
			session.setAttribute("approvedType", "13.受験休暇");
			break;
		case 14:
			session.setAttribute("approvedType", "14.産前産後休暇");
			break;

		}

		// 日付関連のデータの形式の編集
		StringBuilder fromDate = new StringBuilder();
		fromDate.append(data[3].substring(0, 4) + "/");
		fromDate.append(data[3].substring(4, 6) + "/");
		fromDate.append(data[3].substring(6, 8));

		StringBuilder toDate = new StringBuilder();
		toDate.append(data[4].substring(0, 4) + "/");
		toDate.append(data[4].substring(4, 6) + "/");
		toDate.append(data[4].substring(6, 8));

		StringBuilder fromTime = new StringBuilder();
		fromTime.append(data[5].substring(0, 4) + "/");
		fromTime.append(data[5].substring(4, 6) + "/");
		fromTime.append(data[5].substring(6, 8) + " ");
		fromTime.append(data[5].substring(8, 10) + ":");
		fromTime.append(data[5].substring(10, 12));

		StringBuilder toTime = new StringBuilder();
		toTime.append(data[6].substring(0, 4) + "/");
		toTime.append(data[6].substring(4, 6) + "/");
		toTime.append(data[6].substring(6, 8) + " ");
		toTime.append(data[6].substring(8, 10) + ":");
		toTime.append(data[6].substring(10, 12));

		session.setAttribute("approvedNumber", request.getParameter("radio"));
		session.setAttribute("approvedId", data[1]);
		session.setAttribute("approvedName", employee[3]);
		session.setAttribute("approvedBelongsCode", employee[4]);
		session.setAttribute("approvedBelongs", belongs[1]);
		session.setAttribute("approve1Id", belongs[2]);
		session.setAttribute("approve2Id", belongs[3]);
		session.setAttribute("approve1Name", approve1[3]);
		session.setAttribute("approve2Name", approve2[3]);
		session.setAttribute("approvedFromDate", fromDate);
		session.setAttribute("approvedToDate", toDate);
		session.setAttribute("approvedDate", fromDate + " ～ " + toDate);
		session.setAttribute("approvedFromTime", fromTime);
		session.setAttribute("approvedToTime", toTime);
		session.setAttribute("approvedTime", fromTime + " ～ " + toTime);
		session.setAttribute("approvedReason", data[7]);
		session.setAttribute("approvedAddress", data[8]);
		session.setAttribute("approvedRemarks", data[9]);

		response.sendRedirect("approveAction.jsp");
	}
}
