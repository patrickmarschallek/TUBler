package de.TUBlr.manager.web.actions;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.TUBlr.manager.web.HttpRequestActionBase;
import de.TUBlr.persistence.Comment;
import de.TUBlr.persistence.EntityManager;

public class CommentAction extends HttpRequestActionBase {

	private EntityManager em = new EntityManager();

	@Override
	public void perform(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		String imageKey = req.getParameter("blobKey");
		String commentText = req.getParameter("commentText");
		if (imageKey != null && commentText != null && !imageKey.isEmpty()) {
			Comment comment = new Comment();
			comment.setText(commentText);
			comment.setKey(UUID.randomUUID().toString());
			comment.setImageKey(imageKey);
			this.em.persist(comment);
		}
	}

}
