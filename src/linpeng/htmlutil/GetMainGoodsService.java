package linpeng.htmlutil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import linpeng.domain.Phone;

public class GetMainGoodsService {

	/**
	 * 获取首页的图片和标题
	 * page 从零开始（0表示第一页）
	 * @return
	 */
	public List<Phone> getMainPhones(int page){
		List<Phone> phones=new ArrayList<Phone>();
		Document document;
		try {
			document = Jsoup.connect("http://shop.coolpad.cn/sort-ajax-"+(page+1)+".htm?typelevel=2&typeId=440&sortBy=0").timeout(3000).get();
			if(document!=null){
				Elements elements=document.getElementsByClass("li_cxpd_list_main_sp1");
				Elements elements2=document.getElementsByClass("li_cxpd_list_main_sp");
				for(Element element:elements){
					Phone phone=new Phone();
					phone.setPhoneDescribe(element.child(1).child(1).text());
					phone.setPhoneName(element.child(1).child(0).text());
					phone.setPhonePrice(element.child(1).child(2).text());
					phone.setPhoneUrl(element.select("img").first().absUrl("src"));
					phone.setPhonePhoto(null);
					phone.setUrl(element.child(0).child(0).attr("href"));
				    phones.add(phone);
				}
				for(Element element:elements2){
					Phone phone=new Phone();
					phone.setPhoneDescribe(element.child(1).child(1).text());
					phone.setPhoneName(element.child(1).child(0).text());
					phone.setPhonePrice(element.child(1).child(2).text());
					phone.setPhoneUrl(element.select("img").first().absUrl("src"));
					phone.setPhonePhoto(null);
					phone.setUrl(element.child(0).child(0).attr("href"));
				    phones.add(phone);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return phones;
	}
}
