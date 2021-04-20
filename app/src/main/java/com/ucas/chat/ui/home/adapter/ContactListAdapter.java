package com.ucas.chat.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ucas.chat.R;
import com.ucas.chat.bean.contact.ContactListBean;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    protected Context mContext;
    protected List<ContactListBean> mDatas;
    protected LayoutInflater mInflater;

    public ContactListAdapter(Context mContext, List<ContactListBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    public List<ContactListBean> getDatas() {
        return mDatas;
    }

    public ContactListAdapter setDatas(List<ContactListBean> datas) {
        mDatas = datas;
        return this;
    }

    @NonNull
    @Override
    public ContactListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_contact_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.ViewHolder holder, final int position) {
        final ContactListBean bean = mDatas.get(position);
        holder.tvName.setText(bean.getName());
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "pos:" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView headIcon;
        View content;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            headIcon = (ImageView) itemView.findViewById(R.id.ivAvatar);
            content = itemView.findViewById(R.id.content);
        }
    }
}
