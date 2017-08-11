package com.tdt.billionaire;

public class Question {
    public String id;
    public String ques;
    public String optA;
    public String optB;
    public String optC;
    public String optD;
    public String optR;

    public Question() {

    }

    public Question(String id, String ques, String optA, String optB, String optC, String optD, String optR) {
        this.id = id;
        this.ques = ques;
        this.optA = optA;
        this.optB = optB;
        this.optC = optC;
        this.optD = optD;
        this.optR = optR;
    }
}
