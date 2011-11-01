package com.irr310.server;

import java.util.ArrayList;
import java.util.List;

import com.irr310.common.Game;
import com.irr310.common.engine.FramerateEngine;
import com.irr310.common.event.AddShipEvent;
import com.irr310.common.event.AddWorldObjectEvent;
import com.irr310.common.event.DefaultEngineEventVisitor;
import com.irr310.common.event.EngineEvent;
import com.irr310.common.event.PauseEngineEvent;
import com.irr310.common.event.QuitGameEvent;
import com.irr310.common.event.StartEngineEvent;
import com.irr310.common.event.WorldShipAddedEvent;
import com.irr310.common.tools.Vect3;
import com.irr310.common.world.Component;
import com.irr310.common.world.Ship;
import com.irr310.common.world.capacity.Capacity;
import com.irr310.common.world.capacity.LinearEngineCapacity;
import com.irr310.server.controller.CapacityController;
import com.irr310.server.controller.LinearEngineController;
import com.irr310.server.game.ShipFactory;

public class ServerGameEngine extends FramerateEngine {

    private List<CapacityController> capacityControllers;

    public ServerGameEngine() {
        capacityControllers = new ArrayList<CapacityController>();
        framerate = new Duration(15000000);
    }

    @Override
    protected void processEvent(EngineEvent e) {
        e.accept(new GameEngineEventVisitor());
    }

    @Override
    protected void frame() {
        for (CapacityController controller : capacityControllers) {
            controller.update(framerate.getSeconds());
        }
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
                    // o = new Camera(GameServer.pickNewId());
                    break;
                case LINEAR_ENGINE:
                    // o = new LinearEngine(GameServer.pickNewId());
                    break;
            }

            if (event.getPosition() != null) {
                o.changeTranslation(event.getPosition());
            }

            /*
             * if(event.getRotation() != null) {
             * o.getRotation().set(event.getRotation()); }
             */

            if (event.getLinearSpeed() != null) {
                o.changeLinearSpeed(event.getLinearSpeed());
            }

            if (event.getRotationSpeed() != null) {
                o.changeRotationSpeed(event.getRotationSpeed());
            }

            o.setName(event.getName());

            Game.getInstance().getWorld().addObject(o);
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

            // ship.getKernel().exec("leftProperller.setThrustTarget(1)");

            GameServer.getInstance().getWorld().addShip(ship, new Vect3(0, 0, 0));
        }

        @Override
        public void visit(WorldShipAddedEvent event) {
            Ship ship = event.getShip();

            for (Component component : ship.getComponents()) {
                for (Capacity capacity : component.getCapacities()) {
                    if (capacity instanceof LinearEngineCapacity) {
                        addCapacityController(new LinearEngineController(component, (LinearEngineCapacity) capacity));
                    }

                }
            }

        }
        
        @Override
        public void visit(StartEngineEvent event) {
            pause(false);
        }

        @Override
        public void visit(PauseEngineEvent event) {
            pause(true);
        }

        

    }
    
    private void addCapacityController(CapacityController controller) {
        capacityControllers.add(controller);
    }

    @Override
    protected void init() {
    }

    @Override
    protected void end() {
    }

}