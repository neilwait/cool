package linpeng.coolpad;

import java.util.HashMap;

import linpeng.domain.Phone;
import linpeng.globel.MyGlobel;
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

public class DetailsActivity extends Activity {

	private WebView webView;
	private String dataGoodParameter;   //手机参数html
	private String dataGoodDetails;     //手机详情html
	private String dataGoodGift;        //手机赠品html
	
	private Button btnGoodParameter;
	private Button btnGoodGift;
	private Button btnGoodDetails;
	private Phone phone;
	
	private TextView textPhoneName;
	private TextView textPhoneDescribe;
	private TextView textPhonePrice;
	
	private ImageView imagePhonePhoto;
	
	private RelativeLayout relaProgressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_details);
		webView=(WebView)findViewById(R.id.details_webview);
		btnGoodDetails=(Button)findViewById(R.id.details_btn_good_details);
		btnGoodGift=(Button)findViewById(R.id.details_btn_good_gift);
		btnGoodParameter=(Button)findViewById(R.id.details_btn_good_parameter);
		textPhoneName=(TextView)findViewById(R.id.details_text_phone_name);
		textPhoneDescribe=(TextView)findViewById(R.id.details_text_phone_describe);
		textPhonePrice=(TextView)findViewById(R.id.details_text_phone_price);
		imagePhonePhoto=(ImageView)findViewById(R.id.details_image_phone_photo);
		relaProgressBar=(RelativeLayout)findViewById(R.id.details_relative_progress);
		
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		int index=getIntent().getIntExtra("index",0);
		phone=MyGlobel.phones.get(index);
		
		textPhoneName.setText(phone.getPhoneName());
		textPhoneDescribe.setText(phone.getPhoneDescribe());
		textPhonePrice.setText(phone.getPhonePrice());
		new MyAsyncTaskGetBitmap().execute("");
		
	}
	
	public void back(View view){
		finish();
	}
	
	public void goodParameter(View view) {
		webView.loadDataWithBaseURL(null, dataGoodParameter, "text/html", "utf-8",null);
		setTextColor(btnGoodParameter);
	}
	
	public void goodDetails(View view){
		webView.loadDataWithBaseURL(null, dataGoodDetails, "text/html", "utf-8",null);
		setTextColor(btnGoodDetails);
	}

	public void goodGift(View view){
		webView.loadDataWithBaseURL(null, dataGoodGift, "text/html", "utf-8",null);
		setTextColor(btnGoodGift);
	}
	
	public class MyAsyncTaskGetUrl extends AsyncTask<String, String, HashMap<String, String>>{

		@Override
		protected HashMap<String, String> doInBackground(String... params) {
			HashMap<String, String> datas=new GetGoodIntroduceService().getAllData(params[0]);
			return datas;
		}

		@Override
		protected void onPostExecute(HashMap<String, String> datas) {
			if(datas!=null){
				dataGoodParameter=datas.get("dataGoodParameter");
				dataGoodDetails=datas.get("dataGoodDetails");
				dataGoodGift=datas.get("dataGoodGift");
				System.out.println(dataGoodGift);
				webView.loadDataWithBaseURL(null, dataGoodParameter, "text/html", "utf-8",null);
			}
		}	
	}
	
	public class MyAsyncTaskGetBitmap extends AsyncTask<String, String, Bitmap>{

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap=new GetGoodPictureService().getPictureByUrlAndIndex(phone.getUrl(), 0);
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			imagePhonePhoto.setImageBitmap(bitmap);
			relaProgressBar.setVisibility(View.GONE);
			new MyAsyncTaskGetUrl().execute(phone.getUrl());
		}	
	}
	
	public void setTextColor(Button button){
		btnGoodParameter.setTextColor(Color.parseColor("#3F3F3F"));
		btnGoodGift.setTextColor(Color.parseColor("#3F3F3F"));
		btnGoodDetails.setTextColor(Color.parseColor("#3F3F3F"));
		button.setTextColor(Color.parseColor("#FF6600"));
	}
}
