package de.TUBlr.manager.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import de.TUBlr.persistence.Comment;
import de.TUBlr.persistence.EntityManager;
import de.TUBlr.persistence.IEntityManager;
import de.TUBlr.persistence.Image;

public class Upload extends HttpServlet {

	private static final long serialVersionUID = 5150165634919063335L;
	private IEntityManager em = new EntityManager();
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		Map param = req.getParameterMap();
		List<BlobKey> blobKeyList = blobs.get("uploadedFiles");
		BlobInfo blobInfo;
		if (blobKeyList == null
				&& blobstoreService.getBlobInfos(req).size() > 0) {
			blobKeyList = blobs.get("commentImage");
			blobInfo = blobstoreService.getBlobInfos(req).get("commentImage")
					.get(0);
		} else {
			blobInfo = blobstoreService.getBlobInfos(req).get("uploadedFiles")
					.get(0);
		}
		BlobKey blobKey = blobKeyList.get(0);

		if (blobInfo.getSize() > 0) {
			String ancestorKey = req.getParameter("blobKey");
			String commentText = req.getParameter("commentText");
			if (ancestorKey != null && commentText != null
					&& !ancestorKey.isEmpty()) {
				Comment comment = new Comment();
				comment.setText(commentText);
				comment.setKey(UUID.randomUUID().toString());
				comment.setImageKey(blobKey.getKeyString());
				comment.setAncestor(ancestorKey);
				this.em.persist(comment);
			} else {
				this.persist(blobKey.getKeyString(),
						req.getParameter("message"));
			}
		} else {
			blobstoreService.delete(blobKey);
		}
		res.sendRedirect("/TUBlr");
	}

	private void persist(String blobKey, String message) {
		Image img = new Image();
		img.setKey(blobKey);
		img.setMessage(message);
		this.em.persist(img);
	}
}
