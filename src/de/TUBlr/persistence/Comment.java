package de.TUBlr.persistence;

import java.util.Date;


public class Comment extends EntityObject {
	private String text;
	private String key;
	private Date created = new Date(System.currentTimeMillis());

	@Ancestor(reference = Image.class)
	private String ancestor;
	
	private String imageKey;

	public String getImageKey() {
		return imageKey;
	}

	public void setImageKey(String imageKey) {
		this.imageKey = imageKey;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getAncestor() {
		return ancestor;
	}

	public void setAncestor(String ancestor) {
		this.ancestor = ancestor;
	}

	@Override
	public String toString() {
		return "Comment [text=" + text + ", key=" + key + ", created="
				+ created + ", ancestor=" + ancestor + ", imageKey=" + imageKey
				+ "]";
	}
}
