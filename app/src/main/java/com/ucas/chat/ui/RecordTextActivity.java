package com.ucas.chat.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ucas.chat.R;
import com.ucas.chat.base.BaseActivity;
import com.ucas.chat.ui.view.voice.dialog.MYAudio;
import com.ucas.chat.ui.view.voice.dialog.OnSendVoiceListenr;

public class RecordTextActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_text);


        findViewById(R.id.wavButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                RecordPlayDialog dialog = new RecordPlayDialog(RecordTextActivity.this);
//                dialog.setOnSendVoiceListenr(new OnSendVoiceListenr() {
//                    @Override
//                    public void onSend(MYAudio audio) {
//                        Toast.makeText(RecordTextActivity.this, "wav文件路径:" + audio.audio_url,
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//                dialog.show();

            }
        });

        findViewById(R.id.trnsferButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                VoiceDialog dialog = new VoiceDialog(RecordTextActivity.this);
//                dialog.setOnSendVoiceListenr(new OnSendVoiceListenr() {
//                    @Override
//                    public void onSend(MYAudio audio) {
//                        Toast.makeText(RecordTextActivity.this, "wav文件路径:" + audio.audio_url, Toast.LENGTH_SHORT).show();
//                    }
//                });
//                dialog.show();

            }
        });
    }
}
