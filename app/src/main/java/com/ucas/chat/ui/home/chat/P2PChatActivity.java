package com.ucas.chat.ui.home.chat;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.supets.pet.nativelib.Settings;
import com.ucas.chat.R;
import com.ucas.chat.base.BaseActivity;
import com.ucas.chat.bean.session.ChatSession;
import com.ucas.chat.bean.session.MsgDirectionEnum;
import com.ucas.chat.bean.session.SessionTypeEnum;
import com.ucas.chat.bean.session.message.AudioAttachment;
import com.ucas.chat.bean.session.message.IMMessage;
import com.ucas.chat.ui.listener.OnItemClickListener;
import com.ucas.chat.ui.view.ChatUiHelper;
import com.ucas.chat.ui.view.RecordButton;
import com.ucas.chat.ui.view.SounchTouchView;
import com.ucas.chat.ui.view.StateButton;
import com.ucas.chat.ui.view.audio.AudioPlayManager;
import com.ucas.chat.ui.view.chat.AudioPlayHandler;
import com.ucas.chat.ui.view.chat.RViewHolder;
import com.ucas.chat.ui.view.msg.MsgRecyclerView;
import com.ucas.chat.ui.view.voice.TimeDateUtils;
import com.ucas.chat.ui.view.voice.dialog.MYAudio;
import com.ucas.chat.utils.ChatMsgHandler;
import com.ucas.chat.utils.PictureFileUtil;

import com.zlylib.fileselectorlib.FileSelector;
import com.zlylib.fileselectorlib.utils.Const;

import com.ucas.chat.utils.TextUtils;
import com.ucas.chat.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static com.ucas.chat.MyApplication.getContext;

public class P2PChatActivity extends BaseActivity implements RecordButton.OnRecordListener {

    public static final int REQUEST_CODE_IMAGE = 0001;
    public static final int REQUEST_CODE_VEDIO = 0002;
    public static final int REQUEST_CODE_FILE = 0003;

    private ImageView mImBack;
    private MsgRecyclerView mRvChat;
    private LinearLayout mLlContent;
    private StateButton mBtnSend;//发送按钮
    private EditText mEtContent;
    private RelativeLayout mRlBottomLayout;
    private LinearLayout mLlAdd;//添加布局
    private LinearLayout mLlVoice;//录音布局
    private SounchTouchView mTransferAudio;//变音布局
    private ImageView mIvAdd;
    private ImageView mIvAudio;
    private RecordButton mRecordButton;
    private TextView mAudioTime;
    private View mLeftAudioAnim;
    private View mRightAudioAnim;

    private RelativeLayout mRlPhoto;
    private RelativeLayout mRlVideo;

    private LinearLayoutManager mLayoutManager;
    private List<IMMessage> mMsgList;
    private MessageListAdapter mAdapter;

    private ChatSession mChatSession;
    private ChatMsgHandler mChatHandler;

    private ChatUiHelper mUiHelper;
    private MYAudio audio;
    private int voiceType = 0;//是否变声了

    private String mPlayId = "";
    private boolean isAudioPlay = false;
    private AudioPlayHandler mAudioPlayHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2p_chat);
        mImBack = findViewById(R.id.imBack);
        mRvChat = findViewById(R.id.rcv_msg_list);
        mLlContent = findViewById(R.id.llContent);
        mBtnSend = findViewById(R.id.btn_send);
        mEtContent = findViewById(R.id.et_chat_message);
        mRlBottomLayout = findViewById(R.id.bottom_layout);
        mLlAdd = findViewById(R.id.llAdd);
        mLlVoice = findViewById(R.id.llVoice);
        mTransferAudio = findViewById(R.id.transferAudioLayout);
        mIvAdd = findViewById(R.id.iv_more);
        mIvAudio = findViewById(R.id.iv_input_type);
        mRecordButton = findViewById(R.id.record);
        mAudioTime = findViewById(R.id.time);
        mLeftAudioAnim = findViewById(R.id.leftAnim);
        mRightAudioAnim = findViewById(R.id.rightAnim);

        mRlPhoto = findViewById(R.id.rlPhoto);
        mRlVideo = findViewById(R.id.rlVideo);
        createChatSession();
        initContent();
        initListener();
    }

    private void initListener() {
        mBtnSend.setOnClickListener(this);
        mImBack.setOnClickListener(this);
        mRlPhoto.setOnClickListener(this);
        mRlVideo.setOnClickListener(this);
        findViewById(R.id.rlFile).setOnClickListener(this);
        mRecordButton.setOnRecordListener(this);
        mTransferAudio.setListener(new SounchTouchView.OnSoundTouchListener() {
            @Override
            public void onConfirm(int type, int length) {
                voiceType = type;
                audio = new MYAudio(voiceType == 0 ? Settings.recordingOriginPath : Settings.recordingVoicePath, length);
                sendMessage(mChatHandler.createAudioMessage(audio.audio_url, audio.length));
            }

            @Override
            public void onCancel(int type) {
                mUiHelper.hideTransferAudioLayout();
                mUiHelper.hideBottomLayout(false);
            }
        });
    }

    /**
     * 初始化 当前聊天会话
     */
    private void createChatSession() {
        mChatSession = new ChatSession();
        mChatSession.setSessionType(SessionTypeEnum.P2P);
        //  mChatSession.setSessionId(chatInfo.getAccount());
        mChatSession.setChatAccount("123");
        //  mChatSession.setMyAccount(myInfo.getAccount());
        //  mChatSession.setChatInfo(chatInfo);
        //  mChatSession.setMyInfo(myInfo);

        mChatHandler = new ChatMsgHandler(getContext(), mChatSession);
    }

    private void initContent() {
        mLayoutManager = (LinearLayoutManager) mRvChat.getLayoutManager();
        mMsgList = new ArrayList<>();
        mAdapter = new MessageListAdapter(getContext(), mMsgList);
        mRvChat.setAdapter(mAdapter);
        initChatUi();
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(RViewHolder holder, IMMessage message) {
                switch (message.getMsgType()) {
                    case image:
                        showAttachOnActivity(P2PChatActivity.this, ShowImageActivity.class, message);
                        break;
                    case audio:
                        playAudio(holder, message);
                        break;
                    case video:
                        showAttachOnActivity(P2PChatActivity.this, ShowVideoActivity.class, message);
                        break;
                }
            }
        });
    }

    private void initChatUi() {
        //mBtnAudio
        mUiHelper= ChatUiHelper.with(this);
        mUiHelper.bindContentLayout(mLlContent)
                 .bindttToSendButton(mBtnSend)
                 .bindEditText(mEtContent)
                 .bindBottomLayout(mRlBottomLayout)
                 .bindAddLayout(mLlAdd)
                 .bindToAddButton(mIvAdd)
                 .bindAudioLayout(mLlVoice)
                 .bindAudioIv(mIvAudio)
                 .bindTransferAudioLayout(mTransferAudio);
        //底部布局弹出,聊天列表上滑
        mRvChat.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    mRvChat.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mAdapter.getItemCount() > 0) {
                                mRvChat.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                            }
                        }
                    });
                }
            }
        });
        //点击空白区域关闭键盘
        mRvChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mUiHelper.hideBottomLayout(false);
                mUiHelper.hideSoftInput();
                return false;
            }
        });
        //
//        ((RecordButton) mBtnAudio).setOnFinishedRecordListener(new RecordButton.OnFinishedRecordListener() {
//            @Override
//            public void onFinishedRecord(String audioPath, int time) {
//                LogUtil.d("录音结束回调");
//                File file = new File(audioPath);
//                if (file.exists()) {
//                    sendAudioMessage(audioPath,time);
//                }
//            }
//        });
    }

    /*** 播放音频，并监听播放进度，更新页面动画 ***/
    public void playAudio(final RViewHolder holder, final IMMessage message) {

        if (isAudioPlay) {
            // 如果正在播放，那会先关闭当前播放
            AudioPlayManager.pause();
            AudioPlayManager.release();
            mAudioPlayHandler.stopAnimTimer();
            isAudioPlay = false;

            // 如果关闭的是自己,那关闭后就停止执行下面的操作
//            if (message.getUuid().equals(mPlayId)) {
//                mPlayId = "";
//                return;
//            }
        }

        if (mAudioPlayHandler == null) {
            mAudioPlayHandler = new AudioPlayHandler();
        }

        AudioAttachment audioAttachment = (AudioAttachment) message.getAttachment();
        if (audioAttachment == null || TextUtils.isEmpty(audioAttachment.getPath())) {
            ToastUtils.showMessage(getContext(), "音频附件失效，播放失败！");
            return;
        }

        final ImageView imageView = holder.getImageView(R.id.iv_audio_sound);
        final boolean isLeft = message.getDirect() == MsgDirectionEnum.In;

        AudioPlayManager.playAudio(P2PChatActivity.this, audioAttachment.getPath(),
                new AudioPlayManager.OnPlayAudioListener() {
                    @Override
                    public void onPlay() {
                        // 启动播放动画
                        isAudioPlay = true;
                        mPlayId = message.getUuid();
                        mAudioPlayHandler.startAudioAnim(imageView, isLeft);
                    }

                    @Override
                    public void onComplete() {
                        isAudioPlay = false;
                        mPlayId = "";
                        mAudioPlayHandler.stopAnimTimer();
                    }

                    @Override
                    public void onError(String message) {
                        isAudioPlay = false;
                        mPlayId = "";
                        mAudioPlayHandler.stopAnimTimer();
                        ToastUtils.showMessage(P2PChatActivity.this, message);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.imBack:
                finish();
                break;
            case R.id.btn_send:
                sendTextMessage();
                break;
            case R.id.rlPhoto:
                PictureFileUtil.openGalleryPic(P2PChatActivity.this, REQUEST_CODE_IMAGE);
                break;
            case R.id.rlVideo:
                PictureFileUtil.openGalleryAudio(P2PChatActivity.this, REQUEST_CODE_VEDIO);
                break;
            case R.id.rlFile:
                openFileSelector(3);
                break;
            case R.id.record:

                break;
        }
    }

    private void sendTextMessage() {
        String textMessage = mEtContent.getText().toString();
        mEtContent.getText().clear();
        sendMessage(mChatHandler.createTextMessage(textMessage));
    }

    /*** 消息发送 ***/
    private void sendMessage(IMMessage message) {

//        if (mMsgList.isEmpty() ||
//                message.getTime() - mMsgList.get(mMsgList.size() - 1).getTime() > TEN_MINUTE) {
//            mMsgList.add(mChatHandler.createTimeMessage(message));
//        }

        //  mMsgList.add(mChatHandler.createTimeMessage(message));
        // 将新消息加入列表并刷新界面
        mMsgList.add(message);
        mAdapter.notifyItemInserted(mMsgList.size());
        mLayoutManager.scrollToPosition(mMsgList.size());
        // 发送消息并监听消息发送状态
        //NIMClient.getService(MsgService.class).sendMessage(message, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_FILE:
                    if (data.getStringArrayListExtra(Const.EXTRA_RESULT_SELECTION) != null) {
                        ArrayList<String> essFileList = data.getStringArrayListExtra(Const.EXTRA_RESULT_SELECTION);
                        for (int i = 0; i < essFileList.size(); i++) {
                            sendMessage(mChatHandler.createFileMessage(essFileList.get(i)));
                        }
                    }
                    break;
                case REQUEST_CODE_IMAGE:
                    // 图片选择结果回调
                    List<LocalMedia> selectListPic = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia media : selectListPic) {
                        sendMessage(mChatHandler.createImageMessage(media.getPath(), media.getCompressPath()));
                    }
                    break;
                case REQUEST_CODE_VEDIO:
                    // 视频选择结果回调
                    List<LocalMedia> selectListVideo = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia media : selectListVideo) {
                        sendMessage(mChatHandler.createVideoMessage(media.getPath()));
                    }
                    break;
            }
        }

    }

    /**
     * 选择文件,需要先申请文件存储权限
     * int maxCount 选择的数量
     */
    private void openFileSelector(int maxCount) {
        FileSelector.from(this)
                // .onlyShowFolder()  //只显示文件夹
                //.onlySelectFolder()  //只能选择文件夹
                // .isSingle() // 只能选择一个
                .setMaxCount(maxCount) //设置最大选择数
                .setFileTypes("png", "jpg", "doc", "docx", "apk", "mp3", "gif", "txt", "mp4", "zip") //设置文件类型
                .setSortType(FileSelector.BY_NAME_ASC) //设置名字排序
                //.setSortType(FileSelector.BY_TIME_ASC) //设置时间排序
                //.setSortType(FileSelector.BY_SIZE_DESC) //设置大小排序
                //.setSortType(FileSelector.BY_EXTENSION_DESC) //设置类型排序
                .requestCode(REQUEST_CODE_FILE) //设置返回码
                .start();
    }

    private void hideAnim() {
        mAudioTime.setText("按住录音");
        mLeftAudioAnim.setVisibility(View.GONE);
        mRightAudioAnim.setVisibility(View.GONE);
        Animatable anim = (Animatable) mLeftAudioAnim.getBackground();
        anim.stop();
        Animatable ranim = (Animatable) mRightAudioAnim.getBackground();
        ranim.stop();
    }

    private void showAnim() {
        mLeftAudioAnim.setVisibility(View.VISIBLE);
        mRightAudioAnim.setVisibility(View.VISIBLE);
        Animatable anim = (Animatable) mLeftAudioAnim.getBackground();
        anim.start();
        Animatable ranim = (Animatable) mRightAudioAnim.getBackground();
        ranim.start();
        mAudioTime.setText("0:00");
    }

    @Override
    public void recordStart() {
        showAnim();
    }

    @Override
    public void recordTime(long time) {
        if (mLeftAudioAnim.getVisibility() == View.GONE) {
            mAudioTime.setText("按住录音");
        } else {
            mAudioTime.setText(TimeDateUtils.formatRecordTime((int) time));
        }
    }

    @Override
    public void recordFail() {
        hideAnim();
        ToastUtils.showMessage(getContext(),getString(R.string.audio_fail));
    }

    @Override
    public void recordSuccess(String path, int length) {
        hideAnim();
        ToastUtils.showMessage(getContext(), path);
        mUiHelper.showTransferAudioLayout();
        mTransferAudio.setAudioLength(length);
    }

    @Override
    public void cancelRecord() {
        hideAnim();
        ToastUtils.showMessage(getContext(),getString(R.string.audio_cancel));
    }

    @Override
    public void recordLengthShort() {
        hideAnim();
        ToastUtils.showMessage(getContext(),getString(R.string.audio_short));
    }
}
