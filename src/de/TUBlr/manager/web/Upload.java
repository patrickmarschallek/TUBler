package de.TUBlr.manager.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

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
		List<BlobKey> blobKeyList = blobs.get("uploadedFiles");
		BlobKey blobKey = blobKeyList.get(0);
		this.persist(blobKey.getKeyString(), req.getParameter("message"));
		res.sendRedirect("/TUBlr");
	}

	private void persist(String blobKey, String message) {
		Image img = new Image();
		img.setKey(blobKey);
		img.setMessage(message);
		this.em.persist(img);
	}
}
