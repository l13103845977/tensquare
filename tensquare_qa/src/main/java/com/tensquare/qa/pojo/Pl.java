package com.tensquare.qa.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="tb_pl")
public class Pl implements Serializable {
    @Id
    private  String  problemid;


    private  String  labelid;

    public Pl() {
    }

    public Pl(String problemid, String labelid) {
        this.problemid = problemid;
        this.labelid = labelid;
    }

    public String getProblemid() {
        return problemid;
    }

    public void setProblemid(String problemid) {
        this.problemid = problemid;
    }

    public String getLabelid() {
        return labelid;
    }

    public void setLabelid(String labelid) {
        this.labelid = labelid;
    }

    @Override
    public String toString() {
        return "Pl{" +
                "problemid='" + problemid + '\'' +
                ", labelid='" + labelid + '\'' +
                '}';
    }
}
