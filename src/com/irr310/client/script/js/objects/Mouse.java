package com.irr310.client.script.js.objects;

public class Mouse {
	
	public Mouse() {
	}

	public int getX() {
	    return org.lwjgl.input.Mouse.getX();
	}
	
	public int getY() {
        return org.lwjgl.input.Mouse.getY();
    }
	
	public Vec2 getPosition() {
	    return new Vec2(org.lwjgl.input.Mouse.getX(), org.lwjgl.input.Mouse.getY());
	}
	
}
