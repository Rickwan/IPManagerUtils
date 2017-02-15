package cn.magicbeans.android.ipmanager.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

import cn.magicbeans.android.ipmanager.ui.MBIPActivity;

/**
 * author 万强
 * date 17/2/9 上午11:13
 * desc ${TODO}
 */
public class MBShakeUtils implements SensorEventListener {

    private Activity mContext;

    private SensorManager mSensorManager;

    private Sensor mAccelerometerSensor;

    private Vibrator vibrator;

    private boolean isShake;

    public MBShakeUtils(Activity activity) {

        this.mContext = activity;
    }

    public void init() {

        vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        //获取 SensorManager 负责管理传感器
        mSensorManager = ((SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE));
        if (mSensorManager != null) {
            //获取加速度传感器
            mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (mAccelerometerSensor != null) {
                mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    public void unRegister() {

        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }

        if (vibrator != null) {
            vibrator.cancel();
        }


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        if (type == Sensor.TYPE_ACCELEROMETER) {
            //获取三个方向值
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            if ((Math.abs(x) > 17 || Math.abs(y) > 17 || Math
                    .abs(z) > 17) && !isShake) {
                isShake = true;

                showConfirmDialog();
                vibrator.vibrate(700);
//                vibrator.vibrate(new long[]{10,500,100,200},2);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {

                            //开始震动 发出提示音 展示动画效果
//                            mHandler.obtainMessage(START_SHAKE).sendToTarget();
                            Thread.sleep(500);
                            //再来一次震动提示
//                            mHandler.obtainMessage(AGAIN_SHAKE).sendToTarget();
//                            Thread.sleep(500);
//                            mHandler.obtainMessage(END_SHAKE).sendToTarget();
                            isShake = false;

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private void showConfirmDialog() {
        new AlertDialog.Builder(mContext)
                .setMessage("是否前往设置IP?")
                .setTitle("提示")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(mContext, MBIPActivity.class);
                        mContext.startActivityForResult(intent, MBIPContant.REQUEST_CODE);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                }).show();
    }


}