package linpeng.domain;

import java.io.Serializable;
import java.lang.ref.SoftReference;

import android.graphics.Bitmap;

public class Phone implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String phoneDescribe;
	private SoftReference<Bitmap> phonePhoto;
	private String phoneUrl;
	private String phoneName;
	private String phonePrice;
	private String url; 
	public String getPhoneDescribe() {
		return phoneDescribe;
	}
	public void setPhoneDescribe(String phoneDescribe) {
		this.phoneDescribe = phoneDescribe;
	}
	
	public SoftReference<Bitmap> getPhonePhoto() {
		return phonePhoto;
	}
	public void setPhonePhoto(SoftReference<Bitmap> phonePhoto) {
		this.phonePhoto = phonePhoto;
	}
	public String getPhoneName() {
		return phoneName;
	}
	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}
	
	public String getPhonePrice() {
		return phonePrice;
	}
	public void setPhonePrice(String phonePrice) {
		this.phonePrice = phonePrice;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPhoneUrl() {
		return phoneUrl;
	}
	public void setPhoneUrl(String phoneUrl) {
		this.phoneUrl = phoneUrl;
	} 
}
