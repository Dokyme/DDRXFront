package com.ddrx.ddrxfront.Model;

import java.util.List;

/**
 * Created by vincentshaw on 2018/3/23.
 */

public class CardFieldTrainingItem {
    private String name;
    private int type;
    private boolean name_visible;
    private int level;
    private int align;
    private List<TrainingSet> trainingSets;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isName_visible() {
        return name_visible;
    }

    public void setName_visible(boolean name_visible) {
        this.name_visible = name_visible;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAlign() {
        return align;
    }

    public void setAlign(int align) {
        this.align = align;
    }

    public List<TrainingSet> getTrainingSets() {
        return trainingSets;
    }

    public void setTrainingSets(List<TrainingSet> trainingSets) {
        this.trainingSets = trainingSets;
    }

    public CardFieldTrainingItem(String name, int type, boolean name_visible, int level, int align, List<TrainingSet> trainingSets) {
        this.name = name;
        this.type = type;
        this.name_visible = name_visible;
        this.level = level;
        this.align = align;
        this.trainingSets = trainingSets;

    }

}

class TrainingSet{
    public int start_period;
    public int end_period;
    public int training_type;
    public String training_data;
    public TrainingSet(int start_period, int end_period, int training_type, String training_data){
        this.start_period = start_period;
        this.end_period = end_period;
        this.training_type = training_type;
        this.training_data = training_data;
    }
}