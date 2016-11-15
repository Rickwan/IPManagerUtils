package cn.magicbeans.android.ipmanager.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.magicbeans.android.ipmanager.R;
import cn.magicbeans.android.ipmanager.module.MBIPInfo;
import cn.magicbeans.android.ipmanager.utils.MBIPContant;
import cn.magicbeans.android.ipmanager.utils.MBIPUtils;

public class MBIPEditActivity extends Activity implements View.OnClickListener {

    private EditText ipView, portView;

    private TextView titleView;

    private int type;

    private MBIPInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mbipedit);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        findViewById(R.id.mb_commit_tv).setOnClickListener(this);
        findViewById(R.id.back_iv).setOnClickListener(this);
        titleView = (TextView) findViewById(R.id.mb_title_tv);
        ipView = (EditText) findViewById(R.id.ip_et);
        portView = (EditText) findViewById(R.id.port_et);
        type = getIntent().getIntExtra(MBIPContant.TYPE, MBIPContant.OPERATE.INSERT.ordinal());
        if (type == MBIPContant.OPERATE.INSERT.ordinal()) {
            titleView.setText(getString(R.string.mb_insert));
        } else if (type == MBIPContant.OPERATE.EDIT.ordinal()) {
            titleView.setText(getString(R.string.mb_edit));
            info = (MBIPInfo) getIntent().getSerializableExtra(MBIPContant.IP);
            if (info != null) {
                ipView.setText(info.ip);
                portView.setText(info.port);
                ipView.setSelection(ipView.length());
            }
        }



    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        if (v.getId() == R.id.mb_commit_tv) {
            commitData();
        }  else if (v.getId() == R.id.back_iv) {
            finish();
        }

    }

    /**
     * 保存数据
     */
    private void commitData() {

        String ip = ipView.getText().toString();

        if (TextUtils.isEmpty(ip)) {
            Toast.makeText(this, R.string.mb_empty_ip, Toast.LENGTH_SHORT).show();
            return;
        }
        String port = portView.getText().toString();

        if (TextUtils.isEmpty(port)) {
            Toast.makeText(this, R.string.mb_empty_port, Toast.LENGTH_SHORT).show();
            return;
        }

        MBIPInfo temp=null;
        if (info == null) {
            info = new MBIPInfo(ip, port, 0);
        } else {
            temp= new MBIPInfo();
            temp.setIp(ip);
            temp.setPort(port);
        }

        if (type == MBIPContant.OPERATE.INSERT.ordinal()) {
            MBIPUtils.getInstance(this).insertIPPort(info);
        } else if (type == MBIPContant.OPERATE.EDIT.ordinal()) {
            MBIPUtils.getInstance(this).updateIPPort(info, temp);
        }
        setData();
    }

    private void setData() {
        Intent resultIntent = new Intent();
        setResult(MBIPContant.RESULT_CODE, resultIntent);
        finish();
    }

    /**
     * 关闭软键盘
     */
    private void hideKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(ipView.getWindowToken(), 0);
    }
}