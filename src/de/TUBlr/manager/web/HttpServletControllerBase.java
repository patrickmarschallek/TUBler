package de.TUBlr.manager.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.TUBlr.manager.web.actions.ShowHomeAction;

/**
 * @author berdux
 * 
 */
public abstract class HttpServletControllerBase extends HttpServlet {
	protected HashMap actions;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig conf) throws ServletException {
		HttpRequestActionBase action = null;
		actions = new HashMap();
		actions.put("", new ShowHomeAction());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String op = getOperation(req);
		HttpRequestActionBase action = (HttpRequestActionBase) actions.get(op);
		if (action == null)
			new HttpRequestActionBase() {
				@Override
				public void perform(HttpServletRequest req,
						HttpServletResponse resp) throws ServletException {
					try {
						forward(req, resp, "/tublr.jsp");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.perform(req, resp);
		else
			action.perform(req, resp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String op = getOperation(req);
		HttpRequestActionBase action = (HttpRequestActionBase) actions.get(op);
		action.perform(req, resp);
	}

	/**
	 * Methode muss noch definiert werden, um die Kennung der Operation aus der
	 * URL zu lesen
	 * 
	 * @param req
	 *            Http-Request
	 * @return Name der Aktion, die ausgefuehrt werden soll
	 */
	protected abstract String getOperation(HttpServletRequest req);
}
