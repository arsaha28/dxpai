package com.dxpai.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
public class PredictScore implements Comparable{

    private String category;
    private float score;

    @Override
    public int compareTo(@NotNull Object o) {
        PredictScore object = (PredictScore) o;
        if(this.score> object.score){
            return -11;
        }else if(this.score == object.score){
            return 0;
        }else {
            return 1;
        }
    }
}
