package com.ucas.chat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucas.chat.R;
import com.ucas.chat.base.BaseActivity;
import com.ucas.chat.bean.UserBean;
import com.ucas.chat.bean.contact.ConstantValue;
import com.ucas.chat.utils.SharedPreferencesUtil;
import com.ucas.chat.utils.ToastUtils;

import static com.ucas.chat.MyApplication.getContext;

/**
 * 修改密码
 */
public class ChangePasswordActivity extends BaseActivity {

    private ImageView mImBack;
    private TextView mTvUserName;
    private EditText mEdPassWord;
    private EditText mEdPassWordAgain;
    private Button mButtConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
        initListener();

    }

    private void initView() {
        mImBack = findViewById(R.id.im_back);
        mTvUserName = findViewById(R.id.tv_user_name);
        mEdPassWord = findViewById(R.id.ed_pass_word);
        mEdPassWordAgain = findViewById(R.id.ed_pass_word_again);
        mButtConfirm = findViewById(R.id.butt_confirm);
        UserBean bean = SharedPreferencesUtil.getUserBeanSharedPreferences(getContext());
        if (null != bean){
            mTvUserName.setText(bean.getUserName());
        }
    }

    private void initListener() {
        mImBack.setOnClickListener(this);
        mButtConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.im_back:
                finish();
                break;
            case R.id.butt_confirm:
               changePassword();
                break;
        }
    }

    private void changePassword() {
        String password = mEdPassWord.getText().toString().trim();

        if (TextUtils.isEmpty(password)) {
            Log.d(TAG, "login: password is null");
            ToastUtils.showMessage(getContext(),getString(R.string.tip_pass_word));
            return;
        }

        if (!mEdPassWordAgain.getText().toString().trim().equals(password)) {
            Log.d(TAG, "login: password is null");
            ToastUtils.showMessage(getContext(),getString(R.string.tip_confirm_pass_word));
            return;
        }

        UserBean bean = SharedPreferencesUtil.getUserBeanSharedPreferences(getContext());
        bean.setPassword(password);
        SharedPreferencesUtil.setUserBeanSharedPreferences(getContext(), bean);

        Intent intent = getIntent();
        setResult(ConstantValue.LOGIN_TO_CHANGE_PASSWORD,intent);
        finish();

    }


}
