package com.workflow;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ApprovePick")
public class ApprovePick extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ApprovePick() {
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

		String id = (String) session.getAttribute("id");

		ArrayList<ArrayList<String>> list = new ArrayList<>();
		BufferedReader brData = null;
		BufferedReader brData1 = null;
		BufferedReader brData2 = null;
		BufferedReader brEmployee = null;
		BufferedReader brBelongs = null;
		String[] employee = new String[6];
		String[] belongs = new String[4];

		try {
			// ソート前データの取得
			brData = Files.newBufferedReader(
					Paths.get(referenceDirectory + "data.csv"),
					Charset.forName("UTF-8"));
			String lineData = "";
			ArrayList<ArrayList<String>> sortList = new ArrayList<>();

			while ((lineData = brData.readLine()) != null) {
				String[] data = lineData.split(",", -1);
				ArrayList<String> listSub = new ArrayList<>();
				for (int i = 0; i < data.length; i++) {
					listSub.add(data[i]);
				}
				brEmployee = Files.newBufferedReader(Paths.get(referenceDirectory + "employee_muster.csv"),
						Charset.forName("UTF-8"));
				String lineEmployee = "";

				while ((lineEmployee = brEmployee.readLine()) != null) {
					employee = lineEmployee.split(",", -1);
					if (employee[0].equals(data[1])) {
						listSub.set(5,employee[3]);
						break;
					}
				}
				brBelongs = Files.newBufferedReader(Paths.get(referenceDirectory + "belongs.csv"),
						Charset.forName("UTF-8"));
				String lineBelongs = "";

				while ((lineBelongs = brBelongs.readLine()) != null) {
					belongs = lineBelongs.split(",", -1);
					if (belongs[0].equals(employee[4])) {
						listSub.set(6,belongs[1]);
						break;
					}
				}
				// 申請データの承認者が一致したデータ(承認差戻なし)をリストに入れる
				if (data[11].equals(id) && data[14].equals("")) {
					sortList.add(listSub);
				}
			}

			// 取得期間(FROM)を取得
			ArrayList<String> sort = new ArrayList<>();

			for (int i = 0; i < sortList.size(); i++) {
				sort.add(sortList.get(i).get(3));
			}

			session.setAttribute("approvedItems", sort.size());

			// 取得期間(FROM)順にソート
			Collections.sort(sort);

			// ソートしたデータをリストに入れる
			for (int i = 0; i < sort.size(); i++) {
				for (int j = 0; j < sortList.size(); j++) {
					if ((sortList.get(j).get(3)).equals(sort.get(i))) {
						list.add(sortList.get(j));
						sortList.remove(j);
						break;
					}
				}
			}

			// 申請データの承認者が一致したデータ(差戻)をリストに入れる
			brData1 = Files.newBufferedReader(Paths.get(referenceDirectory + "data.csv"),
					Charset.forName("UTF-8"));
			String lineData1 = "";

			while ((lineData1 = brData1.readLine()) != null) {
				String[] data = lineData1.split(",", -1);
				ArrayList<String> listSub = new ArrayList<>();
				for (int i = 0; i < data.length; i++) {
					listSub.add(data[i]);
				}
				brEmployee = Files.newBufferedReader(Paths.get(referenceDirectory + "employee_muster.csv"),
						Charset.forName("UTF-8"));
				String lineEmployee = "";

				while ((lineEmployee = brEmployee.readLine()) != null) {
					employee = lineEmployee.split(",", -1);
					if (employee[0].equals(data[1])) {
						listSub.set(5,employee[3]);
						break;
					}
				}
				brBelongs = Files.newBufferedReader(Paths.get(referenceDirectory + "belongs.csv"),
						Charset.forName("UTF-8"));
				String lineBelongs = "";

				while ((lineBelongs = brBelongs.readLine()) != null) {
					belongs = lineBelongs.split(",", -1);
					if (belongs[0].equals(employee[4])) {
						listSub.set(6,belongs[1]);
						break;
					}
				}
				if (data[11].equals(id) && data[14].equals("差戻")) {
					list.add(listSub);
				}
			}

			// 申請データの承認者が一致したデータ(承認)をリストに入れる
			brData2 = Files.newBufferedReader(Paths.get(referenceDirectory + "data.csv"),
					Charset.forName("UTF-8"));
			String lineData2 = "";

			while ((lineData2 = brData2.readLine()) != null) {
				String[] data = lineData2.split(",", -1);
				ArrayList<String> listSub = new ArrayList<>();
				for (int i = 0; i < data.length; i++) {
					listSub.add(data[i]);
				}
				brEmployee = Files.newBufferedReader(Paths.get(referenceDirectory + "employee_muster.csv"),
						Charset.forName("UTF-8"));
				String lineEmployee = "";

				while ((lineEmployee = brEmployee.readLine()) != null) {
					employee = lineEmployee.split(",", -1);
					if (employee[0].equals(data[1])) {
						listSub.set(5,employee[3]);
						break;
					}
				}
				brBelongs = Files.newBufferedReader(Paths.get(referenceDirectory + "belongs.csv"),
						Charset.forName("UTF-8"));
				String lineBelongs = "";

				while ((lineBelongs = brBelongs.readLine()) != null) {
					belongs = lineBelongs.split(",", -1);
					if (belongs[0].equals(employee[4])) {
						listSub.set(6,belongs[1]);
						break;
					}
				}
				if (data[11].equals(id) && data[14].equals("承認")) {
					list.add(listSub);
				}
			}

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
		session.setAttribute("list", list);
		response.sendRedirect("approveChoose.jsp");
	}

}
