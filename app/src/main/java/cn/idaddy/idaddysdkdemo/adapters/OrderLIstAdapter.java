package cn.idaddy.idaddysdkdemo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.idaddy.idaddysdkdemo.R;

public class OrderLIstAdapter extends BaseAdapter{
    public List<String> datas = new ArrayList<>();
    private LayoutInflater inflater;


    public OrderLIstAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void refresh(List<String> datas){
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public String getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.order_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.order_info);
            holder.textView.setText(datas.get(position));
            convertView.setTag(holder);
        } else {
            //直接通过holder获取下面三个子控件，不必使用findviewbyid，加快了 UI 的响应速度
            holder = (ViewHolder) convertView.getTag();
            holder.textView.setText(datas.get(position));
        }
        return convertView;
    }


    static class ViewHolder{
        TextView textView;
    }
}
