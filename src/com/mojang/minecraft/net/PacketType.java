package com.mojang.minecraft.net;


public final class PacketType {

   public static final PacketType[] packets = new PacketType[256];
   public static final PacketType INDENTIFICATION = new PacketType(new Class[]{Byte.TYPE, String.class, String.class, Byte.TYPE});
   public static final PacketType PING = new PacketType(new Class[0]);
   public static final PacketType LEVEL_INITIALIZE = new PacketType(new Class[0]);
   public static final PacketType LEVEL_DATA_CHUNK = new PacketType(new Class[]{Short.TYPE, byte[].class, Byte.TYPE});
   public static final PacketType LEVEL_FINALIZE = new PacketType(new Class[]{Short.TYPE, Short.TYPE, Short.TYPE});
   public static final PacketType PLAYER_SET_BLOCK = new PacketType(new Class[]{Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE, Byte.TYPE});
   public static final PacketType BLOCK_CHANGE = new PacketType(new Class[]{Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE});
   public static final PacketType SPAWN_PLAYER = new PacketType(new Class[]{Byte.TYPE, String.class, Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE, Byte.TYPE});
   public static final PacketType POSITION_ROTATION = new PacketType(new Class[]{Byte.TYPE, Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE, Byte.TYPE});
   public static final PacketType POSITION_ROTATION_UPDATE = new PacketType(new Class[]{Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE});
   public static final PacketType POSITION_UPDATE = new PacketType(new Class[]{Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE});
   public static final PacketType ROTATION_UPDATE = new PacketType(new Class[]{Byte.TYPE, Byte.TYPE, Byte.TYPE});
   public static final PacketType DESPAWN_PLAYER = new PacketType(new Class[]{Byte.TYPE});
   public static final PacketType CHAT_MESSAGE = new PacketType(new Class[]{Byte.TYPE, String.class});
   public static final PacketType DISCONNECT = new PacketType(new Class[]{String.class});
   public static final PacketType UPDATE_PLAYER_TYPE = new PacketType(new Class[]{Byte.TYPE});
   public final int length;
   private static int nextOpcode = 0;
   public final byte opCode;
   public Class[] params;


   private PacketType(Class ... var1) {
      this.opCode = (byte)(nextOpcode++);
      packets[this.opCode] = this;
      this.params = new Class[var1.length];
      int var2 = 0;

      for(int var3 = 0; var3 < var1.length; ++var3) {
         Class var4 = var1[var3];
         this.params[var3] = var4;
         if(var4 == Long.TYPE) {
            var2 += 8;
         } else if(var4 == Integer.TYPE) {
            var2 += 4;
         } else if(var4 == Short.TYPE) {
            var2 += 2;
         } else if(var4 == Byte.TYPE) {
            ++var2;
         } else if(var4 == Float.TYPE) {
            var2 += 4;
         } else if(var4 == Double.TYPE) {
            var2 += 8;
         } else if(var4 == byte[].class) {
            var2 += 1024;
         } else if(var4 == String.class) {
            var2 += 64;
         }
      }

      this.length = var2;
   }

}
