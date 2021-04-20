package com.ucas.chat.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ucas.chat.R;
import com.ucas.chat.base.BaseFragment;
import com.ucas.chat.bean.contact.ContactListBean;
import com.ucas.chat.ui.home.adapter.ContactListAdapter;
import com.ucas.chat.ui.view.decoration.DividerItemDecoration;
import com.xdroid.IndexBar.widget.IndexBar;
import com.xdroid.suspension.SuspensionDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 通讯录
 */
public class ContactListFragment extends BaseFragment {

    private ImageView mImAdd;
    private RecyclerView mRv;
    private ContactListAdapter mAdapter;
    private LinearLayoutManager mManager;
    private List<ContactListBean> mDatas = new ArrayList<>();

    /**
     * 右侧边栏导航区域
     */
    private IndexBar mIndexBar;

    private SuspensionDecoration mDecoration;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRv = (RecyclerView) view.findViewById(R.id.rv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(getActivity()));

        mAdapter = new ContactListAdapter(getActivity(), mDatas);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(mDecoration = new SuspensionDecoration(getActivity(), mDatas));
        //如果add两个，那么按照先后顺序，依次渲染。
        mRv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        mIndexBar = (IndexBar) view.findViewById(R.id.indexBar);//IndexBar
        //indexbar初始化
        mIndexBar
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager

        //模拟线上加载数据
        initDatas(getResources().getStringArray(R.array.provinces));
        Bundle bundle = new Bundle();
//        bundle.putString(UIController.BUNDLE_LOGIN_ACCOUNT, mEdUserName.getText().toString().trim());
//        bundle.putString(UIController.BUNDLE_LOGIN_PASSWORD, mEdPwd.getText().toString().trim());
//        ControlManager.getControlManager().setCurrentModule(ModuleDef.FEATURE_CONTACT_LIST_MODULE);
//        ControlManager.getControlManager().sendMessageToModule( ModuleDef.MESSAGE_TO_CONTACT_LIST_MODULE ,bundle);


    }

    private void initDatas(final String[] data) {
                mDatas = new ArrayList<>();
                for (int i = 0; i < data.length; i++) {
                    ContactListBean bean = new ContactListBean();
                    bean.setName(data[i]);
                    mDatas.add(bean);
                }
                mAdapter.setDatas(mDatas);
                mAdapter.notifyDataSetChanged();

                mIndexBar.setmSourceDatas(mDatas)//设置数据
                        .invalidate();
                mDecoration.setmDatas(mDatas);
    }
}
