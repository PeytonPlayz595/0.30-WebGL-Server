package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.Vector3DCreator;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.liquid.LiquidType;
import com.mojang.minecraft.level.tile.Tile$SoundType;
import com.mojang.minecraft.level.tile.c;
import com.mojang.minecraft.level.tile.d;
import com.mojang.minecraft.level.tile.e;
import com.mojang.minecraft.level.tile.f;
import com.mojang.minecraft.level.tile.g;
import com.mojang.minecraft.level.tile.h;
import com.mojang.minecraft.level.tile.i;
import com.mojang.minecraft.level.tile.j;
import com.mojang.minecraft.level.tile.k;
import com.mojang.minecraft.level.tile.l;
import com.mojang.minecraft.level.tile.m;
import com.mojang.minecraft.level.tile.n;
import com.mojang.minecraft.level.tile.o;
import com.mojang.minecraft.level.tile.p;
import com.mojang.minecraft.level.tile.q;
import com.mojang.minecraft.level.tile.r;
import com.mojang.minecraft.level.tile.s;
import com.mojang.minecraft.level.tile.t;
import com.mojang.minecraft.model.Vector3D;
import com.mojang.minecraft.phys.AABB;
import java.util.Random;

public class a {

   protected static Random a = new Random();
   public static final a[] b = new a[256];
   public static final boolean[] c = new boolean[256];
   private static boolean[] ad = new boolean[256];
   private static boolean[] ae = new boolean[256];
   public static final boolean[] d = new boolean[256];
   private static int[] af = new int[256];
   public static final a e;
   public static final a f;
   public static final a g;
   public static final a h;
   public static final a i;
   public static final a j;
   public static final a k;
   public static final a l;
   public static final a m;
   public static final a n;
   public static final a o;
   public static final a p;
   public static final a q;
   public static final a r;
   public static final a s;
   public static final a t;
   public static final a u;
   public static final a v;
   public static final a w;
   public static final a x;
   public static final a y;
   public static final a z;
   public static final a A;
   public static final a B;
   public static final a C;
   public static final a D;
   public static final a E;
   public static final a F;
   public static final a G;
   public static final a H;
   public static final a I;
   public static final a J;
   public static final a K;
   public static final a L;
   public static final a M;
   public static final a N;
   public static final a O;
   public static final a P;
   public static final a Q;
   public static final a R;
   public static final a S;
   public static final a T;
   public static final a U;
   public static final a V;
   public static final a W;
   public static final a X;
   public static final a Y;
   public static final a Z;
   public static final a aa;
   public final int ab;
   public Tile$SoundType ac;
   private boolean ag;
   private float ah;
   private float ai;
   private float aj;
   private float ak;
   private float al;
   private float am;


   protected a(int var1) {
      this.ag = true;
      b[var1] = this;
      this.ab = var1;
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      ad[var1] = this.c();
      ae[var1] = this.a();
      d[var1] = false;
   }

   public boolean a() {
      return true;
   }

   protected final void a(boolean var1) {
      c[this.ab] = var1;
   }

   protected final void a(float var1, float var2, float var3, float var4, float var5, float var6) {
      this.ah = var1;
      this.ai = var2;
      this.aj = var3;
      this.ak = var4;
      this.al = var5;
      this.am = var6;
   }

   protected a(int var1, int var2) {
      this(var1);
   }

   public final void a(int var1) {
      af[this.ab] = 16;
   }

   public AABB a(int var1, int var2, int var3) {
      return new AABB((float)var1 + this.ah, (float)var2 + this.ai, (float)var3 + this.aj, (float)var1 + this.ak, (float)var2 + this.al, (float)var3 + this.am);
   }

   public boolean b() {
      return true;
   }

   public boolean c() {
      return true;
   }

   public void a(Level var1, int var2, int var3, int var4, Random var5) {}

   public LiquidType d() {
      return LiquidType.NOT_LIQUID;
   }

   public void a(Level var1, int var2, int var3, int var4, int var5) {}

   public void a(Level var1, int var2, int var3, int var4) {}

   public int e() {
      return 0;
   }

   public void b(Level var1, int var2, int var3, int var4) {}

   public void c(Level var1, int var2, int var3, int var4) {}

   public int f() {
      return 1;
   }

   public void a(Level var1, float var2) {
      if(!var1.creativeMode) {
         int var3 = this.f();

         for(int var4 = 0; var4 < var3; ++var4) {
            if(a.nextFloat() <= var2) {
               a.nextFloat();
               a.nextFloat();
               a.nextFloat();
            }
         }

      }
   }

   public final boolean g() {
      return this.ag;
   }

   public final Vector3DCreator a(int var1, int var2, int var3, Vector3D var4, Vector3D var5) {
      var4 = var4.add((float)(-var1), (float)(-var2), (float)(-var3));
      var5 = var5.add((float)(-var1), (float)(-var2), (float)(-var3));
      Vector3D var6 = var4.getXIntersection(var5, this.ah);
      Vector3D var7 = var4.getXIntersection(var5, this.ak);
      Vector3D var8 = var4.getYIntersection(var5, this.ai);
      Vector3D var9 = var4.getYIntersection(var5, this.al);
      Vector3D var10 = var4.getZIntersection(var5, this.aj);
      var5 = var4.getZIntersection(var5, this.am);
      if(!this.a(var6)) {
         var6 = null;
      }

      if(!this.a(var7)) {
         var7 = null;
      }

      if(!this.b(var8)) {
         var8 = null;
      }

      if(!this.b(var9)) {
         var9 = null;
      }

      if(!this.c(var10)) {
         var10 = null;
      }

      if(!this.c(var5)) {
         var5 = null;
      }

      Vector3D var11 = null;
      if(var6 != null) {
         var11 = var6;
      }

      if(var7 != null && (var11 == null || var4.distanceSquared(var7) < var4.distanceSquared(var11))) {
         var11 = var7;
      }

      if(var8 != null && (var11 == null || var4.distanceSquared(var8) < var4.distanceSquared(var11))) {
         var11 = var8;
      }

      if(var9 != null && (var11 == null || var4.distanceSquared(var9) < var4.distanceSquared(var11))) {
         var11 = var9;
      }

      if(var10 != null && (var11 == null || var4.distanceSquared(var10) < var4.distanceSquared(var11))) {
         var11 = var10;
      }

      if(var5 != null && (var11 == null || var4.distanceSquared(var5) < var4.distanceSquared(var11))) {
         var11 = var5;
      }

      if(var11 == null) {
         return null;
      } else {
         byte var12 = -1;
         if(var11 == var6) {
            var12 = 4;
         }

         if(var11 == var7) {
            var12 = 5;
         }

         if(var11 == var8) {
            var12 = 0;
         }

         if(var11 == var9) {
            var12 = 1;
         }

         if(var11 == var10) {
            var12 = 2;
         }

         if(var11 == var5) {
            var12 = 3;
         }

         return new Vector3DCreator(var1, var2, var3, var12, var11.add((float)var1, (float)var2, (float)var3));
      }
   }

   private boolean a(Vector3D var1) {
      return var1 == null?false:var1.y >= this.ai && var1.y <= this.al && var1.z >= this.aj && var1.z <= this.am;
   }

   private boolean b(Vector3D var1) {
      return var1 == null?false:var1.x >= this.ah && var1.x <= this.ak && var1.z >= this.aj && var1.z <= this.am;
   }

   private boolean c(Vector3D var1) {
      return var1 == null?false:var1.x >= this.ah && var1.x <= this.ak && var1.y >= this.ai && var1.y <= this.al;
   }

   static {
      e var10000 = new e(1, 1);
      float var0 = 1.0F;
      var0 = 1.0F;
      var0 = 1.0F;
      Tile$SoundType var1 = Tile$SoundType.stone;
      e var2 = var10000;
      var10000.ac = var1;
      boolean var3 = false;
      var2.ag = false;
      e = var2;
      l var22 = new l(2);
      var0 = 0.6F;
      var0 = 1.0F;
      var0 = 0.9F;
      var1 = Tile$SoundType.grass;
      l var4 = var22;
      var22.ac = var1;
      f = var4;
      c var23 = new c(3, 2);
      var0 = 0.5F;
      var0 = 1.0F;
      var0 = 0.8F;
      var1 = Tile$SoundType.grass;
      c var5 = var23;
      var23.ac = var1;
      g = var5;
      a var21 = new a(4, 16);
      var0 = 1.5F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      a var6 = var21;
      var21.ac = var1;
      var3 = false;
      var6.ag = false;
      h = var6;
      var21 = new a(5, 4);
      var0 = 1.5F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.wood;
      var6 = var21;
      var21.ac = var1;
      i = var6;
      h var20 = new h(6, 15);
      var0 = 0.0F;
      var0 = 1.0F;
      var0 = 0.7F;
      var1 = Tile$SoundType.none;
      h var7 = var20;
      var20.ac = var1;
      j = var7;
      var21 = new a(7, 17);
      var0 = 999.0F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      var6 = var21;
      var21.ac = var1;
      var3 = false;
      var6.ag = false;
      k = var6;
      r var18 = new r(8, LiquidType.WATER);
      var0 = 100.0F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.none;
      r var8 = var18;
      var18.ac = var1;
      l = var8;
      i var19 = new i(9, LiquidType.WATER);
      var0 = 100.0F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.none;
      i var9 = var19;
      var19.ac = var1;
      m = var9;
      var18 = new r(10, LiquidType.LAVA);
      var0 = 100.0F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.none;
      var8 = var18;
      var18.ac = var1;
      n = var8;
      var19 = new i(11, LiquidType.LAVA);
      var0 = 100.0F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.none;
      var9 = var19;
      var19.ac = var1;
      o = var9;
      f var17 = new f(12, 18);
      var0 = 0.5F;
      var0 = 1.0F;
      var0 = 0.8F;
      var1 = Tile$SoundType.gravel;
      f var10 = var17;
      var17.ac = var1;
      p = var10;
      var17 = new f(13, 19);
      var0 = 0.6F;
      var0 = 1.0F;
      var0 = 0.8F;
      var1 = Tile$SoundType.gravel;
      var10 = var17;
      var17.ac = var1;
      q = var10;
      n var16 = new n(14, 32);
      var0 = 3.0F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      n var11 = var16;
      var16.ac = var1;
      var3 = false;
      var11.ag = false;
      r = var11;
      var16 = new n(15, 33);
      var0 = 3.0F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      var11 = var16;
      var16.ac = var1;
      var3 = false;
      var11.ag = false;
      s = var11;
      var16 = new n(16, 34);
      var0 = 3.0F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      var11 = var16;
      var16.ac = var1;
      var3 = false;
      var11.ag = false;
      t = var11;
      j var36 = new j(17);
      var0 = 2.5F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.wood;
      j var12 = var36;
      var36.ac = var1;
      u = var12;
      p var38 = new p(18, 22);
      var0 = 0.2F;
      var0 = 0.4F;
      var0 = 1.0F;
      var1 = Tile$SoundType.grass;
      p var14 = var38;
      var38.ac = var1;
      v = var14;
      o var37 = new o(19);
      var0 = 0.6F;
      var0 = 0.9F;
      var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      o var13 = var37;
      var37.ac = var1;
      w = var13;
      q var39 = new q(20, 49, false);
      var0 = 0.3F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.metal;
      q var15 = var39;
      var39.ac = var1;
      x = var15;
      var21 = new a(21, 64);
      var0 = 0.8F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var21;
      var21.ac = var1;
      y = var6;
      var21 = new a(22, 65);
      var0 = 0.8F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var21;
      var21.ac = var1;
      z = var6;
      var21 = new a(23, 66);
      var0 = 0.8F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var21;
      var21.ac = var1;
      A = var6;
      var21 = new a(24, 67);
      var0 = 0.8F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var21;
      var21.ac = var1;
      B = var6;
      var21 = new a(25, 68);
      var0 = 0.8F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var21;
      var21.ac = var1;
      C = var6;
      var21 = new a(26, 69);
      var0 = 0.8F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var21;
      var21.ac = var1;
      D = var6;
      var21 = new a(27, 70);
      var0 = 0.8F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var21;
      var21.ac = var1;
      E = var6;
      var21 = new a(28, 71);
      var0 = 0.8F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var21;
      var21.ac = var1;
      F = var6;
      var21 = new a(29, 72);
      var0 = 0.8F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var21;
      var21.ac = var1;
      G = var6;
      var21 = new a(30, 73);
      var0 = 0.8F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var21;
      var21.ac = var1;
      H = var6;
      var21 = new a(31, 74);
      var0 = 0.8F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var21;
      var21.ac = var1;
      I = var6;
      var21 = new a(32, 75);
      var0 = 0.8F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var21;
      var21.ac = var1;
      J = var6;
      var21 = new a(33, 76);
      var0 = 0.8F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var21;
      var21.ac = var1;
      K = var6;
      var21 = new a(34, 77);
      var0 = 0.8F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var21;
      var21.ac = var1;
      L = var6;
      var21 = new a(35, 78);
      var0 = 0.8F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var21;
      var21.ac = var1;
      M = var6;
      var21 = new a(36, 79);
      var0 = 0.8F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      var6 = var21;
      var21.ac = var1;
      N = var6;
      m var34 = new m(37, 13);
      var0 = 0.0F;
      var0 = 1.0F;
      var0 = 0.7F;
      var1 = Tile$SoundType.none;
      m var25 = var34;
      var34.ac = var1;
      O = var25;
      var34 = new m(38, 12);
      var0 = 0.0F;
      var0 = 1.0F;
      var0 = 0.7F;
      var1 = Tile$SoundType.none;
      var25 = var34;
      var34.ac = var1;
      P = var25;
      t var35 = new t(39, 29);
      var0 = 0.0F;
      var0 = 1.0F;
      var0 = 0.7F;
      var1 = Tile$SoundType.none;
      t var24 = var35;
      var35.ac = var1;
      Q = var24;
      var35 = new t(40, 28);
      var0 = 0.0F;
      var0 = 1.0F;
      var0 = 0.7F;
      var1 = Tile$SoundType.none;
      var24 = var35;
      var35.ac = var1;
      R = var24;
      d var30 = new d(41, 40);
      var0 = 3.0F;
      var0 = 1.0F;
      var0 = 0.7F;
      var1 = Tile$SoundType.metal;
      d var27 = var30;
      var30.ac = var1;
      var3 = false;
      var27.ag = false;
      S = var27;
      var30 = new d(42, 39);
      var0 = 5.0F;
      var0 = 1.0F;
      var0 = 0.7F;
      var1 = Tile$SoundType.metal;
      var27 = var30;
      var30.ac = var1;
      var3 = false;
      var27.ag = false;
      T = var27;
      k var31 = new k(43, true);
      var0 = 2.0F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      k var26 = var31;
      var31.ac = var1;
      var3 = false;
      var26.ag = false;
      U = var26;
      var31 = new k(44, false);
      var0 = 2.0F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      var26 = var31;
      var31.ac = var1;
      var3 = false;
      var26.ag = false;
      V = var26;
      var21 = new a(45, 7);
      var0 = 2.0F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      var6 = var21;
      var21.ac = var1;
      var3 = false;
      var6.ag = false;
      W = var6;
      s var32 = new s(46, 8);
      var0 = 0.0F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.cloth;
      s var28 = var32;
      var32.ac = var1;
      X = var28;
      g var33 = new g(47, 35);
      var0 = 1.5F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.wood;
      g var29 = var33;
      var33.ac = var1;
      Y = var29;
      var21 = new a(48, 36);
      var0 = 1.0F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      var6 = var21;
      var21.ac = var1;
      var3 = false;
      var6.ag = false;
      Z = var6;
      var10000 = new e(49, 37);
      var0 = 10.0F;
      var0 = 1.0F;
      var0 = 1.0F;
      var1 = Tile$SoundType.stone;
      var2 = var10000;
      var10000.ac = var1;
      var3 = false;
      var2.ag = false;
      aa = var2;
   }
}
