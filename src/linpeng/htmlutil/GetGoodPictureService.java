package linpeng.htmlutil;


import linpeng.net.util.GetBitmapUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.graphics.Bitmap;

public class GetGoodPictureService {

	/**
	 * 通过url和顺序获取图片
	 * @param url   手机网页地址
	 * @param index   第几张展示图片
	 * @return
	 */
	public Bitmap getPictureByUrlAndIndex(String url,int index){
		Bitmap bitmap=null;
		String m_url=url.split("goods/")[0]+"goods/m_"+url.split("goods/")[1];
		try {
			Document document=Jsoup.connect(m_url).userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A405 Safari/8536.25").get();
			System.out.println(document.getElementsByClass("ps_s_img").toString());
			Element element=document.getElementsByClass("ps_s_img").first().child(0).child(index);
		    bitmap = new GetBitmapUtil().getBitmapByUrl(element.select("img").first().absUrl("src"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}
