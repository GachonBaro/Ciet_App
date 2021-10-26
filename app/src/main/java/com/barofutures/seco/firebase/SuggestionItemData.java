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
import java.util.Map;

public class SuggestionItemData implements Serializable {
    private String menuName;
    private boolean isLiked=false;
    private RecipeNutrientData nutrient;
    private RecipeManualData manual;
    private RecipeTypeData type;
    private EcoStepData ecoStep;

    // Constructor
    public SuggestionItemData(){}

    // 메뉴이름은 getKey(), 나머지는 각 클래스의 Instance 생성 후 setDataFromDataSnapshot 메소드로 값 설정
    public void setMenuDataFromDataSnapshot(DataSnapshot ds){

        // 레시피 데이터 Instance 생성
        this.menuName= ds.getKey();

        this.nutrient=new RecipeNutrientData();
        this.manual=new RecipeManualData();
        this.type=new RecipeTypeData();

        this.nutrient.setDataFromDataSnapshot(ds);
        this.manual.setDataFromDataSnapshot(ds);
        this.type.setDataFromDataSnapshot(ds);

        // 운동 데이터 Instance 생성
        this.ecoStep=new EcoStepData();
        this.ecoStep.setDataFromDataSnapshot(ds);
    }

    // getter, setter
    public String getMenuName(){ return menuName; }
    public Map<String, Object> getTypeData(){ return type.toMap(); }
    public Map<String, Object> getManualData(){ return manual.toMap(); }
    public Map<String, Object> getNutrientData(){ return nutrient.toMap(); }

    public void likeMenu(){
        if(!isLiked){
            isLiked=false;
        }
        else{
            isLiked=true;
        }
    }

    public void setNutrient(RecipeNutrientData nutrient){
        this.nutrient=nutrient;
    }
    public void setManual(RecipeManualData manual){
        this.manual=manual;
    }
    public void setType(RecipeTypeData type){
        this.type=type;
    }
}
