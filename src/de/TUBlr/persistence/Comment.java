package de.TUBlr.persistence;

public class Comment extends EntityObject {
	private String text;
	private String key;

	@Ancestor(reference = Image.class)
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

}
