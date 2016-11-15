package cn.magicbeans.android.ipmanager.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.magicbeans.android.ipmanager.R;
import cn.magicbeans.android.ipmanager.module.MBIPInfo;
import cn.magicbeans.android.ipmanager.ui.MBIPActivity;
import cn.magicbeans.android.ipmanager.ui.MBIPEditActivity;
import cn.magicbeans.android.ipmanager.utils.MBIPContant;
import cn.magicbeans.android.ipmanager.utils.MBIPUtils;
import cn.magicbeans.android.ipmanager.view.MBAlertDialog;

public class MBIPAdapter extends BaseAdapter {

    private List<MBIPInfo> infos;

    private Context context;

    private LayoutInflater inflate;

    public MBIPAdapter(Context context) {
        this.context = context;
        inflate = LayoutInflater.from(context);
        infos = MBIPUtils.getInstance(context).queryData();
    }

    public void refresh() {
        infos.clear();
        infos = MBIPUtils.getInstance(context).queryData();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return infos == null ? 0 : infos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflate.inflate(R.layout.ip_item_layout, null);
            holder = new ViewHolder(context, convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.init(infos.get(position));
        return convertView;
    }

    private class ViewHolder implements View.OnClickListener {

        private TextView ipView, portView;

        private MBIPInfo info;

        public ViewHolder(final Context context, View convertView) {
            ipView = (TextView) convertView.findViewById(R.id.ip_tv);
            portView = (TextView) convertView.findViewById(R.id.port_tv);
            convertView.findViewById(R.id.edit_tv).setOnClickListener(this);
            convertView.findViewById(R.id.delete_tv).setOnClickListener(this);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setData(context, info);
                }
            });
        }

        public void init(MBIPInfo info) {
            this.info = info;
            ipView.setText(info.ip+(info.isDefeault==1?"(默认)":""));
            portView.setText(info.port);

        }

        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.edit_tv) {
                Intent editIntent = new Intent(context, MBIPEditActivity.class);
                editIntent.putExtra(MBIPContant.TYPE, MBIPContant.OPERATE.EDIT.ordinal());
                editIntent.putExtra(MBIPContant.IP, info);
                ((MBIPActivity) context).startActivityForResult(editIntent, MBIPContant.REQUEST_CODE);

            } else if (view.getId() == R.id.delete_tv) {

                MBAlertDialog dialog = new MBAlertDialog(context,R.style.mb_dialog);
                dialog.setConfirmClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MBIPUtils.getInstance(context).deleteIPPort(info);
                        refresh();
                    }
                });
                dialog.show();

            }

        }

        private void setData(Context context, MBIPInfo info) {

            MBIPUtils.getInstance(context).setDefeaultIPPort(info);

            Intent resultIntent = new Intent();
            resultIntent.putExtra(MBIPContant.IP, info);
            ((MBIPActivity) context).setResult(MBIPContant.RESULT_CODE, resultIntent);
            ((MBIPActivity) context).finish();
        }
    }
}
