package com.fff.ingood.data;


import org.json.JSONArray;

public class Person {

    public static final String ATTRIBUTES_PERSON_ACCOUNT = "email";
    public static final String ATTRIBUTES_PERSON_PASSWORD = "userpassword";
    public static final String ATTRIBUTES_PERSON_NAME = "displayname";
    public static final String ATTRIBUTES_PERSON_GENDER = "gender";
    public static final String ATTRIBUTES_PERSON_AGE = "age";
    public static final String ATTRIBUTES_PERSON_LOCATION = "location";
    public static final String ATTRIBUTES_PERSON_INTERESTS = "interests";
    public static final String ATTRIBUTES_PERSON_DESCRIPTION = "description";
    public static final String ATTRIBUTES_PERSON_JOIN_ACTIVITY = "joinactivities";
    public static final String ATTRIBUTES_PERSON_HOLD_ACTIVITY = "holdactivities";
    public static final String ATTRIBUTES_PERSON_SAVE_ACTIVITY = "saveactivities";

    public static final String ATTRIBUTES_PERSON_GOOD_MEMBER = "goodmember";
    public static final String ATTRIBUTES_PERSON_GOOD_LEADER = "goodleader";
    public static final String ATTRIBUTES_PERSON_NEW_PASSWORD = "newuserpassword";
    public static final String ATTRIBUTES_PERSON_ONLINE = "online";

    private int m_age;					    //年紀 (必填)
    private String m_gender;			    //性別 (Male\Female) (必填)
    private String m_email;		            //mail (account) (必填) (主Key)
    private String m_userpassword;		    //password (必填)
    private String m_displayname;			//暱稱 (必填)
    private String m_icon;				    //Icon
    private String m_description;		    //個人介紹
    private String m_location;			    //所在地區
    private JSONArray m_interests;		    //興趣Tag
    private JSONArray m_joinactivities;		//現在參加的活動
    private JSONArray m_holdactivities;		//現在舉辦的活動
    private JSONArray m_saveactivities;		//現在保存的活動
    int m_goodmember;				        //好團員指數
    int m_goodleader;				        //好團長指數
    int m_online;			                //是否上線(1 = online)(預設0)

    public Person(String sEmail, String sPwd, String sGender, String sName, int iAge){
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
        return  m_email;
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

    public int getAge(){
        return  m_age;
    }
    public void setAge(int iAge){
        m_age  = iAge;
    }

    public int getGoodMember(){
        return  m_goodmember;
    }
    public void setGoodMember(int iGoodMember){
        m_goodmember  = iGoodMember;
    }

    public int getGoodLeader(){
        return  m_goodleader;
    }
    public void setGoodLeader(int iGoodLeader){
        m_goodleader  = iGoodLeader;
    }

    public int getOnLine(){
        return  m_online;
    }
    public void setOnLine(int iOnline){
        m_online  = iOnline;
    }

    public JSONArray getInterests(){
        return  m_interests;
    }
    public void setInterests(JSONArray aInterests){
        m_interests  = aInterests;
    }

    public JSONArray getJoinActivities(){
        return  m_joinactivities;
    }
    public void setJoinActivities(JSONArray aActivities){
        m_joinactivities  = aActivities;
    }

    public JSONArray getHoldActivities(){
        return  m_holdactivities;
    }
    public void setHoldActivities(JSONArray aActivities){
        m_holdactivities  = aActivities;
    }

    public JSONArray getSaveActivities(){
        return  m_saveactivities;
    }
    void setSaveActivities(JSONArray aActivities){
        m_saveactivities  = aActivities;
    }
}
