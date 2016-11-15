package cn.magicbeans.android.ipmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;


import java.util.ArrayList;
import java.util.List;

import cn.magicbeans.android.ipmanager.module.MBIPInfo;
import cn.magicbeans.android.ipmanager.utils.MBIPContant;

/**
 * Created by Administrator on 2015/10/12 0012.
 */
public class MBIPDao {
    MBDBHelper helper = null;

    public MBIPDao(Context cxt) {
        helper = new MBDBHelper(cxt);
    }

    public MBIPDao(Context cxt, int version) {
        helper = new MBDBHelper(cxt, version);
    }

    /**
     * 保存IP信息
     *
     * @param ipInfo
     */
    public void insert(MBIPInfo ipInfo) {

        if (ipInfo == null || isMBIPInfoExist(ipInfo)) {
            return;
        }
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MBIPContant.IP, ipInfo.ip);
        cv.put(MBIPContant.PORT, ipInfo.port);
        cv.put(MBIPContant.IS_DEFEAULT, ipInfo.isDefeault);
        db.insert(MBIPContant.TABLE_NAME, null, cv);
        db.close();
    }

    /**
     * 更新IP信息
     *
     * @param oldipInfo
     * @param ipInfo
     */
    public void update(MBIPInfo oldipInfo,MBIPInfo ipInfo) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MBIPContant.IP, ipInfo.ip);
        cv.put(MBIPContant.PORT, ipInfo.port);
        cv.put(MBIPContant.IS_DEFEAULT, ipInfo.isDefeault);
        db.update(MBIPContant.TABLE_NAME, cv, MBIPContant.IP + " =? and " + MBIPContant.PORT + " =?", new String[]{oldipInfo.ip, oldipInfo.port});
        db.close();

    }

    /**
     * 设置是否为默认
     * @param ipInfo
     */
    public void changeState(MBIPInfo ipInfo){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MBIPContant.IP, ipInfo.ip);
        cv.put(MBIPContant.PORT, ipInfo.port);
        cv.put(MBIPContant.IS_DEFEAULT, ipInfo.isDefeault==0?1:0);
        db.update(MBIPContant.TABLE_NAME, cv, MBIPContant.IP + " =? and " + MBIPContant.PORT + "=?", new String[]{ipInfo.ip,ipInfo.port});
        db.close();
    }
    /**
     * 删除IP信息
     *
     * @param ipInfo
     */
    public void delete(MBIPInfo ipInfo) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(MBIPContant.TABLE_NAME, MBIPContant.IP + " =? and " + MBIPContant.PORT + "=?", new String[]{ipInfo.ip, ipInfo.port});
        db.close();
    }

    /**
     * 查询所有IP信息
     *
     * @return
     */
    public List<MBIPInfo> queryData() {
        List<MBIPInfo> infos;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + MBIPContant.TABLE_NAME, null);
        if (cursor == null) {
            db.close();
            return null;
        }
        infos = new ArrayList<>();
        while (cursor.moveToNext()) {
            String ip = cursor.getString(cursor.getColumnIndex(MBIPContant.IP));
            String port = cursor.getString(cursor.getColumnIndex(MBIPContant.PORT));
            int isdefeault = cursor.getInt(cursor.getColumnIndex(MBIPContant.IS_DEFEAULT));
            MBIPInfo info = new MBIPInfo(ip, port, isdefeault);
            infos.add(info);
        }
        cursor.close();
        db.close();
        return infos;
    }

    /**
     * 查询默认IP
     *
     * @return
     */
    public MBIPInfo gueryDefeaultIPInfo() {
        MBIPInfo info = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + MBIPContant.TABLE_NAME, null);
        if (cursor == null) {
            db.close();
            return null;
        }
        while (cursor.moveToNext()) {
            String ip = cursor.getString(cursor.getColumnIndex(MBIPContant.IP));
            String port = cursor.getString(cursor.getColumnIndex(MBIPContant.PORT));
            int isdefeault = cursor.getInt(cursor.getColumnIndex(MBIPContant.IS_DEFEAULT));
            if (isdefeault == 1) {
                info = new MBIPInfo(ip, port, isdefeault);
                break;
            }
        }
        cursor.close();
        db.close();
        return info;
    }


    /**
     * 查询是否存在
     * @param info
     * @return
     */
    public boolean isMBIPInfoExist(MBIPInfo info) {
        boolean isExist = false;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + MBIPContant.TABLE_NAME, null);
        if (cursor == null) {
            db.close();
            return isExist;
        }
        while (cursor.moveToNext()) {
            String ip = cursor.getString(cursor.getColumnIndex(MBIPContant.IP));
            String port = cursor.getString(cursor.getColumnIndex(MBIPContant.PORT));
            if (TextUtils.equals(ip, info.ip) && TextUtils.equals(port, info.port)) {
                isExist = true;
                break;
            }
        }
        cursor.close();
        db.close();
        return isExist;
    }

}