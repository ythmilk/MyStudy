package com.example.yth.vollytest;

/**
 * Created by yth on 2015/12/30.
 */
public class Person {
    private String name="";
    private String age="";
    private String sex="";
    public  Person(){

    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "姓名"+name+"年龄"+age+"性别"+sex;

    }
}
