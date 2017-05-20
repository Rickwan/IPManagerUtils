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

/**
 * IP设置
 */
public class MBIPEditActivity extends Activity implements View.OnClickListener {

    private EditText ipView1, ipView2, ipView3, ipView4, portView;

    private TextView titleView;

    private int type;

    private MBIPInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_edit);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {

        findViewById(R.id.mb_commit_tv).setOnClickListener(this);
        findViewById(R.id.back_iv).setOnClickListener(this);
        titleView = (TextView) findViewById(R.id.title_tv);
        ipView1 = (EditText) findViewById(R.id.ip_et1);
        ipView2 = (EditText) findViewById(R.id.ip_et2);
        ipView3 = (EditText) findViewById(R.id.ip_et3);
        ipView4 = (EditText) findViewById(R.id.ip_et4);
        portView = (EditText) findViewById(R.id.port_et);

        type = getIntent().getIntExtra(MBIPContant.TYPE, MBIPContant.OPERATE.INSERT.ordinal());
        if (type == MBIPContant.OPERATE.INSERT.ordinal()) {
            titleView.setText(getString(R.string.mb_insert));
        } else if (type == MBIPContant.OPERATE.EDIT.ordinal()) {
            titleView.setText(getString(R.string.mb_edit));
            info = (MBIPInfo) getIntent().getSerializableExtra(MBIPContant.IP);
            if (info != null) {

                String[] ips = info.ip.split("\\.");
                if (ips != null && ips.length == 4) {
                    ipView1.setText(ips[0] + "".replace(".", ""));
                    ipView2.setText(ips[1] + "".replace(".", ""));
                    ipView3.setText(ips[2] + "".replace(".", ""));
                    ipView4.setText(ips[3] + "".replace(".", ""));

                }
                portView.setText(info.port);
            }
        }

        ipView1.setSelection(ipView1.length());

    }


    @Override
    public void onClick(View v) {
        hideKeyboard();
        if (v.getId() == R.id.mb_commit_tv) {
            commitData();
        } else if (v.getId() == R.id.back_iv) {
            finish();
        }

    }

    /**
     * 保存数据
     */
    private void commitData() {

        String ip1 = ipView1.getText().toString();
        String ip2 = ipView2.getText().toString();
        String ip3 = ipView3.getText().toString();
        String ip4 = ipView4.getText().toString();


        if (TextUtils.isEmpty(ip1) || TextUtils.isEmpty(ip2) || TextUtils.isEmpty(ip3) || TextUtils.isEmpty(ip4)) {
            Toast.makeText(this, R.string.mb_empty_ip, Toast.LENGTH_SHORT).show();
            return;
        }
        String port = portView.getText().toString();

        if (TextUtils.isEmpty(port)) {
            Toast.makeText(this, R.string.mb_empty_port, Toast.LENGTH_SHORT).show();
            return;
        }

        MBIPInfo temp = null;
        if (info == null) {
            info = new MBIPInfo(ip1 + "." + ip2 + "." + ip3 + "." + ip4, port, 0);
        } else {
            temp = new MBIPInfo();
            temp.setIp(ip1 + "." + ip2 + "." + ip3 + "." + ip4);
            temp.setPort(port);
            temp.setIsDefeault(info.isDefeault);
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
        inputMethodManager.hideSoftInputFromWindow(portView.getWindowToken(), 0);
    }


}
