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
package com.barofutures.seco.model;

public class UserData {

    private String username;
    private String email;
    private String SETI;
    private String CMI;
    private String badge;
    private String like;
    private String SET;
    public static boolean isChallenge=false;

    public UserData() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserData(String username, String email, String SETI, String CMI, String badge, String like, String SET) {
        this.username = username;
        this.email = email;
        this.SETI = SETI;
        this.CMI = CMI;
        this.badge = badge;
        this.like = like;
        this.SET = SET;
    }

    public UserData(String username, String email) {
        this.username = username;
        this.email = email;
        this.SETI = "SETI_null";
        this.CMI = "CMI_null";
        this.badge = "badge_null";
        this.like = "like_null";
        this.SET = "SET_null";
    }

    // getter, setter


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSETI() {
        return SETI;
    }

    public void setSETI(String SETI) {
        this.SETI = SETI;
    }

    public String getCMI() {
        return CMI;
    }

    public void setCMI(String CMI) {
        this.CMI = CMI;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getSET() {
        return SET;
    }

    public void setSET(String SET) {
        this.SET = SET;
    }
}
