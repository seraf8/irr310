package com.irr310.server;

import com.irr310.common.engine.EventEngine;
import com.irr310.common.event.AddShipEvent;
import com.irr310.common.event.AddWorldObjectEvent;
import com.irr310.common.event.DefaultEngineEventVisitor;
import com.irr310.common.event.EngineEvent;
import com.irr310.common.event.QuitGameEvent;
import com.irr310.server.game.ShipFactory;
import com.irr310.server.game.world.Camera;
import com.irr310.server.game.world.Component;
import com.irr310.server.game.world.LinearEngine;
import com.irr310.server.game.world.Ship;

public class GameEngine extends EventEngine {

	public GameEngine() {
	}

	@Override
	protected void processEvent(EngineEvent e) {
		e.accept(new GameEngineEventVisitor());
	}

	private final class GameEngineEventVisitor extends DefaultEngineEventVisitor {
		@Override
		public void visit(QuitGameEvent event) {
			System.out.println("stopping game engine");
			setRunning(false);
		}

		@Override
		public void visit(AddWorldObjectEvent event) {
			Component o = null;
			
			switch (event.getType()) {
			case CAMERA:
				o = new Camera();
				break;
			case LINEAR_ENGINE:
				o = new LinearEngine();
				break;
			}

			if(event.getPosition() != null) {
				o.changeTranslation(event.getPosition());
			}
			
			/*if(event.getRotation() != null) {
				o.getRotation().set(event.getRotation());
			}*/
			
			if(event.getLinearSpeed() != null) {
				o.changeLinearSpeed(event.getLinearSpeed());
			}
			
			if(event.getRotationSpeed() != null) {
				o.changeRotationSpeed(event.getRotationSpeed());
			}
			

			o.setName(event.getName());
			
			GameServer.getInstance().getGame().getWorld().addObject(o);
		}
		
		
		@Override
		public void visit(AddShipEvent event) {
			Ship ship = null;

			switch (event.getType()) {
			case SIMPLE:
				ship = ShipFactory.createSimpleShip();
				ship.setOwner(event.getOwner());
				break;
			}

			
			//ship.getKernel().exec("leftProperller.setThrustTarget(1)");
			
			GameServer.getInstance().getGame().getWorld().addShip(ship, new Vect3(0, 0, 0));
		}
		
	}

	@Override
	protected void init() {
	}

	@Override
	protected void end() {
	}

}
