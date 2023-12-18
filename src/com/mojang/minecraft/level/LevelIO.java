package com.mojang.minecraft.level;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.LevelObjectInputStream;
import com.mojang.minecraft.server.MinecraftServer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class LevelIO {

   private MinecraftServer server;


   public LevelIO(MinecraftServer var1) {
      this.server = var1;
   }

   public final Level load(InputStream var1) {
      if(this.server != null) {
         this.server.a("Loading level");
      }

      if(this.server != null) {
         this.server.b("Reading..");
      }

      try {
         DataInputStream var10;
         if((var10 = new DataInputStream(new GZIPInputStream(var1))).readInt() != 656127880) {
            return null;
         } else {
            byte var12;
            if((var12 = var10.readByte()) > 2) {
               return null;
            } else if(var12 <= 1) {
               String var14 = var10.readUTF();
               String var15 = var10.readUTF();
               long var3 = var10.readLong();
               short var5 = var10.readShort();
               short var6 = var10.readShort();
               short var7 = var10.readShort();
               byte[] var8 = new byte[var5 * var6 * var7];
               var10.readFully(var8);
               var10.close();
               Level var11;
               (var11 = new Level()).setData(var5, var7, var6, var8);
               var11.name = var14;
               var11.creator = var15;
               var11.createTime = var3;
               return var11;
            } else {
               Level var2;
               LevelObjectInputStream var13;
               (var2 = (Level)(var13 = new LevelObjectInputStream(var10)).readObject()).initTransient();
               var13.close();
               return var2;
            }
         }
      } catch (Exception var9) {
         var9.printStackTrace();
         System.err.println("Failed to load level: " + var9.toString());
         return null;
      }
   }

   public static void save(Level var0, OutputStream var1) {
      try {
         DataOutputStream var3;
         (var3 = new DataOutputStream(new GZIPOutputStream(var1))).writeInt(656127880);
         var3.writeByte(2);
         ObjectOutputStream var4;
         (var4 = new ObjectOutputStream(var3)).writeObject(var0);
         var4.close();
      } catch (Exception var2) {
         var2.printStackTrace();
      }
   }
}
