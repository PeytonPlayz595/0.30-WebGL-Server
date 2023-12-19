package net.io;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

@WebSocket
public class WebSocketChannel {
	
    private static Set<Session> sessions;
    private final Queue<ByteBuffer> writeQueue;
    private final ByteBuffer readBuffer;
    private boolean reading;
    private boolean isConnected = false;
    
    public WebSocketChannel(int port) {
    	writeQueue = new LinkedList<ByteBuffer>();
        readBuffer = ByteBuffer.allocate(1024);
        reading = false;
        sessions = new HashSet<Session>();
        writeQueue.clear();
        
    	try {
			this.startServer(port);
			isConnected = true;
		} catch (Exception e) {
			isConnected = false;
			e.printStackTrace();
		}
    }

    @OnWebSocketConnect
    private void onConnect(Session session) {
        sessions.add(session);
    }
    
    @OnWebSocketMessage
    private void onMessage(byte[] data, int offset, int length) {
    	for (byte b : data) {
            readBuffer.put(b);
        }

        if (reading) {
            readBuffer.flip();
            reading = false;
        }
    }

    @OnWebSocketClose
    private void onClose(Session session, int statusCode, String reason) {
        sessions.remove(session);
    }

    @OnWebSocketError
    private void onError(Session session, Throwable throwable) {
        System.err.println(throwable.getMessage());
    }

    private void startServer(int port) throws Exception {
    	org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(port);
    	org.eclipse.jetty.servlet.ServletContextHandler context = new org.eclipse.jetty.servlet.ServletContextHandler();
        context.setContextPath("");
        context.addServlet(MyWebSocketServlet.class, "");

        server.setHandler(context);
        server.start();
    }

    public void stopServer() {
        for (Session session : sessions) {
            session.close();
        }
    }
    
    public boolean connectionOpen() {
        return isConnected;
    }
    
    public int read(ByteBuffer buffer) {
        if (reading) {
            return 0;
        }

        if (readBuffer.hasRemaining()) {
            int bytesToRead = Math.min(buffer.remaining(), readBuffer.remaining());
            for (int i = 0; i < bytesToRead; i++) {
                buffer.put(readBuffer.get());
            }
            return bytesToRead;
        } else {
            readBuffer.clear();
            reading = true;
            return 0;
        }
    }

    public int write(ByteBuffer buffer) {
        if (!isConnected) {
            return 0;
        }

        int bytesToWrite = 0;
        for (Session session : sessions) {
            if (session.isOpen()) {
            	int sessionBytesToWrite = Math.min(buffer.remaining(), session.getPolicy().getMaxTextMessageBufferSize());
                if (sessionBytesToWrite > 0) {
                    byte[] data = new byte[sessionBytesToWrite];
                    buffer.get(data);
                    session.getRemote().sendBytesByFuture(ByteBuffer.wrap(data));
                }
                bytesToWrite += sessionBytesToWrite;
            }
        }

        if (buffer.hasRemaining()) {
            writeQueue.offer(buffer);
        }

        return bytesToWrite;
    }
}