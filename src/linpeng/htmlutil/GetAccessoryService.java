package linpeng.htmlutil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import linpeng.domain.Accessory;
import linpeng.domain.Phone;

public class GetAccessoryService {

	/**
	 * 获取配件列表
	 * page 从零开始（0表示第一页）
	 * @return
	 */
	public List<Accessory> getAccessories(int page){
		List<Accessory> accessories=new ArrayList<Accessory>();
		Document document;
		try {
			document = Jsoup.connect("http://shop.coolpad.cn/partsChanel-2-"+(page+1)+".htm#a1").timeout(3000).get();
			if(document!=null){
				Elements elements=document.getElementsByClass("li_cxpd_list_main_sp1");
				Elements elements2=document.getElementsByClass("li_cxpd_list_main_sp");
				elements.addAll(elements2);
				for(Element element:elements){
					Accessory accessory=new Accessory();
					accessory.setAccessoryDescribe(element.child(1).child(1).text());
					accessory.setAccessoryName(element.child(1).child(0).text());
					accessory.setAccessoryPrice(element.child(1).child(2).text());
					accessory.setAccessoryUrl(element.select("img").first().absUrl("src"));
					accessory.setAccessoryPhoto(null);
					accessory.setUrl(element.child(0).child(0).attr("href"));
					accessories.add(accessory);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return accessories;
	}
}
