package com.irr310.client.graphics.gui;

import java.util.ArrayList;
import java.util.List;

import org.fenggui.event.ButtonPressedEvent;
import org.fenggui.event.IButtonPressedListener;

import fr.def.iss.vd2.lib_v3d.V3DColor;
import fr.def.iss.vd2.lib_v3d.gui.V3DButton;
import fr.def.iss.vd2.lib_v3d.gui.V3DContainer;
import fr.def.iss.vd2.lib_v3d.gui.V3DGuiRectangle;
import fr.def.iss.vd2.lib_v3d.gui.V3DLabel;
import fr.def.iss.vd2.lib_v3d.gui.V3DGuiComponent.GuiYAlignment;

public class UpgradeMenu extends V3DContainer{

    int tabOffset = 40;
    List<GuiTab> tabs = new ArrayList<GuiTab>();
    
    public UpgradeMenu() {
        setPosition(-2, 123);
        setSize(500, 600);
        setyAlignment(GuiYAlignment.BOTTOM);

        V3DGuiRectangle upgradeBase = new V3DGuiRectangle();
        upgradeBase.setyAlignment(GuiYAlignment.BOTTOM);
        upgradeBase.setPosition(0, 0);
        upgradeBase.setSize(500, 600);
        upgradeBase.setBorderWidth(4);
        upgradeBase.setFillColor(GuiConstants.irrFill);
        upgradeBase.setBorderColor(GuiConstants.irrGreen);
        add(upgradeBase);

        V3DGuiRectangle upgradeTop = new V3DGuiRectangle();
        upgradeTop.setyAlignment(GuiYAlignment.BOTTOM);
        upgradeTop.setPosition(0, 0);
        upgradeTop.setBorderWidth(4);
        upgradeTop.setSize(500, 30);
        upgradeTop.setFillColor(GuiConstants.irrGreen);
        upgradeTop.setBorderColor(GuiConstants.irrGreen);
        
        
        //Tabs
        addTab(new UpgradeWeaponTab());
        addTab(new UpgradeWeaponTab());
        addTab(new UpgradeWeaponTab());
        addTab(new UpgradeWeaponTab());
        addTab(new UpgradeWeaponTab());
        addTab(new UpgradeWeaponTab());
//        addTab("Weapon", new UpgradeWeaponTab());
//        addTab("Defenses", new UpgradeWeaponTab());
//        addTab("Ship", new UpgradeWeaponTab());
//        addTab("Monolith", new UpgradeWeaponTab());
//        addTab("Tools", new UpgradeWeaponTab());
//        
        //Tab content
        V3DGuiRectangle tabContent= new V3DGuiRectangle();
        tabContent.setyAlignment(GuiYAlignment.TOP);
        tabContent.setPosition(140, 20);
        tabContent.setSize(340, 530);
        tabContent.setBorderWidth(2);
        tabContent.setFillColor(GuiConstants.irrFill);
        tabContent.setBorderColor(GuiConstants.irrGreen);
        add(tabContent);
        
        
        add(upgradeTop);
        
        setActive(tabs.get(0));
    }

    private void addTab(final GuiTab tabComponent) {
        
        tabs.add(tabComponent);
        V3DContainer labelPane = tabComponent.getLabelPane();
        
        labelPane.setPosition(10, tabOffset);
        
        add(labelPane);
        
        
        
        V3DButton button = new V3DButton("");
        button.setPadding(labelPane.getHeight(), labelPane.getWidth(), 0,0);
        button.setPosition(10, tabOffset);
        button.getFenGUIWidget().addButtonPressedListener(new IButtonPressedListener() {
            
            @Override
            public void buttonPressed(ButtonPressedEvent e) {
                setActive(tabComponent);
            }
        });
        add(button);

        tabOffset  += labelPane.getHeight();

        
        
    }

    protected void setActive(GuiTab tabComponent) {
        for(GuiTab tab : tabs) {
            tab.setActive(tab == tabComponent);
        }
    }
    
    
    
}