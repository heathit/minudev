package com.minu.jjaljup.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.minu.jjaljup.Define;
import com.minu.jjaljup.widget.QuickProgressDialog;

import net.htmlparser.jericho.Source;

public class NetAdapter {
	private Activity activity;
	private String url;
	private Handler handler;
	
    private boolean showDialog = false;
    
    
	public NetAdapter() {
		init();
	}

	/**
	 * init
	 */
	private void init() {
		activity = null;
		url = null;
		handler = null;
		showDialog = true;
	}
	
//*******************
// API 세팅 - start
//*******************
	public void getHTMLSource(Activity activity, String url, Handler handler){
		init();

		this.activity = activity;
		this.handler = handler;
		this.url = url;

		new NetTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}

	public void getImgURLList(Activity activity, ArrayList<String> downList, Handler handler){
		init();

		this.activity = activity;
		this.handler = handler;

		new DownTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, downList);
	}


//*******************
// API 세팅 - end
//*******************

	class NetTask extends AsyncTask<Void, Void, Message> {
		private QuickProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			if(dialog == null && showDialog){
				dialog = new QuickProgressDialog(activity);
				dialog .getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.show();
			}
		}

		@Override
		protected Message doInBackground(Void... args) {

			Source source = null;
			String content = null;

			try {
				//url에서 html 소스를 읽어온다.
				//source=new Source(new URL(sourceUrlString));
				// 한글사용을 위해서는 윗 줄의 코드를 아래처럼 수정
				URL sUrl = new URL(url);
				InputStream is = sUrl.openStream();
				source= new Source(new InputStreamReader(is,"utf-8"));
				// "euc-kr" 부분은 charset에 맞춰 변경해준다. UTF-8, KSC5601 etc...

				// 전체 소스 구문을 분석한다.
//				source.fullSequentialParse();

			} catch (Exception e) {
				e.printStackTrace();
				return handler.obtainMessage(-1, source);
			}
			if(source == null){
				return handler.obtainMessage(-1, source);
			}
			return handler.obtainMessage(0, source);
		}
		 
		@Override
		protected void onPostExecute(Message msg) {
			handler.sendMessage(msg);

			if(dialog != null && showDialog){
				dialog.dismiss();
			}
		}
	}


	class DownTask extends AsyncTask<ArrayList<String>, Void, Message> {
		private QuickProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			if(dialog == null && showDialog){
				dialog = new QuickProgressDialog(activity);
				dialog .getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.show();
			}
		}

		@Override
		protected Message doInBackground(ArrayList<String>... args) {

			ArrayList<String> downList = args[0];
			Source source = null;

			for(String no : downList){
				try {
					URL sUrl = new URL(Define.CONTENT_URL+no);
					InputStream is = sUrl.openStream();
					source= new Source(new InputStreamReader(is,"utf-8"));

					source.fullSequentialParse();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			/*

			try {
				//url에서 html 소스를 읽어온다.
				//source=new Source(new URL(sourceUrlString));
				// 한글사용을 위해서는 윗 줄의 코드를 아래처럼 수정
				URL sUrl = new URL(url);
				InputStream is = sUrl.openStream();
				source= new Source(new InputStreamReader(is,"utf-8"));
				// "euc-kr" 부분은 charset에 맞춰 변경해준다. UTF-8, KSC5601 etc...

				// 전체 소스 구문을 분석한다.
//				source.fullSequentialParse();

			} catch (Exception e) {
				e.printStackTrace();
				return handler.obtainMessage(-1, source);
			}


			if(source == null){
				return handler.obtainMessage(-1, source);
			}
			*/
//			return handler.obtainMessage(-1, source);
			return handler.obtainMessage(0, source);
		}

		@Override
		protected void onPostExecute(Message msg) {
			handler.sendMessage(msg);

			if(dialog != null && showDialog){
				dialog.dismiss();
			}
		}
	}

}
