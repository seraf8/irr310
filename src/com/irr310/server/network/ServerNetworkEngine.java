package com.irr310.server.network;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import com.irr310.common.engine.EventEngine;
import com.irr310.common.event.DefaultEngineEventVisitor;
import com.irr310.common.event.EngineEvent;
import com.irr310.common.event.NetworkEvent;
import com.irr310.common.event.QuitGameEvent;
import com.irr310.common.network.NetworkMessage;
import com.irr310.common.network.protocol.LoginRequestMessage;
import com.irr310.common.network.protocol.LoginResponseMessage;
import com.irr310.common.network.protocol.ShipListMessage;
import com.irr310.common.network.protocol.SignupRequestMessage;
import com.irr310.common.network.protocol.SignupResponseMessage;
import com.irr310.common.world.Player;
import com.irr310.common.world.Ship;
import com.irr310.common.world.view.ShipView;
import com.irr310.server.GameServer;

public class ServerNetworkEngine extends EventEngine {

    public ServerNetworkEngine() {

        try {
            NetworkWorker worker = new NetworkWorker(this);
            new Thread(worker).start();
            new Thread(new NioServer(null, 22310, worker)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void processEvent(EngineEvent e) {
        e.accept(new NetworkEngineEventVisitor());
    }

    private final class NetworkEngineEventVisitor extends DefaultEngineEventVisitor {
        @Override
        public void visit(QuitGameEvent event) {
            System.out.println("stopping network engine");
            setRunning(false);

        }

        @Override
        public void visit(NetworkEvent event) {
            NetworkMessage message = event.getMessage();
            switch (message.getType()) {
                case LOGIN_REQUEST: {

                    if (event.getClient().isLogged()) {
                        event.getClient().send(new LoginResponseMessage(message.getResponseIndex(), false, "already logged as "
                                + event.getClient().getPlayer().getLogin(), null));
                        break;
                    }

                    LoginRequestMessage m = (LoginRequestMessage) message;
                    if (!GameServer.getInstance().isPlayerExist(m.login)) {
                        event.getClient().send(new LoginResponseMessage(message.getResponseIndex(), false, "unknown user", null));
                        break;
                    }

                    Player player = GameServer.getInstance().getPlayerByLogin(m.login);

                    if (!player.checkPassword(m.password)) {
                        event.getClient().send(new LoginResponseMessage(message.getResponseIndex(), false, "bad password", null));
                        break;
                    }

                    // Ok, you can login.

                    event.getClient().setPlayer(player);

                    event.getClient().send(new LoginResponseMessage(message.getResponseIndex(), true, "success", player.toView()));
                }
                    break;

                case SIGNUP_REQUEST: {
                    if (event.getClient().isLogged()) {
                        event.getClient().send(new SignupResponseMessage(message.getResponseIndex(), false, "already logged as "
                                + event.getClient().getPlayer().getLogin()));
                        break;
                    }

                    SignupRequestMessage m = (SignupRequestMessage) message;
                    if (GameServer.getInstance().isPlayerExist(m.login)) {
                        event.getClient().send(new SignupResponseMessage(message.getResponseIndex(), false, "username already used"));
                        break;
                    }
                    
                    // Ok, you can create the account
                    
                    GameServer.getInstance().createPlayer(m.login, m.password);
                    event.getClient().send(new SignupResponseMessage(message.getResponseIndex(), true, "success"));
                }
                    break;
                    
                case SHIP_LIST_REQUEST: {
                    if (!event.getClient().isLogged()) {
                        break;
                    }
                    
                    List<ShipView> shipList = new ArrayList<ShipView>();
                    
                    for(Ship ship : event.getClient().getPlayer().getShipList()) {
                        shipList.add(ship.toView());
                    }
                    
                    event.getClient().send(new ShipListMessage(message.getResponseIndex(), shipList));
                    
                    
                }
                    break;
                default:
                    System.err.println("Unsupported network type");
            }
        }

    }

    @Override
    protected void init() {
    }

    @Override
    protected void end() {
    }

}
