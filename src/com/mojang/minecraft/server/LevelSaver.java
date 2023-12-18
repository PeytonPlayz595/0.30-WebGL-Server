package com.mojang.minecraft.server;

import com.mojang.minecraft.server.NetworkManager;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.zip.GZIPOutputStream;

final class LevelSaver extends Thread {

   // $FF: synthetic field
   private byte[] bytes;
   // $FF: synthetic field
   private NetworkManager networkManager;


   LevelSaver(NetworkManager var1, byte[] var2) {
	  super();
      this.networkManager = var1;
      this.bytes = var2;
   }

   public final void run() {
      try {
         ByteArrayOutputStream var1 = new ByteArrayOutputStream();
         Thread.sleep(500L);
         ByteArrayOutputStream var2 = var1;
         byte[] var3 = this.bytes;

         try {
            DataOutputStream var6;
            (var6 = new DataOutputStream(new GZIPOutputStream(var2))).writeInt(var3.length);
            var6.write(var3);
            var6.close();
         } catch (Exception var4) {
            throw new RuntimeException(var4);
         }

         Thread.sleep(500L);
         this.networkManager.a(var1.toByteArray());
      } catch (InterruptedException var5) {
         ;
      }
   }
}
