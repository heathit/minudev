package com.minu.jjaljup;

import android.app.SearchManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Toast;

import com.minu.jjaljup.adapter.ListAdapter;
import com.minu.jjaljup.data.ContentItem;
import com.minu.jjaljup.util.HTMLParser;
import com.minu.jjaljup.util.NetAdapter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import net.htmlparser.jericho.Source;

public class MainActivity extends AppCompatActivity {
    private final int DEFAULT_LIST = 100;
    private final int SEARCHED_LIST = 101;
    private int current_list = DEFAULT_LIST;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<ContentItem> contentList = null;

    private int current_page = 1;
    private String keyword = null;
    private MenuItem mSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(downloadEventListener);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ListAdapter(contentList);
        recyclerView.setAdapter(adapter);


        new NetAdapter().getHTMLSource(this, getReqURL(), boardHandler);

    }

    private Handler boardHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){
                if(msg.obj != null && msg.obj instanceof Source){
                    Source source = (Source)msg.obj;

                    contentList = HTMLParser.boardParse(source);

                    if(contentList.size()>0){
                        ((ListAdapter)adapter).changeItemList(contentList);
                        ((ListAdapter)adapter).notifyDataSetChanged();
                        ((ListAdapter)adapter).setCurrent_page(current_page);
                        recyclerView.scrollToPosition(0);
                    }else{
                        current_page = 1;
                        current_list = DEFAULT_LIST;
                        new NetAdapter().getHTMLSource(MainActivity.this, getReqURL(), boardHandler);
                        Toast.makeText(MainActivity.this, "항목이 없습니다", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    current_page = 1;
                    current_list = DEFAULT_LIST;
                    Toast.makeText(MainActivity.this, "게시판 읽어오기 오류", Toast.LENGTH_SHORT).show();
                }
            }else{
                current_page = 1;
                current_list = DEFAULT_LIST;
                Toast.makeText(MainActivity.this, "게시판 읽어오기 오류", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private Handler downHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };


    public void onClick(View v){
        boolean chk;
        int pos;
        CheckBox checkBox;
        switch (v.getId()){
            case R.id.next_btn:
                current_page++;
                new NetAdapter().getHTMLSource(this, getReqURL(), boardHandler);
                break;
            case R.id.prev_btn:
                if(current_page > 1){
                    current_page--;
                    new NetAdapter().getHTMLSource(this, getReqURL(), boardHandler);
                }
                break;
            case R.id.layout_item:
                checkBox = (CheckBox)v.getTag();
                pos = (int)checkBox.getTag();
                chk = !checkBox.isChecked();
                checkBox.setChecked(chk);
                contentList.get(pos).setCheck(chk);
                break;
            case R.id.chk:
                checkBox = (CheckBox)v;
                pos = (int)checkBox.getTag();
                contentList.get(pos).setCheck(checkBox.isChecked());
                break;
        }
    }

    public String getReqURL(){
        if(current_list == SEARCHED_LIST
                && keyword != null && keyword.length() > 0) {
            return Define.getSearchUrl(keyword, current_page);
        }else {
            keyword = null;
            return Define.OU_URL+current_page;
        }
    }

    private View.OnClickListener downloadEventListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ArrayList<String> downList = new ArrayList<>();

            for(ContentItem item : contentList){
                if(item.isCheck()){
                    downList.add(item.getNo());
                }
            }

            if(downList.size() == 0){
                Toast.makeText(MainActivity.this, "선택된 항목이 없습니다", Toast.LENGTH_SHORT).show();
                return;
            }

            new NetAdapter().getImgURLList(MainActivity.this, downList, downHandler);
        }
    };



    @Override
    public void onBackPressed() {
        if(mSearch.isActionViewExpanded()){
            mSearch.collapseActionView();
        }else if(current_page > 1){
            current_page--;
            new NetAdapter().getHTMLSource(this, getReqURL(), boardHandler);
        }else{
            super.onBackPressed();
        }
//        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        mSearch = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(mSearch);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("query : " + query);
                searchView.setQuery("", false);
                mSearch.collapseActionView();

                keyword = query;
                current_page = 1;
                current_list = SEARCHED_LIST;
                new NetAdapter().getHTMLSource(MainActivity.this, getReqURL(), boardHandler);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("newText : " + newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            return true;
        }
        if (id == R.id.action_refresh) {
            current_page = 1;
            current_list = DEFAULT_LIST;
            new NetAdapter().getHTMLSource(this, getReqURL(), boardHandler);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
