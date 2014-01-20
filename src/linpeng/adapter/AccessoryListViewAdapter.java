package linpeng.adapter;

import java.lang.ref.SoftReference;
import java.util.List;
import linpeng.coolpad.R;
import linpeng.domain.Accessory;
import linpeng.domain.Accessory;
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

public class AccessoryListViewAdapter extends BaseAdapter {

	private List<Accessory> accessories;
	private Context context;

	public AccessoryListViewAdapter(Context context,List<Accessory> accessories) {
		super();
		this.context=context;
		this.accessories = accessories;
	}

	public List<Accessory> getAccessories() {
		return accessories;
	}

	public void setAccessories(List<Accessory> accessories) {
		this.accessories = accessories;
	}

	@Override
	public int getCount() {
		return accessories.size();
	}

	@Override
	public Object getItem(int position) {
		return accessories.get(position);
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
			relativeLayout=(RelativeLayout) View.inflate(context, R.layout.listview_accessory, null);
		}
		ImageView imageView=(ImageView) relativeLayout.findViewById(R.id.main_listview_image);
		//防止上一个convertView的缓存图片
		imageView.setImageBitmap(null);
		//防止图片错误，设置一个标识，传入的imageView应和他要去图片的地址一样才不错位
		imageView.setTag(accessories.get(position).getAccessoryUrl());
		if(accessories.get(position).getAccessoryPhoto()!=null&&accessories.get(position).getAccessoryPhoto().get()!=null){
			imageView.setImageBitmap(accessories.get(position).getAccessoryPhoto().get());
		}else{
			MyAsyncTask myAsyncTask = new MyAsyncTask();
			myAsyncTask.imageView = imageView;
			myAsyncTask.execute(position);
		}
		((TextView)relativeLayout.findViewById(R.id.main_listview_text_accessory_name)).setText(accessories.get(position).getAccessoryName());
		((TextView)relativeLayout.findViewById(R.id.main_listview_text_accessory_describe)).setText(accessories.get(position).getAccessoryDescribe());
		((TextView)relativeLayout.findViewById(R.id.main_listview_text_accessory_price)).setText(accessories.get(position).getAccessoryPrice().replace("酷派价", ""));

		return relativeLayout;
	}

	class MyAsyncTask extends AsyncTask<Integer, String, Bitmap>{
		ImageView imageView = null;
		int index=0;
		@Override
		protected Bitmap doInBackground(Integer... params) {
			index = params[0];
			Bitmap bitmap =  new GetBitmapUtil().getBitmapByUrl(accessories.get(index).getAccessoryUrl());
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			accessories.get(index).setAccessoryPhoto(new SoftReference<Bitmap>(bitmap));
			System.out.println(imageView.getTag().toString()+"   "+accessories.get(index).getAccessoryUrl());
			//防止图片错误，会重用imageView判断是否是先前位置的imageView
			if(imageView.getTag().toString().equals(accessories.get(index).getAccessoryUrl())){
				imageView.setImageBitmap(accessories.get(index).getAccessoryPhoto().get());
			}else{
				System.out.println("error");
			}
		}
	}
}
