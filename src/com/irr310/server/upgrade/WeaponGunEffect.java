package com.irr310.server.upgrade;

import com.irr310.common.world.Component;
import com.irr310.common.world.Player;
import com.irr310.common.world.Ship;
import com.irr310.common.world.item.Item;
import com.irr310.common.world.upgrade.Upgrade;
import com.irr310.common.world.upgrade.Upgrade.UpgradeCategory;
import com.irr310.common.world.upgrade.UpgradeOwnership;

public class WeaponGunEffect extends UpgradeEffect {

    
    @Override
    public void apply(UpgradeOwnership playerUpgrade) {
        Player player = playerUpgrade.getPlayer();
        int currentWeaponCount = 0;
        for(Item item: player.getInventory()) {
            if(item.getName().equals("weapon.gun")) {
                currentWeaponCount ++;
            }
        }
        
        for(Ship ship: player.getShipList()) {
            for(Component component: ship.getComponents()) {
                if(component.getName().equals("weapon.gun")) {
                    currentWeaponCount ++;
                }
            }
        }
        System.err.println("current count: "+currentWeaponCount);
        System.err.println("target count: "+playerUpgrade.getRank());
        
        
    }

    @Override
    public Upgrade generateUpgrade() {
        Upgrade weaponDamageUpgrade = new Upgrade();
        weaponDamageUpgrade.setCategory(UpgradeCategory.WEAPONS);
        weaponDamageUpgrade.setGlobalDescription("Buy a gun.");
        weaponDamageUpgrade.setTag("weapon.gun");
        weaponDamageUpgrade.setName("Gun");
        weaponDamageUpgrade.setInitialRank(1);
        for(int i = 0; i  < 21 ; i++) {
            weaponDamageUpgrade.addRank((int) (50 * Math.pow(1.5, i)), ""+(i+1)+" gun.");
        }
        return weaponDamageUpgrade;
    }
}
