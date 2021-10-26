/*
Copyright 2021 Baro Futures

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.barofutures.seco.firebase.firestore;

public class ChallengeData {
    private String startDate;       // 시작 날짜 (YYYY-MM-dd)
    private String endDate;         // 종료 날짜 (YYYY-MM-dd)
    private long maxBadgeNum;       // 최대로 얻을 수 있는 배지 개수, sum(각 활동의 1회당 배지 수 * 수행 요일 수) * 수행 기간(주)
    private long currentBadgeNum;   // 지금까지 얻은 배지 개수
    private boolean isSucceed;      // 성공 여부 (기본값 false)
    private long additionalBadgeNum;    // 성공하면 얻을 수 있는 추가 배지 개수

    // 같은 level에 activityList(활동 리스트) Collection도 있음


    public ChallengeData() {
        super();
        this.currentBadgeNum = 0;
        this.isSucceed = false;
    }

    public ChallengeData(String startDate, String endDate, long maxBadgeNum, long additionalBadgeNum) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxBadgeNum = maxBadgeNum;
        this.currentBadgeNum = 0;
        this.isSucceed = false;
        this.additionalBadgeNum = additionalBadgeNum;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long getMaxBadgeNum() {
        return maxBadgeNum;
    }

    public void setMaxBadgeNum(long maxBadgeNum) {
        this.maxBadgeNum = maxBadgeNum;
    }

    public long getCurrentBadgeNum() {
        return currentBadgeNum;
    }

    public void setCurrentBadgeNum(long currentBadgeNum) {
        this.currentBadgeNum = currentBadgeNum;
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean succeed) {
        isSucceed = succeed;
    }

    public long getAdditionalBadgeNum() {
        return additionalBadgeNum;
    }

    public void setAdditionalBadgeNum(long additionalBadgeNum) {
        this.additionalBadgeNum = additionalBadgeNum;
    }
}
