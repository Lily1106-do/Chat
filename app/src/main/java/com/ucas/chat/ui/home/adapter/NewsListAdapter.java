package com.ucas.chat.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucas.chat.R;
import com.ucas.chat.bean.NewsBean;
import com.ucas.chat.bean.UserBean;

import java.util.List;

import static com.ucas.chat.MyApplication.getContext;

public class NewsListAdapter extends BaseAdapter {

    private Context mContext;
    private List<NewsBean> newsList;

    public NewsListAdapter(Context mContext, List<NewsBean> list) {
        this.mContext = mContext;
        this.newsList = list;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_news_list ,null);
            viewHolder = new ViewHolder();
            viewHolder.im_head = view.findViewById(R.id.im_head);
            viewHolder.tv_friend_name = view.findViewById(R.id.tv_friend_name);
            viewHolder.tv_news_content = view.findViewById(R.id.tv_news_content);
            viewHolder.tv_news_state = view.findViewById(R.id.tv_news_state);
            viewHolder.tv_time = view.findViewById(R.id.tv_time);
            view.setTag(viewHolder);// 将ViewHolder存储在View中。
        }else {
            view = convertView;
            viewHolder=(ViewHolder)view.getTag(); //重新获取ViewHolder
        }
        viewHolder.im_head.setImageResource(UserBean.imHead[newsList.get(position).getFriendHeadNum()]);
        viewHolder.tv_friend_name.setText(newsList.get(position).getFriendName());
        if (newsList.get(position).getIsReadNews() == 1){
            viewHolder.tv_news_state.setText(mContext.getString(R.string.read));
            viewHolder.tv_news_state.setTextColor(mContext.getResources().getColor(R.color.gray_color));
        }else {
            viewHolder.tv_news_state.setText(mContext.getString(R.string.no_read));
            viewHolder.tv_news_state.setTextColor(mContext.getResources().getColor(R.color.blue4));
        }
        viewHolder.tv_news_content.setText(newsList.get(position).getLastNews());
        viewHolder.tv_time.setText(newsList.get(position).getLastNewsTime());
        return view;
    }

    static class ViewHolder{
       ImageView im_head;
       TextView tv_friend_name;
       TextView tv_news_state;
       TextView tv_news_content;
       TextView tv_time;
    }
}
