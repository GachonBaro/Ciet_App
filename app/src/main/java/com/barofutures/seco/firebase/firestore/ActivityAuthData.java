package com.barofutures.seco.firebase.firestore;

public class ActivityAuthData {
    private String authDate;        // 인증 날짜
    private String endTime;       // 종료 시간
    private String activityName;        // 활동 이름
    private long earnedBadgeNum;      // 획득한 배지 수
    private double carbonReductionAmount;       // 탄소 저감량

    public ActivityAuthData() {

    }

    public ActivityAuthData(String authDate, String endTime, String activityName, long earnedBadgeNum, double carbonReductionAmount) {
        this.authDate = authDate;
        this.endTime = endTime;
        this.activityName = activityName;
        this.earnedBadgeNum = earnedBadgeNum;
        this.carbonReductionAmount = carbonReductionAmount;
    }

    public String getAuthDate() {
        return authDate;
    }

    public void setAuthDate(String authDate) {
        this.authDate = authDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public long getEarnedBadgeNum() {
        return earnedBadgeNum;
    }

    public void setEarnedBadgeNum(long earnedBadgeNum) {
        this.earnedBadgeNum = earnedBadgeNum;
    }

    public double getCarbonReductionAmount() {
        return carbonReductionAmount;
    }

    public void setCarbonReductionAmount(double carbonReductionAmount) {
        this.carbonReductionAmount = carbonReductionAmount;
    }
}
