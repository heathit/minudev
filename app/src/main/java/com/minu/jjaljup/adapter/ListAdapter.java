package com.minu.jjaljup.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minu.jjaljup.Define;
import com.minu.jjaljup.R;
import com.minu.jjaljup.data.ContentItem;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_FOOTER = 1;

    private int current_page = 1;

    private boolean isLoading;

    private ArrayList<ContentItem> mItems;

    public ListAdapter(ArrayList<ContentItem> items){
        mItems = items == null ? new ArrayList<ContentItem>() : items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item,parent,false);
            return new ListViewHolder(v);
        } else if (viewType == VIEW_TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_footer,parent,false);
            return new FooterViewHolder(v);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListViewHolder) {
            ((ListViewHolder)holder).checkBox.setChecked(mItems.get(position).isCheck());
            ((ListViewHolder)holder).no_textView.setText(mItems.get(position).getNo());
            ((ListViewHolder)holder).title_textView.setText(mItems.get(position).getSubject());

            ((ListViewHolder)holder).checkBox.setTag(position);



        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder loadingViewHolder = (FooterViewHolder) holder;
            loadingViewHolder.current_page_tv.setText(String.valueOf(current_page));
            if(current_page==1){
                loadingViewHolder.prev_btn.setVisibility(View.GONE);
            }else{
                loadingViewHolder.prev_btn.setVisibility(View.VISIBLE);
            }

            if(mItems.size() < Define.max_item_cnt){
                loadingViewHolder.next_btn.setVisibility(View.GONE);
            }else{
                loadingViewHolder.next_btn.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.size() <= position ? VIEW_TYPE_FOOTER : VIEW_TYPE_ITEM;
//        return super.getItemViewType(position);
    }



    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void addItemList(ArrayList<ContentItem> itemList){
        mItems.addAll(itemList);
    }

    public void changeItemList(ArrayList<ContentItem> itemList){
        mItems = null;
        mItems = itemList;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public class ListViewHolder  extends RecyclerView.ViewHolder {

        public CheckBox checkBox;
        public TextView no_textView;
        public TextView title_textView;

        public ListViewHolder(View view) {
            super(view);

            RelativeLayout layout_item = (RelativeLayout) view.findViewById(R.id.layout_item);
            checkBox = (CheckBox) view.findViewById(R.id.chk);
            no_textView = (TextView) view.findViewById(R.id.txt_no);
            title_textView = (TextView) view.findViewById(R.id.txt_title);

            layout_item.setTag(checkBox);

        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public TextView current_page_tv;
        public TextView prev_btn;
        public TextView next_btn;

        public FooterViewHolder(View itemView) {
            super(itemView);
            current_page_tv = (TextView) itemView.findViewById(R.id.current_page_tv);
            prev_btn = (TextView) itemView.findViewById(R.id.prev_btn);
            next_btn = (TextView) itemView.findViewById(R.id.next_btn);
        }
    }



}