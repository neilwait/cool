package linpeng.frament;

import java.util.ArrayList;
import java.util.List;
import linpeng.adapter.PhoneListViewAdapter;
import linpeng.coolpad.DetailsActivity;
import linpeng.coolpad.R;
import linpeng.domain.Phone;
import linpeng.globel.MyGlobel;
import linpeng.htmlutil.GetMainGoodsService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class PhoneFrament extends Fragment{
	private FragmentActivity activity;
	private ListView listView;
	private List<Phone> phones=new ArrayList<Phone>();
	private PhoneListViewAdapter phoneListViewAdapter;
	private boolean isLoading = false;
	private int pageNow = 0;
	private View footView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frament_phone, container, false);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
		listView=(ListView)activity.findViewById(R.id.phone_listview);
		footView = View.inflate(activity, R.layout.foot, null);
		listView.addFooterView(footView);
		phoneListViewAdapter=new PhoneListViewAdapter(activity, phones);
		listView.setAdapter(phoneListViewAdapter);
		listView.setOnScrollListener(new MyScrollListener());
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(activity,DetailsActivity.class);
				intent.putExtra("index", arg2);
				startActivity(intent);
			}
		});
		new MyAsyncTaskGetData().execute("");
	}
	public class MyAsyncTaskGetData extends AsyncTask<String, String, List<Phone>>{

		@Override
		protected List<Phone> doInBackground(String... params) {
			List<Phone> tempPhones=new GetMainGoodsService().getMainPhones(pageNow);
			return tempPhones;
		}

		@Override
		protected void onPostExecute(List<Phone> tempPhones) {
			phones.addAll(tempPhones);
			MyGlobel.phones=phones;
			phoneListViewAdapter.setPhones(phones);
			phoneListViewAdapter.notifyDataSetChanged();
			isLoading = false;
			//判断是否加载到最后一页
			if(tempPhones.size()==0){
				Toast.makeText(activity, "已经加载完毕", Toast.LENGTH_SHORT).show();
				listView.removeFooterView(footView);
				isLoading = true;
			}
		} 

	}
	public class MyScrollListener implements OnScrollListener{

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
		 
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if(firstVisibleItem+visibleItemCount==totalItemCount&&isLoading==false){
				isLoading=true;
				pageNow++;
				new MyAsyncTaskGetData().execute("");
			}
		}
		
	}
}
