package com.barofutures.seco.model;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SuggestItemClass {
    private String imageUrl, name, calories, carbon_reduction, num_badge;
    private ArrayList<SuggestItemClass> items;

    // 생성자
    public SuggestItemClass(){}

    // Recipe 생성자
    public SuggestItemClass(String imageUrl, String name, String calories){
        this.imageUrl=imageUrl;
        this.name=name;
        this.calories=calories;
    }
    // Step 생성자
    // imageUrl은 아직 이미지가 없어서 nullable로 정의
    public SuggestItemClass(@Nullable String imageUrl, String name, String carbon_reduction, String num_badge){
        this.imageUrl=imageUrl;
        this.name=name;
        this.carbon_reduction=carbon_reduction;
        this.num_badge=num_badge;
    }


    /*
     * getter, setter
     */
    public String getImageUrl(){
        return imageUrl;
    }

    public String getName(){
        return name;
    }

    public String getCalories(){
        return calories;
    }

    public void setImageUrl(String url){this.imageUrl=url;}

    public void setName(String name){
        this.name=name;
    }

    public void setCalories(String calories){
        this.calories=calories;
    }

    public String getCarbon_reduction() {
        return carbon_reduction;
    }

    public void setCarbon_reduction(String carbon_reduction) {
        this.carbon_reduction = carbon_reduction;
    }

    public String getNum_badge() {
        return num_badge;
    }

    public void setNum_badge(String num_badge) {
        this.num_badge = num_badge;
    }

    public void setItems(ArrayList<SuggestItemClass> items){
        this.items=(ArrayList<SuggestItemClass>)items.clone();
    }

    // 임의의 데이터 리스트 생성 후 반환
    public ArrayList<SuggestItemClass> getItems(){
        items=new ArrayList<>();
        //SuggestItem item1=new SuggestItem("https://mblogthumb-phinf.pstatic.net/20150531_120/dew36_1433081464444qFjyN_JPEG/2.jpg?type=w2", "엄청난 가지 볶음", "둘이 먹다 둘다 기절", "식단");
        SuggestItemClass item2=new SuggestItemClass("https://blog.kakaocdn.net/dn/cYoXBb/btqIMWuY1qW/fSwZ4nWQ57n0cQFEXUxdd0/img.jpg", "지수와 함께", "20층 계단 오르기");
        SuggestItemClass item3=new SuggestItemClass("https://pbs.twimg.com/media/CRWpdT2UAAA6BHq.jpg", "가천대에서", "보물찾기");
        SuggestItemClass item4=new SuggestItemClass("https://ww.namu.la/s/0b67b80ef9aae5e0f1c91762671b03de9f859e375bb536100983458b14b00dab9b442ba4299bfe93204bf52e0a7f462b5739d48c37c8e56f56731b32ee9b97f939917a2ad91f2d9bd3240dab7cd769d1be77f7da2756628caf7e51b8f70db025e0e88aa7a2b97d82d31076632f2c5de7", "강동원과 눈싸움", "진 사람이 딱밤맞기");
        SuggestItemClass item5=new SuggestItemClass("https://i.ytimg.com/vi/d76hJV-Q8hE/maxresdefault.jpg", "혼자야?", "아니요");
        SuggestItemClass item6=new SuggestItemClass("https://i.ytimg.com/vi/LV9mKbfQOS8/maxresdefault.jpg", "다 쌌냐?", "아뇨 5분만 더주세요");

        //items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        items.add(item6);
        return items;
    }
}