package de.TUBlr.persistence;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

public class Image extends EntityObject implements Comparable<Image>{
	private String message;
	private String key;
	private Date created = new Date(System.currentTimeMillis());
	
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "Image [message=" + message + ", key=" + key + ", created="
				+ created + "]";
	}

	@Override
	public int compareTo(Image o) {
		return o.created.compareTo(this.created);
	}

}
