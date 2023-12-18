package com.mojang.minecraft.server;

import com.mojang.minecraft.server.MinecraftServer;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

final class HeartbeatManager extends Thread {

   // $FF: synthetic field
   private String name;
   // $FF: synthetic field
   private MinecraftServer server;


   HeartbeatManager(MinecraftServer var1, String var2) {
	  super();
      this.server = var1;
      this.name = var2;
   }

   public final void run() {
      HttpURLConnection var1 = null;

      try {
         (var1 = (HttpURLConnection)(new URL("http://www.minecraft.net/heartbeat.jsp")).openConnection()).setRequestMethod("POST");
         var1.setDoOutput(true);
         var1.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
         var1.setRequestProperty("Content-Length", "" + Integer.toString(this.name.getBytes().length));
         var1.setRequestProperty("Content-Language", "en-US");
         var1.setUseCaches(false);
         var1.setDoInput(true);
         var1.setDoOutput(true);
         var1.connect();
         DataOutputStream var2;
         (var2 = new DataOutputStream(var1.getOutputStream())).writeBytes(this.name);
         var2.flush();
         var2.close();
         BufferedReader var9;
         String var3 = (var9 = new BufferedReader(new InputStreamReader(var1.getInputStream()))).readLine();
         if(!MinecraftServer.b(this.server).equals(var3)) {
            MinecraftServer.logger.info("To connect directly to this server, surf to: " + var3);
            PrintWriter var4;
            (var4 = new PrintWriter(new FileWriter("externalurl.txt"))).println(var3);
            var4.close();
            MinecraftServer.logger.info("(This is also in externalurl.txt)");
            MinecraftServer.a(this.server, var3);
         }

         var9.close();
         return;
      } catch (Exception var7) {
         MinecraftServer.logger.severe("Failed to assemble heartbeat: " + var7);
         var7.printStackTrace();
      } finally {
         if(var1 != null) {
            var1.disconnect();
         }

      }

   }
}
