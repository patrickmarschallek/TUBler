package de.TUBlr.manager.web.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

import de.TUBlr.manager.web.HttpRequestActionBase;
import de.TUBlr.persistence.EntityManager;
import de.TUBlr.persistence.EntityObject;
import de.TUBlr.persistence.IEntityManager;
import de.TUBlr.persistence.Image;

public class ShowHomeAction extends HttpRequestActionBase {

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	private IEntityManager em = new EntityManager();

	@Override
	public void perform(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		try {
			List<Image> imageList =  this.em.findAll(Image.class);	
			
			req.setAttribute("imageList", imageList);
			this.forward(req, resp, "tublr.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
