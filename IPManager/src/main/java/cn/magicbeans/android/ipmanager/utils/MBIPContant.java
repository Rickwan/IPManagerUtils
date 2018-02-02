package cn.magicbeans.android.ipmanager.utils;


public class MBIPContant {

    public static  int  RESULT_CODE = 1000;

    public static  int  REQUEST_CODE = 2000;

    /**
     * 数据库名
     */
    public final static String DB_NAME ="IP_UTIL.db";

    /**
     * 表名
     */
    public final static String TABLE_NAME ="ips";

    /**
     * 操作类型
     */
    public final static  String  TYPE = "type";

    /**
     * IP
     */
    public final static  String  IP = "ip";
    /**
     * 端口
     */
    public final static  String  PORT = "port";

    /**
     * 是否为默认IP
     */
    public final static  String  IS_DEFEAULT = "isDefeault";


    /**
     * 是否为域名
     */
    public final static  String  IS_DOMAIN = "isDomain";

    /**
     * 数据操作
     */
    public enum OPERATE{
        //插入、编辑、删除
        INSERT,EDIT,DELETE
    }


}
