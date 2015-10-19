package linpeng.frament;

import java.util.List;
import linpeng.adapter.MainListViewAdapter;
import linpeng.component.MyListView;
import linpeng.coolpad.NewsDetailsActivity;
import linpeng.coolpad.R;
import linpeng.database.DBNewsListManage;
import linpeng.domain.News;
import linpeng.htmlutil.GetNewsListService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
/**
 * ������
 * @author Administrator
 *
 */
public class MainFrament extends Fragment{

	private FragmentActivity activity;
	private MyListView listView;
	private MainListViewAdapter adapter;
	private List<News> newss;
	//��ǰҳ��
	private int pageNow = 0;
	//�ж��Ƿ����ڼ��ظ���
	private boolean isLoading = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frament_main, container, false);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
		listView = (MyListView)activity.findViewById(R.id.main_listview);

		newss = new DBNewsListManage().getNewss(activity);
		adapter = new MainListViewAdapter(newss, activity);
		listView.addFooterView(View.inflate(activity, R.layout.foot, null));
		listView.setAdapter(adapter);
		listView.setOnScrollListener(new MyScrollListener());
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position!=0&&position!=newss.size()+1){
					Intent intent = new Intent(activity,NewsDetailsActivity.class);
					intent.putExtra("url", newss.get(position-1).getUrl());
					startActivity(intent);
				}
			}
		});
		new MyAsyncTask().execute("");
	}

	public class MyAsyncTask extends AsyncTask<String, String, List<News>>{
		@Override
		protected List<News> doInBackground(String... params) {
			List<News> tempNews = new GetNewsListService().getNews(pageNow);
			return tempNews;
		}
		@Override
		protected void onPostExecute(List<News> tempNews) {
			//�ж��Ƿ��ǵ�һ�μ��أ��ǵĻ��������ݿ���ȡ�������滻�ɴ������ȡ�����ݣ������ʾ������һҳ������һҳ���ݼ��뵱ǰ��������
			if(pageNow!=0){ 
			    newss.addAll(tempNews);
			}else{
				newss = tempNews;
				//����һҳ���ݼ������ݿ�
				new DBNewsListManage().addNewsList(tempNews, activity);
			}
			if(newss!=null){
				adapter.setNews(newss);
				adapter.notifyDataSetChanged();
			}
			isLoading=false;
		}
	}
	
	public class MyScrollListener implements OnScrollListener{

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
		
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
//			System.out.println(firstVisibleItem+"   "+visibleItemCount+"    "+totalItemCount);
			if(firstVisibleItem+visibleItemCount==totalItemCount&&isLoading==false){
				isLoading=true;
				pageNow++;
				new MyAsyncTask().execute("");
			}
		}
		
	}

}
