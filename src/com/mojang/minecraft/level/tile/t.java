package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.a;
import com.mojang.minecraft.level.tile.m;
import java.util.Random;

public final class t extends m {

   protected t(int var1, int var2) {
      super(var1, var2);
      float var3 = 0.2F;
      this.a(0.5F - var3, 0.0F, 0.5F - var3, var3 + 0.5F, var3 * 2.0F, var3 + 0.5F);
   }

   public final void a(Level var1, int var2, int var3, int var4, Random var5) {
      int var6 = var1.getTile(var2, var3 - 1, var4);
      if(var1.isLit(var2, var3, var4) || var6 != e.ab && var6 != q.ab && var6 != h.ab) {
         var1.setTile(var2, var3, var4, 0);
      }

   }
}
