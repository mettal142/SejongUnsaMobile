package com.example.dikid.unsa_m;

public class UserData {
    String ID;
    String Name;
    String Num;
    String Dep;
    String Job;
    String Email;
    String Point;
    String Info;
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getDep() {
        return Dep;
    }

    public void setDep(String dep) {
        Dep = dep;
    }
    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPoint() {
        return Point;
    }

    public void setPoint(String point) {
        Point = point;
    }

    public UserData(String id,String name, String num, String dep, String job, String email, String point,String info) {
        ID=id;
        Name = name;
        Num = num;
        Dep = dep;
        Job = job;
        Email = email;
        Point = point;
        Info=info;
    }
}
