package com.irr310.common.event;


public interface EngineEventVisitor {

	public abstract void visit(QuitGameEvent event);

	public abstract void visit(StartEngineEvent event);
	
	public abstract void visit(InitEngineEvent event);
	
	public abstract void visit(PauseEngineEvent event);

	public abstract void visit(UseScriptEvent event);

	public abstract void visit(AddWorldObjectEvent event);

	public abstract void visit(CelestialObjectAddedEvent event);

	public abstract void visit(AddShipEvent event);

	public abstract void visit(WorldShipAddedEvent event);

    public abstract void visit(NetworkEvent event);

    public abstract void visit(PlayerAddedEvent event);

    public abstract void visit(KeyPressedEvent event);
    
    public abstract void visit(KeyReleasedEvent event);

    public abstract void visit(PlayerLoggedEvent event);

    public abstract void visit(MinimizeWindowEvent event);

    public abstract void visit(CollisionEvent event);

    public abstract void visit(DamageEvent event);

    public abstract void visit(MouseEvent event);
}
