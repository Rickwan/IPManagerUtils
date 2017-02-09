package com.ipmanager.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.magicbeans.android.ipmanager.module.MBIPInfo;
import cn.magicbeans.android.ipmanager.ui.MBIPActivity;
import cn.magicbeans.android.ipmanager.utils.MBIPContant;
import cn.magicbeans.android.ipmanager.utils.MBIPUtils;
import cn.magicbeans.android.ipmanager.utils.MBShakeUtils;


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
                setIP();
            }
        });

        String ip = MBIPUtils.getInstance(this).getIPPort();

        if (TextUtils.isEmpty(ip)) {
            ipView.setText("默认IP地址为空");
        } else {
            ipView.setText("默认IP地址:" + ip);
        }

    }

    private void setIP() {
        Intent intent = new Intent(IPSetActivity.this, MBIPActivity.class);
        startActivityForResult(intent, MBIPContant.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == MBIPContant.RESULT_CODE) {

            MBIPInfo info = (MBIPInfo) data.getSerializableExtra(MBIPContant.IP);
            ipView.setText("新IP地址：" + info.ip + ":" + info.port);


        }
    }

    MBShakeUtils shakeUtils;

    @Override
    protected void onStart() {
        super.onStart();
       initShakeConfige();
    }

    @Override
    protected void onStop() {
        super.onStop();
        shakeUtils.unRegister();
    }


    private void initShakeConfige(){
        shakeUtils = new MBShakeUtils();
        shakeUtils.init(this, new MBShakeUtils.OnShakeListener() {
            @Override
            public void onShaked() {

                new AlertDialog.Builder(IPSetActivity.this)
                        .setMessage("是否前往设置IP?")
                        .setTitle("提示")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setIP();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }
}
