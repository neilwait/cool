package linpeng.frament;

import java.util.ArrayList;
import java.util.List;

import linpeng.adapter.AccessoryListViewAdapter;
import linpeng.adapter.PhoneListViewAdapter;
import linpeng.coolpad.AccessoryDetailsActivity;
import linpeng.coolpad.DetailsActivity;
import linpeng.coolpad.R;
import linpeng.domain.Accessory;
import linpeng.domain.Phone;
import linpeng.frament.PhoneFrament.MyAsyncTaskGetData;
import linpeng.frament.PhoneFrament.MyScrollListener;
import linpeng.globel.MyGlobel;
import linpeng.htmlutil.GetAccessoryService;
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
/**
 * 配件列表
 * @author Administrator
 *
 */
public class AccessoryFrament extends Fragment{

	private FragmentActivity activity;
	private ListView listView;
	private List<Accessory> accessories=new ArrayList<Accessory>();
	private AccessoryListViewAdapter accessoryListViewAdapter;
	private boolean isLoading = false;
	private int pageNow = 0;
	private View footView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frament_accessory, container, false);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
		listView=(ListView)activity.findViewById(R.id.accessory_listview);
		footView = View.inflate(activity, R.layout.foot, null);
		listView.addFooterView(footView);
		accessoryListViewAdapter=new AccessoryListViewAdapter(activity, accessories);
		listView.setAdapter(accessoryListViewAdapter);
		listView.setOnScrollListener(new MyScrollListener());
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(activity,AccessoryDetailsActivity.class);
				intent.putExtra("index", arg2);
				startActivity(intent);
			}
		});
		new MyAsyncTaskGetData().execute("");
	}
	public class MyAsyncTaskGetData extends AsyncTask<String, String, List<Accessory>>{

		@Override
		protected List<Accessory> doInBackground(String... params) {
			List<Accessory> tempAccessorys=new GetAccessoryService().getAccessories(pageNow);
			return tempAccessorys;
		}

		@Override
		protected void onPostExecute(List<Accessory> tempAccessorys) {
			accessories.addAll(tempAccessorys);
			MyGlobel.accessories=accessories;
			accessoryListViewAdapter.setAccessories(accessories);
			accessoryListViewAdapter.notifyDataSetChanged();
			isLoading = false;
			//判断是否加载到最后一页
			if(tempAccessorys.size()==0){
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
