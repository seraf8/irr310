package com.irr310.common.world.system;

import com.irr310.common.tools.Vec3;
import com.irr310.common.world.World;
import com.irr310.common.world.system.Part.CollisionShape;
import com.irr310.server.GameServer;

public class Asteroid extends CelestialObject {

    public Asteroid(World world, long id, String name) {
        super(world, id, name);
        setPhysicalResistance(0.6);
        setDurabilityMax(250);
        setDurability(250);
        setSkin("asteroid");
        
        Part part = new Part(GameServer.pickNewId(), this);
        part.setMass(170.);
        part.setLinearDamping(0.001);
        part.setAngularDamping(0.01);
        part.setCollisionShape(CollisionShape.SPHERE);
        this.addPart(part);
        
        
        part.setShape(new Vec3(3, 3, 3));
        
    }

}
