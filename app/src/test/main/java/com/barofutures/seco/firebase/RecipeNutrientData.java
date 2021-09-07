package com.barofutures.seco.firebase;

import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RecipeNutrientData implements Serializable {
    private String INFO_CAR;
    private String INFO_ENG;
    private String INFO_FAT;
    private String INFO_NA;
    private String INFO_PRO;

    // Constructor
    public RecipeNutrientData() {
    }

    public RecipeNutrientData(String INFO_CAR, String INFO_ENG, String INFO_FAT,
                              String INFO_NA, String INFO_PRO) {
        this.INFO_CAR = INFO_CAR;
        this.INFO_ENG = INFO_ENG;
        this.INFO_FAT = INFO_FAT;
        this.INFO_NA = INFO_NA;
        this.INFO_PRO = INFO_PRO;
    }

    // DataSnapshot을 이용하여 값 설정
    public void setDataFromDataSnapshot(DataSnapshot ds){
        this.INFO_CAR=ds.child("INFO_CAR").getValue().toString();
        this.INFO_ENG=ds.child("INFO_ENG").getValue().toString();
        this.INFO_FAT=ds.child("INFO_FAT").getValue().toString();
        this.INFO_NA=ds.child("INFO_NA").getValue().toString();
        this.INFO_PRO=ds.child("INFO_PRO").getValue().toString();
    }

    // Thumbnail 데이터 반환
    public String getCalories(){
        return INFO_CAR;
    }

    // Map으로 데이터 반환
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("INFO_CAR", INFO_CAR);
        result.put("INFO_ENG", INFO_ENG);
        result.put("INFO_FAT", INFO_FAT);
        result.put("INFO_NA", INFO_NA);
        result.put("INFO_PRO", INFO_PRO);

        return result;
    }

    // getter, setter

    public String getINFO_CAR() {
        return INFO_CAR;
    }

    public void setINFO_CAR(String INFO_CAR) {
        this.INFO_CAR = INFO_CAR;
    }

    public String getINFO_ENG() {
        return INFO_ENG;
    }

    public void setINFO_ENG(String INFO_ENG) {
        this.INFO_ENG = INFO_ENG;
    }

    public String getINFO_FAT() {
        return INFO_FAT;
    }

    public void setINFO_FAT(String INFO_FAT) {
        this.INFO_FAT = INFO_FAT;
    }

    public String getINFO_NA() {
        return INFO_NA;
    }

    public void setINFO_NA(String INFO_NA) {
        this.INFO_NA = INFO_NA;
    }

    public String getINFO_PRO() {
        return INFO_PRO;
    }

    public void setINFO_PRO(String INFO_PRO) {
        this.INFO_PRO = INFO_PRO;
    }
}
