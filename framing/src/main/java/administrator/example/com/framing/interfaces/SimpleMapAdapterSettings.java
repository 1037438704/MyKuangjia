package administrator.example.com.framing.interfaces;

import android.view.View;
import java.util.Map;

public interface SimpleMapAdapterSettings {
    Object setViewHolder(View convertView);
    
    void setData(Object viewHolder, Map<String, Object> data);
}
