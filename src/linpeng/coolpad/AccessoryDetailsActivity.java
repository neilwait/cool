package linpeng.coolpad;

import java.util.HashMap;

import linpeng.domain.Accessory;
import linpeng.globel.MyGlobel;
import linpeng.htmlutil.GetAccessoryIntroduceService;
import linpeng.htmlutil.GetAccessoryPictureService;
import linpeng.htmlutil.GetGoodIntroduceService;
import linpeng.htmlutil.GetGoodPictureService;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AccessoryDetailsActivity extends Activity {

	private WebView webView;
	private String dataGoodParameter;   //手机参数html
	private String dataGoodaccessory_details;     //手机详情html
	private String dataGoodGift;        //手机赠品html
	
	private Button btnGoodParameter;
	private Button btnGoodGift;
	private Button btnGoodDetails;
	private Accessory accessory;
	
	private TextView textAccessoryName;
	private TextView textAccessoryDescribe;
	private TextView textAccessoryPrice;
	
	private ImageView imageAccessoryPhoto;
	
	private RelativeLayout relaProgressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_accessory_details);
		webView=(WebView)findViewById(R.id.accessory_details_webview);
		btnGoodDetails=(Button)findViewById(R.id.accessory_details_btn_good_accessory_details);
		btnGoodGift=(Button)findViewById(R.id.accessory_details_btn_good_gift);
		btnGoodParameter=(Button)findViewById(R.id.accessory_details_btn_good_parameter);
		textAccessoryName=(TextView)findViewById(R.id.accessory_details_text_accessory_name);
		textAccessoryDescribe=(TextView)findViewById(R.id.accessory_details_text_accessory_describe);
		textAccessoryPrice=(TextView)findViewById(R.id.accessory_details_text_accessory_price);
		imageAccessoryPhoto=(ImageView)findViewById(R.id.accessory_details_image_accessory_photo);
		relaProgressBar=(RelativeLayout)findViewById(R.id.accessory_details_relative_progress);
		
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		int index=getIntent().getIntExtra("index",0);
		accessory=MyGlobel.accessories.get(index);
		
		textAccessoryName.setText(accessory.getAccessoryName());
		textAccessoryDescribe.setText(accessory.getAccessoryDescribe());
		textAccessoryPrice.setText(accessory.getAccessoryPrice());
		new MyAsyncTaskGetBitmap().execute("");
		
	}
	
	public void back(View view){
		finish();
	}
	
	public void goodParameter(View view) {
		webView.loadDataWithBaseURL(null, dataGoodParameter, "text/html", "utf-8",null);
		setTextColor(btnGoodParameter);
	}
	
	public void goodaccessory_details(View view){
		webView.loadDataWithBaseURL(null, dataGoodaccessory_details, "text/html", "utf-8",null);
		setTextColor(btnGoodDetails);
	}

	public void goodGift(View view){
		webView.loadDataWithBaseURL(null, dataGoodGift, "text/html", "utf-8",null);
		setTextColor(btnGoodGift);
	}
	
	public class MyAsyncTaskGetUrl extends AsyncTask<String, String, HashMap<String, String>>{

		@Override
		protected HashMap<String, String> doInBackground(String... params) {
			HashMap<String, String> datas=new GetAccessoryIntroduceService().getAllData(accessory.getUrl());
			return datas;
		}

		@Override
		protected void onPostExecute(HashMap<String, String> datas) {
			if(datas!=null){
				dataGoodParameter=datas.get("dataGoodParameter");
				dataGoodaccessory_details=datas.get("dataGoodDetails");
				dataGoodGift=datas.get("dataGoodGift");
				webView.loadDataWithBaseURL(null, dataGoodParameter, "text/html", "utf-8",null);
			}
		}	
	}
	
	public class MyAsyncTaskGetBitmap extends AsyncTask<String, String, Bitmap>{

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap=new GetAccessoryPictureService().getPictureByUrlAndIndex(accessory.getUrl(), 0);
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			imageAccessoryPhoto.setImageBitmap(bitmap);
			relaProgressBar.setVisibility(View.GONE);
			new MyAsyncTaskGetUrl().execute("");
		}	
	}
	
	public void setTextColor(Button button){
		btnGoodParameter.setTextColor(Color.parseColor("#3F3F3F"));
		btnGoodGift.setTextColor(Color.parseColor("#3F3F3F"));
		btnGoodDetails.setTextColor(Color.parseColor("#3F3F3F"));
		button.setTextColor(Color.parseColor("#FF6600"));
	}
}
