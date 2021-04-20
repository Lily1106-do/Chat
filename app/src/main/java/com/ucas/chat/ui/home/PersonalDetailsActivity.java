package com.ucas.chat.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.ucas.chat.R;
import com.ucas.chat.base.BaseActivity;
import com.ucas.chat.bean.UserBean;
import com.ucas.chat.ui.home.chat.P2PChatActivity;
import com.ucas.chat.ui.view.dialog.CommonDialog;
import com.ucas.chat.ui.view.dialog.InputDialog;

public class PersonalDetailsActivity extends BaseActivity {

    private ImageView mImBack;
    private TextView tv_remark_name;
    private RelativeLayout rel_change_remark;
    private Switch mSwitch;
    private TextView tv_send_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_personal_details);
        initView();
        initListener();

    }

    private void initView() {
        mImBack = findViewById(R.id.im_back);
        tv_remark_name = findViewById(R.id.tv_remark_name);
        mSwitch = findViewById(R.id.s_v);
        rel_change_remark = findViewById(R.id.rel_change_remark);
        tv_send_message = findViewById(R.id.tv_send_message);
    }

    private void initListener() {
        mImBack.setOnClickListener(this);
        rel_change_remark.setOnClickListener(this);
        tv_send_message.setOnClickListener(this);

        mSwitch.setChecked(false);
        mSwitch.setSwitchTextAppearance(PersonalDetailsActivity.this,R.style.s_false);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //控制开关字体颜色
                if (b) {
                    mSwitch.setSwitchTextAppearance(PersonalDetailsActivity.this,R.style.s_true);
                }else {
                    mSwitch.setSwitchTextAppearance(PersonalDetailsActivity.this,R.style.s_false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.im_back:
                finish();
                break;
            case R.id.rel_change_remark:
                final InputDialog dialog = new InputDialog(PersonalDetailsActivity.this);
                dialog.setOnClickBottomListener(new InputDialog.OnClickBottomListener() {

                    @Override
                    public void onPositiveClick(String remark) {
                        tv_remark_name.setText(remark);
                        dialog.dismiss();
                    }
                    @Override
                    public void onNegtiveClick() {
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.tv_send_message:
                Intent intent = new Intent(PersonalDetailsActivity.this, P2PChatActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
