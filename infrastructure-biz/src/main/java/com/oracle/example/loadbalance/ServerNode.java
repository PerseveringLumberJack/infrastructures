package com.oracle.example.loadbalance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerNode {

    private String nodeName;

    /** 设置的weight */
    private int weight;

    /** 当前weight */
    private int currentWeight;

    public void selected(int total) {
        currentWeight -= total;
    }

    public void increaseCurrentWeight() {
        currentWeight += weight;
    }


}
