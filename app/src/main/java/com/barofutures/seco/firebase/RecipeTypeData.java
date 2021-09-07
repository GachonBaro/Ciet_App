package com.barofutures.seco.firebase;

import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RecipeTypeData implements Serializable {
    private String ATT_FILE_NO_MAIN;
    private String ATT_FILE_NO_MK;
    private String RCP_PAT2;
    private String RCP_WAY2;

    // Constructor
    public RecipeTypeData() {
    }

    public RecipeTypeData(String ATT_FILE_NO_MAIN, String ATT_FILE_NO_MK,
                          String RCP_PAT2, String RCP_WAY2) {
        this.ATT_FILE_NO_MAIN = ATT_FILE_NO_MAIN;
        this.ATT_FILE_NO_MK = ATT_FILE_NO_MK;
        this.RCP_PAT2 = RCP_PAT2;
        this.RCP_WAY2 = RCP_WAY2;
    }

    // DataSnapshot을 이용하여 값 설정
    public void setDataFromDataSnapshot(DataSnapshot ds){
        this.ATT_FILE_NO_MAIN=ds.child("ATT_FILE_NO_MAIN").getValue().toString();
        this.ATT_FILE_NO_MK=ds.child("ATT_FILE_NO_MK").getValue().toString();
        this.RCP_PAT2=ds.child("RCP_PAT2").getValue().toString();
        this.RCP_WAY2=ds.child("RCP_WAY2").getValue().toString();
    }

    // Thumbnail 데이터 반환
    public String getThumbnailUrl(){
        return ATT_FILE_NO_MAIN;
    }

    // Map으로 데이터 반환
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ATT_FILE_NO_MAIN", ATT_FILE_NO_MAIN);
        result.put("ATT_FILE_NO_MK", ATT_FILE_NO_MK);
        result.put("RCP_PAT2", RCP_PAT2);
        result.put("RCP_WAY2", RCP_WAY2);

        return result;
    }

    // getter, setter

    public String getATT_FILE_NO_MAIN() {
        return ATT_FILE_NO_MAIN;
    }

    public void setATT_FILE_NO_MAIN(String ATT_FILE_NO_MAIN) {
        this.ATT_FILE_NO_MAIN = ATT_FILE_NO_MAIN;
    }

    public String getATT_FILE_NO_MK() {
        return ATT_FILE_NO_MK;
    }

    public void setATT_FILE_NO_MK(String ATT_FILE_NO_MK) {
        this.ATT_FILE_NO_MK = ATT_FILE_NO_MK;
    }

    public String getRCP_PAT2() {
        return RCP_PAT2;
    }

    public void setRCP_PAT2(String RCP_PAT2) {
        this.RCP_PAT2 = RCP_PAT2;
    }

    public String getRCP_WAY2() {
        return RCP_WAY2;
    }

    public void setRCP_WAY2(String RCP_WAY2) {
        this.RCP_WAY2 = RCP_WAY2;
    }
}
