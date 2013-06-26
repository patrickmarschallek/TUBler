package de.TUBlr.manager.web.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import de.TUBlr.manager.web.HttpRequestActionBase;
import de.TUBlr.persistence.Comment;
import de.TUBlr.persistence.EntityManager;
import de.TUBlr.persistence.IEntityManager;
import de.TUBlr.persistence.Image;

public class ShowHomeAction extends HttpRequestActionBase {

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	private DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	private IEntityManager em = new EntityManager();

	@Override
	public void perform(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		try {
			List<Entity> list = this.findAllImagesWithAncestor();

			List<Image> imageList = this.em.findAll(Image.class);
			List<Comment> commentList = this.em.findAll(Comment.class);
			HashMap<Image, ArrayList<Comment>> result = this
					.mapCommentsToImages(imageList, commentList);
			req.setAttribute("resultMap", result);
			req.setAttribute("imageUploadUrl",
					this.blobstoreService.createUploadUrl("/upload"));
			this.forward(req, resp, "tublr.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private HashMap<Image, ArrayList<Comment>> mapCommentsToImages(
			List<Image> imageList, List<Comment> commentList) {
		HashMap<Image, ArrayList<Comment>> result = new HashMap<Image, ArrayList<Comment>>();
		for (Image img : imageList) {
			ArrayList<Comment> list = new ArrayList<Comment>();
			result.put(img, list);
			for (Comment comment : commentList) {
				if (comment.getAncestor() != null
						&& comment.getAncestor().equals(img.getKey())) {
					result.get(img).add(comment);
				}
			}
		}
		return result;
	}

	private List<Entity> findAllImagesWithAncestor() {
		// only images
		Query query1 = new Query(Image.class.getName());
		query1.addSort("created", SortDirection.DESCENDING);
		List<Entity> list = this.datastore.prepare(query1).asList(
				FetchOptions.Builder.withLimit(20));

		return list;
	}
}
