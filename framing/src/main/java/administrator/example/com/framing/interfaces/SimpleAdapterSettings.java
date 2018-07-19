package administrator.example.com.framing.interfaces;

import android.view.View;
import administrator.example.com.framing.BaseAdapter;


public interface SimpleAdapterSettings {
    Object setViewHolder(View convertView);
    
    void setData(Object viewHolder, BaseAdapter.BaseDataBean dataBean);
}
