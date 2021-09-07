package com.barofutures.seco.model;

public class RoutineStatus {
    public static String freeRoutineStatusList[][]={
            // 이름, 이름 옆 아이콘, 목표, 탄소 저감량, 칼로리 소모량, isRecipe
            {"용기내 챌린지", "@drawable/ic_brave_challenge","2회", "0.37kg\nCO₂ 저감량", "아직 선택된 메뉴가 없습니다.", "true"},
            {"못난이 농산물", "@drawable/ic_imperfect","1회", "0.37kg\nCO₂ 저감량", "아직 선택된 메뉴가 없습니다.", "true"},
            {"계단 오르기", "@drawable/ic_stairs","12층 오르기", "0.0127kg\nCO₂ 저감량", "84Kcal\n칼로리 소모량", "false"}
    };

    public static String challengeRoutineStatusList[][]={
            // 이름, 이름 옆 아이콘, 목표, 탄소 저감량, 칼로리 소모량, isRecipe
            {"채식 식단", "@drawable/ic_imperfect","1회", "0.64kg\nCO₂ 저감량", "아직 선택된 메뉴가 없습니다.", "true"},
            {"걷기", "@drawable/ic_brave_challenge","10000보", "1.51kg\nCO₂ 저감량", "420Kcal\n칼로리 소모량", "false"},
            {"용기내 챌린지", "@drawable/ic_brave_challenge","2회", "0.37kg\nCO₂ 저감량", "아직 선택된 메뉴가 없습니다.", "true"},
            {"자전거", "@drawable/ic_bicycle","2회", "1.89kg\nCO₂ 저감량", "220Kcal\n칼로리 소모량", "true"},
            {"플로깅", "@drawable/ic_stairs", "2회", "0.46kg\nCO₂ 저감량", "87Kcal\n칼로리 소모량", "true"}
    };
}
