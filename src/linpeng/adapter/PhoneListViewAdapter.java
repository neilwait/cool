package linpeng.adapter;

import java.lang.ref.SoftReference;
import java.util.List;
import linpeng.coolpad.R;
import linpeng.domain.Phone;
import linpeng.net.util.GetBitmapUtil;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PhoneListViewAdapter extends BaseAdapter {

	private List<Phone> phones;
	private Context context;

	public PhoneListViewAdapter(Context context,List<Phone> phones) {
		super();
		this.context=context;
		this.phones = phones;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	@Override
	public int getCount() {
		return phones.size();
	}

	@Override
	public Object getItem(int position) {
		return phones.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RelativeLayout relativeLayout;
		if(convertView!=null){
			relativeLayout=(RelativeLayout) convertView;
		}else{
			relativeLayout=(RelativeLayout) View.inflate(context, R.layout.listview_phone, null);
		}
		ImageView imageView=(ImageView) relativeLayout.findViewById(R.id.main_listview_image);
		//防止上一个convertView的缓存图片
		imageView.setImageBitmap(null);
		//防止图片错误，设置一个标识，传入的imageView应和他要去图片的地址一样才不错位
		imageView.setTag(phones.get(position).getPhoneUrl());
		if(phones.get(position).getPhonePhoto()!=null&&phones.get(position).getPhonePhoto().get()!=null){
			imageView.setImageBitmap(phones.get(position).getPhonePhoto().get());
		}else{
			MyAsyncTask myAsyncTask = new MyAsyncTask();
			myAsyncTask.imageView = imageView;
			myAsyncTask.execute(position);
		}
		((TextView)relativeLayout.findViewById(R.id.main_listview_text_phone_name)).setText(phones.get(position).getPhoneName());
		((TextView)relativeLayout.findViewById(R.id.main_listview_text_phone_describe)).setText(phones.get(position).getPhoneDescribe());
		((TextView)relativeLayout.findViewById(R.id.main_listview_text_phone_price)).setText(phones.get(position).getPhonePrice().replace("酷派价", ""));

		return relativeLayout;
	}

	class MyAsyncTask extends AsyncTask<Integer, String, Bitmap>{
		ImageView imageView = null;
		int index=0;
		@Override
		protected Bitmap doInBackground(Integer... params) {
			index = params[0];
			Bitmap bitmap =  new GetBitmapUtil().getBitmapByUrl(phones.get(index).getPhoneUrl());
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			phones.get(index).setPhonePhoto(new SoftReference<Bitmap>(bitmap));
			System.out.println(imageView.getTag().toString()+"   "+phones.get(index).getPhoneUrl());
			//防止图片错误，会重用imageView判断是否是先前位置的imageView
			if(imageView.getTag().toString().equals(phones.get(index).getPhoneUrl())){
				imageView.setImageBitmap(phones.get(index).getPhonePhoto().get());
			}else{
				System.out.println("error");
			}
		}
	}
}
