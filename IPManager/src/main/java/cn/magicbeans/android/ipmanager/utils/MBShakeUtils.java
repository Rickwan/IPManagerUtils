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

import java.lang.ref.SoftReference;

import cn.magicbeans.android.ipmanager.ui.MBIPActivity;

/**
 * author 万强
 * date 17/2/9 上午11:13
 * desc ${TODO}
 */
public class MBShakeUtils implements SensorEventListener {

    private SoftReference<Activity> reference;

    private SensorManager mSensorManager;

    private Sensor mAccelerometerSensor;

    private Vibrator mVibrator;

    private boolean isShown;

    private long mShakeTime;

    private int mShakeCount;

    private static MBShakeUtils mbShakeUtils;

    public static MBShakeUtils getInstance(Activity activity) {

        if (mbShakeUtils == null) {
            mbShakeUtils = new MBShakeUtils(activity);
        }

        return mbShakeUtils;
    }

    private MBShakeUtils(Activity activity) {
      reference = new SoftReference<>(activity);

    }

    public void init() {

        mVibrator = (Vibrator) reference.get().getSystemService(Context.VIBRATOR_SERVICE);
        //获取 SensorManager 负责管理传感器
        mSensorManager = ((SensorManager) reference.get().getSystemService(Context.SENSOR_SERVICE));
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

        if (mVibrator != null) {
            mVibrator.cancel();
        }


        if (reference!=null){
            reference.clear();
        }

        mbShakeUtils = null;

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
                    .abs(z) > 17) && !isShown) {

                if (System.currentTimeMillis() - mShakeTime > 2000) {
                    mShakeCount = 0;
                } else {
                    mShakeCount++;
                }
                mShakeTime = System.currentTimeMillis();

                if (mShakeCount < 3) {
                    return;
                }
                isShown = true;
                showConfirmDialog();
                mVibrator.vibrate(700);

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private void showConfirmDialog() {
        new AlertDialog.Builder(reference.get())
                .setMessage("是否前往设置IP?")
                .setTitle("提示")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reset();
                        Intent intent = new Intent(reference.get(), MBIPActivity.class);
                        reference.get().startActivityForResult(intent, MBIPContant.REQUEST_CODE);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        reset();
                    }
                }).show();
    }


    private void reset() {

        mShakeCount = 0;
        isShown = false;
    }


}
