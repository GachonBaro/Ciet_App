package com.barofutures.seco.model;

public class SetiQnA {
    public static boolean testLater=false;
    public static String mySETI="";

    // 질문
    public static String[] question = {
            "유기농 농산물과 무농약 농산물의 차이를 안다",
            "섬유 재활용을 통해 제작한 재생섬유 의류는 친환경적이다",
            "일회용 포장재 대신 에코백은  최소N회 이상 사용했을 때에 그 효과가 있다",
            "바나나 껍질은 음식물 쓰레기이다",
            "탄소세가 어떤 내용인지 이해하고 있다",
            "여름철 실내 적정 온도를 알고있다",
            "환경 성적 표지 7대 영향범주에 속하는것은? 자원발자국, 탄소발자국, 오존층영향, 물발자국, 산성비",
            "나는 건강과 환경을 위해 비건 화장품을 이용해본 경험이 있다",
            "나는 음식점에서 음식이 남으면 따로 포장을 하여 챙겨가는 편이다",
            "나는 환경을 위해 읽지 않은 메일을 정리할 의향이 있다",

            "나는 배달 음식을 시킬 때에 일회용품 사용을 자제하는 편이다",
            "나는 물건을 구매하기 위해 10분 내외의 거리는 걸어서 이동할 의향이 있다",
            "나는 가까운 거리는 걷거나 자전거를 타고 이동하는 편이다",
            "나는 환경 개선을 위해 제작한 굿즈를 구매해본 경험이 있다",
            "나는 카페에 방문 할 때에 텀블러를 사용하는 편이다",
            "나는 물건을 구매할 때에 이 물건을 제작한 기업의 환경 활동과 이미지를 고려하는 편이다",
            "나는 유기농 농산물을 구매할 의향이 있다",
            "나는 환경 문제 개선을 위한 캠페인이나 공모전에 참여해 본 경험이 있다",
            "종이 테이프로 포장된 박스는 종이로 분리수거한다",
            "나는 샴푸를 다 사용한 후  본품보다 리필 제품을 사용하는 편이다",

            "나는 친환경 재료를 활용한 레시피를 참고해 음식을 조리할 의향이 있다.",
            "나는 플로깅을 할 의향이 있다",
            "나는 음식을 주문 할 때에 다 먹을 수 있는 만큼만 시킨다.",
            "나는 의류 대여 서비스가 친환경 활동이라고 생각한다",
            "정부에서 진행하고 있는 탄소 포인트제는 온실가스를 줄이기위한 활동이다.",
            "동일 제품군에 대해 친환경인 경우 일반 제품군 대비 N%더 지불할 용의가 있다",
            "나는  #용기내 챌린지에 참여해 이 활동을 타인과 공유할 의사가 있다",
            "나는 5층 미만의 건물은 계단을 이용해 이동할 의향이 있다",
            "나는 못난이 농산물을 구매할 의향이 있다",
            "나는 나의 건강과 환경을 위해 육류 소비를 줄일 의향이 있다."
    };

    // 선택지
    public static String[][] choice = {
            {"매우 아니다", "", "", "", "매우 그렇다"},
            {"매우 아니다", "", "", "", "매우 그렇다"},
            {"90 초과", "100 초과", "110 초과", "120 초과", "130 초과"},
            {"매우 아니다", "", "", "", "매우 그렇다"},
            {"매우 아니다", "", "", "", "매우 그렇다"},

            {"22", "23", "24", "25", "26"},
            {"1", "2", "3", "4", "5"},
            {"경험 없음", "1~3회", "4~6회", "7~9회", "10회 이상"},
            {"매우 아니다", "", "", "", "매우 그렇다"},
            {"매우 아니다", "", "", "", "매우 그렇다"},

            {"매우 아니다", "", "", "", "매우 그렇다"},
            {"매우 아니다", "", "", "", "매우 그렇다"},
            {"매우 아니다", "", "", "", "매우 그렇다"},
            {"경험 없음", "1~3회", "4~6회", "7~9회", "10회 이상"},
            {"매우 아니다", "", "", "", "매우 그렇다"},

            {"매우 아니다", "", "", "", "매우 그렇다"},
            {"매우 아니다", "", "", "", "매우 그렇다"},
            {"매우 아니다", "", "", "", "매우 그렇다"},
            {"경험 없음", "1~3회", "4~6회", "7~9회", "10회 이상"},
            {"매우 아니다", "", "", "", "매우 그렇다"},

            {"매우 아니다", "", "", "", "매우 그렇다"},
            {"매우 아니다", "", "", "", "매우 그렇다"},
            {"매우 아니다", "", "", "", "매우 그렇다"},
            {"매우 아니다", "", "", "", "매우 그렇다"},
            {"매우 아니다", "", "", "", "매우 그렇다"},

            {"매우 아니다", "", "", "", "매우 그렇다"},
            {"매우 아니다", "", "", "", "매우 그렇다"},
            {"매우 아니다", "", "", "", "매우 그렇다"},
            {"매우 아니다", "", "", "", "매우 그렇다"},
            {"매우 아니다", "", "", "", "매우 그렇다"}
    };

    // 답변
    public static int[] answer={
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
    };

    // Constructor
    public SetiQnA() {

    }

    // SETI 계산
    public static void calculateSETI(){
        mySETI="";
        // 10문항 씩 세 영역으로 나누어 각각 S/C, A/P, A/J를 결정
        // 각 영역에서 25점 이상일 경우 S, A, A
        // 25점 미만일 경우 C, P, J 중에서 결정됨
            int sc=0, ap=0, aj=0;

            for(int i=0; i<10; i++){
                sc+=answer[i];
            }
            for(int i=10; i<20; i++){
                ap+=answer[i];
            }
            for(int i=20; i<30; i++){
                aj+=answer[i];
            }

            if(sc>=25){ mySETI+="S"; }
            else{ mySETI+="C"; }
            if(ap>=25){ mySETI+="A"; }
            else{ mySETI+="P"; }
            if(aj>=25){ mySETI+="A"; }
            else{ mySETI+="J"; }
    };

    // SETI 가져오기
    public static String getMySETI(){
        return  mySETI;
    }

}
