package net.io;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

@WebSocket
public class WebSocketNetworkManager extends WebSocketServlet {
    private static Set<Session> sessions = new HashSet<Session>();
    public static int port;
    private static Queue<ByteBuffer> receivedBuffers = new ArrayDeque<ByteBuffer>();
    private static ByteBuffer writeBuffer;

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.register(WebSocketNetworkManager.class);
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        sessions.add(session);
    }
    
    @OnWebSocketMessage
    public void onMessage(byte[] data, int offset, int length) {
        ByteBuffer buffer = ByteBuffer.wrap(data, offset, length);
        receivedBuffers.add(buffer);
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        sessions.remove(session);
    }

    @OnWebSocketError
    public void onError(Session session, Throwable throwable) {
        System.out.println(throwable.getMessage());
    }

    public static void startServer(int port) throws Exception {
    	WebSocketNetworkManager.port = port;

        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(port);
        WebSocketHandler wsHandler = new WebSocketHandler() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.setCreator(new WebSocketCreator() {
					public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
						return new WebSocketNetworkManager();
					}
				});
            }
        };
        server.setHandler(wsHandler);
        server.start();
    }

    public static void stopServer() {
        for (Session session : sessions) {
            session.close();
        }
    }
    
    public static void read(ByteBuffer buf) {
        int bytesRead = 0;
        while (bytesRead < buf.capacity()) {
            if (!receivedBuffers.isEmpty()) {
                ByteBuffer receivedBuffer = receivedBuffers.peek();
                int remainingBytes = buf.capacity() - bytesRead;
                int bytesToRead = Math.min(receivedBuffer.remaining(), remainingBytes);
                receivedBuffer.get(buf.array(), bytesRead, bytesToRead);
                bytesRead += bytesToRead;

                if (receivedBuffer.remaining() == 0) {
                    receivedBuffers.poll();
                }
            } else {
                break;
            }
        }
    }

	public static void write(ByteBuffer buf) {
		if (writeBuffer == null) {
            writeBuffer = ByteBuffer.allocate(buf.capacity());
        }

        int bytesToWrite = Math.min(buf.remaining(), writeBuffer.remaining());
        writeBuffer.put(buf.array(), buf.position(), bytesToWrite);
        buf.position(buf.position() + bytesToWrite);

        if (writeBuffer.remaining() == 0 || buf.remaining() == 0) {
            writeBuffer.flip();
            byte[] data = new byte[writeBuffer.remaining()];
            writeBuffer.get(data);
            for(Session session : sessions) {
            	try {
					session.getRemote().sendBytes(ByteBuffer.wrap(data));
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
            writeBuffer.clear();
        }

        if (buf.remaining() > 0) {
            write(buf);
        }
	}
}