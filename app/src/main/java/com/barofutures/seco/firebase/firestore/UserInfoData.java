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
    private boolean vegan;         // 비건 유무
    private boolean carOwner;     // 자가용 이용 유무
    private String activityDay;     // 활동 요일(루틴 실천이 가능한 요일 )

    // current state
    private String seti;            // 현재 SETI type
    private double carbonReductionAmount;   // 현재까지의 탄소 저감
    private long badgeNum;        // 현재 배지 개수
    private boolean challengeMode;    // 현재 챌린지 모드를 진행하고 있는지 유무
    private long donationBadgeNum;      // 기부한 배지 개수

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
        this.vegan = false;
        this.carOwner = false;
        this.activityDay = "activityDay_NULL";

        this.seti = "SETI_NULL";
        this.carbonReductionAmount = 0;
        this.badgeNum = 0;
        this.challengeMode = false;
        this.donationBadgeNum = 0;
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
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public boolean isCarOwner() {
        return carOwner;
    }

    public void setCarOwner(boolean carOwner) {
        this.carOwner = carOwner;
    }

    public String getActivityDay() {
        return activityDay;
    }

    public void setActivityDay(String activityDay) {
        this.activityDay = activityDay;
    }

    public String getSeti() {
        return seti;
    }

    public void setSeti(String seti) {
        this.seti = seti;
    }

    public double getCarbonReductionAmount() {
        return carbonReductionAmount;
    }

    public void setCarbonReductionAmount(double carbonReductionAmount) {
        this.carbonReductionAmount = carbonReductionAmount;
    }

    public long getBadgeNum() {
        return badgeNum;
    }

    public void setBadgeNum(long badgeNum) {
        this.badgeNum = badgeNum;
    }

    public boolean isChallengeMode() {
        return challengeMode;
    }

    public void setChallengeMode(boolean challengeMode) {
        this.challengeMode = challengeMode;
    }

    public long getDonationBadgeNum() {
        return donationBadgeNum;
    }

    public void setDonationBadgeNum(long donationBadgeNum) {
        this.donationBadgeNum = donationBadgeNum;
    }
}
