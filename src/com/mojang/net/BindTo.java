package com.mojang.net;

import com.mojang.minecraft.server.MinecraftServer;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.LinkedList;
import java.util.List;

public final class BindTo {

   public ServerSocketChannel serverChannel;
   public MinecraftServer server;
   public List c = new LinkedList();


   public BindTo(int var1, MinecraftServer var2) {
      this.server = var2;
      this.serverChannel = ServerSocketChannel.open();
      this.serverChannel.socket().bind(new InetSocketAddress(var1));
      this.serverChannel.configureBlocking(false);
   }
}
