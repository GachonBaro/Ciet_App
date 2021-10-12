package com.barofutures.seco.firebase.firestore;

public class UserInfoData {
    // get data from Google Auth
    private String UID;              // UID
    private String userName;        // 사용자 이름
    private String email;           // 사용자 이메일 주소

    // get date from initial survey (8 questions)
    private String nickname;        // 닉네임
    private String job;             // 직업
    private String gender;          // 성별
    private String routineGoal;     // 루틴으로 달성하고 싶은 목표
    private String interestActivity;    // 하고 싶은 친환경 활동
    private boolean isVegan;         // 비건 유무
    private boolean isCarOwner;     // 자가용 이용 유무
    private String activityDay;     // 활동 요일(루틴 실천이 가능한 요일 )

    // current state
    private String SETI;            // 현재 SETI type
    private String CMIGrade;        // 현재 CMI 등급
    private double CMINum;          // 현재 CMI 값
    private long badgeNum;        // 현재 뱃지 개수
    private boolean isChallengeMode;    // 현재 챌린지 모드를 진행하고 있는지 유무

    public UserInfoData() {

    }

    public UserInfoData(String UID, String userName, String email) {
        this.UID = UID;
        this.userName = userName;
        this.email = email;

        this.nickname = "nickname_NULL";
        this.job = "job_NULL";
        this.gender = "gender_NULL";
        this.routineGoal = "routineGoal_NULL";
        this.interestActivity = "interestActivity_NULL";
        this.isVegan = false;
        this.isCarOwner = false;
        this.activityDay = "activityDay_NULL";

        this.SETI = "SETI_NULL";
        this.CMIGrade = "CMIGrade_NULL";
        this.CMINum = 0.0;
        this.badgeNum = 0;
        this.isChallengeMode = false;
    }

    // getter, setter


    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRoutineGoal() {
        return routineGoal;
    }

    public void setRoutineGoal(String routineGoal) {
        this.routineGoal = routineGoal;
    }

    public String getInterestActivity() {
        return interestActivity;
    }

    public void setInterestActivity(String interestActivity) {
        this.interestActivity = interestActivity;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }

    public boolean isCarOwner() {
        return isCarOwner;
    }

    public void setCarOwner(boolean carOwner) {
        isCarOwner = carOwner;
    }

    public String getActivityDay() {
        return activityDay;
    }

    public void setActivityDay(String activityDay) {
        this.activityDay = activityDay;
    }

    public String getSETI() {
        return SETI;
    }

    public void setSETI(String SETI) {
        this.SETI = SETI;
    }

    public String getCMIGrade() {
        return CMIGrade;
    }

    public void setCMIGrade(String CMIGrade) {
        this.CMIGrade = CMIGrade;
    }

    public double getCMINum() {
        return CMINum;
    }

    public void setCMINum(double CMINum) {
        this.CMINum = CMINum;
    }

    public long getBadgeNum() {
        return badgeNum;
    }

    public void setBadgeNum(long badgeNum) {
        this.badgeNum = badgeNum;
    }

    public boolean isChallengeMode() {
        return isChallengeMode;
    }

    public void setChallengeMode(boolean challengeMode) {
        isChallengeMode = challengeMode;
    }
}
