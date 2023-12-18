package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.liquid.LiquidType;
import com.mojang.minecraft.level.tile.a;
import com.mojang.minecraft.phys.AABB;
import java.util.Random;

public class r extends a {

   protected LiquidType ad;
   protected int ae;
   protected int af;


   protected r(int var1, LiquidType var2) {
      super(var1);
      this.ad = var2;
      a.d[var1] = true;
      this.af = var1;
      this.ae = var1 + 1;
      float var4 = 0.01F;
      float var3 = 0.1F;
      this.a(var4 + 0.0F, 0.0F - var3 + var4, var4 + 0.0F, var4 + 1.0F, 1.0F - var3 + var4, var4 + 1.0F);
      this.a(true);
      if(var2 == LiquidType.LAVA) {
         this.a(16);
      }

   }

   public final boolean a() {
      return false;
   }

   public final void a(Level var1, int var2, int var3, int var4) {
      var1.addToTickNextTick(var2, var3, var4, this.af);
   }

   public void a(Level var1, int var2, int var3, int var4, Random var5) {
      boolean var7 = false;
      var4 = var4;
      var3 = var3;
      var2 = var2;
      var1 = var1;
      this = this;
      boolean var8 = false;

      boolean var6;
      do {
         --var3;
         if(var1.getTile(var2, var3, var4) != 0 || !this.d(var1, var2, var3, var4)) {
            break;
         }

         if(var6 = var1.setTile(var2, var3, var4, this.af)) {
            var8 = true;
         }
      } while(var6 && this.ad != LiquidType.LAVA);

      ++var3;
      if(this.ad == LiquidType.WATER || !var8) {
         var8 = var8 | this.e(var1, var2 - 1, var3, var4) | this.e(var1, var2 + 1, var3, var4) | this.e(var1, var2, var3, var4 - 1) | this.e(var1, var2, var3, var4 + 1);
      }

      if(!var8) {
         var1.setTileNoUpdate(var2, var3, var4, this.ae);
      } else {
         var1.addToTickNextTick(var2, var3, var4, this.af);
      }

   }

   private boolean d(Level var1, int var2, int var3, int var4) {
      if(this.ad == LiquidType.WATER) {
         for(int var7 = var2 - 2; var7 <= var2 + 2; ++var7) {
            for(int var5 = var3 - 2; var5 <= var3 + 2; ++var5) {
               for(int var6 = var4 - 2; var6 <= var4 + 2; ++var6) {
                  if(var1.getTile(var7, var5, var6) == a.w.ab) {
                     return false;
                  }
               }
            }
         }
      }

      return true;
   }

   private boolean e(Level var1, int var2, int var3, int var4) {
      if(var1.getTile(var2, var3, var4) == 0) {
         if(!this.d(var1, var2, var3, var4)) {
            return false;
         }

         if(var1.setTile(var2, var3, var4, this.af)) {
            var1.addToTickNextTick(var2, var3, var4, this.af);
         }
      }

      return false;
   }

   public final AABB a(int var1, int var2, int var3) {
      return null;
   }

   public final boolean b() {
      return true;
   }

   public final boolean c() {
      return false;
   }

   public final LiquidType d() {
      return this.ad;
   }

   public void a(Level var1, int var2, int var3, int var4, int var5) {
      if(var5 != 0) {
         LiquidType var6 = a.b[var5].d();
         if(this.ad == LiquidType.WATER && var6 == LiquidType.LAVA || var6 == LiquidType.WATER && this.ad == LiquidType.LAVA) {
            var1.setTile(var2, var3, var4, a.e.ab);
            return;
         }
      }

      var1.addToTickNextTick(var2, var3, var4, var5);
   }

   public final int e() {
      return this.ad == LiquidType.LAVA?5:0;
   }

   public final void a(Level var1, float var2) {}

   public final int f() {
      return 0;
   }
}
