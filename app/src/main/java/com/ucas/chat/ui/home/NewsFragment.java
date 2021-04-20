package com.ucas.chat.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ucas.chat.R;
import com.ucas.chat.base.BaseFragment;
import com.ucas.chat.bean.NewsBean;
import com.ucas.chat.bean.UserBean;
import com.ucas.chat.ui.home.adapter.NewsListAdapter;
import com.ucas.chat.ui.home.chat.P2PChatActivity;
import com.ucas.chat.ui.view.dialog.CommonDialog;
import com.ucas.chat.utils.SharedPreferencesUtil;
import com.ucas.chat.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

import static com.ucas.chat.MyApplication.getContext;

/**
 * 消息
 */
public class NewsFragment extends BaseFragment {

    private ImageView mImHead;
    private TextView mTvUserName;
    private TextView mTvIfOnLine;
    private ListView mLvNewsList;
    private NewsListAdapter mAdapter;
    private List<NewsBean> newsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        mImHead = view.findViewById(R.id.im_head);
        mTvUserName = view.findViewById(R.id.tv_user_name);
        mTvIfOnLine = view.findViewById(R.id.tv_if_on_line);
        mLvNewsList  = view.findViewById(R.id.lv_news_list);
        mAdapter = new NewsListAdapter(getActivity(), newsList);
        mLvNewsList.setAdapter(mAdapter);
        UserBean bean = SharedPreferencesUtil.getUserBeanSharedPreferences(getContext());
        if (null != bean){
            mImHead.setImageResource(bean.getImPhoto());
            mTvUserName.setText(bean.getUserName());
        }

        initListener();
    }

    private void initData() {
        newsList = new ArrayList<>();
        NewsBean bean1 = new NewsBean();
        bean1.setFriendHeadNum(0);
        bean1.setLastNews("吃了吗");
        bean1.setFriendName("小明");
        bean1.setIsReadNews(1);
        bean1.setLastNewsTime("12:30");
        NewsBean bean2 = new NewsBean();
        bean2.setFriendHeadNum(3);
        bean2.setLastNews("明天八点开会");
        bean2.setFriendName("张明");
        bean2.setIsReadNews(0);
        bean2.setLastNewsTime("13:30");
        NewsBean bean3 = new NewsBean();
        bean3.setFriendHeadNum(2);
        bean3.setFriendName("李刚");
        bean3.setLastNews("今天加班");
        bean3.setIsReadNews(1);
        bean3.setLastNewsTime("15:30");
        NewsBean bean4 = new NewsBean();
        bean4.setFriendHeadNum(2);
        bean4.setLastNews("明天是周末");
        bean4.setFriendName("王丫");
        bean4.setIsReadNews(5);
        bean4.setLastNewsTime("16:20");
        NewsBean bean5 = new NewsBean();
        bean5.setFriendHeadNum(1);
        bean5.setLastNews("给点一下关注");
        bean5.setFriendName("六六");
        bean5.setIsReadNews(0);
        bean5.setLastNewsTime("20:08");
        newsList.add(bean1);
        newsList.add(bean2);
        newsList.add(bean3);
        newsList.add(bean4);
        newsList.add(bean5);
    }

    private void initListener() {
        mLvNewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), P2PChatActivity.class);
                startActivity(intent);
            }
        });
        mLvNewsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CommonDialog dialog = new CommonDialog(getActivity());
                dialog.setMessage(getContext().getString(R.string.delete_friend))
                        .setImageResId(UserBean.imHead[newsList.get(position).getFriendHeadNum()])
                        .setTitle(getString(R.string.delete_friend))
                        .setSingle(false).setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        newsList.remove(position);
                        mAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegtiveClick() {
                        dialog.dismiss();
                    }
                }).show();
                return true;
            }
        });

    }


}
