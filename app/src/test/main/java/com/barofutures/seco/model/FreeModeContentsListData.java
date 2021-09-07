package com.barofutures.seco.model;

import com.barofutures.seco.R;

import java.util.ArrayList;

// Free mode 각 tab의 recycler view에 들어갈 contents list의 데이터를 담을 class
public class FreeModeContentsListData {
    public ArrayList<Integer> image;            // 배경 사진
    public ArrayList<String> title;            // title (ex. 비건식단)
    public ArrayList<String> activityNum;     // 1 뱃지를 얻을 수 있는 활동 횟수
    public ArrayList<Boolean> isFavorites;     // 좋아요(즐겨찾기) 추가 유무
    public ArrayList<String> contents1;        // 활동 설명1 (굵은 글씨)
    public ArrayList<String> contents2;        // 활동 설명2 (얇은 글씨)
    public ArrayList<String> carbonReduction;  // 탄소 저감량
    public ArrayList<Boolean> isEnabled;        // 활성화 유무


    // eco activity 만 해당
    public ArrayList<String> calorieConsumption;   // 칼로리 소모량

    public FreeModeContentsListData() {
        image = new ArrayList<>();
        title = new ArrayList<>();
        activityNum = new ArrayList<>();
        isFavorites = new ArrayList<>();
        contents1 = new ArrayList<>();
        contents2 = new ArrayList<>();
        carbonReduction = new ArrayList<>();
        isEnabled = new ArrayList<>();
        calorieConsumption = new ArrayList<>();
    }

    // eco meal data 설정 - item 5개
    public void setMeal(ArrayList<Boolean> isFavorites, ArrayList<Boolean> isEnabled) {
        // title
        title.add("채식 식단");
        title.add("유기농 농산물");
        title.add("못난이 농산물");
        title.add("용기내 챌린지");

        // 배경 사진
        image.add(R.drawable.vegan_meal);
        image.add(R.drawable.organic_produce);
        image.add(R.drawable.ugly_produce);
        image.add(R.drawable.container_challenge);

        // 1 뱃지를 얻을 수 있는 활동 횟수
        activityNum.add("1");
        activityNum.add("2");
        activityNum.add("2");
        activityNum.add("2");

        // 활동 설명1 (굵은 글씨)
        contents1.add("채식 식단은 ~이다.");
        contents1.add("유기농 농산물은 ~이다.");
        contents1.add("못난이 농수산물은 ~이다.");
        contents1.add("용기내 챌린지는 ~이다.");

        // 활동 설명2 (얇은 글씨)
        contents2.add("채식 식단은 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~이다.");
        contents2.add("유기농 농산물은 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~이다.");
        contents2.add("못난이 농산물은 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~이다.");
        contents2.add("용기내 챌린지는 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~이다.");

        // 탄소저감량
        carbonReduction.add("0.64kg");
        carbonReduction.add("0.25kg");
        carbonReduction.add("0.37kg");
        carbonReduction.add("0.37kg");

        // 좋아요(즐겨찾기) 추가 유무
        this.isFavorites = isFavorites;
        // 활성화 유무
        this.isEnabled = isEnabled;
    }

    // eco activity data 설정
    public void setActivity(ArrayList<Boolean> isFavorites, ArrayList<Boolean> isEnabled) {
        // title
        title.add("걷기");
        title.add("자전거 타기");
        title.add("플로깅하기");
        title.add("계단 이용하기");

        // 배경 사진
        image.add(R.drawable.walking);
        image.add(R.drawable.cycling);
        image.add(R.drawable.plogging);
        image.add(R.drawable.stepping);

        // 1 뱃지를 얻을 수 있는 활동 횟수
        activityNum.add("10000보");
        activityNum.add("8.17km");
        activityNum.add("2km");
        activityNum.add("12층");

        // 활동 설명1 (굵은 글씨)
        contents1.add("걷기는 ~이다.");
        contents1.add("자전거 타기는 ~이다.");
        contents1.add("플로깅하기는 ~이다.");
        contents1.add("계단 이용하기는 ~이다.");

        // 활동 설명2 (얇은 글씨)
        contents2.add("걷기는 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~이다.");
        contents2.add("자전거 타기는 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~이다.");
        contents2.add("플로깅하기는 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~이다.");
        contents2.add("계단 이용하기는 ~탄소저감량&칼로리소모량 확인 필요~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~이다.");

        // 탄소저감량
        carbonReduction.add("1.51kg");
        carbonReduction.add("1.89kg");
        carbonReduction.add("0.46kg");
        carbonReduction.add("0.0127kg");

        // 칼로리 소모량
        calorieConsumption.add("234kcal");
        calorieConsumption.add("234kcal");
        calorieConsumption.add("234kcal");
        calorieConsumption.add("234kcal");


        // 좋아요(즐겨찾기) 추가 유무
        this.isFavorites = isFavorites;
        // 활성화 유무
        this.isEnabled = isEnabled;
    }

    // eco quest data 설정
    public void setQuest(ArrayList<Boolean> isFavorites, ArrayList<Boolean> isEnabled) {
        // title
        title.add("올바른 재활용 배출");
        title.add("장바구니, 에코백 사용");
        title.add("손수건 사용");
        title.add("리필스테이션 사용");
        title.add("양치컵 사용");
        title.add("이메일 정리");
        title.add("냉장고 확인하기");
        title.add("절전탭 사용하기");

        // 배경 사진
        image.add(R.drawable.recycle);
        image.add(R.drawable.ecobag);
        image.add(R.drawable.handkerchief);
        image.add(R.drawable.refillstation);
        image.add(R.drawable.brushcup);
        image.add(R.drawable.email);
        image.add(R.drawable.refrigerator);
        image.add(R.drawable.electricity);

        // 1 뱃지를 얻을 수 있는 활동 횟수
        activityNum.add("10번");
        activityNum.add("20번");
        activityNum.add("70번 ");
        activityNum.add("10번");
        activityNum.add("20번");
        activityNum.add("70번 ");
        activityNum.add("10번");
        activityNum.add("20번");

        // 활동 설명1 (굵은 글씨)
        contents1.add("다양한 폐기물 올바르게 버리기");
        contents1.add("장바구니와 에코백");
        contents1.add("손수건");
        contents1.add("리필스테이션");
        contents1.add("양치컵");
        contents1.add("이메일");
        contents1.add("가정 음식물 쓰레기");
        contents1.add("절전탭");

        // 활동 설명2 (얇은 글씨)
        contents2.add("쉽게 혼동할 수 있는 폐기물 배출의 올바른 처리 방법을 소개하여 + 분리배출 Tip을 제공");
        contents2.add("우리나라 1인당 연간 비닐봉투 사용량 420여장이고, 비닐봉투를 사용하는데 걸리는 시간은 5분, 완전히 분해되려면 최소 100년이 걸린다. 또한 비닐봉지 1장 사용하면 에코백을 최소 131번 사용한 것과 같다.");
        contents2.add("사업장 1백만 명 핸드 드라이어 한달 사용 시 360.4 ton Co2 발생 1톤의 탄소를 줄이기 위해 360그루 소나무 심어야 함 360.4 ton의 이산화탄소를 줄이기 위해서는 한달에 129,600 그루의 소나무를, 일 년엔 1,555,200 그루의 소나무를 심어야 함");
        contents2.add("'리필 (refill)'의 뜻은 비어 있는 용기에 내용물을 다시 채움. 또는 그러한 서비스를 말함 즉, 리필 스테이션은 다른 포장 없이 원하는 '내용물'을 빈 용기에 채워갈 수 있게 하는 장소  '에코 리필 스테이션' 1개점 기준 연간 플라스틱 1095kg 절감");
        contents2.add("우리나라 1인당 물 사용량 평균 287L. 유럽 국가들의 2배 수준 양치컵을 사용하지 않을 경우 양치 1회 물 사용량 36L 양치 컵 사용시 양치 1회 물 사용량 0.6L");
        contents2.add("이메일 한 통에 약 4g 이산화탄소 발생 전 세계 이메일 사용자 총 23억명 이 모든 사람이 이메일을 10개씩만 지워도 무려 1,725,000GB 저장공간 절약 이는 곧,  19,356톤의 전기 화력 절감");
        contents2.add("4인 가족 기준, 음식물 쓰레기 20%만 줄여도 연간 온실가스 145kg 절감 이는 소나무 30그루 1년 동안 흡수하는 온실가스 양 또한 에너지를 연간 144kWh 절약 이는 세탁기 1,080회 / 냉장고 3.3개월 / 텔레비전 5~6개월 사용");
        contents2.add("월 평균 450kWh 사용 가구에 스마트 플러그 설치시, 연간 31kWh (5.8% ↓) 전력 절감");


        // 탄소저감량
        carbonReduction.add("1.51kg");
        carbonReduction.add("1.89kg");
        carbonReduction.add("0.46kg");
        carbonReduction.add("0.0127kg");
        carbonReduction.add("1.51kg");
        carbonReduction.add("1.89kg");
        carbonReduction.add("0.46kg");
        carbonReduction.add("0.0127kg");

        // 칼로리 소모량
        calorieConsumption.add("234kcal");
        calorieConsumption.add("234kcal");
        calorieConsumption.add("234kcal");
        calorieConsumption.add("234kcal");
        calorieConsumption.add("234kcal");
        calorieConsumption.add("234kcal");
        calorieConsumption.add("234kcal");
        calorieConsumption.add("234kcal");


        // 좋아요(즐겨찾기) 추가 유무
        this.isFavorites = isFavorites;
        // 활성화 유무
        this.isEnabled = isEnabled;
    }

    // eco favorites data 설정
    public void setFavorites(ArrayList<Boolean> isFavorites, ArrayList<Boolean> isEnabled) {
        // title
        title.add("걷기");
        title.add("자전거 타기");
        title.add("플로깅하기");

        // 배경 사진
        image.add(R.drawable.walking);
        image.add(R.drawable.cycling);
        image.add(R.drawable.plogging);

        // 1 뱃지를 얻을 수 있는 활동 횟수
        activityNum.add("10000보");
        activityNum.add("8.17km");
        activityNum.add("2km");
        activityNum.add("12층");

        // 활동 설명1 (굵은 글씨)
        contents1.add("걷기는 ~이다.");
        contents1.add("자전거 타기는 ~이다.");
        contents1.add("플로깅하기는 ~이다.");

        // 활동 설명2 (얇은 글씨)
        contents2.add("걷기는 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~이다.");
        contents2.add("자전거 타기는 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~이다.");
        contents2.add("플로깅하기는 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~이다.");

        // 탄소저감량
        carbonReduction.add("1.51kg");
        carbonReduction.add("1.89kg");
        carbonReduction.add("0.46kg");

        // 칼로리 소모량
        calorieConsumption.add("234kcal");
        calorieConsumption.add("234kcal");
        calorieConsumption.add("234kcal");


        // 좋아요(즐겨찾기) 추가 유무
        this.isFavorites = isFavorites;
        // 활성화 유무
        this.isEnabled = isEnabled;
    }

    // getter
    public ArrayList<String> getTitle() {
        return title;
    }

    public ArrayList<String> getActivityNum() {
        return activityNum;
    }

    public ArrayList<Boolean> getIsFavorites() {
        return isFavorites;
    }

    public ArrayList<String> getContents1() {
        return contents1;
    }

    public ArrayList<String> getContents2() {
        return contents2;
    }

    public ArrayList<String> getCarbonReduction() {
        return carbonReduction;
    }

    public ArrayList<Boolean> getIsEnabled() {
        return isEnabled;
    }

    public ArrayList<String> getCalorieConsumption() {
        return calorieConsumption;
    }
}
