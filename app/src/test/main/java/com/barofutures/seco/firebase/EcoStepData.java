package com.barofutures.seco.firebase;

import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EcoStepData implements Serializable {
    private String name;
    private String carbon_reduction;
    private String num_badge;

    // Constructor
    public EcoStepData(){}

    public EcoStepData(String name, String carbon_reduction, String num_badge){
        this.name=name;
        this.carbon_reduction=carbon_reduction;
        this.num_badge=num_badge;
    }

    // DataSnapshot을 이용하여 값 설정
    public void setDataFromDataSnapshot(DataSnapshot ds){
        this.name=ds.child("name").getValue().toString();
        this.carbon_reduction=ds.child("carbon_reduction").getValue().toString();
        this.num_badge=ds.child("num_badge").getValue().toString();
    }

    // Thumbnail 데이터 반환
    public String getBadgeAmount(){
        return num_badge;
    }

    // Map으로 데이터 반환
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("carbon_reduction", carbon_reduction);
        result.put("num_badge", num_badge);
        return result;
    }

    // getter, setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
