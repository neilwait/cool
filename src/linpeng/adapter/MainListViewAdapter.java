package linpeng.adapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import linpeng.coolpad.R;
import linpeng.domain.News;
import linpeng.net.util.GetBitmapUtil;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainListViewAdapter extends BaseAdapter{

	private List<News> newss;
	private Context context;
	private HashMap<String, SoftReference<Bitmap>> hashMap=new HashMap<String, SoftReference<Bitmap>>();

	public MainListViewAdapter(List<News> newss,Context context){
		this.newss = newss;
		this.context = context;
	}
	@Override
	public int getCount() {
		return newss.size()+1;
	}

	@Override
	public Object getItem(int position) {
		return newss.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RelativeLayout relativeLayout = null;
		if(position == 0){
			relativeLayout = (RelativeLayout) View.inflate(context, R.layout.listview_coolnews_image, null);
			relativeLayout.setTag("-1");
			setViews(relativeLayout);
		}else{
			if(convertView==null||"-1".equals(convertView.getTag())){
				relativeLayout = (RelativeLayout) View.inflate(context, R.layout.listview_coolnews, null);
			}else{
				relativeLayout = (RelativeLayout) convertView;
			}
			
			((TextView)relativeLayout.findViewById(R.id.main_listview_text_title)).setText(newss.get(position-1).getTitle());
			((TextView)relativeLayout.findViewById(R.id.main_listview_text_source)).setText(newss.get(position-1).getSource());
			((TextView)relativeLayout.findViewById(R.id.main_listview_text_date)).setText(newss.get(position-1).getDate());
			
			
			/*
			 *ͼƬ���ش����ж��Ƿ����ͼƬ���첽���أ� 
			 */
			ImageView imageView = (ImageView)relativeLayout.findViewById(R.id.main_listview_image);
			imageView.setImageBitmap(null);
			imageView.setTag(newss.get(position-1).getPhotoUrl()+(position-1));	
			if(newss.get(position-1).getPhotoUrl()==null||newss.get(position-1).getPhotoUrl().equals("")){
				imageView.setVisibility(View.GONE);
			}else{
				imageView.setVisibility(View.VISIBLE);
				if(hashMap.get(newss.get(position-1).getPhotoUrl())!=null&&hashMap.get(newss.get(position-1).getPhotoUrl()).get()!=null){
					imageView.setImageBitmap(hashMap.get(newss.get(position-1).getPhotoUrl()).get());
				}else{
					MyAsyncTask asyncTask = new MyAsyncTask();
					asyncTask.imageView = imageView;
					asyncTask.execute(position-1);
				}
			}
		}
		return relativeLayout;
	}

	/*
	 * �첽����ͼƬ
	 */
	class MyAsyncTask extends AsyncTask<Integer, String, Bitmap>{
		ImageView imageView = null;
		int index=0;
		@Override
		protected Bitmap doInBackground(Integer... params) {
			index = params[0];
			Bitmap bitmap =  new GetBitmapUtil().getBitmapByUrl(newss.get(index).getPhotoUrl());
			return bitmap;
		}
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			hashMap.put(newss.get(index).getPhotoUrl(), new SoftReference<Bitmap>(bitmap));
	    	//��ֹͼƬ���󣬻�����imageView�ж��Ƿ�����ǰλ�õ�imageView
			if(imageView.getTag().toString().equals(newss.get(index).getPhotoUrl()+index)){
				imageView.setImageBitmap(hashMap.get(newss.get(index).getPhotoUrl()).get());
			}else{
//				System.out.println("error");
			}
		}
	}

	/*
	 * ���ÿ�ͷ�Ĺ���ͼƬ
	 */
	private void setViews(RelativeLayout relativeLayout) {

		ViewPager viewPager = (ViewPager) relativeLayout.findViewById(R.id.main_viewpager);
		List<ImageView> imageViews = new ArrayList<ImageView>();
		ImageView imageView1 = new ImageView(context);
		imageView1.setImageResource(R.drawable.a1);
		imageViews.add(imageView1);
		ImageView imageView2 = new ImageView(context);
		imageView2.setImageResource(R.drawable.a2);
		imageViews.add(imageView2);
		ImageView imageView3 = new ImageView(context);
		imageView3.setImageResource(R.drawable.a3);
		imageViews.add(imageView3);
		ImageView imageView4 = new ImageView(context);
		imageView4.setImageResource(R.drawable.a4);
		imageViews.add(imageView4);
		ImageView imageView5 = new ImageView(context);
		imageView5.setImageResource(R.drawable.a5);
		imageViews.add(imageView5);
		viewPager.setAdapter(new MainViewPagerAdapter(imageViews));
	}
	
	public void setNews(List<News> newss) {
		this.newss = newss;
	}

}
