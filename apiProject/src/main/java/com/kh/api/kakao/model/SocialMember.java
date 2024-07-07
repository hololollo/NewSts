package com.kh.api.kakao.model;

public class SocialMember {
	private String id;
	private String nickName;
	private String thumbnailImg;
	
	public SocialMember() {};
	
	public SocialMember(String id, String nickName, String thumbnailImg) {
		super();
		this.id = id;
		this.nickName = nickName;
		this.thumbnailImg = thumbnailImg;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getThumbnailImg() {
		return thumbnailImg;
	}
	public void setThumbnailImg(String thumbnailImg) {
		this.thumbnailImg = thumbnailImg;
	}
	
	@Override
	public String toString() {
		return "SocialMember [id=" + id + ", nickName=" + nickName + ", thumbnailImg=" + thumbnailImg + "]";
	}
	
}
