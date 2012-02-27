package com.irr310.common.tools;


public class Vec2 {

    public final double y;
    public final double x;

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2(Vec2 v) {
        this.x = v.x;
        this.y = v.y;
    }
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vec2 minus(Vec2 v) {
        return new Vec2(x - v.x, y - v.y);
    }
    
    public double length() {
        return Math.sqrt(x * x + y * y);
    }
    public Vec2 divide(double d) {
        return new Vec2(x / d, y / d);
    }

    public Vec2 multiply(double d) {
        return new Vec2(x * d, y * d);
    }
    
    
    public Vec2 normalize() {
        return this.divide(length());
    }

    public Vec2 add(Vec2 v) {
        return new Vec2(v.x + x, v.y + y);
    }
    
    public Vec2 plus(Vec2 v) {
        return new Vec2(v.x + x, v.y + y);
    }
    
    @Override
    public String toString() {
        return "[x=" + x + ", y=" + y + "]";
    }
}
