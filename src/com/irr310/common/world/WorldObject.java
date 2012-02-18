package com.irr310.common.world;

import java.util.ArrayList;
import java.util.List;

public abstract class WorldObject extends GameEntity {

    private String name;
    protected List<Part> parts;
    private Part firstPart;
    private String skin;
    private double durabilityMax;
    private double durability;
    private double physicalResistance;
    private double heatResistance;

    public WorldObject(long id, String name) {
        super(id);
        this.name = name;
        firstPart = null;
        parts = new ArrayList<Part>();
        skin = "";
        durabilityMax = 100;
        durability = durabilityMax;
        physicalResistance = 0;
        heatResistance = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void addPart(Part part) {
        parts.add(part);
        if (firstPart == null) {
            firstPart = part;
        }
    }

    public Part getFirstPart() {
        return firstPart;
    }
    
    public void setSkin(String skin) {
        this.skin = skin;
    }
    
    public String getSkin() {
        return skin;
    }
    
    public double getDurability() {
        return durability;
    }
    
    public double getDurabilityMax() {
        return durabilityMax;
    }
    
    public double getHeatResistance() {
        return heatResistance;
    }
    
    public double getPhysicalResistance() {
        return physicalResistance;
    }
    
    public void setDurability(double durability) {
        this.durability = durability;
    }
    
    public void setDurabilityMax(double durabilityMax) {
        this.durabilityMax = durabilityMax;
    }
    
    public void setHeatResistance(double heatResistance) {
        this.heatResistance = heatResistance;
    }
    
    public void setPhysicalResistance(double physicalResistance) {
        this.physicalResistance = physicalResistance;
    }
    
    public boolean isBroken() {
        return this.durability <= 0;
    }
    

}
