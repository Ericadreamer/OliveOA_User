package com.oliveoa.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Transient;

@Entity@Keep
public class LeaveApplication {

    private String laid;
    private String eid;
    private long begintime;
    private long endtime;
    private String reason;
    private int type;
    private long normalRest;
    private long swapRest;
    private long shouldRest;
    private long supplementRest;
    private int orderby;
    @Transient
    private long createtime;
    @Transient
    private long updatetime;

    public LeaveApplication() {
    }

    public LeaveApplication(String laid, String eid, long begintime, long endtime, String reason, int type, long normalRest, long swapRest, long shouldRest, long supplementRest, int orderby) {
        this.laid = laid;
        this.eid = eid;
        this.begintime = begintime;
        this.endtime = endtime;
        this.reason = reason;
        this.type = type;
        this.normalRest = normalRest;
        this.swapRest = swapRest;
        this.shouldRest = shouldRest;
        this.supplementRest = supplementRest;
        this.orderby = orderby;
    }

    public LeaveApplication(String laid, String eid, long begintime, long endtime, String reason, int type, long normalRest, long swapRest, long shouldRest, long supplementRest, int orderby, long createtime, long updatetime) {
        this.laid = laid;
        this.eid = eid;
        this.begintime = begintime;
        this.endtime = endtime;
        this.reason = reason;
        this.type = type;
        this.normalRest = normalRest;
        this.swapRest = swapRest;
        this.shouldRest = shouldRest;
        this.supplementRest = supplementRest;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public String getLaid() {
        return laid;
    }

    public void setLaid(String laid) {
        this.laid = laid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public long getBegintime() {
        return begintime;
    }

    public void setBegintime(long begintime) {
        this.begintime = begintime;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getNormalRest() {
        return normalRest;
    }

    public void setNormalRest(long normalRest) {
        this.normalRest = normalRest;
    }

    public long getSwapRest() {
        return swapRest;
    }

    public void setSwapRest(long swapRest) {
        this.swapRest = swapRest;
    }

    public long getShouldRest() {
        return shouldRest;
    }

    public void setShouldRest(long shouldRest) {
        this.shouldRest = shouldRest;
    }

    public long getSupplementRest() {
        return supplementRest;
    }

    public void setSupplementRest(long supplementRest) {
        this.supplementRest = supplementRest;
    }

    public int getOrderby() {
        return orderby;
    }

    public void setOrderby(int orderby) {
        this.orderby = orderby;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        return "LeaveApplication{" +
                "laid='" + laid + '\'' +
                ", eid='" + eid + '\'' +
                ", begintime=" + begintime +
                ", endtime=" + endtime +
                ", reason='" + reason + '\'' +
                ", type=" + type +
                ", normalRest=" + normalRest +
                ", swapRest=" + swapRest +
                ", shouldRest=" + shouldRest +
                ", supplementRest=" + supplementRest +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }
}
