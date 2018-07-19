package administrator.example.com.framing.interfaces;

import android.view.View;

import administrator.example.com.framing.BaseAdapter;

public interface MultipleAdapterSettings {
    Object setViewHolder(int type, View convertView);
    
    void setData(int type, Object viewHolder, BaseAdapter.BaseDataBean dataBean);
}