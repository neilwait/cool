package linpeng.htmlutil;

import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GetAccessoryIntroduceService {

	private static Document document;
	public HashMap<String, String> getAllData(String url) {
		HashMap<String, String> urls=new HashMap<String, String>();
		try {
			document=Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.49 Safari/537.36").get();
			String m_url=url.split("goods/")[0]+"goods/m_"+url.split("goods/")[1];
			Document document2=Jsoup.connect(m_url).userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A405 Safari/8536.25").get();
			urls.put("dataGoodDetails",document.getElementById("con_one_1").toString());
			System.out.println(document.getElementById("con_one_1").toString()+"---");
			urls.put("dataGoodParameter",document2.head()+document2.getElementById("con_mone_2").toString().replaceAll("none", "block"));
			urls.put("dataGoodGift",document.getElementById("con_one_3").toString().replaceAll("none", "block"));
		} catch (Exception e) {
			e.printStackTrace();
			urls=null;
		}
		System.out.println("--------"+urls.get("dataGoodDetails")+"---------------------");
		return urls;
	}
}
