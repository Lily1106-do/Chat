package com.ucas.chat.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ucas.chat.R;
import com.ucas.chat.base.BaseFragment;

/**
 * 我
 */
public class MeFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = new Bundle();
//        bundle.putString(UIController.BUNDLE_LOGIN_ACCOUNT, mEdUserName.getText().toString().trim());
//        bundle.putString(UIController.BUNDLE_LOGIN_PASSWORD, mEdPwd.getText().toString().trim());

    }
}
