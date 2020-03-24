package com.bipin.healthcare.Database;

public class Model {
    private String name;
    private String confirmed;
    private String ctodayschange;
    private String death;
    private String dtodayschange;
    private String recovered;
    private String serious;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getCtodayschange() {
        return ctodayschange;
    }

    public void setCtodayschange(String ctodayschange) {
        this.ctodayschange = ctodayschange;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public String getDtodayschange() {
        return dtodayschange;
    }

    public void setDtodayschange(String dtodayschange) {
        this.dtodayschange = dtodayschange;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getSerious() {
        return serious;
    }

    public void setSerious(String serious) {
        this.serious = serious;
    }
}
