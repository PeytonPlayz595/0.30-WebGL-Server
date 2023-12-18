package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.liquid.LiquidType;
import com.mojang.minecraft.level.tile.a;

public final class f extends a {

   public f(int var1, int var2) {
      super(var1, var2);
   }

   public final void a(Level var1, int var2, int var3, int var4) {
      this.d(var1, var2, var3, var4);
   }

   public final void a(Level var1, int var2, int var3, int var4, int var5) {
      this.d(var1, var2, var3, var4);
   }

   private void d(Level var1, int var2, int var3, int var4) {
      int var11 = var2;
      int var5 = var3;
      int var6 = var4;

      while(true) {
         int var8 = var5 - 1;
         int var10;
         LiquidType var12;
         if(!((var10 = var1.getTile(var11, var8, var6)) == 0?true:((var12 = b[var10].d()) == LiquidType.WATER?true:var12 == LiquidType.LAVA)) || var5 <= 0) {
            if(var5 != var3) {
               if((var10 = var1.getTile(var11, var5, var6)) > 0 && b[var10].d() != LiquidType.NOT_LIQUID) {
                  var1.setTileNoUpdate(var11, var5, var6, 0);
               }

               var1.swap(var2, var3, var4, var11, var5, var6);
            }

            return;
         }

         --var5;
      }
   }
}
