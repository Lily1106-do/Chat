package com.ucas.chat.ui.login;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ucas.chat.R;
import com.ucas.chat.base.BaseActivity;
import com.ucas.chat.bean.UserBean;
import com.ucas.chat.bean.contact.ConstantValue;
import com.ucas.chat.ui.ChangePasswordActivity;
import com.ucas.chat.ui.home.HomeActivity;
import com.ucas.chat.ui.register.RegisterActivity;
import com.ucas.chat.ui.view.RoundProgressBar;
import com.ucas.chat.utils.PermissionUtils;
import com.ucas.chat.utils.SharedPreferencesUtil;
import com.ucas.chat.utils.TextUtils;
import com.ucas.chat.utils.ToastUtils;

import java.util.Random;

import static com.ucas.chat.MyApplication.getContext;

public class LoginActivity extends BaseActivity{

    private TextView mEdUserName;
    private EditText mEdPassWord;
    private Button mButtConfirm;
    private TextView mTvForgetPassword;
    private TextView mTvRegister;
    private RoundProgressBar mRoundProgressBar;

    private final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int PERMISSION_REQUEST_CODE = 100001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initPermission();
        mEdUserName = findViewById(R.id.ed_user_name);
        mEdPassWord = findViewById(R.id.ed_pass_word);
        mTvForgetPassword = findViewById(R.id.tv_forget_password);
        mButtConfirm = findViewById(R.id.butt_confirm);
        mTvRegister = findViewById(R.id.tv_register);
        mRoundProgressBar =findViewById(R.id.roundProgressBar);
        mRoundProgressBar.setProgress(50);
        initClick();
    }

    private void initClick() {
        mButtConfirm.setOnClickListener(this);
        mTvForgetPassword.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_forget_password:
                UserBean bean = SharedPreferencesUtil.getUserBeanSharedPreferences(getContext());
                if (null != bean){
                    Intent intentPassword = new Intent(LoginActivity.this, ChangePasswordActivity.class);
                    startActivityForResult(intentPassword,ConstantValue.LOGIN_TO_CHANGE_PASSWORD);
                }else {
                    ToastUtils.showMessage(getContext(),getString(R.string.tip_registered_account_num));
                }

                break;
            case R.id.tv_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.butt_confirm:
                login();
                break;
        }
    }

    private void login(){
        UserBean bean = SharedPreferencesUtil.getUserBeanSharedPreferences(getContext());
        String name = mEdUserName.getText().toString().trim();
        String password = mEdPassWord.getText().toString().trim();
        if (null != bean){
            if (!TextUtils.isEmpty(bean.getPassword()) && !TextUtils.isEmpty(bean.getUserName())){
                if (bean.getUserName().equals(name) && bean.getPassword().equals(password)){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else {
                    ToastUtils.showMessage(getContext(),getString(R.string.tip_login_error));
                }
            }
        }else {
            ToastUtils.showMessage(getContext(),getString(R.string.tip_registered_account_num));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantValue.LOGIN_TO_CHANGE_PASSWORD) {
            UserBean bean = SharedPreferencesUtil.getUserBeanSharedPreferences(getContext());
            if (null != bean){
                mEdUserName.setText(bean.getUserName());
                mEdPassWord.setText(bean.getPassword());
            }
        }

    }

    /**
     * 检查，申请权限
     */
    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean has = PermissionUtils.checkPermissions(this, BASIC_PERMISSIONS);
            if (!has) {
                PermissionUtils.requestPermissions(this, PERMISSION_REQUEST_CODE,
                        BASIC_PERMISSIONS);
            }
        }
    }
}
