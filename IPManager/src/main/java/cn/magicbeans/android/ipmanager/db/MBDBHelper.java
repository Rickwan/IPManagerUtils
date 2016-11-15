package cn.magicbeans.android.ipmanager.db;

/**
 * Created by Administrator on 2015/10/12 0012.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import cn.magicbeans.android.ipmanager.utils.MBIPContant;


public class MBDBHelper extends SQLiteOpenHelper {

    private final static int VERSION = 1;//版本号

    /**
     * 自带的构造方法
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public MBDBHelper(Context context, String name, CursorFactory factory,
                      int version) {
        super(context, name, factory, version);
    }

    /**
     * 为了每次构造时不用传入dbName和版本号，自己得新定义一个构造方法
     * @param cxt
     */
    public MBDBHelper(Context cxt) {
        this(cxt, MBIPContant.DB_NAME, null, VERSION);//调用上面的构造方法
    }

    /**
     * 版本变更时
     * @param cxt
     * @param version
     */
    public MBDBHelper(Context cxt, int version) {
        this(cxt, MBIPContant.DB_NAME, null, version);
    }

    /**
     * 当数据库创建的时候调用
     * @param db
     */
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + MBIPContant.TABLE_NAME +
                "(id integer primary key autoincrement," +
                MBIPContant.IP + " text," +
                MBIPContant.PORT + " text," +
                MBIPContant.IS_DEFEAULT + " interger)";

        db.execSQL(sql);
    }

    /**
     * 版本更新时调用
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "update " + MBIPContant.TABLE_NAME + " ....";//自己的Update操作
        db.execSQL(sql);
    }

}