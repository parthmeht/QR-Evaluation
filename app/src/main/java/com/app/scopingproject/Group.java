package com.app.scopingproject;

public class Group implements Comparable{
    private String name;
    private int noOfEvaluators, score;
    private float avgScore;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoOfEvaluators() {
        return noOfEvaluators;
    }

    public void setNoOfEvaluators(int noOfEvaluators) {
        this.noOfEvaluators = noOfEvaluators;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public float getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(float avgScore) {
        this.avgScore = avgScore;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", noOfEvaluators=" + noOfEvaluators +
                ", score=" + score +
                ", avgScore=" + avgScore +
                '}';
    }

    @Override
    public int compareTo(Object group) {
        int compareage= (int) ((Group)group).getAvgScore();
        return (int) (compareage-this.avgScore);
    }

}
