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
import com.ucas.chat.bean.UserBean;
import com.ucas.chat.ui.home.adapter.NewsListAdapter;
import com.ucas.chat.ui.home.chat.P2PChatActivity;
import com.ucas.chat.utils.SharedPreferencesUtil;
import com.ucas.chat.utils.TextUtils;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImHead = view.findViewById(R.id.im_head);
        mTvUserName = view.findViewById(R.id.tv_user_name);
        mTvIfOnLine = view.findViewById(R.id.tv_if_on_line);
        mLvNewsList  = view.findViewById(R.id.lv_news_list);
        mAdapter = new NewsListAdapter(getActivity());
        mLvNewsList.setAdapter(mAdapter);

        UserBean bean = SharedPreferencesUtil.getUserBeanSharedPreferences(getContext());
        if (null != bean){
            mImHead.setImageResource(bean.getImPhoto());
            mTvUserName.setText(bean.getUserName());
        }

        Bundle bundle = new Bundle();
//        bundle.putString(UIController.BUNDLE_LOGIN_ACCOUNT, mEdUserName.getText().toString().trim());
//        bundle.putString(UIController.BUNDLE_LOGIN_PASSWORD, mEdPwd.getText().toString().trim());
//        ControlManager.getControlManager().setCurrentModule(ModuleDef.FEATURE_NEWS_MODULE);
//        ControlManager.getControlManager().sendMessageToModule( ModuleDef.MESSAGE_TO_NEWS_MODULE ,bundle);
        mLvNewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), P2PChatActivity.class);
                startActivity(intent);
            }
        });
    }

}
