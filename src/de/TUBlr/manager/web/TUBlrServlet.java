package de.TUBlr.manager.web;

import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import de.TUBlr.manager.web.actions.ShowHomeAction;

public class TUBlrServlet extends HttpServletControllerBase {

	private static final long serialVersionUID = 3218329961842485677L;

	public void init(ServletConfig conf) throws ServletException {
		this.actions = new HashMap<String, HttpRequestActionBase>();
		this.actions.put("", new ShowHomeAction());
	}

	protected String getOperation(HttpServletRequest req) {
		return req.getParameter("do");
	}
}
