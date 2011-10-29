package com.irr310.server.network;

import java.nio.channels.SocketChannel;

import sun.io.Converters;

import com.irr310.common.network.protocol.HelloMessage;
import com.irr310.common.network.protocol.LoginRequestMessage;
import com.irr310.common.network.protocol.LoginResponseMessage;
import com.irr310.common.network.protocol.NetworkMessage;
import com.irr310.common.network.protocol.NetworkMessageType;
import com.irr310.common.tools.TypeConversion;

public class MessageParser {

    private final NioServer server;
    private final SocketChannel socketChannel;
    private byte[] headerBuffer;
    private byte[] dataBuffer;
    private int dataSize;
    private int dataBufferOffset;
    private int headerBufferOffset;
    private int messageTypeId;
    private final NetworkEngine networkEngine;
    private long messageResponseId;

    public MessageParser(NetworkEngine networkEngine, NioServer server, SocketChannel socketChannel) {
        this.networkEngine = networkEngine;
        this.server = server;
        this.socketChannel = socketChannel;
        headerBuffer = new byte[NetworkMessage.HEADER_SIZE];
        headerBufferOffset = 0;
        dataBuffer = null;
        dataSize = 0;
        dataBufferOffset = 0;
        messageTypeId = 0;
    }

    public void parseData(byte[] data) {
        parseData(data, 0);
    }
    
    public void parseData(byte[] data, int offset) {

        if (data.length == offset) {
            // No more data to parse

        } else if (headerBufferOffset < NetworkMessage.HEADER_SIZE) {
            // No complete header

            int missingInHeader = NetworkMessage.HEADER_SIZE - headerBufferOffset;
            int availableInBuffer = data.length - offset;

            int sizeToConsume = Math.min(missingInHeader, availableInBuffer);

            System.arraycopy(data, offset, headerBuffer, headerBufferOffset, sizeToConsume);
            headerBufferOffset += sizeToConsume;

            parseData(data, offset + sizeToConsume);
        } else {
            if (dataBuffer == null) {
                // New data buffer
                dataSize = TypeConversion.intFromByteArray(headerBuffer, 13);
                messageTypeId = TypeConversion.intFromByteArray(headerBuffer, 1);
                messageResponseId = TypeConversion.longFromByteArray(headerBuffer, 5);
                dataBuffer = new byte[dataSize];
                dataBufferOffset = 0;
            }

            int missingInData = dataSize - dataBufferOffset;
            int availableInBuffer = data.length - offset;

            int sizeToConsume = Math.min(missingInData, availableInBuffer);

            System.arraycopy(data, offset, dataBuffer, dataBufferOffset, sizeToConsume);
            dataBufferOffset += sizeToConsume;

            if (dataSize - dataBufferOffset == 0) {
                // The message is complete. Parse it.
                parseMessage();

                // Clean all buffers
                headerBufferOffset = 0;
                dataBufferOffset = 0;
                dataBuffer = null;
            }

            parseData(data, offset + sizeToConsume);

        }

    }

    private void parseMessage() {

        NetworkMessageType messageType = NetworkMessageType.values()[messageTypeId];

        NetworkMessage message = null;

        switch (messageType) {
            case HELLO:
                message = new HelloMessage();
                break;
            case LOGIN_REQUEST:
                message = new LoginRequestMessage();
                break;
            case LOGIN_RESPONSE:
                message = new LoginResponseMessage();
                break;
        }

        if (message != null) {
            message.setResponseIndex(messageResponseId);
            message.load(dataBuffer);

            networkEngine.pushMessage(socketChannel, message);
        }

    }

    
}
