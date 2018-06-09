package com.fff.ingood.data;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ActivityAttr implements Serializable {

    @SerializedName("id")
    private String m_id;

    @SerializedName("publisheremail")
    private String m_publisheremail;

    @SerializedName("publisheruserpassword")
    private String m_publisherpwd;

    @SerializedName("publishbegin")
    private String m_publishbegin;

    @SerializedName("publishend")
    private String m_publishend;

    @SerializedName("largeactivity")
    private String m_largeactivity;

    @SerializedName("earlybird")
    private String m_earlybird;

    @SerializedName("displayname")
    private String m_displayname;

    @SerializedName("datebegin")
    private String m_datebegin;

    @SerializedName("dateend")
    private String m_dateend;

    @SerializedName("location")
    private String m_location;

    @SerializedName("status")
    private String m_status;

    @SerializedName("description")
    private String m_description;

    @SerializedName("tags")
    private String m_tags;

    @SerializedName("good")
    private String m_good;

    @SerializedName("nogood")
    private String m_nogood;

    @SerializedName("attention")
    private String m_attention;

    @SerializedName("attendees")
    private String m_attendees;

    private String m_ts;
    public ActivityAttr(String sEmail,
                        String sPwd,
                        String pBegin,
                        String pEnd,
                        String bLarge,
                        String sName,
                        String dBegin,
                        String dEnd,
                        String sLocation
                    ){
        m_publisheremail = sEmail;
        m_publisherpwd = sPwd;
        m_displayname = sName;
        m_location= sLocation;
        m_publishbegin = pBegin;
        m_publishend = pEnd;
        m_datebegin = dBegin;
        m_dateend = dEnd;
        m_largeactivity = bLarge;
    }

    public ActivityAttr(){
    }

    public String getPublisherEmail(){
        return  m_publisheremail;
    }
    public void setPublisherEmail(String sEmail){
        m_publisheremail  = sEmail;
    }

    public String getPublisherPwd(){
        return  m_publisherpwd;
    }
    public void setPublisherPwd(String sPwd){
        m_publisherpwd  = sPwd;
    }

    public String getName(){
        return  m_displayname;
    }
    public void setName(String sName){
        m_displayname  = sName;
    }

    public String getStatus(){
        return  m_status;
    }
    public void setStatus(String sStatus){
        m_status  = sStatus;
    }

    public String getLocation(){
        return  m_location;
    }
    public void setLocation(String sLocation){
        m_location  = sLocation;
    }

    public String getPublisBegin(){
        return  m_publishbegin;
    }
    public void setPublisBegin(String lTime){
        m_publishbegin  = lTime;
    }

    public String getPublisEnd(){
        return  m_publishend;
    }
    public void setPublisEnd(String lTime){
        m_publishend  = lTime;
    }

    public String getDateBegin(){
        return  m_datebegin;
    }
    public void setDateBegin(String lTime){
        m_datebegin  = lTime;
    }

    public String getDateEnd(){
        return  m_dateend;
    }
    public void setDateEnd(String lTime){
        m_dateend  = lTime;
    }

    public String getLargeActivity(){
        return  m_largeactivity;
    }
    public void setLargeActivity(String bLarge){
        m_largeactivity  = bLarge;
    }

    public String getEarlyBird(){
        return  m_earlybird;
    }
    public void setEarlyBird(String bEarly){
        m_earlybird  = bEarly;
    }

    public String getId(){
        return  m_id;
    }
    public void setId(String iId){
        m_id  = iId;
    }

    public String getNoGood(){
        return m_nogood;
    }
    public void setNoGood(String inogood){
        m_nogood = inogood;
    }

    public String getGood(){
        return m_good;
    }
    public void setGood(String igood){
        m_good = igood;
    }

    public String getAttention(){
        return  m_attention;
    }
    public void setAttention(String iAttens){
        m_attention  = iAttens;
    }

    public String getDescription(){
        return  m_description;
    }
    public void setDescription(String sDes){
        m_description  = sDes;
    }

    public String getTags(){
        return  m_tags;
    }
    public void setTags(String aTags){
        m_tags  = aTags;
    }

    public String getAttendees(){
        return  m_attendees;
    }
    public void setAttendees(String aAttendees){
        m_attendees  = aAttendees;
    }
}
