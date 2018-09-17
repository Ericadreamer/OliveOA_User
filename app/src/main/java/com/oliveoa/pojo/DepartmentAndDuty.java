package com.oliveoa.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Keep
@Entity
public class DepartmentAndDuty {
    private String dcid;
    private String dpname;
    private String pcid;
    private String pname;

    public DepartmentAndDuty() {
    }

    public DepartmentAndDuty(String dcid, String dpname, String pcid, String pname) {
        this.dcid = dcid;
        this.dpname = dpname;
        this.pcid = pcid;
        this.pname = pname;
    }

    public String getDcid() {
        return dcid;
    }

    public void setDcid(String dcid) {
        this.dcid = dcid;
    }

    public String getDpname() {
        return dpname;
    }

    public void setDpname(String dpname) {
        this.dpname = dpname;
    }

    public String getPcid() {
        return pcid;
    }

    public void setPcid(String pcid) {
        this.pcid = pcid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @Override
    public String toString() {
        return "DepartmentAndDuty{" +
                "dcid='" + dcid + '\'' +
                ", dpname='" + dpname + '\'' +
                ", pcid='" + pcid + '\'' +
                ", pname='" + pname + '\'' +
                '}';
    }
}
