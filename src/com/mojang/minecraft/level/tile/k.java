package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.a;

public final class k extends a {

   private boolean ad;


   public k(int var1, boolean var2) {
      super(var1, 6);
      this.ad = var2;
      if(!var2) {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      }

   }

   public final boolean c() {
      return this.ad;
   }

   public final void a(Level var1, int var2, int var3, int var4, int var5) {
      if(this == V) {
         ;
      }
   }

   public final void b(Level var1, int var2, int var3, int var4) {
      if(this != V) {
         super.b(var1, var2, var3, var4);
      }

      if(var1.getTile(var2, var3 - 1, var4) == V.ab) {
         var1.setTile(var2, var3, var4, 0);
         var1.setTile(var2, var3 - 1, var4, U.ab);
      }

   }

   public final boolean a() {
      return this.ad;
   }
}
