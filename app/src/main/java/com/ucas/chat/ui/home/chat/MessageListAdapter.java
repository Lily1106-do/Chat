package com.ucas.chat.ui.home.chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.joooonho.SelectableRoundedImageView;
import com.ucas.chat.R;
import com.ucas.chat.bean.UserBean;
import com.ucas.chat.bean.session.ImageAttachment;
import com.ucas.chat.bean.session.MsgDirectionEnum;
import com.ucas.chat.bean.session.MsgTypeEnum;
import com.ucas.chat.bean.session.VideoAttachment;
import com.ucas.chat.bean.session.message.AudioAttachment;
import com.ucas.chat.bean.session.message.FileAttachment;
import com.ucas.chat.bean.session.message.IMMessage;
import com.ucas.chat.ui.download.DownloadActivity;
import com.ucas.chat.ui.listener.OnItemClickListener;
import com.ucas.chat.ui.view.chat.RViewHolder;
import com.ucas.chat.utils.ChatUtils;
import com.ucas.chat.utils.GlideUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;


public class MessageListAdapter extends RecyclerView.Adapter<RViewHolder> {

    //好友消息
    private static final int MSG_TEXT_L = 0x20000;
    private static final int MSG_IMG_L = 0x20001;
    private static final int MSG_AUDIO_L = 0x20002;
    private static final int MSG_VIDEO_L = 0x20003;
    private static final int MSG_FILE_L = 0X20004;
    //本人消息
    private static final int MSG_TEXT_R = 0x30000;
    private static final int MSG_IMG_R = 0x30001;
    private static final int MSG_AUDIO_R = 0x30002;
    private static final int MSG_VIDEO_R = 0x30003;
    private static final int MSG_FILE_R = 0X30004;

    private Context mContext;
    private LayoutInflater mInflater;
    private ChatUtils mChatUtils;
    private List<IMMessage> mMessageList;
    private SimpleDateFormat mDateFormat;

    private OnItemClickListener mItemClickListener;

    public MessageListAdapter(Context context, List<IMMessage> messages) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mMessageList = messages;
        mChatUtils = new ChatUtils(context);
        mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
    }

    @Override
    public int getItemViewType(int position) {
        return getViewLayoutId(getMsgViewType(mMessageList.get(position - 1).getDirect(),
                mMessageList.get(position - 1).getMsgType()));
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int layoutId) {
        View view = mInflater.inflate(layoutId, parent, false);
        return new RViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(RViewHolder holder, int position) {
        bindMsgView(holder, mMessageList.get(position - 1));
//        if (mMessageList.get(position - 1).getUuid() == null) {
//            String time = mDateFormat.format(new Date(mMessageList.get(position - 1).getTime()));
//            holder.setText(R.id.tv_msg_time, time);
//        } else {
//            bindMsgView(holder, mMessageList.get(position - 1));
//        }
    }

    private void bindMsgView(final RViewHolder holder, final IMMessage message) {

        ImageView headView = holder.getImageView(R.id.iv_head_picture);
        // 设置头像
        if (message.getDirect() == MsgDirectionEnum.In) {

//            ImageUtils.setImageByUrl(mContext, headView, mChatSession.getChatInfo().getAvatar(),
//                    R.mipmap.app_logo);

            // 设置好友头像点击事件--打开好友信息界面
            headView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



//                    Intent intent = new Intent(mContext, FriendInfoActivity.class);
//                    intent.putExtra("NimUserInfo", mChatSession.getChatInfo());
//                    intent.putExtra("FLAG",FriendInfoActivity.FLAG_SHOW_FRIEND);
//                    mContext.startActivity(intent);
                }
            });

        } else {
            UserBean bean = new UserBean();
            headView.setImageResource(bean.getImPhoto());
        }

        // 根据消息状态和附件传输状态决定是否显示progress bar
        if (mChatUtils.isTransferring(message)) {
            holder.setVisible(R.id.progress_status, true);
        } else {
            holder.setVisible(R.id.progress_status, false);
        }

        // 根据类型绑定数据
        int viewType = getMsgViewType(message.getDirect(), message.getMsgType());
        switch (viewType) {
            // 文本
            case MSG_TEXT_L:
            case MSG_TEXT_R:
                TextView textView = holder.getTextView(R.id.tv_chat_msg);
                textView.setText(message.getContent());
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemClickListener != null){
                            mItemClickListener.onItemClick(holder,message);
                        }
                    }
                });
                break;

            // 图像
            case MSG_IMG_L:
            case MSG_IMG_R:
                ImageAttachment imageAttachment = (ImageAttachment) message.getAttachment();
                final SelectableRoundedImageView imageView = (SelectableRoundedImageView)
                        holder.getImageView(R.id.iv_msg_img);

                if (TextUtils.isEmpty(imageAttachment.getThumbPath() )){
                    GlideUtils.loadChatImage(mContext,imageAttachment.getPath(), imageView);
                }else{
                    File file = new File(imageAttachment.getThumbPath());
                    if (file.exists()) {
                        GlideUtils.loadChatImage(mContext,imageAttachment.getThumbPath(), imageView);
                    }else {
                        GlideUtils.loadChatImage(mContext,imageAttachment.getPath(),imageView);
                    }
                }
              //  Bitmap bitmap = mChatUtils.getBitmap(imageAttachment);
//                if (bitmap != null){
//                    imageView.setImageBitmap(bitmap);
//                }else {
//                    imageView.setImageResource(R.mipmap.bg_img_defalut);
//                }
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemClickListener != null){
                            mItemClickListener.onItemClick(holder,message);
                        }
                    }
                });
                break;

            // 音频
            case MSG_AUDIO_L:
            case MSG_AUDIO_R:
                AudioAttachment audioAttachment = (AudioAttachment) message.getAttachment();
                holder.setText(R.id.tv_audio_time, mChatUtils.getAudioTime(audioAttachment.getDuration()));
                RelativeLayout layout = holder.getReltiveLayout(R.id.layout_audio_msg);
                mChatUtils.setAudioLayoutWidth(layout, audioAttachment.getDuration());

                holder.getReltiveLayout(R.id.layout_audio_msg)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mItemClickListener != null){
                                    mItemClickListener.onItemClick(holder,message);
                                }
                            }
                        });
                break;

            // 视频
            case MSG_VIDEO_L:
            case MSG_VIDEO_R:
                VideoAttachment videoAttachment = (VideoAttachment) message.getAttachment();
                Bitmap videoCover = mChatUtils.getVideoCover(videoAttachment);
                SelectableRoundedImageView roundedImageView =
                        (SelectableRoundedImageView) holder.getImageView(R.id.iv_video_cover);
                if (videoCover != null) {
                    roundedImageView.setImageBitmap(videoCover);
                } else {
                    roundedImageView.setImageResource(R.mipmap.bg_img_defalut);
                }
                ImageView play = holder.getImageView(R.id.iv_btn_play);
                if (mChatUtils.isTransferring(message)) {
                    play.setVisibility(View.GONE);
                } else {
                    play.setVisibility(View.VISIBLE);
                    play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mItemClickListener != null){
                                mItemClickListener.onItemClick(holder,message);
                            }
                        }
                    });
                }
                holder.setText(R.id.tv_video_time,
                        mChatUtils.getVideoTime(videoAttachment.getDuration()));
                break;
            case MSG_FILE_L:
            case MSG_FILE_R:
                final FileAttachment fileAttachment = (FileAttachment) message.getAttachment();
                LinearLayout linearLayout = holder.getConvertView().findViewById(R.id.rc_message);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,DownloadActivity.class);
                        intent.putExtra("fileName",fileAttachment.getDisplayName());
                        mContext.startActivity(intent);
                    }
                });
                break;
        }
    }

    private int getViewLayoutId(int viewType) {
        switch (viewType) {
            // 收到的消息
            case MSG_TEXT_L:
                return R.layout.item_msg_text_left;
            case MSG_IMG_L:
                return R.layout.item_msg_img_left;
            case MSG_AUDIO_L:
                return R.layout.item_msg_audio_left;
            case MSG_VIDEO_L:
                return R.layout.item_msg_video_left;
            case MSG_FILE_L:
                return R.layout.item_msg_file_left;

            // 发出的消息
            case MSG_TEXT_R:
                return R.layout.item_msg_text_right;
            case MSG_IMG_R:
                return R.layout.item_msg_img_right;
            case MSG_AUDIO_R:
                return R.layout.item_msg_audio_right;
            case MSG_VIDEO_R:
                return R.layout.item_msg_video_right;
            case MSG_FILE_R:
                return R.layout.item_msg_file_right;
            default:
                return R.layout.item_msg_list_time;

        }
    }

    private int getMsgViewType(MsgDirectionEnum direct, MsgTypeEnum type) {

        // 收到的消息，头像显示在 left
        if (direct == MsgDirectionEnum.In) {
            if (type == MsgTypeEnum.text) {
                return MSG_TEXT_L;
            } else if (type == MsgTypeEnum.image) {
                return MSG_IMG_L;
            } else if (type == MsgTypeEnum.audio) {
                return MSG_AUDIO_L;
            } else if (type == MsgTypeEnum.video) {
                return MSG_VIDEO_L;
            } else if (type == MsgTypeEnum.file){
                return MSG_FILE_L;
            }else {
                return 0;
            }
        } else { // 发出的消息,头像显示在右边
            if (type == MsgTypeEnum.text) {
                return MSG_TEXT_R;
            } else if (type == MsgTypeEnum.image) {
                return MSG_IMG_R;
            } else if (type == MsgTypeEnum.audio) {
                return MSG_AUDIO_R;
            } else if (type == MsgTypeEnum.video) {
                return MSG_VIDEO_R;
            } else if (type == MsgTypeEnum.file){
                return MSG_FILE_R;
            }else {
                return 0;
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mItemClickListener = listener;
    }

}
