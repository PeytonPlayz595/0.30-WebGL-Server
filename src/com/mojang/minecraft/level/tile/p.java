package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.tile.b;

public final class p extends b {

   protected p(int var1, int var2) {
      super(18, 22, true);
   }

   public final int f() {
      return a.nextInt(10) == 0?1:0;
   }
}
