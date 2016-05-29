package se.tda367.flashcards.models;

import android.graphics.Shader;

/**
 * Created by ZlatanH on 2016-05-19.
 */
public class StatisticsItem {
    private String text;
    private Shader shader;
    private int color;
    private int statistic;

    public StatisticsItem(int color, int statistic) {
        this.color = color;
        this.statistic = statistic;
    }

    public Shader getShader() {
        return this.shader;
    }

    public void setShader (Shader shader) {
        this.shader = shader;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getStatistic() {
        return this.statistic;
    }

    public void setStatistic(int statistic) {
        this.statistic = statistic;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text){
        this.text = text;
    }

}
