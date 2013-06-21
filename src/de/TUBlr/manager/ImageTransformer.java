package de.TUBlr.manager;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

public class ImageTransformer {

	public String transformToThumbnail(BlobKey key,int size) {

		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(key);
		options.imageSize(size);
		String url = imagesService.getServingUrl(options);

		return url;
	}
}
