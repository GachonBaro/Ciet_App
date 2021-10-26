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
package com.barofutures.seco.firebase;

import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RecipeManualData implements Serializable {
    private String MANUAL01;
    private String MANUAL02;
    private String MANUAL03;
    private String MANUAL04;
    private String MANUAL05;
    private String MANUAL06;
    private String MANUAL07;
    private String MANUAL08;
    private String MANUAL09;
    private String MANUAL10;
    private String MANUAL11;
    private String MANUAL12;
    private String MANUAL13;
    private String MANUAL14;
    private String MANUAL15;
    private String MANUAL16;
    private String MANUAL17;


    // Constructor
    public RecipeManualData() {
    }

    public RecipeManualData(String MANUAL01, String MANUAL02, String MANUAL03, String MANUAL04, String MANUAL05,
                            String MANUAL06, String MANUAL07, String MANUAL08, String MANUAL09, String MANUAL10,
                            String MANUAL11, String MANUAL12, String MANUAL13, String MANUAL14, String MANUAL15,
                            String MANUAL16, String MANUAL17) {
        this.MANUAL01 = MANUAL01;
        this.MANUAL02 = MANUAL02;
        this.MANUAL03 = MANUAL03;
        this.MANUAL04 = MANUAL04;
        this.MANUAL05 = MANUAL05;
        this.MANUAL06 = MANUAL06;
        this.MANUAL07 = MANUAL07;
        this.MANUAL08 = MANUAL08;
        this.MANUAL09 = MANUAL09;
        this.MANUAL10 = MANUAL10;
        this.MANUAL11 = MANUAL11;
        this.MANUAL12 = MANUAL12;
        this.MANUAL13 = MANUAL13;
        this.MANUAL14 = MANUAL14;
        this.MANUAL15 = MANUAL15;
        this.MANUAL16 = MANUAL16;
        this.MANUAL17 = MANUAL17;
    }

    // DataSnapshot을 이용하여 값 설정
    public void setDataFromDataSnapshot(DataSnapshot ds){
        this.MANUAL01=ds.child("MANUAL01").getValue().toString();
        this.MANUAL02=ds.child("MANUAL02").getValue().toString();
        this.MANUAL03=ds.child("MANUAL03").getValue().toString();
        this.MANUAL04=ds.child("MANUAL04").getValue().toString();
        this.MANUAL05=ds.child("MANUAL05").getValue().toString();
        this.MANUAL06=ds.child("MANUAL06").getValue().toString();
        this.MANUAL07=ds.child("MANUAL07").getValue().toString();
        this.MANUAL08=ds.child("MANUAL08").getValue().toString();
        this.MANUAL09=ds.child("MANUAL09").getValue().toString();
        this.MANUAL10=ds.child("MANUAL10").getValue().toString();
        this.MANUAL11=ds.child("MANUAL11").getValue().toString();
        this.MANUAL12=ds.child("MANUAL12").getValue().toString();
        this.MANUAL13=ds.child("MANUAL13").getValue().toString();
        this.MANUAL14=ds.child("MANUAL14").getValue().toString();
        this.MANUAL15=ds.child("MANUAL15").getValue().toString();
        this.MANUAL16=ds.child("MANUAL16").getValue().toString();
        this.MANUAL17=ds.child("MANUAL17").getValue().toString();
    }

    // Map으로 데이터 반환
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("MANUAL01", MANUAL01);
        result.put("MANUAL02", MANUAL02);
        result.put("MANUAL03", MANUAL03);
        result.put("MANUAL04", MANUAL04);
        result.put("MANUAL05", MANUAL05);
        result.put("MANUAL06", MANUAL06);
        result.put("MANUAL07", MANUAL07);
        result.put("MANUAL08", MANUAL08);
        result.put("MANUAL09", MANUAL09);
        result.put("MANUAL10", MANUAL10);
        result.put("MANUAL11", MANUAL11);
        result.put("MANUAL12", MANUAL12);
        result.put("MANUAL13", MANUAL13);
        result.put("MANUAL14", MANUAL14);
        result.put("MANUAL15", MANUAL15);
        result.put("MANUAL16", MANUAL16);
        result.put("MANUAL17", MANUAL17);

        return result;
    }

    // getter, setter

    public String getMANUAL01() {
        return MANUAL01;
    }

    public void setMANUAL01(String MANUAL01) {
        this.MANUAL01 = MANUAL01;
    }

    public String getMANUAL02() {
        return MANUAL02;
    }

    public void setMANUAL02(String MANUAL02) {
        this.MANUAL02 = MANUAL02;
    }

    public String getMANUAL03() {
        return MANUAL03;
    }

    public void setMANUAL03(String MANUAL03) {
        this.MANUAL03 = MANUAL03;
    }

    public String getMANUAL04() {
        return MANUAL04;
    }

    public void setMANUAL04(String MANUAL04) {
        this.MANUAL04 = MANUAL04;
    }

    public String getMANUAL05() {
        return MANUAL05;
    }

    public void setMANUAL05(String MANUAL05) {
        this.MANUAL05 = MANUAL05;
    }

    public String getMANUAL06() {
        return MANUAL06;
    }

    public void setMANUAL06(String MANUAL06) {
        this.MANUAL06 = MANUAL06;
    }

    public String getMANUAL07() {
        return MANUAL07;
    }

    public void setMANUAL07(String MANUAL07) {
        this.MANUAL07 = MANUAL07;
    }

    public String getMANUAL08() {
        return MANUAL08;
    }

    public void setMANUAL08(String MANUAL08) {
        this.MANUAL08 = MANUAL08;
    }

    public String getMANUAL09() {
        return MANUAL09;
    }

    public void setMANUAL09(String MANUAL09) {
        this.MANUAL09 = MANUAL09;
    }

    public String getMANUAL10() {
        return MANUAL10;
    }

    public void setMANUAL10(String MANUAL10) {
        this.MANUAL10 = MANUAL10;
    }

    public String getMANUAL11() {
        return MANUAL11;
    }

    public void setMANUAL11(String MANUAL11) {
        this.MANUAL11 = MANUAL11;
    }

    public String getMANUAL12() {
        return MANUAL12;
    }

    public void setMANUAL12(String MANUAL12) {
        this.MANUAL12 = MANUAL12;
    }

    public String getMANUAL13() {
        return MANUAL13;
    }

    public void setMANUAL13(String MANUAL13) {
        this.MANUAL13 = MANUAL13;
    }

    public String getMANUAL14() {
        return MANUAL14;
    }

    public void setMANUAL14(String MANUAL14) {
        this.MANUAL14 = MANUAL14;
    }

    public String getMANUAL15() {
        return MANUAL15;
    }

    public void setMANUAL15(String MANUAL15) {
        this.MANUAL15 = MANUAL15;
    }

    public String getMANUAL16() {
        return MANUAL16;
    }

    public void setMANUAL16(String MANUAL16) {
        this.MANUAL16 = MANUAL16;
    }

    public String getMANUAL17() {
        return MANUAL17;
    }

    public void setMANUAL17(String MANUAL17) {
        this.MANUAL17 = MANUAL17;
    }
}
