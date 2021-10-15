package com.barofutures.seco.model;

import com.barofutures.seco.R;

import java.util.ArrayList;

// Free mode 각 tab의 recycler view에 들어갈 contents list의 데이터를 담을 class
public class ContentsDetailData {
    public ArrayList<Integer> image;            // 배경 사진
    public ArrayList<String> title;            // title (ex. 비건식단)
    public ArrayList<String> activityNum;     // 뱃지를 얻을 수 있는 활동 횟수
    public ArrayList<String> badgeNum;          // 활동 1회 완료 시, 지급하는 뱃지 개수
    public ArrayList<Boolean> isFavorites;     // 좋아요(즐겨찾기) 추가 유무
    public ArrayList<String> contents1;        // 활동 설명1 (굵은 글씨)
    public ArrayList<String> contents2;        // 활동 설명2 (얇은 글씨)
    public ArrayList<String> carbonReduction;  // 탄소 저감량
    public ArrayList<Boolean> isEnabled;        // 활성화 유무


    // eco activity 만 해당
    public ArrayList<String> calorieConsumption;   // 칼로리 소모량

    public ContentsDetailData() {
        image = new ArrayList<>();
        title = new ArrayList<>();
        activityNum = new ArrayList<>();
        badgeNum = new ArrayList<>();
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
        activityNum.add("1회");
        activityNum.add("1회");
        activityNum.add("1회");
        activityNum.add("1회");

        // 1회 활동 시, 얻을 수 있는 뱃지 수
        badgeNum.add("3");
        badgeNum.add("2");
        badgeNum.add("2");
        badgeNum.add("2");


        // 활동 설명1 (굵은 글씨)
        contents1.add("CO₂ 배출량이 큰 동물성 음식 줄이기");
        contents1.add("나의 건강과 지구의 건강을 한 번에 챙기기");
        contents1.add("상품 규격에 맞지 않아 버려지는 식자재 구하기");
        contents1.add("일회용 포장재 대신 다회용기 사용하기");

        // 활동 설명2 (얇은 글씨)
        contents2.add("동물성 음식을 먹는 것을 피하고, 식물성 음식만을 먹는 식단");
        contents2.add("일체의 화학물질, 즉 제초제, 살충, 살균제 등 합성농약이나 화학비료, 호르몬제 등을 사용하지 않는 농업으로 재배한 농산물");
        contents2.add("흠집이 있거나 모양이 이상해 상품성이 떨어지는 농산물\n" +
                "품질이나 맛은 일반 농산물과 차이가 없지만 가격이 저렴해 환경과 가성비를 모두 챙길수 있다.");
        contents2.add("음식 포장에 무분별하게 사용되는 일회용품을 줄이고자 가정 내 다회용 용기를 들고 가 일회용품 대신 포장을 이끄는 캠페인");

        // 탄소저감량
        carbonReduction.add("0.64kg");
        carbonReduction.add("0.37kg");
        carbonReduction.add("0.37kg");
        carbonReduction.add("0.25kg");

        // 좋아요(즐겨찾기) 추가 유무
        this.isFavorites = isFavorites;
        // 활성화 유무
        this.isEnabled = isEnabled;
    }

    // eco activity data 설정
    public void setActivity(ArrayList<Boolean> isFavorites, ArrayList<Boolean> isEnabled) {
        // title
        title.add("걷기");
        title.add("자전거로 출퇴근");
        title.add("플로깅");
        title.add("계단 이용하기");

        // 배경 사진
        image.add(R.drawable.walking);
        image.add(R.drawable.cycling);
        image.add(R.drawable.plogging);
        image.add(R.drawable.stepping);

        // 1 뱃지를 얻을 수 있는 활동 횟수
        activityNum.add("6.5km");
        activityNum.add("15km");
        activityNum.add("2km");
        activityNum.add("12층");

        // 1회 활동 시, 얻을 수 있는 뱃지 수
        badgeNum.add("1");
        badgeNum.add("3");
        badgeNum.add("3");
        badgeNum.add("2");

        // 활동 설명1 (굵은 글씨)
        contents1.add("일상 속 틈틈히 운동 효과를 낼 수 있는 걷기");
        contents1.add("CO₂ 발생 없는 건강한 이동수단");
        contents1.add("조깅을 하면서 쓰레기를 수거하는 친환경 운동법");
        contents1.add("엘리베이터 대신 계단 오르기");

        // 활동 설명2 (얇은 글씨)
        contents2.add("매연이나 CO₂ 배출이 많은 교통 수단 대신 걷기로 대체");
        contents2.add("걷기보다 근육 사용량이 높아 운동 효과가 높으며 매연 발생량이 없어 지구 건강에도 좋은 자전거 타기");
        contents2.add("플로깅: 이삭을 줍는다는 뜻인 스웨덴어 PLOCKA UPP(PICK UP)과 조깅(JOGGING)의 합성어");
        contents2.add("엘리베이터가 발생시키는 전기 사용량과  CO₂ 배출량을 줄일 수 있는 활동");

        // 탄소저감량
        carbonReduction.add("1.51kg");
        carbonReduction.add("3.53kg");
        carbonReduction.add("2kg");
        carbonReduction.add("0.0127kg");

        // 칼로리 소모량
        calorieConsumption.add("420kcal");
        calorieConsumption.add("220kcal");
        calorieConsumption.add("87kcal");
        calorieConsumption.add("84kcal");


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
        activityNum.add("1회");
        activityNum.add("1회");
        activityNum.add("1회");
        activityNum.add("1회");
        activityNum.add("1회");
        activityNum.add("1회");
        activityNum.add("1회");
        activityNum.add("1회");


        // 1회 활동 시, 얻을 수 있는 뱃지 수
        badgeNum.add("2");
        badgeNum.add("1");
        badgeNum.add("1");
        badgeNum.add("1");
        badgeNum.add("1");
        badgeNum.add("1");
        badgeNum.add("1");
        badgeNum.add("1");

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
        contents2.add("플라스틱 용기 안의 내용물은 깨끗이 비우고 이물질은 제거합니다. 플라스틱과 다른 재질은 별도 제거 후 일반 플라스틱끼리 모아서 분리배출합니다.\n ※출처: 서울사랑");
        contents2.add("우리나라 1인당 연간 비닐봉투 사용량 420여장이고, 비닐봉투를 사용하는데 걸리는 시간은 5분, 완전히 분해되려면 최소 100년이 걸린다. 또한 비닐봉지 1장 사용하면 에코백을 최소 131번 사용한 것과 같다.");
        contents2.add("사업장 1백만 명 핸드 드라이어 한달 사용 시 360.4 ton Co2 발생 1톤의 탄소를 줄이기 위해 360그루 소나무 심어야 함 360.4 ton의 이산화탄소를 줄이기 위해서는 한달에 129,600 그루의 소나무를, 일 년엔 1,555,200 그루의 소나무를 심어야 함");
        contents2.add("'리필 (refill)'의 뜻은 비어 있는 용기에 내용물을 다시 채움. 또는 그러한 서비스를 말함 즉, 리필 스테이션은 다른 포장 없이 원하는 '내용물'을 빈 용기에 채워갈 수 있게 하는 장소  '에코 리필 스테이션' 1개점 기준 연간 플라스틱 1095kg 절감");
        contents2.add("우리나라 1인당 물 사용량 평균 287L. 유럽 국가들의 2배 수준 양치컵을 사용하지 않을 경우 양치 1회 물 사용량 36L 양치 컵 사용시 양치 1회 물 사용량 0.6L");
        contents2.add("이메일 한 통에 약 4g 이산화탄소 발생 전 세계 이메일 사용자 총 23억명 이 모든 사람이 이메일을 10개씩만 지워도 무려 1,725,000GB 저장공간 절약 이는 곧,  19,356톤의 전기 화력 절감");
        contents2.add("4인 가족 기준, 음식물 쓰레기 20%만 줄여도 연간 온실가스 145kg 절감 이는 소나무 30그루 1년 동안 흡수하는 온실가스 양 또한 에너지를 연간 144kWh 절약 이는 세탁기 1,080회 / 냉장고 3.3개월 / 텔레비전 5~6개월 사용");
        contents2.add("월 평균 450kWh 사용 가구에 스마트 플러그 설치시, 연간 31kWh (5.8% ↓) 전력 절감");

        // 탄소저감량
        carbonReduction.add("0.302kg");
        carbonReduction.add("0.0945kg");
        carbonReduction.add("0.0066kg");
        carbonReduction.add("0.0025kg");
        carbonReduction.add("0.0755kg");
        carbonReduction.add("0.027kg");
        carbonReduction.add("0.046kg");
        carbonReduction.add("0.0013kg");

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

        // 활동 설명1 (굵은 글씨)
        contents1.add("일상 속 틈틈히 운동 효과를 낼 수 있는 걷기");
        contents1.add("CO₂ 발생 없는 건강한 이동수단");
        contents1.add("조깅을 하면서 쓰레기를 수거하는 친환경 운동법");

        // 활동 설명2 (얇은 글씨)
        contents2.add("매연이나 CO₂ 배출이 많은 교통 수단 대신 걷기로 대체");
        contents2.add("걷기보다 근육 사용량이 높아 운동 효과가 높으며 매연 발생량이 없어 지구 건강에도 좋은 자전거 타기");
        contents2.add("플로깅: 이삭을 줍는다는 뜻인 스웨덴어 PLOCKA UPP(PICK UP)과 조깅(JOGGING)의 합성어");

        // 탄소저감량
        carbonReduction.add("1.51kg");
        carbonReduction.add("1.89kg");
        carbonReduction.add("0.46kg");

        // 칼로리 소모량
        calorieConsumption.add("420kcal");
        calorieConsumption.add("220kcal");
        calorieConsumption.add("87kcal");


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
