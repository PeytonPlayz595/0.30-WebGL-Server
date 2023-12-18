package com.mojang.net;

import com.mojang.minecraft.server.MinecraftServer;
import java.util.LinkedList;
import java.util.List;

public final class BindTo {
   public MinecraftServer server;
   public List<NetworkHandler> c = new LinkedList<NetworkHandler>();


   public BindTo(MinecraftServer var2) {
      this.server = var2;
   }
}
