// Copyright 2010 DEF
//
// This file is part of V3dScene.
//
// V3dScene is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// V3dScene is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with V3dScene.  If not, see <http://www.gnu.org/licenses/>.

package com.irr310.i3d;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.irr310.common.tools.Log;

import fr.def.iss.vd2.lib_v3d.V3DColor;


/**
 * Class representing a color with alpha channel
 * 
 * @author fberto
 */
public class Color {

    public static Color randomLightOpaqueColor() {

        float r = (float) Math.random();
        float g = (float) Math.random();
        float b = (float) Math.random();

        if (r + g + b < 0.3) {
            return randomLightOpaqueColor();
        } else {
            return new Color(r, g, b);
        }

    }
    
    public static Color randomDarkOpaqueColor() {

        float r = (float) Math.random();
        float g = (float) Math.random();
        float b = (float) Math.random();

        if (r + g + b > 0.7) {
            return randomDarkOpaqueColor();
        } else {
            return new Color(r, g, b);
        }

    }

    public float r = 0;
    public float g = 0;
    public float b = 0;
    public float a = 1;
    
    
    public static Pattern rgbHexaPattern = Pattern.compile("^#[0-9a-fA-F]{6}$");
    public static Pattern rgbaHexaPattern = Pattern.compile("^#[0-9a-fA-F]{8}$");
    public static Pattern rgbRgbPattern = Pattern.compile("^rgb\\(([0-9]{0,3}),([0-9]{0,3}),([0-9]{0,3})\\)$");
    public static Pattern rgbaRgbPattern = Pattern.compile("^rgb\\(([0-9]{0,3}),([0-9]{0,3}),([0-9]{0,3}),([0-9]{0,3})\\)$");

    /**
     * Initialize black opaque color
     */
    public Color() {
    }

    /**
     * Initialize color
     * 
     * @param r red between 0.0 and 1.0
     * @param g green between 0.0 and 1.0
     * @param b blue between 0.0 and 1.0
     * @param a alpha between 0.0 and 1.0
     */
    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    /**
     * Initialize color
     * 
     * @param r red between 0 and 255
     * @param g green between 0 and 255
     * @param b blue between 0 and 255
     * @param a alpha between 0.0 and 1.0
     */
    public Color(int r, int g, int b, float a) {
        this.r = r / 255f;
        this.g = g / 255f;
        this.b = b / 255f;
        this.a = a;
    }

    /**
     * Copy a color
     * 
     * @param color copied color
     */
    public Color(Color color) {
        this(color.r, color.g, color.b, color.a);
    }

    /**
     * Initialize opaque color
     * 
     * @param r red between 0.0 and 1.0
     * @param g green between 0.0 and 1.0
     * @param b blue between 0.0 and 1.0
     */
    public Color(float r, float g, float b) {
        this(r, g, b, 1f);
    }

    /**
     * Initialize opaque color
     * 
     * @param r red between 0 and 1
     * @param g green between 0 and 1
     * @param b blue between 0 and 1
     */
    public Color(int r, int g, int b) {
        this(r, g, b, 1f);
    }

    /**
     * Initialize for an awt color
     * 
     * @param color awt color
     */
    public Color(java.awt.Color color) {
        this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() / 255f);
    }

    
    
    public Color(String colorString) {
        
        // #ffeea2
        Matcher matcherRgbHexa = rgbHexaPattern.matcher(colorString);
        if(matcherRgbHexa.matches()) {
            r = Integer.parseInt(colorString.substring(1, 3), 16) / 255f;
            g = Integer.parseInt(colorString.substring(3, 5), 16) / 255f;
            b = Integer.parseInt(colorString.substring(5, 7), 16) / 255f;
            a = 1;
            return;
        } 
        
        Matcher matcherRgbaHexa = rgbaHexaPattern.matcher(colorString);
        if(matcherRgbaHexa.matches()) {
            r = Integer.parseInt(colorString.substring(1, 3), 16) / 255f;
            g = Integer.parseInt(colorString.substring(3, 5), 16) / 255f;
            b = Integer.parseInt(colorString.substring(5, 7), 16) / 255f;
            a = Integer.parseInt(colorString.substring(7, 9), 16) / 255f;
            return;
        }

        Log.error("Invalid color: "+colorString);
        
     // #ffeea2 or rgb(10,25,65)
        
        
        
        
    }

    public static Color transparent = new Color(1.0f, 1.0f, 1.0f, 0.0f);
    public static Color fadetoblack = new Color(0.0f, 0.0f, 0.0f, 0.0f);

    public static Color azure = new Color(204, 204, 255);
    public static Color black = new Color(0, 0, 0);
    public static Color blue = new Color(0, 0, 255);
    public static Color darkblue = new Color(0, 0, 51);
    public static Color darkgrey = new Color(64, 64, 64);
    public static Color emerald = new Color(1, 215, 88);
    public static Color fushia = new Color(244, 0, 161);
    public static Color green = new Color(0, 255, 0);
    public static Color grey = new Color(128, 128, 128);
    public static Color lightgrey = new Color(192, 192, 192);
    public static Color lavander = new Color(150, 131, 236);
    public static Color lilas = new Color(182, 102, 210);
    public static Color magenta = new Color(255, 0, 255);
    public static Color mauve = new Color(212, 115, 212);
    public static Color pink = new Color(253, 108, 158);
    public static Color red = new Color(255, 0, 0);
    public static Color violet = new Color(207, 160, 233);
    public static Color white = new Color(255, 255, 255);
    public static Color yellow = new Color(255, 255, 0);

    /**
     * Return a new instance of the same color
     * 
     * @return copied color
     */
    public Color copy() {
        return new Color(r, g, b, a);
    }

    public boolean isSame(Color sameColor) {
        return a == sameColor.a && r == sameColor.r && g == sameColor.g && b == sameColor.b;
    }

    public org.fenggui.util.Color toColor() {
        return new org.fenggui.util.Color(r, g, b, a);
    }

    public Color setAlpha(float alpha) {
        a = alpha;
        return this;
    }

    public org.fenggui.util.Color getFengguiColor() {
        return new org.fenggui.util.Color(r, g, b, a);
    }

    public static Color mix(Color baseColor, Color targetColor, float mix) {
        return new Color(baseColor.r * (1 - mix) + targetColor.r * mix, //
                            baseColor.g * (1 - mix) + targetColor.g * mix, //
                            baseColor.b * (1 - mix) + targetColor.b * mix, //
                            baseColor.a * (1 - mix) + targetColor.a * mix); //
    }
}
