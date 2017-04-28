package com.example.sunpeng.homeworklist_mvp.data;

import java.util.List;

/**
 * Created by sunpeng on 2017/4/25.
 */

public class Homework {
    private String id;
    private String name;
    private String bedition;
    private String stageid;
    private String gradeid;
    private String buildtime;
    private String status;
    private String classid;
    private String subScore;
    private String waitFinishNum;
    private String parentid;
    private String defaultGroup;
    private List<String> students;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBedition() {
        return bedition;
    }

    public void setBedition(String bedition) {
        this.bedition = bedition;
    }

    public String getStageid() {
        return stageid;
    }

    public void setStageid(String stageid) {
        this.stageid = stageid;
    }

    public String getGradeid() {
        return gradeid;
    }

    public void setGradeid(String gradeid) {
        this.gradeid = gradeid;
    }

    public String getBuildtime() {
        return buildtime;
    }

    public void setBuildtime(String buildtime) {
        this.buildtime = buildtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getSubScore() {
        return subScore;
    }

    public void setSubScore(String subScore) {
        this.subScore = subScore;
    }

    public String getWaitFinishNum() {
        return waitFinishNum;
    }

    public void setWaitFinishNum(String waitFinishNum) {
        this.waitFinishNum = waitFinishNum;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getDefaultGroup() {
        return defaultGroup;
    }

    public void setDefaultGroup(String defaultGroup) {
        this.defaultGroup = defaultGroup;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }
}
