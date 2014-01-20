package linpeng.domain;

import java.lang.ref.SoftReference;

import android.graphics.Bitmap;

public class Accessory {

	private String accessoryDescribe;
	//Õº∆¨µÿ÷∑
	private String accessoryUrl;
	private String accessoryName;
	private String accessoryPrice;
	private SoftReference<Bitmap> accessoryPhoto;
	private String url;
	public String getAccessoryDescribe() {
		return accessoryDescribe;
	}
	public void setAccessoryDescribe(String accessoryDescribe) {
		this.accessoryDescribe = accessoryDescribe;
	}
	public String getAccessoryUrl() {
		return accessoryUrl;
	}
	public void setAccessoryUrl(String accessoryUrl) {
		this.accessoryUrl = accessoryUrl;
	}
	public String getAccessoryName() {
		return accessoryName;
	}
	public void setAccessoryName(String accessoryName) {
		this.accessoryName = accessoryName;
	}
	public String getAccessoryPrice() {
		return accessoryPrice;
	}
	public void setAccessoryPrice(String accessoryPrice) {
		this.accessoryPrice = accessoryPrice;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public SoftReference<Bitmap> getAccessoryPhoto() {
		return accessoryPhoto;
	}
	public void setAccessoryPhoto(SoftReference<Bitmap> accessoryPhoto) {
		this.accessoryPhoto = accessoryPhoto;
	}
	
}
