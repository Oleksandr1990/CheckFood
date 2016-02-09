package org.checkfood.model;

/**
 * Created by alexander on 11.01.2016.
 */
public class Additive {
    String number;
    String name;
    int danger;
    String link;
    private Boolean notAllowedEU;
    private Boolean notAllowedRU;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDanger() {
        return danger;
    }

    public void setDanger(int danger) {
        this.danger = danger;
    }


    public Boolean getNotAllowedEU() {
        return notAllowedEU;
    }

    public void setNotAllowedEU(Boolean notAllowedEU) {
        this.notAllowedEU = notAllowedEU;
    }

    public Boolean getNotAllowedRU() {
        return notAllowedRU;
    }

    public void setNotAllowedRU(Boolean notAllowedRU) {
        this.notAllowedRU = notAllowedRU;
    }

}
