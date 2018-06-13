package com.oliveoa.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
public class OvertimeApplication {
    private String oaid;
    private String eid;
    private String reason;
    private long begintime;
    private long endtime;
    private int orderby;
    private long createtime;
    private long updatetime;

    public OvertimeApplication() {
    }

    public OvertimeApplication(String oaid, String eid, String reason, long begintime, long endtime, int orderby, long createtime, long updatetime) {
        this.oaid = oaid;
        this.eid = eid;
        this.reason = reason;
        this.begintime = begintime;
        this.endtime = endtime;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public String getOaid() {
        return oaid;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
        return "OvertimeApplication{" +
                "oaid='" + oaid + '\'' +
                ", eid='" + eid + '\'' +
                ", reason='" + reason + '\'' +
                ", begintime='" + begintime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }
}
