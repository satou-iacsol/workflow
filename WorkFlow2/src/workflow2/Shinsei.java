package workflow2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Shinsei
 */
@WebServlet("/Shinsei")
public class Shinsei extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Shinsei() {
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
		String user = (String) session.getAttribute("user");
		String authority = (String) session.getAttribute("authority");
		String fullname = (String) session.getAttribute("fullname");
		String affiliationCode = (String) session.getAttribute("affiliationCode");
		String mail = (String) session.getAttribute("mail");
		String affiliationName = (String) session.getAttribute("affiliationName");
		String approverNumber_1 = (String) session.getAttribute("approverNumber_1");
		String approverName_1 = (String) session.getAttribute("approverName_1");
		String approverNumber_2 = (String) session.getAttribute("approverNumber_2");
		String approverName_2 = (String) session.getAttribute("approverName_2");

		session.setAttribute("user", user);
		session.setAttribute("authority",authority);
		session.setAttribute("fullname", fullname);
		session.setAttribute("affiliationCode", affiliationCode);
		session.setAttribute("mail", mail);
		session.setAttribute("affiliationName", affiliationName);
		session.setAttribute("approverNumber_1", approverNumber_1);
		session.setAttribute("approverName_1", approverName_1);
		session.setAttribute("approverNumber_2", approverNumber_2);
		session.setAttribute("approverName_2", approverName_2);
		response.sendRedirect("shinsei.jsp");
//		BufferedReader br = null;
//
//		String array[] = new String[6];
//		String array2[] = new String[5];
//		String[][] belongs = new String[2][4];
//
//		try {
//			br = Files.newBufferedReader(
//					Paths.get("C:\\Users\\Seiya\\git\\workflow\\WorkFlow2\\employee_muster.csv"),
//					Charset.forName("UTF-8"));
//
//			String line = "";
//
//			//
//			while ((line = br.readLine()) != null) {
//				//array配列にcsvの情報をカンマ区切りで格納
//				array = line.split(",", -1);
//				shozoku = array[4];
//				fullname = array[3];
//
//				break;
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (br != null) {
//					br.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		try {
//			br = Files.newBufferedReader(
//					Paths.get("C:\\Users\\Seiya\\git\\workflow\\WorkFlow2\\belongs.csv"),
//					Charset.forName("UTF-8"));
//
//			String line = "";
//
//			//
//			for (int i = 0; i < 2; i++) {
//				line = br.readLine();
//				array2 = (line.split(",", -1));
//				for (int j = 0; j < 4; j++) {
//					belongs[i][j] = array2[j];
//				}
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (br != null) {
//					br.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}

		//		request.setAttribute("user", user);
		//		request.setAttribute("shozoku", shozoku);
		//		request.setAttribute("fullname", fullname);
		//		RequestDispatcher dispatch = request.getRequestDispatcher("shinsei.jsp");
		//		dispatch.forward(request, response);

	}

}
