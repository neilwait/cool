package linpeng.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import linpeng.domain.News;

public class DBNewsListManage {
	
	/**
	 * �����ݿ����news����
	 * @param newss
	 * @param context
	 */
	public void addNewsList(List<News> newss,Context context){
		if(newss.size()>0){
			clearNewsList(context);
		}
		for(News news:newss){
			addNews(news, context);
		}
	}
	
	/**
	 * �����ݿ��м���һ����¼
	 * @param news  һ��news
	 * @param context 
	 */
	public void addNews(News news,Context context){
		SQLiteDatabase sqLiteDatabase = LPDataBaseHelper.getInstance(context).getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("title", news.getTitle());
		contentValues.put("url", news.getUrl());
		contentValues.put("photoUrl", news.getPhotoUrl());
		contentValues.put("source", news.getSource());
		contentValues.put("date", news.getDate());
		if(sqLiteDatabase!=null){
			sqLiteDatabase.insert("newslist", null, contentValues);
			sqLiteDatabase.close();
		}
	}
	
	/**
	 * ȡ��news��������
	 * @param context
	 * @return
	 */
	public List<News> getNewss(Context context){
		List<News> newss = new ArrayList<News>();
		SQLiteDatabase sqLiteDatabase = LPDataBaseHelper.getInstance(context).getReadableDatabase();
		if(sqLiteDatabase!=null){
			Cursor cursor = sqLiteDatabase.query("newslist", new String[] { "title",
					"url", "photoUrl", "source" ,"date"}, null, null, null, null, null);
			while(cursor.moveToNext()){
				News news = new News();
				news.setTitle(cursor.getString(cursor.getColumnIndex("title")));
				news.setUrl(cursor.getString(cursor.getColumnIndex("url")));
				news.setPhotoUrl(cursor.getString(cursor.getColumnIndex("photoUrl")));
				news.setSource(cursor.getString(cursor.getColumnIndex("source")));
				news.setDate(cursor.getString(cursor.getColumnIndex("date")));
				newss.add(news);
			}
			sqLiteDatabase.close();
		}
		return newss;
	}

	/**
	 * �������
	 * @param context
	 */
	public void clearNewsList(Context context){
		SQLiteDatabase sqLiteDatabase=LPDataBaseHelper.getInstance(context).getWritableDatabase();
		if (sqLiteDatabase != null)  {
			sqLiteDatabase.delete("newslist", null, null);
		} 
		sqLiteDatabase.close();
	}
}
