package com.wallet.speedpe.Model;

public class HomePlan_Items {
    public Integer id;
    public String PlanName;
    public Integer Todays_Total, Todays_Available;
    public Integer InTotal_Total, InTotal_Available;

    public HomePlan_Items(Integer id, String planName, Integer todays_Total, Integer todays_Available, Integer inTotal_Total, Integer inTotal_Available) {
        this.id = id;
        PlanName = planName;
        Todays_Total = todays_Total;
        Todays_Available = todays_Available;
        InTotal_Total = inTotal_Total;
        InTotal_Available = inTotal_Available;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanName() {
        return PlanName;
    }

    public void setPlanName(String planName) {
        PlanName = planName;
    }

    public Integer getTodays_Total() {
        return Todays_Total;
    }

    public void setTodays_Total(Integer todays_Total) {
        Todays_Total = todays_Total;
    }

    public Integer getTodays_Available() {
        return Todays_Available;
    }

    public void setTodays_Available(Integer todays_Available) {
        Todays_Available = todays_Available;
    }

    public Integer getInTotal_Total() {
        return InTotal_Total;
    }

    public void setInTotal_Total(Integer inTotal_Total) {
        InTotal_Total = inTotal_Total;
    }

    public Integer getInTotal_Available() {
        return InTotal_Available;
    }

    public void setInTotal_Available(Integer inTotal_Available) {
        InTotal_Available = inTotal_Available;
    }
}
