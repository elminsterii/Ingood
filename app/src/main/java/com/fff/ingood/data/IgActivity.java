package com.fff.ingood.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class IgActivity implements Serializable {

    public static final String IGA_STATUS_START_YET = "0";
    public static final String IGA_STATUS_START_TODAY = "1";
    public static final String IGA_STATUS_START = "2";
    public static final String IGA_STATUS_CLOSED = "3";
    public static final String TAG_IGACTIVITY = "igactivity";

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

    @SerializedName("maxattention")
    private String m_maxattention;

    @SerializedName("maxoffer")
    private String m_maxoffer;

    @SerializedName("offertook")
    private String m_offertook;

    private String m_ts;

    public IgActivity(String m_id, String m_publisheremail, String m_publisherpwd, String m_publishbegin, String m_publishend, String m_largeactivity, String m_earlybird, String m_displayname, String m_datebegin, String m_dateend, String m_location, String m_status, String m_description, String m_tags, String m_good, String m_nogood, String m_attention, String m_attendees, String m_maxattention, String m_maxoffer, String m_offertook, String m_ts) {
        this.m_id = m_id;
        this.m_publisheremail = m_publisheremail;
        this.m_publisherpwd = m_publisherpwd;
        this.m_publishbegin = m_publishbegin;
        this.m_publishend = m_publishend;
        this.m_largeactivity = m_largeactivity;
        this.m_earlybird = m_earlybird;
        this.m_displayname = m_displayname;
        this.m_datebegin = m_datebegin;
        this.m_dateend = m_dateend;
        this.m_location = m_location;
        this.m_status = m_status;
        this.m_description = m_description;
        this.m_tags = m_tags;
        this.m_good = m_good;
        this.m_nogood = m_nogood;
        this.m_attention = m_attention;
        this.m_attendees = m_attendees;
        this.m_maxattention = m_maxattention;
        this.m_maxoffer = m_maxoffer;
        this.m_offertook = m_offertook;
        this.m_ts = m_ts;
    }

    public IgActivity(){
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

    public String getPublishBegin(){
        return  m_publishbegin;
    }
    public void setPublishBegin(String lTime){
        m_publishbegin  = lTime;
    }

    public String getPublishEnd(){
        return  m_publishend;
    }
    public void setPublishEnd(String lTime){
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

    public String getMaxAttention(){
        return  m_maxattention;
    }
    public void setMaxAttention(String maxAttention){
        m_maxattention  = maxAttention;
    }

    public String getMaxOffer() {
        return m_maxoffer;
    }

    public void setMaxOffer(String m_maxoffer) {
        this.m_maxoffer = m_maxoffer;
    }

    public String getOfferTook() {
        return m_offertook;
    }

    public void setOfferTook(String m_offertook) {
        this.m_offertook = m_offertook;
    }
}
