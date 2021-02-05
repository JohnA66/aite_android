package com.haoniu.quchat.entity;

import java.io.Serializable;
import java.util.List;

public class MultiDevice implements Serializable{
    public String address;        // "广东省,揭阳市",
    public String did;            // "9E93DEF0069566E01132826994EEC2F595188F19",
    public String dname;          // "HUAWEI MHA-AL00",
    public String ip;             // "113.103.106.222",
    public String os;             // "Android",
    public long ut;             // 1607150660000
    public List<Device> devList;        // [],

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public long getUt() {
        return ut;
    }

    public void setUt(long ut) {
        this.ut = ut;
    }

    public List<Device> getDevList() {
        return devList;
    }

    public void setDevList(List<Device> devList) {
        this.devList = devList;
    }

    public static class Device implements Serializable {
        public String address;        // "广东省,揭阳市",
        public String did;            // "9E93DEF0069566E01132826994EEC2F595188F19",
        public String dname;          // "HUAWEI MHA-AL00",
        public String ip;             // "113.103.106.222",
        public String os;             // "Android",
        public long ut;             // 1607150660000

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDid() {
            return did;
        }

        public void setDid(String did) {
            this.did = did;
        }

        public String getDname() {
            return dname;
        }

        public void setDname(String dname) {
            this.dname = dname;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public long getUt() {
            return ut;
        }

        public void setUt(long ut) {
            this.ut = ut;
        }
    }
}
