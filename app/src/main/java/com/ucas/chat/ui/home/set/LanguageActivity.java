package com.ucas.chat.ui.home.set;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ucas.chat.R;
import com.ucas.chat.base.BaseActivity;
import com.ucas.chat.utils.ToastUtils;

import java.util.Locale;

public class LanguageActivity extends BaseActivity {
    private LinearLayout m_chinese;
    private LinearLayout m_english;
    private ImageView mImBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        mImBack = findViewById(R.id.im_back);
        mImBack.setOnClickListener(this);
        m_english = findViewById(R.id.to_english);
        m_english.setClickable(true);
        m_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sta=getResources().getConfiguration().locale.getLanguage();
                ToastUtils.showMessage(LanguageActivity.this, sta);
            }
        });

        m_chinese = findViewById(R.id.to_chinese);
        m_chinese.setClickable(true);
        m_chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sta=getResources().getConfiguration().locale.getLanguage();
                ToastUtils.showMessage(LanguageActivity.this, sta);
            }
        });
    }
    public void shiftLanguage(String sta){
        if (sta.equals("en")){
            Locale.setDefault(Locale.ENGLISH);
            Configuration config = getBaseContext().getResources().getConfiguration();
            config.locale = Locale.ENGLISH;
            getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
            refreshSelf();

        }else{
            Locale.setDefault(Locale.CHINESE);
            Configuration config = getBaseContext().getResources().getConfiguration();
            config.locale = Locale.CHINESE;
            getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
            refreshSelf();
        }
    }
    public void refreshSelf(){
        Intent intent=new Intent(this,LanguageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.im_back:
                finish();
                break;
        }
    }
}
