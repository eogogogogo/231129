package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Forward;
import service.LoLMM;

@WebServlet({ "/chartfrm", "/winrate" })
public class ChartController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String cmd = request.getServletPath();
		System.out.println("cmd=" + cmd);
		LoLMM lm = new LoLMM(request, response);
		String json = null;
		if (cmd.equals("/chartfrm")) {
			json = lm.chartfrm();
		} else if (cmd.equals("/winrate")) {
			json = lm.winrate();
		}

		if (json != null) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.write(json); // (json형식)
			out.close();
		}
	}// doProcess End

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

}
