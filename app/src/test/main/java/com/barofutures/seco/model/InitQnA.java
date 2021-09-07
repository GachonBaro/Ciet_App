package com.barofutures.seco.model;

import java.util.ArrayList;

public class InitQnA {
    public static boolean isAsked=false;

    // 질문
    public static String Q0="어떤 직업을 갖고 계신가요?";
    public static String Q1="성별이 어떻게 되시나요?";
    public static String Q2="루틴으로 어떤 목표를 달성하고 싶으신가요?";
    public static String Q3="어떤 친환경 활동에 참여하고 싶으신가요?";
    public static String Q4="비건이신가요?";
    public static String Q5="자가용을 이용하고 계신가요?";
    public static String Q6="루틴 실천이 가능한 요일을\n선택해 주세요.";
    public static String Q7="사용하고자 하는 닉네임을 입력해주세요.";
    public static String Q8="SETI로 환경유형검사를 해보시겠어요?";

    // 선택지
    public static String[] C0= {"경영직", "사무직", "공무원", "자영업", "생산직", "서비스", "전문직", "학생", "전업주부", "군인", "기타"};
    public static String[] C458 = {"네", "아니오"};

    public static String[] C1={"여자", "남자", "답변하고 싶지 않음"};
    public static String[] C2={"나의 친환경 이미지 부각", "쓰레기 배출량 절감", "건강한 생활습관 형성", "지구 환경 지키기", "건강한 식생활 형성"};
    public static String[] C3={"플라스틱 줄이기", "채식하기", "플로깅하기", "친환경 재료 구매하기", "친환경 요리하기", "포장용기 사용 줄이기"};
    public static String[] C6={"일", "월", "화", "수", "목", "금", "토"};

    // 답변
    public static String A0;
    public static String A1;
    public static ArrayList<String> A2=new ArrayList<>();
    public static ArrayList<String> A3=new ArrayList<>();
    public static String A4;
    public static String A5;
    public static ArrayList<String> A6=new ArrayList<>();
    public static String A7;
    public static String A8;

}