package com.mojang.minecraft.server;

import com.mojang.minecraft.AvailableBlockType;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.a;
import com.mojang.minecraft.net.PacketType;
import com.mojang.minecraft.server.LevelSaver;
import com.mojang.minecraft.server.MinecraftServer;
import com.mojang.net.NetworkHandler;
import com.mojang.util.MathHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public final class NetworkManager {

   private static Logger logger = MinecraftServer.logger;
   public final NetworkHandler networkHandler;
   private final MinecraftServer server;
   private boolean l = false;
   private boolean m = false;
   public String playerName = "";
   public final int playerID;
   private ArrayList n = new ArrayList();
   private long time;
   private List p = new ArrayList();
   private int timeTillTalk = 0;
   public int xSpawn;
   public int ySpawn;
   public int zSpawn;
   public int pitchSpawn;
   public int yawSpawn;
   private boolean kicked = false;
   private int clicks = 0;
   private int t = 0;
   private volatile byte[] bytes = null;
   public boolean i = false;


   public NetworkManager(MinecraftServer var1, NetworkHandler var2, int var3) {
      this.server = var1;
      this.networkHandler = var2;
      this.playerID = var3;
      this.time = System.currentTimeMillis();
      var2.networkManager = this;
      Level var4 = var1.mainLevel;
      this.xSpawn = (var4.xSpawn << 5) + 16;
      this.ySpawn = (var4.ySpawn << 5) + 16;
      this.zSpawn = (var4.zSpawn << 5) + 16;
      this.yawSpawn = (int)(var4.rotSpawn * 256.0F / 360.0F);
      this.pitchSpawn = 0;
   }

   public final String toString() {
      NetworkHandler var1;
      return !this.l?(var1 = this.networkHandler).address:this.playerName + " (" + (var1 = this.networkHandler).address + ")";
   }

   public final void a(PacketType var1, Object[] var2) {
      if(!this.kicked) {
         if(var1 != PacketType.INDENTIFICATION) {
            if(var1 != PacketType.PING) {
               if(this.l && this.m) {
                  if(var1 == PacketType.PLAYER_SET_BLOCK) {
                     if(this.p.size() > 1200) {
                        this.d("Too much lag");
                     } else {
                        this.p.add(var2);
                     }
                  } else if(var1 == PacketType.CHAT_MESSAGE) {
                     String var7;
                     if((var7 = var2[1].toString().trim()).length() > 0) {
                        this.c(var7);
                     }

                  } else {
                     if(var1 == PacketType.POSITION_ROTATION) {
                        if(this.p.size() > 1200) {
                           this.d("Too much lag");
                           return;
                        }

                        this.p.add(var2);
                     }

                  }
               }
            }
         } else {
            byte var6 = ((Byte)var2[0]).byteValue();
            String var3 = ((String)var2[1]).trim();
            String var8 = (String)var2[2];
            char[] var4 = var3.toCharArray();

            for(int var5 = 0; var5 < var4.length; ++var5) {
               if(var4[var5] < 32 || var4[var5] > 127) {
                  this.d("Bad name!");
                  return;
               }
            }

            if(var3.length() < 2 || var3.length() > 16) {
               this.a("Illegal name.");
            }

            if(this.server.verifyNames && !var8.equals(this.server.saltGenerator.generate(var3))) {
               this.a("The name wasn\'t verified by minecraft.net!");
            } else if(var6 != 7) {
               this.a("Wrong protocol version.");
            } else if(this.server.playerManager2.containsPlayer(var3)) {
               this.a("You\'re banned!");
            } else if(this.server.adminSlot && !this.server.playerManager1.containsPlayer(var3) && this.server.a() < 1) {
               this.networkHandler.send(PacketType.DISCONNECT, new Object[]{"The server is full!"});
               logger.info(var3 + " connected, but got kicked because the server was almost full and there are reserved admin slots.");
               this.server.a(this);
               this.kicked = true;
            } else {
               NetworkManager var11;
               if((var11 = this.server.c(var3)) != null) {
                  var11.a("You logged in from another computer.");
               }

               logger.info(this + " logged in as " + var3);
               this.l = true;
               this.playerName = var3;
               this.networkHandler.send(PacketType.INDENTIFICATION, new Object[]{Byte.valueOf((byte)7), this.server.serverName, this.server.MOTD, Integer.valueOf(this.server.playerManager1.containsPlayer(var3)?100:0)});
               Level var9 = this.server.mainLevel;
               byte[] var10 = var9.copyBlocks();
               (new LevelSaver(this, var10)).start();
               this.server.playerManager4.addPlayer(var3);
            }
         }
      }
   }

   private void c(String var1) {
      var1 = var1.trim();
      this.timeTillTalk += var1.length() + 15 << 2;
      if(this.timeTillTalk > 600) {
         this.timeTillTalk = 760;
         this.b(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), "Too much chatter! Muted for eight seconds."});
         logger.info("Muting " + this.playerName + " for chatting too much");
      } else {
         char[] var2 = var1.toCharArray();

         for(int var3 = 0; var3 < var2.length; ++var3) {
            if(var2[var3] < 32 || var2[var3] > 127) {
               this.d("Bad chat message!");
               return;
            }
         }

         if(var1.startsWith("/")) {
            if(this.server.playerManager1.containsPlayer(this.playerName)) {
               this.server.a(this, var1.substring(1));
            } else {
               this.b(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), "You\'re not a server admin!"});
            }
         } else {
            logger.info(this.playerName + " says: " + var1);
            this.server.a(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(this.playerID), this.playerName + ": " + var1});
         }
      }
   }

   public final void a(String var1) {
      this.networkHandler.send(PacketType.DISCONNECT, new Object[]{var1});
      logger.info("Kicking " + this + ": " + var1);
      this.server.a(this);
      this.kicked = true;
   }

   private void d(String var1) {
      this.a("Cheat detected: " + var1);
   }

   public final void b(String var1) {
      this.b(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), var1});
   }

   public final void a(byte[] var1) {
      this.bytes = var1;
   }

   public final void a() {
      if(this.clicks >= 2) {
         this.clicks -= 2;
      }

      if(this.timeTillTalk > 0) {
         --this.timeTillTalk;
         if(this.timeTillTalk == 600) {
            this.b(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), "You can now talk again."});
            this.timeTillTalk = 300;
         }
      }

      Object[] var2;
      boolean var10000;
      if(this.p.size() > 0) {
         for(boolean var1 = true; this.p.size() > 0 && var1; var1 = var10000) {
            byte var3;
            byte var4;
            short var5;
            short var6;
            short var13;
            byte var10004;
            short var10001;
            short var10003;
            short var10002;
            if((var2 = (Object[])this.p.remove(0))[0] instanceof Short) {
               var10001 = ((Short)var2[0]).shortValue();
               var10002 = ((Short)var2[1]).shortValue();
               var10003 = ((Short)var2[2]).shortValue();
               var10004 = ((Byte)var2[3]).byteValue();
               var3 = ((Byte)var2[4]).byteValue();
               var4 = var10004;
               var5 = var10003;
               var6 = var10002;
               var13 = var10001;
               ++this.clicks;
               if(this.clicks == 100) {
                  this.d("Too much clicking!");
               } else {
                  Level var7 = this.server.mainLevel;
                  float var8 = (float)var13 - (float)this.xSpawn / 32.0F;
                  float var9 = (float)var6 - ((float)this.ySpawn / 32.0F - 1.62F);
                  float var10 = (float)var5 - (float)this.zSpawn / 32.0F;
                  var8 = var8 * var8 + var9 * var9 + var10 * var10;
                  var9 = 8.0F;
                  if(var8 >= var9 * var9) {
                     System.out.println("Distance: " + MathHelper.sqrt(var8));
                     this.d("Distance");
                  } else if(!AvailableBlockType.blocks.contains(a.b[var3])) {
                     this.d("Tile type");
                  } else if(var13 >= 0 && var6 >= 0 && var5 >= 0 && var13 < var7.width && var6 < var7.depth && var5 < var7.height) {
                     if(var4 == 0) {
                        if(var7.getTile(var13, var6, var5) != a.k.ab || this.server.playerManager1.containsPlayer(this.playerName)) {
                           var7.setTile(var13, var6, var5, 0);
                        }
                     } else {
                        a var16;
                        if((var16 = a.b[var7.getTile(var13, var6, var5)]) == null || var16 == a.l || var16 == a.m || var16 == a.n || var16 == a.o) {
                           if(this.i && var3 == a.e.ab) {
                              var7.setTile(var13, var6, var5, a.k.ab);
                           } else {
                              var7.setTile(var13, var6, var5, var3);
                           }

                           a.b[var3].a(var7, var13, var6, var5);
                        }
                     }
                  }
               }

               var10000 = true;
            } else {
               ((Byte)var2[0]).byteValue();
               var10001 = ((Short)var2[1]).shortValue();
               var10002 = ((Short)var2[2]).shortValue();
               var10003 = ((Short)var2[3]).shortValue();
               var10004 = ((Byte)var2[4]).byteValue();
               var3 = ((Byte)var2[5]).byteValue();
               var4 = var10004;
               var5 = var10003;
               var6 = var10002;
               var13 = var10001;
               if(var13 == this.xSpawn && var6 == this.ySpawn && var5 == this.zSpawn && var4 == this.yawSpawn && var3 == this.pitchSpawn) {
                  var10000 = true;
               } else {
                  boolean var21 = var13 == this.xSpawn && var6 == this.ySpawn && var5 == this.zSpawn;
                  if(this.t++ % 2 == 0) {
                     int var22 = var13 - this.xSpawn;
                     int var23 = var6 - this.ySpawn;
                     int var24 = var5 - this.zSpawn;
                     if(var22 >= 128 || var22 < -128 || var23 >= 128 || var23 < -128 || var24 >= 128 || var24 < -128 || this.t % 20 <= 1) {
                        this.xSpawn = var13;
                        this.ySpawn = var6;
                        this.zSpawn = var5;
                        this.yawSpawn = var4;
                        this.pitchSpawn = var3;
                        this.server.a(this, PacketType.POSITION_ROTATION, new Object[]{Integer.valueOf(this.playerID), Short.valueOf(var13), Short.valueOf(var6), Short.valueOf(var5), Byte.valueOf(var4), Byte.valueOf(var3)});
                        var10000 = false;
                        continue;
                     }

                     if(var13 == this.xSpawn && var6 == this.ySpawn && var5 == this.zSpawn) {
                        this.yawSpawn = var4;
                        this.pitchSpawn = var3;
                        this.server.a(this, PacketType.ROTATION_UPDATE, new Object[]{Integer.valueOf(this.playerID), Byte.valueOf(var4), Byte.valueOf(var3)});
                     } else if(var4 == this.yawSpawn && var3 == this.pitchSpawn) {
                        this.xSpawn = var13;
                        this.ySpawn = var6;
                        this.zSpawn = var5;
                        this.server.a(this, PacketType.POSITION_UPDATE, new Object[]{Integer.valueOf(this.playerID), Integer.valueOf(var22), Integer.valueOf(var23), Integer.valueOf(var24)});
                     } else {
                        this.xSpawn = var13;
                        this.ySpawn = var6;
                        this.zSpawn = var5;
                        this.yawSpawn = var4;
                        this.pitchSpawn = var3;
                        this.server.a(this, PacketType.POSITION_ROTATION_UPDATE, new Object[]{Integer.valueOf(this.playerID), Integer.valueOf(var22), Integer.valueOf(var23), Integer.valueOf(var24), Byte.valueOf(var4), Byte.valueOf(var3)});
                     }
                  }

                  var10000 = var21;
               }
            }
         }
      }

      if(!this.l && System.currentTimeMillis() - this.time > 5000L) {
         this.a("You need to log in!");
      } else if(this.bytes != null) {
         Level var11 = this.server.mainLevel;
         byte[] var15 = new byte[1024];
         int var20 = 0;
         int var19 = this.bytes.length;
         this.networkHandler.send(PacketType.LEVEL_INITIALIZE, new Object[0]);

         int var17;
         while(var19 > 0) {
            var17 = var19;
            if(var19 > var15.length) {
               var17 = var15.length;
            }

            System.arraycopy(this.bytes, var20, var15, 0, var17);
            this.networkHandler.send(PacketType.LEVEL_DATA_CHUNK, new Object[]{Integer.valueOf(var17), var15, Integer.valueOf((var20 + var17) * 100 / this.bytes.length)});
            var19 -= var17;
            var20 += var17;
         }

         this.networkHandler.send(PacketType.LEVEL_FINALIZE, new Object[]{Integer.valueOf(var11.width), Integer.valueOf(var11.depth), Integer.valueOf(var11.height)});
         this.networkHandler.send(PacketType.SPAWN_PLAYER, new Object[]{Integer.valueOf(-1), this.playerName, Integer.valueOf(this.xSpawn), Integer.valueOf(this.ySpawn), Integer.valueOf(this.zSpawn), Integer.valueOf(this.yawSpawn), Integer.valueOf(this.pitchSpawn)});
         this.server.a(this, PacketType.SPAWN_PLAYER, new Object[]{Integer.valueOf(this.playerID), this.playerName, Integer.valueOf((var11.xSpawn << 5) + 16), Integer.valueOf((var11.ySpawn << 5) + 16), Integer.valueOf((var11.zSpawn << 5) + 16), Integer.valueOf((int)(var11.rotSpawn * 256.0F / 360.0F)), Integer.valueOf(0)});
         this.server.a(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), this.playerName + " joined the game"});
         Iterator var18 = this.server.b().iterator();

         while(var18.hasNext()) {
            NetworkManager var12;
            if((var12 = (NetworkManager)var18.next()) != null && var12 != this && var12.l) {
               this.networkHandler.send(PacketType.SPAWN_PLAYER, new Object[]{Integer.valueOf(var12.playerID), var12.playerName, Integer.valueOf(var12.xSpawn), Integer.valueOf(var12.ySpawn), Integer.valueOf(var12.zSpawn), Integer.valueOf(var12.yawSpawn), Integer.valueOf(var12.pitchSpawn)});
            }
         }

         this.m = true;
         var17 = 0;

         while(var17 < this.n.size()) {
            PacketType var14 = (PacketType)this.n.get(var17++);
            var2 = (Object[])((Object[])this.n.get(var17++));
            this.b(var14, var2);
         }

         this.n = null;
         this.bytes = null;
      }
   }

   public final void b(PacketType var1, Object ... var2) {
      if(!this.m) {
         this.n.add(var1);
         this.n.add(var2);
      } else {
         this.networkHandler.send(var1, var2);
      }
   }

   public final void a(Exception var1) {
      if(var1 instanceof IOException) {
         logger.info(this + " lost connection suddenly. (" + var1 + ")");
      } else {
         logger.warning(this + ":" + var1);
         logger.log(java.util.logging.Level.WARNING, "Exception handling " + this + "!", var1);
         var1.printStackTrace();
      }

      this.server.a(this, PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), this.playerName + " left the game"});
      MinecraftServer.b(this);
   }

}
