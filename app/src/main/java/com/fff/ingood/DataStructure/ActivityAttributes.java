package com.fff.ingood.DataStructure;

import org.json.JSONArray;

public class ActivityAttributes {

    public static final String ATTRIBUTES_ACTIVITY_ID = "id";
    public static final String ATTRIBUTES_ACTIVITY_IDS = "ids";
    public static final String ATTRIBUTES_ACTIVITY_PUBLISHER_EMAIL = "publisheremail";
    public static final String ATTRIBUTES_ACTIVITY_PUBLISHER_PASSWORD = "publisheruserpassword";
    public static final String ATTRIBUTES_ACTIVITY_PUBLISH_BEGIN = "publishbegin";
    public static final String ATTRIBUTES_ACTIVITY_PUBLISH_END = "publishend";
    public static final String ATTRIBUTES_ACTIVITY_LARGE_ACTIVITY = "largeactivity";
    public static final String ATTRIBUTES_ACTIVITY_EARLY_BIRD = "earlybird";
    public static final String ATTRIBUTES_ACTIVITY_DISPLAYNAME = "displayname";
    public static final String ATTRIBUTES_ACTIVITY_DATE_BEGIN = "datebegin";
    public static final String ATTRIBUTES_ACTIVITY_DATE_END = "dateend";
    public static final String ATTRIBUTES_ACTIVITY_LOCATION = "location";
    public static final String ATTRIBUTES_ACTIVITY_IMAGE = "image";
    public static final String ATTRIBUTES_ACTIVITY_DESCRIPTION = "description";
    public static final String ATTRIBUTES_ACTIVITY_TAGS = "tags";
    public static final String ATTRIBUTES_ACTIVITY_GOOD_ACTIVITY = "goodactivity";
    public static final String ATTRIBUTES_ACTIVITY_ATTENTION = "attention";
    public static final String ATTRIBUTES_ACTIVITY_ATTENDEES = "attendees";
    public static final String ATTRIBUTES_ACTIVITY_STATUS = "status";

    private String m_publisheremail;	        //發佈者 (person.email) (必填)
    private String m_publisherePwd;	        //發佈者 (person.email) (必填)
    private String m_displayname;			    //活動名 (必填)
    private String m_location;			        //活動地點 (必填)
    private Long m_publishbegin;		        //發佈開始時間 (必填)
    private Long m_publishend;		            //發佈結束時間 (必填)
    private Long m_datebegin;		            //活動開始時間 (必填)
    private Long m_dateend;		                //活動結束時間 (必填)
    private boolean m_largeactivity;	            //是否為大型活動 (必填)(1 = 是) (預設0)
    private boolean m_earlybird;	                //是否為早鳥活動 (必填)(1 = 是)(預設0)

    private int m_id;					        //活動ID (不填) (主key)
    private int m_ts;					        //timestamp (不填)
    private int m_goodactivity;				    //好活動指數
    private int m_attention;				    //參與度
    private String m_status;			        //活動狀態 (未開始/已開始/已結束)
    private String m_image;			            //活動圖片
    private String m_description;		        //活動描述
    private JSONArray m_tags;			            //活動屬性 (第一個是要地區，例如 ”新北,認養,貓”)
    private JSONArray m_attendees;				    //參與人(person.emails)

    public  ActivityAttributes(String sEmail,
                               String sName,
                               String sLocation,
                               Long pBegin,
                               Long pEnd,
                               Long dBegin,
                               Long dEnd,
                               boolean bLarge,
                               boolean bEarly){
        m_publisheremail = sEmail;
        m_displayname = sName;
        m_location= sLocation;
        m_publishbegin = pBegin;
        m_publishend = pEnd;
        m_datebegin = dBegin;
        m_dateend = dEnd;
        m_largeactivity = bLarge;
        m_earlybird = bEarly;
    }

    public  ActivityAttributes(){
    }

    public String getPublisherEmail(){
        return  m_publisheremail;
    }
    public void setPublisherEmail(String sEmail){
        m_publisheremail  = sEmail;
    }

    public String getPublisherPwd(){
        return  m_publisherePwd;
    }
    public void setPublisherPwd(String sPwd){
        m_publisherePwd  = sPwd;
    }

    public String getName(){
        return  m_displayname;
    }
    public void setName(String sName){
        m_displayname  = sName;
    }

    public String getLocation(){
        return  m_location;
    }
    public void setLocation(String sLocation){
        m_location  = sLocation;
    }

    public Long getPublisBegin(){
        return  m_publishbegin;
    }
    public void setPublisBegin(Long lTime){
        m_publishbegin  = lTime;
    }

    public Long getPublisEnd(){
        return  m_publishend;
    }
    public void setPublisEnd(Long lTime){
        m_publishend  = lTime;
    }

    public Long getDateBegin(){
        return  m_datebegin;
    }
    public void setDateBegin(Long lTime){
        m_datebegin  = lTime;
    }

    public Long getDateEnd(){
        return  m_dateend;
    }
    public void setDateEnd(Long lTime){
        m_dateend  = lTime;
    }

    public boolean getLargeActivity(){
        return  m_largeactivity;
    }
    public void setLargeActivity(boolean bLarge){
        m_largeactivity  = bLarge;
    }

    public boolean getEarlyBird(){
        return  m_earlybird;
    }
    public void setEarlyBird(boolean bEarly){
        m_earlybird  = bEarly;
    }

    public int getId(){
        return  m_id;
    }
    public void setId(int iId){
        m_id  = iId;
    }

    public int getGoodActivity(){
        return  m_goodactivity;
    }
    public void setGoodActivity(int igood){
        m_goodactivity  = igood;
    }

    public int getAttention(){
        return  m_attention;
    }
    public void setAttention(int iAttens){
        m_attention  = iAttens;
    }

    public String getStatus(){
        return  m_status;
    }
    public void setStatus(String sStatus){
        m_status  = sStatus;
    }

    public String getDescription(){
        return  m_description;
    }
    public void setDescription(String sDes){
        m_description  = sDes;
    }

    public JSONArray getTags(){
        return  m_tags;
    }
    public void setTags(JSONArray aTags){
        m_tags  = aTags;
    }

    public JSONArray getAttendees(){
        return  m_attendees;
    }
    public void setAttendees(JSONArray aAttendees){
        m_attendees  = aAttendees;
    }
}
