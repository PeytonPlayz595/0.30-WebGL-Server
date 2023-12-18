package com.mojang.minecraft.server;

import com.mojang.minecraft.level.LevelIO;
import com.mojang.minecraft.level.generator.LevelGenerator;
import com.mojang.minecraft.net.PacketType;
import com.mojang.minecraft.server.ConsoleInput;
import com.mojang.minecraft.server.HeartbeatManager;
import com.mojang.minecraft.server.LogFormatter;
import com.mojang.minecraft.server.LogHandler;
import com.mojang.minecraft.server.NetworkManager;
import com.mojang.minecraft.server.PlayerManager;
import com.mojang.minecraft.server.SaltGenerator;
import com.mojang.minecraft.server.UNKNOWN0;
import com.mojang.net.BindTo;
import com.mojang.net.NetworkHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinecraftServer implements Runnable {

   static Logger logger = Logger.getLogger("MinecraftServer");
   static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
   private BindTo bindTo;
   private Map m = new HashMap();
   private List n = new ArrayList();
   private List o = new ArrayList();
   private int maxPlayers;
   private Properties properties = new Properties();
   public com.mojang.minecraft.level.Level mainLevel;
   private boolean public_ = false;
   public String serverName;
   public String MOTD;
   public boolean adminSlot;
   private NetworkManager[] networkManager;
   public PlayerManager playerManager1 = new PlayerManager("Admins", new File("admins.txt"));
   public PlayerManager playerManager2 = new PlayerManager("Banned", new File("banned.txt"));
   private PlayerManager playerManager3 = new PlayerManager("Banned (IP)", new File("banned-ip.txt"));
   public PlayerManager playerManager4 = new PlayerManager("Players", new File("players.txt"));
   private List v = new ArrayList();
   private String salt = "" + (new Random()).nextLong();
   private String x = "";
   public SaltGenerator saltGenerator;
   public boolean verifyNames;
   private boolean growTrees;
   private int maxConnections;


   public MinecraftServer() {
      this.saltGenerator = new SaltGenerator(this.salt);
      this.verifyNames = false;
      this.growTrees = false;

      try {
         this.properties.load(new FileReader("server.properties"));
      } catch (Exception var4) {
         logger.warning("Failed to load server.properties!");
      }

      try {
         this.serverName = this.properties.getProperty("server-name", "Minecraft Server");
         this.MOTD = this.properties.getProperty("motd", "Welcome to my Minecraft Server!");
         this.maxPlayers = Integer.parseInt(this.properties.getProperty("max-players", "16"));
         this.public_ = Boolean.parseBoolean(this.properties.getProperty("public", "true"));
         this.verifyNames = Boolean.parseBoolean(this.properties.getProperty("verify-names", "true"));
         this.growTrees = Boolean.parseBoolean(this.properties.getProperty("grow-trees", "false"));
         this.adminSlot = Boolean.parseBoolean(this.properties.getProperty("admin-slot", "false"));
         if(this.maxPlayers < 1) {
            this.maxPlayers = 1;
         }

         if(this.maxPlayers > 32) {
            this.maxPlayers = 32;
         }

         this.maxConnections = Integer.parseInt(this.properties.getProperty("max-connections", "3"));
         this.properties.setProperty("server-name", this.serverName);
         this.properties.setProperty("motd", this.MOTD);
         this.properties.setProperty("max-players", "" + this.maxPlayers);
         this.properties.setProperty("public", "" + this.public_);
         this.properties.setProperty("verify-names", "" + this.verifyNames);
         this.properties.setProperty("max-connections", "3");
         this.properties.setProperty("grow-trees", "" + this.growTrees);
         this.properties.setProperty("admin-slot", "" + this.adminSlot);
      } catch (Exception var3) {
         var3.printStackTrace();
         logger.warning("server.properties is broken! Delete it or fix it!");
         System.exit(0);
      }

      if(!this.verifyNames) {
         logger.warning("######################### WARNING #########################");
         logger.warning("verify-names is set to false! This means that anyone who");
         logger.warning("connects to this server can choose any username he or she");
         logger.warning("wants! This includes impersonating an OP!");
         if(this.public_) {
            logger.warning("");
            logger.warning("AND SINCE THIS IS A PUBLIC SERVER, IT WILL HAPPEN TO YOU!");
            logger.warning("");
         }

         logger.warning("If you wish to fix this, edit server.properties, and change");
         logger.warning("verify-names to true.");
         logger.warning("###########################################################");
      }

      try {
         this.properties.store(new FileWriter("server.properties"), "Minecraft server properties");
      } catch (Exception var2) {
         logger.warning("Failed to save server.properties!");
      }

      this.networkManager = new NetworkManager[this.maxPlayers];
      this.bindTo = new BindTo(this);
      (new ConsoleInput(this)).start();
   }

   public final void a(NetworkHandler var1) {
      NetworkManager var2;
      if((var2 = (NetworkManager)this.m.get(var1)) != null) {
         this.playerManager4.removePlayer(var2.playerName);
         logger.info(var2 + " disconnected");
         this.m.remove(var2.networkHandler);
         this.n.remove(var2);
         if(var2.playerID >= 0) {
            this.networkManager[var2.playerID] = null;
         }

         this.a(PacketType.DESPAWN_PLAYER, new Object[]{Integer.valueOf(var2.playerID)});
      }

   }

   private void b(NetworkHandler var1) {
      this.o.add(new UNKNOWN0(var1, 100));
   }

   public final void a(NetworkManager var1) {
      this.o.add(new UNKNOWN0(var1.networkHandler, 100));
   }

   public static void b(NetworkManager var0) {
      var0.networkHandler.close();
   }

   public final void a(PacketType var1, Object ... var2) {
      for(int var3 = 0; var3 < this.n.size(); ++var3) {
         try {
            ((NetworkManager)this.n.get(var3)).b(var1, var2);
         } catch (Exception var5) {
            ((NetworkManager)this.n.get(var3)).a(var5);
         }
      }

   }

   public final void a(NetworkManager var1, PacketType var2, Object ... var3) {
      for(int var4 = 0; var4 < this.n.size(); ++var4) {
         if(this.n.get(var4) != var1) {
            try {
               ((NetworkManager)this.n.get(var4)).b(var2, var3);
            } catch (Exception var6) {
               ((NetworkManager)this.n.get(var4)).a(var6);
            }
         }
      }

   }

   public void run() {
      logger.info("Now accepting input on 8080");
      int var1 = 50000000;
      int var2 = 500000000;

      try {
         long var3 = System.nanoTime();
         long var5 = System.nanoTime();
         int var7 = 0;

         while(true) {
            this.d();

            for(; System.nanoTime() - var5 > (long)var1; ++var7) {
               var5 += (long)var1;
               this.c();
               if(var7 % 1200 == 0) {
                  MinecraftServer var8 = this;

                  try {
                     new LevelIO(var8);
                     LevelIO.save(var8.mainLevel, new FileOutputStream("server_level.dat"));
                  } catch (Exception var11) {
                     logger.severe("Failed to save the level! " + var11);
                  }

                  logger.info("Level saved! Load: " + this.n.size() + "/" + this.maxPlayers);
               }

               if(var7 % 900 == 0) {
                  HashMap var9;
                  (var9 = new HashMap()).put("name", this.serverName);
                  var9.put("users", Integer.valueOf(this.n.size()));
                  var9.put("max", Integer.valueOf(this.maxPlayers - (this.adminSlot?1:0)));
                  var9.put("public", Boolean.valueOf(this.public_));
                  var9.put("salt", this.salt);
                  var9.put("admin-slot", Boolean.valueOf(this.adminSlot));
                  var9.put("version", Byte.valueOf((byte)7));
                  String var13 = a((Map)var9);
                  (new HeartbeatManager(this, var13)).start();
               }
            }

            while(System.nanoTime() - var3 > (long)var2) {
               var3 += (long)var2;
               this.a(PacketType.PING, new Object[0]);
            }

            Thread.sleep(5L);
         }
      } catch (Exception var12) {
         logger.log(Level.SEVERE, "Error in main loop, server shutting down!", var12);
         var12.printStackTrace();
      }
   }

   private static String a(Map var0) {
      try {
         String var1 = "";

         String var3;
         for(Iterator var2 = var0.keySet().iterator(); var2.hasNext(); var1 = var1 + var3 + "=" + URLEncoder.encode(var0.get(var3).toString(), "UTF-8")) {
            var3 = (String)var2.next();
            if(var1 != "") {
               var1 = var1 + "&";
            }
         }

         return var1;
      } catch (Exception var4) {
         var4.printStackTrace();
         throw new RuntimeException("Failed to assemble heartbeat! This is pretty fatal");
      }
   }

   private void c() {
      Iterator var1 = this.n.iterator();

      while(var1.hasNext()) {
         NetworkManager var2 = (NetworkManager)var1.next();

         try {
            var2.a();
         } catch (Exception var8) {
            var2.a(var8);
         }
      }

      this.mainLevel.tick();

      for(int var9 = 0; var9 < this.o.size(); ++var9) {
         UNKNOWN0 var10 = (UNKNOWN0)this.o.get(var9);
         this.a(var10.networkHandler);

         try {
            NetworkHandler var3 = var10.networkHandler;

            if(var3.out.position() > 0) {
               var3.out.flip();
               var3.write(var3.out);
               var3.out.compact();
            }

            if(var10.b-- <= 0) {
               try {
                  var10.networkHandler.close();
               } catch (Exception var5) {
                  ;
               }

               this.o.remove(var9--);
            }
         } catch (Exception var7) {
            try {
               var10.networkHandler.close();
            } catch (Exception var4) {
               ;
            }
         }
      }

   }

   public final void a(String var1) {
      logger.info(var1);
   }

   public final void b(String var1) {
      logger.fine(var1);
   }

   private void d() {
      List var1 = this.v;
      synchronized(this.v) {
         while(this.v.size() > 0) {
            this.a((NetworkManager)null, (String)this.v.remove(0));
         }
      }

      BindTo var13 = this.bindTo;
      NetworkHandler var2 = new NetworkHandler(8080);
      var13.c.add(var2);

      for(int var17 = 0; var17 < var13.c.size(); ++var17) {
         NetworkHandler var15 = (NetworkHandler)var13.c.get(var17);

         try {
            NetworkHandler var20 = var15;
            var15.read(var15.in);
            int var19 = 0;

            while(var20.in.position() > 0 && var19++ != 100) {
               var20.in.flip();
               byte var22 = var20.in.get(0);
               PacketType var25;
               if((var25 = PacketType.packets[var22]) == null) {
                  throw new IOException("Bad command: " + var22);
               }

               if(var20.in.remaining() < var25.length + 1) {
                  var20.in.compact();
                  break;
               }

               var20.in.get();
               Object[] var21 = new Object[var25.params.length];

               for(int var7 = 0; var7 < var21.length; ++var7) {
                  var21[var7] = var20.receive(var25.params[var7]);
               }

               var20.networkManager.a(var25, var21);
               if(!var20.connected) {
                  break;
               }

               var20.in.compact();
            }

            if(var20.out.position() > 0) {
               var20.out.flip();
               var20.write(var20.out);
               var20.out.compact();
            }
         } catch (Exception var9) {
            MinecraftServer var10001 = var13.server;
            NetworkManager var23;
            if((var23 = (NetworkManager)var13.server.m.get(var15)) != null) {
               var23.a(var9);
            }
         }

         try {
            if(!var15.connected) {
               var15.close();
               var13.server.a(var15);
               var13.c.remove(var17--);
            }
         } catch (Exception var8) {
            var8.printStackTrace();
         }
      }
   }

   public final void a(NetworkManager var1, String var2) {
      while(var2.startsWith("/")) {
         var2 = var2.substring(1);
      }

      logger.info((var1 == null?"[console]":var1.playerName) + " admins: " + var2);
      String[] var3;
      if((var3 = var2.split(" "))[0].toLowerCase().equals("ban") && var3.length > 1) {
         this.e(var3[1]);
      } else if(var3[0].toLowerCase().equals("kick") && var3.length > 1) {
         this.d(var3[1]);
      } else if(var3[0].toLowerCase().equals("banip") && var3.length > 1) {
         this.h(var3[1]);
      } else if(var3[0].toLowerCase().equals("unban") && var3.length > 1) {
         String var5 = var3[1];
         this.playerManager2.removePlayer(var5);
      } else if(var3[0].toLowerCase().equals("op") && var3.length > 1) {
         this.f(var3[1]);
      } else if(var3[0].toLowerCase().equals("deop") && var3.length > 1) {
         this.g(var3[1]);
      } else if(var3[0].toLowerCase().equals("setspawn")) {
         if(var1 != null) {
            this.mainLevel.setSpawnPos(var1.xSpawn / 32, var1.ySpawn / 32, var1.zSpawn / 32, (float)(var1.yawSpawn * 320 / 256));
         } else {
            logger.info("Can\'t set spawn from console!");
         }
      } else {
         if(var3[0].toLowerCase().equals("solid")) {
            if(var1 != null) {
               var1.i = !var1.i;
               if(var1.i) {
                  var1.b("Now placing unbreakable stone");
                  return;
               }

               var1.b("Now placing normal stone");
               return;
            }
         } else {
            if(var3[0].toLowerCase().equals("broadcast") && var3.length > 1) {
               this.a(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), var2.substring("broadcast ".length()).trim()});
               return;
            }

            if(var3[0].toLowerCase().equals("say") && var3.length > 1) {
               this.a(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), var2.substring("say ".length()).trim()});
               return;
            }

            if((var3[0].toLowerCase().equals("teleport") || var3[0].toLowerCase().equals("tp")) && var3.length > 1) {
               if(var1 == null) {
                  logger.info("Can\'t teleport from console!");
                  return;
               }

               NetworkManager var4;
               if((var4 = this.c(var3[1])) == null) {
                  var1.b(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), "No such player"});
                  return;
               }

               var1.networkHandler.send(PacketType.POSITION_ROTATION, new Object[]{Integer.valueOf(-1), Integer.valueOf(var4.xSpawn), Integer.valueOf(var4.ySpawn), Integer.valueOf(var4.zSpawn), Integer.valueOf(var4.yawSpawn), Integer.valueOf(var4.pitchSpawn)});
            } else if(var1 != null) {
               var1.b(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), "Unknown command!"});
            }
         }

      }
   }

   public final void a(int var1, int var2, int var3) {
      this.a(PacketType.BLOCK_CHANGE, new Object[]{Integer.valueOf(var1), Integer.valueOf(var2), Integer.valueOf(var3), Integer.valueOf(this.mainLevel.getTile(var1, var2, var3))});
   }

   public final int a() {
      int var1 = 0;

      for(int var2 = 0; var2 < this.maxPlayers; ++var2) {
         if(this.networkManager[var2] == null) {
            ++var1;
         }
      }

      return var1;
   }

   private int e() {
      for(int var1 = 0; var1 < this.maxPlayers; ++var1) {
         if(this.networkManager[var1] == null) {
            return var1;
         }
      }

      return -1;
   }

   public final List b() {
      return this.n;
   }

   private void d(String var1) {
      boolean var2 = false;
      Iterator var3 = this.n.iterator();

      while(var3.hasNext()) {
         NetworkManager var4;
         if((var4 = (NetworkManager)var3.next()).playerName.equalsIgnoreCase(var1)) {
            var2 = true;
            var4.a("You were kicked");
         }
      }

      if(var2) {
         this.a(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), var1 + " got kicked from the server!"});
      }

   }

   private void e(String var1) {
      this.playerManager2.addPlayer(var1);
      boolean var2 = false;
      Iterator var3 = this.n.iterator();

      while(var3.hasNext()) {
         NetworkManager var4;
         if((var4 = (NetworkManager)var3.next()).playerName.equalsIgnoreCase(var1)) {
            var2 = true;
            var4.a("You were banned");
         }
      }

      if(var2) {
         this.a(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), var1 + " got banned!"});
      }

   }

   private void f(String var1) {
      this.playerManager1.addPlayer(var1);
      Iterator var3 = this.n.iterator();

      while(var3.hasNext()) {
         NetworkManager var2;
         if((var2 = (NetworkManager)var3.next()).playerName.equalsIgnoreCase(var1)) {
            var2.b("You\'re now op!");
            var2.b(PacketType.UPDATE_PLAYER_TYPE, new Object[]{Integer.valueOf(100)});
         }
      }

   }

   private void g(String var1) {
      this.playerManager1.removePlayer(var1);
      Iterator var3 = this.n.iterator();

      while(var3.hasNext()) {
         NetworkManager var2;
         if((var2 = (NetworkManager)var3.next()).playerName.equalsIgnoreCase(var1)) {
            var2.i = false;
            var2.b("You\'re no longer op!");
            var2.b(PacketType.UPDATE_PLAYER_TYPE, new Object[]{Integer.valueOf(0)});
         }
      }

   }

   private void h(String var1) {
      boolean var2 = false;
      String var3 = "";
      Iterator var4 = this.n.iterator();

      while(var4.hasNext()) {
         NetworkManager var5;
         NetworkHandler var6;
         if(!(var5 = (NetworkManager)var4.next()).playerName.equalsIgnoreCase(var1)) {
            var6 = var5.networkHandler;
            if(!var5.networkHandler.address.equalsIgnoreCase(var1)) {
               var6 = var5.networkHandler;
               if(!var5.networkHandler.address.equalsIgnoreCase("/" + var1)) {
                  continue;
               }
            }
         }

         var6 = var5.networkHandler;
         this.playerManager3.addPlayer(var5.networkHandler.address);
         var5.a("You were banned");
         if(var3 == "") {
            var3 = var3 + ", ";
         }

         var3 = var3 + var5.playerName;
         var2 = true;
      }

      if(var2) {
         this.a(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), var3 + " got ip banned!"});
      }

   }

   public final NetworkManager c(String var1) {
      Iterator var3 = this.n.iterator();

      NetworkManager var2;
      do {
         if(!var3.hasNext()) {
            return null;
         }
      } while(!(var2 = (NetworkManager)var3.next()).playerName.equalsIgnoreCase(var1));

      return var2;
   }

   public static void main(String[] var0) {
      try {
         MinecraftServer var6;
         MinecraftServer var1 = var6 = new MinecraftServer();
         logger.info("Setting up");
         File var2;
         if((var2 = new File("server_level.dat")).exists()) {
            try {
               var1.mainLevel = (new LevelIO(var1)).load(new FileInputStream(var2));
            } catch (Exception var4) {
               logger.warning("Failed to load level. Generating a new level");
               var4.printStackTrace();
            }
         } else {
            logger.warning("No level file found. Generating a new level");
         }

         if(var1.mainLevel == null) {
            var1.mainLevel = (new LevelGenerator(var1)).generate("--", 256, 256, 64);
         }

         try {
            new LevelIO(var1);
            LevelIO.save(var1.mainLevel, new FileOutputStream("server_level.dat"));
         } catch (Exception var3) {
            ;
         }

         var1.mainLevel.creativeMode = true;
         var1.mainLevel.growTrees = var1.growTrees;
         var1.mainLevel.addListener$74652038(var1);
         (new Thread(var6)).start();
      } catch (Exception var5) {
         logger.severe("Failed to start the server!");
         var5.printStackTrace();
      }
   }

   // $FF: synthetic method
   static List a(MinecraftServer var0) {
      return var0.v;
   }

   // $FF: synthetic method
   static String b(MinecraftServer var0) {
      return var0.x;
   }

   // $FF: synthetic method
   static String a(MinecraftServer var0, String var1) {
      return var0.x = var1;
   }

   static {
      LogFormatter var0 = new LogFormatter();
      Handler[] var1;
      int var2 = (var1 = logger.getParent().getHandlers()).length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Handler var4 = var1[var3];
         logger.getParent().removeHandler(var4);
      }

      ConsoleHandler var6;
      (var6 = new ConsoleHandler()).setFormatter(var0);
      logger.addHandler(var6);

      try {
         LogHandler var7;
         (var7 = new LogHandler(new FileOutputStream("server.log"), var0)).setFormatter(var0);
         logger.addHandler(var7);
      } catch (Exception var5) {
         logger.warning("Failed to open file server.log for writing: " + var5);
      }
   }
}
