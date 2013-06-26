package de.TUBlr.manager.web;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import de.TUBlr.manager.ImageTransformer;

public class Serve extends HttpServlet {

	private static final long serialVersionUID = 4797996312483683615L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	private ImageTransformer transformer = new ImageTransformer();

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		BlobKey blobKey = new BlobKey(req.getParameter("blobKey"));
		String thumbSize = req.getParameter("thumb");
		if (isThumbnail(thumbSize)) {
			res.sendRedirect(this.transformer
					.transformToThumbnail(blobKey, Integer.valueOf(thumbSize)));
		} else {
			this.blobstoreService.serve(blobKey, res);
		}
	}

	private boolean isThumbnail(String parameter) {
		return parameter == null ? false : true;

	}
}
