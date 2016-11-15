package cn.magicbeans.android.ipmanager.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.magicbeans.android.ipmanager.R;


public class MBAlertDialog extends Dialog implements View.OnClickListener {

    private Context context;

    private TextView numView;

    public MBAlertDialog(Context context, int theme) {
        super(context, theme);
    }

    public void show(Context context, String title) {
        this.context = context;
        show();
        numView.setText(title);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_layout);
        numView = (TextView) findViewById(R.id.title);
        findViewById(R.id.submit).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cancel) {
            dismiss();
        } else if (id == R.id.submit) {
            dismiss();
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
        }

    }

    private View.OnClickListener onClickListener;

    public void setConfirmClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
