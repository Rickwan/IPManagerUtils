package cn.magicbeans.android.ipmanager.module;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/12 0012.
 */
public class MBIPInfo implements Serializable{

    /**
     * IP
     */
    public String ip;

    /**
     * 端口
     */
    public String port;

    /**
     * 默认IP 0 false ，1 true
     */
    public int isDefeault;



    public MBIPInfo(){}

    public MBIPInfo(String ip, String port){
        this.ip = ip;
        this.port = port;
    }

    public MBIPInfo(String ip, String port, int isDefeault){
        this.ip = ip;
        this.port = port;
        this.isDefeault= isDefeault;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getIsDefeault() {
        return isDefeault;
    }

    public void setIsDefeault(int isDefeault) {
        this.isDefeault = isDefeault;
    }
}
