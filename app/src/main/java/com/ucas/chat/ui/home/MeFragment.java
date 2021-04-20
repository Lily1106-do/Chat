package com.ucas.chat.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ucas.chat.R;
import com.ucas.chat.base.BaseFragment;
import com.ucas.chat.ui.ChangePasswordActivity;
import com.ucas.chat.ui.home.set.LanguageActivity;
import com.ucas.chat.ui.home.set.SettingActivity;

/**
 * 我
 */
public class MeFragment extends BaseFragment {

    private LinearLayout mSetting;
    private LinearLayout mPass;
    private LinearLayout mQuit;
    private LinearLayout mLang;
    private static boolean exited = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSetting = view.findViewById(R.id.setting);

        mSetting.setClickable(true);
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这个可以返回自己的activity
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
        mPass = view.findViewById(R.id.password_change);
        mPass.setClickable(true);
        mPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        mQuit = view.findViewById(R.id.quit);
        mQuit.setClickable(true);
        mQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
        mLang = view.findViewById(R.id.Language_change);
        mLang.setClickable(true);
        mLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LanguageActivity.class);
                startActivity(intent);
            }
        });
    }
}
