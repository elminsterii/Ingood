package com.fff.ingood.data;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Person implements Serializable {


    @SerializedName("age")
    private String m_age;

    @SerializedName("gender")
    private String m_gender;

    @SerializedName("email")
    private String m_email;

    @SerializedName("userpassword")
    private String m_userpassword;

    @SerializedName("displayname")
    private String m_displayname;

    @SerializedName("description")
    private String m_description;

    @SerializedName("location")
    private String m_location;

    @SerializedName("interests")
    private String m_interests;

    @SerializedName("anonymous")
    private String m_anonymous;

    @SerializedName("saveactivities")
    private String m_saveactivities;

    @SerializedName("good")
    private String m_good;

    @SerializedName("nogood")
    private String m_nogood;

    @SerializedName("online")
    private String m_online;

    @SerializedName("verifycode")
    private String m_verifycode;

    public Person(String sEmail, String sPwd, String sGender, String sName, String iAge){
        m_email = sEmail;
        m_userpassword = sPwd;
        m_displayname = sName;
        m_gender = sGender;
        m_age = iAge;
    }

    public Person(){
    }

    public String getEmail(){
        return  m_email;
    }
    public void setEmail(String sEmail){
        m_email  = sEmail;
    }

    public String getPassword(){
        return  m_userpassword;
    }
    public void setPassword(String sPwd){
        m_userpassword  = sPwd;
    }

    public String getName(){
        return  m_displayname;
    }
    public void setName(String sName){
        m_displayname  = sName;
    }

    public String getGender(){
        return  m_gender;
    }
    public void setGender(String sGender){
        m_gender  = sGender;
    }

    public String getDescription(){
        return  m_description;
    }
    public void setDescription(String sDes){
        m_description  = sDes;
    }

    public String getLocation(){
        return  m_location;
    }
    public void setLocation(String sLocation){
        m_location  = sLocation;
    }

    public String getAge(){
        return  m_age;
    }
    public void setAge(String iAge){
        m_age  = iAge;
    }

    public String getGood(){
        return m_good;
    }
    public void setGood(String good){
        m_good = good;
    }

    public String getNoGood(){
        return m_nogood;
    }
    public void setNoGood(String nogood){
        m_nogood = nogood;
    }

    public String getOnLine(){
        return  m_online;
    }
    public void setOnLine(String iOnline){
        m_online  = iOnline;
    }

    public String getInterests(){
        return  m_interests;
    }
    public void setInterests(String aInterests){
        m_interests  = aInterests;
    }



    public String getAnonymous(){
        return m_anonymous;
    }
    public void setAnonymous(String aActivities){
        m_anonymous = aActivities;
    }

    public String getSaveActivities(){
        return  m_saveactivities;
    }
    public void setSaveActivities(String aActivities){
        m_saveactivities  = aActivities;
    }

    public String getVerifyCode(){
        return  m_verifycode;
    }
    public void setVerifyCode(String code){
        m_verifycode  = code;
    }

}
