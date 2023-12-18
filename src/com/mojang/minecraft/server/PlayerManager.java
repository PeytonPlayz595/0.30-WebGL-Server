package com.mojang.minecraft.server;

import com.mojang.minecraft.server.MinecraftServer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

public final class PlayerManager {

   private static Logger logger = MinecraftServer.logger;
   private String name;
   private File file;
   private Set players = new HashSet();


   public PlayerManager(String var1, File var2) {
      this.name = var1;
      this.file = var2;
      this.load();
   }

   public final void addPlayer(String var1) {
      var1 = var1.toLowerCase();
      this.players.add(var1);
      this.save();
   }

   public final void removePlayer(String var1) {
      var1 = var1.toLowerCase();
      this.players.remove(var1);
      this.save();
   }

   public final boolean containsPlayer(String var1) {
      var1 = var1.toLowerCase();
      return this.players.contains(var1);
   }

   private void load() {
      try {
         BufferedReader var1 = new BufferedReader(new FileReader(this.file));
         String var2 = null;

         while((var2 = var1.readLine()) != null) {
            var2 = var2.toLowerCase();
            this.players.add(var2);
         }

         var1.close();
      } catch (IOException var4) {
         try {
            this.file.createNewFile();
         } catch (IOException var3) {
            var3.printStackTrace();
         }

         logger.warning("Failed to load player list \"" + this.name + "\". (" + var4 + ")");
      }
   }

   private void save() {
      try {
         PrintWriter var1 = new PrintWriter(new FileWriter(this.file));
         Iterator var2 = this.players.iterator();

         while(var2.hasNext()) {
            String var3 = (String)var2.next();
            var1.println(var3);
         }

         var1.close();
      } catch (IOException var4) {
         logger.warning("Failed to save player list \"" + this.name + "\". (" + var4 + ")");
      }
   }

}
