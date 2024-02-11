package com.dxpai.model;

import lombok.Data;

@Data
public class Layover {
    public int duration;
    public String name;
    public String id;
    public boolean overnight;
}
