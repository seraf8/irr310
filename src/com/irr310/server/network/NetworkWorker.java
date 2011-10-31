package com.irr310.server.network;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.irr310.common.event.NetworkEvent;
import com.irr310.common.network.MessageParser;
import com.irr310.common.network.NetworkMessage;

public class NetworkWorker implements Runnable {
	private List queue = new LinkedList();
	private Map<SocketChannel, MessageParser> messageParsers;
    private final NetworkEngine networkEngine;
	
	public NetworkWorker(NetworkEngine networkEngine) {
        this.networkEngine = networkEngine;
        messageParsers = new HashMap<SocketChannel, MessageParser>();
    }
	public void processData(NioServer server, SocketChannel socket, byte[] data, int count) {
		byte[] dataCopy = new byte[count];
		System.arraycopy(data, 0, dataCopy, 0, count);
		synchronized(queue) {
			queue.add(new ServerDataEvent(server, socket, dataCopy));
			queue.notify();
		}
	}
	
	public void run() {
		ServerDataEvent dataEvent;
		
		while(true) {
			// Wait for data to become available
			synchronized(queue) {
				while(queue.isEmpty()) {
					try {
						queue.wait();
					} catch (InterruptedException e) {
					}
				}
				dataEvent = (ServerDataEvent) queue.remove(0);
			}
			
			
			messageParsers.get(dataEvent.socket).parseData(dataEvent.data);
			
			
            // // Return to sender
            // System.out.println("echo");
            // dataEvent.server.send(dataEvent.socket, "echo - ".getBytes());
            // dataEvent.server.send(dataEvent.socket, dataEvent.data);
		}
	}
	//
	//
    public void accept(final NioServer server, final SocketChannel socketChannel) {
        messageParsers.put(socketChannel, new MessageParser() {

            NetworkClient client = new NetworkClient(networkEngine, server, socketChannel);
            
            @Override
            public void processMessage(NetworkMessage message) {
                client.getEngine().pushEvent(new NetworkEvent(message, client));
            }
            
        });        
    }
    public void close(SocketChannel socketChannel) {
        messageParsers.remove(socketChannel);
    }
}
