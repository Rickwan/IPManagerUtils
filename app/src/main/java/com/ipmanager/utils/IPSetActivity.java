package com.ipmanager.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import cn.magicbeans.android.ipmanager.module.MBIPInfo;
import cn.magicbeans.android.ipmanager.ui.MBIPActivity;
import cn.magicbeans.android.ipmanager.utils.MBIPContant;
import cn.magicbeans.android.ipmanager.utils.MBIPUtils;


public class IPSetActivity extends Activity {

    private TextView ipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipset);

        ipView = (TextView) findViewById(R.id.ip_tv);
        findViewById(R.id.set_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IPSetActivity.this, MBIPActivity.class);
                startActivityForResult(intent, MBIPContant.REQUEST_CODE);
            }
        });


        String ip = MBIPUtils.getInstance(this).getIPPort();

        if (TextUtils.isEmpty(ip)) {
            ipView.setText("默认IP地址为空");
        } else {
            ipView.setText("默认IP地址:" + ip);
        }

        findViewById(R.id.set_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer.parseInt("0.1");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == MBIPContant.RESULT_CODE) {

            MBIPInfo info = (MBIPInfo) data.getSerializableExtra(MBIPContant.IP);
            ipView.setText("新IP地址：" + info.ip + ":" + info.port);


        }
    }
}
