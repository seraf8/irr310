package com.irr310.common.world;

import com.irr310.common.Game;
import com.irr310.common.tools.TransformMatrix;
import com.irr310.common.tools.Vect3;
import com.irr310.common.world.view.PartStateView;
import com.irr310.common.world.view.PartView;

public class Part extends GameEntity {

    // private final Vect3 position;
    private Double mass;
    private final Vect3 rotationSpeed;
    private final Vect3 linearSpeed;
    private final TransformMatrix transform;
    private Vect3 shape;
    private Player owner;
    private final WorldObject parentObject;
    
    public Part(long id, WorldObject parentObject) {
        super(id);
        this.parentObject = parentObject;
        rotationSpeed = Vect3.origin();
        linearSpeed = Vect3.origin();
        transform = TransformMatrix.identity();
        mass = 0.;
        shape = Vect3.one();
        owner = null;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public Double getMass() {
        return mass;
    }

    public Vect3 getRotationSpeed() {
        return rotationSpeed;
    }

    public Vect3 getLinearSpeed() {
        return linearSpeed;
    }

    public TransformMatrix getTransform() {
        return transform;
    }

    public Vect3 getShape() {
        return shape;
    }

    public void setShape(Vect3 shape) {
        this.shape = shape;
    }

    public PartView toView() {
        PartView partView = new PartView();
        partView.id = getId();
        partView.linearSpeed = linearSpeed;
        partView.mass = mass;
        partView.rotationSpeed = rotationSpeed;
        partView.shape = shape;
        partView.transform = transform;
        partView.ownerId = (owner == null ? -1 : owner.getId());
        return partView;
    }

    public void fromView(PartView partView) {
        linearSpeed.set(partView.linearSpeed);
        rotationSpeed.set(partView.rotationSpeed);
        mass = partView.mass;
        shape = partView.shape;
        transform.set(partView.transform.getData());
        owner = (partView.ownerId == -1 ? null : Game.getInstance().getWorld().getPlayerById(partView.ownerId));
    }

    public PartStateView toStateView() {
        PartStateView partStateView = new PartStateView();
        partStateView.id = getId();
        partStateView.linearSpeed = linearSpeed;
        partStateView.rotationSpeed = rotationSpeed;
        partStateView.transform = transform;
        return partStateView;
    }

    public void fromStateView(PartStateView partStateView) {
        /*if(!(linearSpeed.equals(partStateView.linearSpeed))) {
            System.out.println("fix speed from: "+linearSpeed.toString()+" to "+partStateView.linearSpeed);
        }*/
        /*if(!(transform.getTranslation().equals(partStateView.transform.getTranslation()))) {
            System.out.println("fix position from: "+transform.getTranslation().toString()+" to "+partStateView.transform.getTranslation().toString());
        }*/
        linearSpeed.set(partStateView.linearSpeed);
        rotationSpeed.set(partStateView.rotationSpeed);
        transform.set(partStateView.transform.getData());
    }

    public Player getOwner() {
        return owner;
    }
    
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public WorldObject getParentObject() {
        return parentObject;
    }
    
    
    
}
