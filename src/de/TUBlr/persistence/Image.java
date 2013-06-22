package de.TUBlr.persistence;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Image extends EntityObject {
	private String message;
	private String key;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		try {
			this.key = URLEncoder.encode(key, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
