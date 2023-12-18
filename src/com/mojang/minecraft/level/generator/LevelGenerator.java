package com.mojang.minecraft.level.generator;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.generator.noise.CombinedNoise;
import com.mojang.minecraft.level.generator.noise.OctaveNoise;
import com.mojang.minecraft.level.tile.a;
import com.mojang.minecraft.server.MinecraftServer;
import com.mojang.util.MathHelper;
import java.util.ArrayList;
import java.util.Random;

public final class LevelGenerator {

   private MinecraftServer server;
   private int width;
   private int depth;
   private int height;
   private Random random = new Random();
   private byte[] blocks;
   private int waterLevel;
   private int[] h = new int[1048576];


   public LevelGenerator(MinecraftServer var1) {
      this.server = var1;
   }

   public final Level generate(String var1, int var2, int var3, int var4) {
      this.server.a("Generating level");
      this.width = 256;
      this.depth = 256;
      this.height = 64;
      this.waterLevel = 32;
      this.blocks = new byte[256 << 8 << 6];
      this.server.b("Raising..");
      LevelGenerator var41 = this;
      CombinedNoise var5 = new CombinedNoise(new OctaveNoise(this.random, 8), new OctaveNoise(this.random, 8));
      CombinedNoise var6 = new CombinedNoise(new OctaveNoise(this.random, 8), new OctaveNoise(this.random, 8));
      OctaveNoise var7 = new OctaveNoise(this.random, 6);
      int[] var8 = new int[this.width * this.depth];
      float var9 = 1.3F;

      int var10;
      int var11;
      for(var10 = 0; var10 < var41.width; ++var10) {
         for(var11 = 0; var11 < var41.depth; ++var11) {
            double var12 = var5.compute((double)((float)var10 * var9), (double)((float)var11 * var9)) / 6.0D + (double)-4;
            double var14 = var6.compute((double)((float)var10 * var9), (double)((float)var11 * var9)) / 5.0D + 10.0D + (double)-4;
            if(var7.compute((double)var10, (double)var11) / 8.0D > 0.0D) {
               var14 = var12;
            }

            double var18;
            if((var18 = Math.max(var12, var14) / 2.0D) < 0.0D) {
               var18 *= 0.8D;
            }

            var8[var10 + var11 * var41.width] = (int)var18;
         }
      }

      this.server.b("Eroding..");
      int[] var42 = var8;
      var41 = this;
      var6 = new CombinedNoise(new OctaveNoise(this.random, 8), new OctaveNoise(this.random, 8));
      CombinedNoise var47 = new CombinedNoise(new OctaveNoise(this.random, 8), new OctaveNoise(this.random, 8));

      int var22;
      int var51;
      int var52;
      for(var51 = 0; var51 < var41.width; ++var51) {
         for(var52 = 0; var52 < var41.depth; ++var52) {
            double var20 = var6.compute((double)(var51 << 1), (double)(var52 << 1)) / 8.0D;
            var11 = var47.compute((double)(var51 << 1), (double)(var52 << 1)) > 0.0D?1:0;
            if(var20 > 2.0D) {
               var22 = ((var42[var51 + var52 * var41.width] - var11) / 2 << 1) + var11;
               var42[var51 + var52 * var41.width] = var22;
            }
         }
      }

      this.server.b("Soiling..");
      var42 = var8;
      var41 = this;
      int var44 = this.width;
      int var49 = this.depth;
      var51 = this.height;
      OctaveNoise var54 = new OctaveNoise(this.random, 8);

      int var23;
      int var25;
      int var24;
      int var27;
      int var26;
      for(var23 = 0; var23 < var44; ++var23) {
         for(var10 = 0; var10 < var49; ++var10) {
            var11 = (int)(var54.compute((double)var23, (double)var10) / 24.0D) - 4;
            var24 = (var22 = var42[var23 + var10 * var44] + var41.waterLevel) + var11;
            var42[var23 + var10 * var44] = Math.max(var22, var24);
            if(var42[var23 + var10 * var44] > var51 - 2) {
               var42[var23 + var10 * var44] = var51 - 2;
            }

            if(var42[var23 + var10 * var44] < 1) {
               var42[var23 + var10 * var44] = 1;
            }

            for(var25 = 0; var25 < var51; ++var25) {
               var26 = (var25 * var41.depth + var10) * var41.width + var23;
               var27 = 0;
               if(var25 <= var22) {
                  var27 = a.g.ab;
               }

               if(var25 <= var24) {
                  var27 = a.e.ab;
               }

               if(var25 == 0) {
                  var27 = a.n.ab;
               }

               var41.blocks[var26] = (byte)var27;
            }
         }
      }

      this.server.b("Carving..");
      boolean var48 = true;
      boolean var43 = false;
      var41 = this;
      var49 = this.width;
      var51 = this.depth;
      var52 = this.height;
      var23 = var49 * var51 * var52 / 256 / 64 << 1;

      for(var10 = 0; var10 < var23; ++var10) {
         float var55 = var41.random.nextFloat() * (float)var49;
         float var56 = var41.random.nextFloat() * (float)var52;
         float var60 = var41.random.nextFloat() * (float)var51;
         var25 = (int)((var41.random.nextFloat() + var41.random.nextFloat()) * 200.0F);
         float var63 = var41.random.nextFloat() * 3.1415927F * 2.0F;
         float var61 = 0.0F;
         float var28 = var41.random.nextFloat() * 3.1415927F * 2.0F;
         float var29 = 0.0F;
         float var30 = var41.random.nextFloat() * var41.random.nextFloat();

         for(var3 = 0; var3 < var25; ++var3) {
            var55 += MathHelper.sin(var63) * MathHelper.cos(var28);
            var60 += MathHelper.cos(var63) * MathHelper.cos(var28);
            var56 += MathHelper.sin(var28);
            var63 += var61 * 0.2F;
            var61 = (var61 *= 0.9F) + (var41.random.nextFloat() - var41.random.nextFloat());
            var28 = (var28 + var29 * 0.5F) * 0.5F;
            var29 = (var29 *= 0.75F) + (var41.random.nextFloat() - var41.random.nextFloat());
            if(var41.random.nextFloat() >= 0.25F) {
               float var45 = var55 + (var41.random.nextFloat() * 4.0F - 2.0F) * 0.2F;
               float var46 = var56 + (var41.random.nextFloat() * 4.0F - 2.0F) * 0.2F;
               float var31 = var60 + (var41.random.nextFloat() * 4.0F - 2.0F) * 0.2F;
               float var32 = ((float)var41.height - var46) / (float)var41.height;
               var32 = 1.2F + (var32 * 3.5F + 1.0F) * var30;
               var32 = MathHelper.sin((float)var3 * 3.1415927F / (float)var25) * var32;

               for(int var33 = (int)(var45 - var32); var33 <= (int)(var45 + var32); ++var33) {
                  for(int var34 = (int)(var46 - var32); var34 <= (int)(var46 + var32); ++var34) {
                     for(int var35 = (int)(var31 - var32); var35 <= (int)(var31 + var32); ++var35) {
                        float var36 = (float)var33 - var45;
                        float var37 = (float)var34 - var46;
                        float var38 = (float)var35 - var31;
                        if(var36 * var36 + var37 * var37 * 2.0F + var38 * var38 < var32 * var32 && var33 >= 1 && var34 >= 1 && var35 >= 1 && var33 < var41.width - 1 && var34 < var41.height - 1 && var35 < var41.depth - 1) {
                           int var65 = (var34 * var41.depth + var35) * var41.width + var33;
                           if(var41.blocks[var65] == a.e.ab) {
                              var41.blocks[var65] = 0;
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      this.populateOre(a.t.ab, 90, 1, 4);
      this.populateOre(a.s.ab, 70, 2, 4);
      this.populateOre(a.r.ab, 50, 3, 4);
      this.server.b("Watering..");
      var41 = this;
      var51 = a.m.ab;

      for(var52 = 0; var52 < var41.width; ++var52) {
         var41.flood(var52, var41.height / 2 - 1, 0, 0, var51);
         var41.flood(var52, var41.height / 2 - 1, var41.depth - 1, 0, var51);
      }

      for(var52 = 0; var52 < var41.depth; ++var52) {
         var41.flood(0, var41.height / 2 - 1, var52, 0, var51);
         var41.flood(var41.width - 1, var41.height / 2 - 1, var52, 0, var51);
      }

      var52 = var41.width * var41.depth / 8000;

      for(var23 = 0; var23 < var52; ++var23) {
         var10 = var41.random.nextInt(var41.width);
         var11 = var41.waterLevel - 1 - var41.random.nextInt(2);
         var22 = var41.random.nextInt(var41.depth);
         if(var41.blocks[(var11 * var41.depth + var22) * var41.width + var10] == 0) {
            var41.flood(var10, var11, var22, 0, var51);
         }
      }

      this.server.b("Melting..");
      var41 = this;
      var44 = this.width * this.depth * this.height / 20000;

      for(var49 = 0; var49 < var44; ++var49) {
         var51 = var41.random.nextInt(var41.width);
         var52 = (int)(var41.random.nextFloat() * var41.random.nextFloat() * (float)(var41.waterLevel - 3));
         var23 = var41.random.nextInt(var41.depth);
         if(var41.blocks[(var52 * var41.depth + var23) * var41.width + var51] == 0) {
            var41.flood(var51, var52, var23, 0, a.o.ab);
         }
      }

      this.server.b("Growing..");
      var42 = var8;
      var41 = this;
      var44 = this.width;
      var49 = this.depth;
      var51 = this.height;
      var54 = new OctaveNoise(this.random, 8);
      OctaveNoise var57 = new OctaveNoise(this.random, 8);

      int var62;
      for(var10 = 0; var10 < var44; ++var10) {
         for(var11 = 0; var11 < var49; ++var11) {
            boolean var59 = var54.compute((double)var10, (double)var11) > 8.0D;
            boolean var58 = var57.compute((double)var10, (double)var11) > 12.0D;
            var26 = ((var25 = var42[var10 + var11 * var44]) * var41.depth + var11) * var41.width + var10;
            if(((var27 = var41.blocks[((var25 + 1) * var41.depth + var11) * var41.width + var10] & 255) == a.l.ab || var27 == a.m.ab) && var25 <= var51 / 2 - 1 && var58) {
               var41.blocks[var26] = (byte)a.q.ab;
            }

            if(var27 == 0) {
               var62 = a.f.ab;
               if(var25 <= var51 / 2 - 1 && var59) {
                  var62 = a.p.ab;
               }

               var41.blocks[var26] = (byte)var62;
            }
         }
      }

      this.server.b("Planting..");
      var42 = var8;
      var41 = this;
      var44 = this.width;
      var49 = this.width * this.depth / 3000;

      for(var51 = 0; var51 < var49; ++var51) {
         var52 = var41.random.nextInt(2);
         var23 = var41.random.nextInt(var41.width);
         var10 = var41.random.nextInt(var41.depth);

         for(var11 = 0; var11 < 10; ++var11) {
            var22 = var23;
            var24 = var10;

            for(var25 = 0; var25 < 5; ++var25) {
               var22 += var41.random.nextInt(6) - var41.random.nextInt(6);
               var24 += var41.random.nextInt(6) - var41.random.nextInt(6);
               if((var52 < 2 || var41.random.nextInt(4) == 0) && var22 >= 0 && var24 >= 0 && var22 < var41.width && var24 < var41.depth) {
                  var26 = var42[var22 + var24 * var44] + 1;
                  if((var41.blocks[(var26 * var41.depth + var24) * var41.width + var22] & 255) == 0) {
                     var62 = (var26 * var41.depth + var24) * var41.width + var22;
                     if((var41.blocks[((var26 - 1) * var41.depth + var24) * var41.width + var22] & 255) == a.f.ab) {
                        if(var52 == 0) {
                           var41.blocks[var62] = (byte)a.O.ab;
                        } else if(var52 == 1) {
                           var41.blocks[var62] = (byte)a.P.ab;
                        }
                     }
                  }
               }
            }
         }
      }

      var42 = var8;
      var41 = this;
      var44 = this.width;
      var51 = this.width * this.depth * this.height / 2000;

      for(var52 = 0; var52 < var51; ++var52) {
         var23 = var41.random.nextInt(2);
         var10 = var41.random.nextInt(var41.width);
         var11 = var41.random.nextInt(var41.height);
         var22 = var41.random.nextInt(var41.depth);

         for(var24 = 0; var24 < 20; ++var24) {
            var25 = var10;
            var26 = var11;
            var27 = var22;

            for(var62 = 0; var62 < 5; ++var62) {
               var25 += var41.random.nextInt(6) - var41.random.nextInt(6);
               var26 += var41.random.nextInt(2) - var41.random.nextInt(2);
               var27 += var41.random.nextInt(6) - var41.random.nextInt(6);
               if((var23 < 2 || var41.random.nextInt(4) == 0) && var25 >= 0 && var27 >= 0 && var26 >= 1 && var25 < var41.width && var27 < var41.depth && var26 < var42[var25 + var27 * var44] - 1 && (var41.blocks[(var26 * var41.depth + var27) * var41.width + var25] & 255) == 0) {
                  int var64 = (var26 * var41.depth + var27) * var41.width + var25;
                  if((var41.blocks[((var26 - 1) * var41.depth + var27) * var41.width + var25] & 255) == a.e.ab) {
                     if(var23 == 0) {
                        var41.blocks[var64] = (byte)a.Q.ab;
                     } else if(var23 == 1) {
                        var41.blocks[var64] = (byte)a.R.ab;
                     }
                  }
               }
            }
         }
      }

      Level var40;
      (var40 = new Level()).waterLevel = this.waterLevel;
      var40.setData(256, 64, 256, this.blocks);
      var40.createTime = System.currentTimeMillis();
      var40.creator = var1;
      var40.name = "A Nice World";
      int[] var53 = var8;
      Level var50 = var40;
      var41 = this;
      var49 = this.width;
      var51 = this.width * this.depth / 4000;

      for(var52 = 0; var52 < var51; ++var52) {
         var23 = var41.random.nextInt(var41.width);
         var10 = var41.random.nextInt(var41.depth);

         for(var11 = 0; var11 < 20; ++var11) {
            var22 = var23;
            var24 = var10;

            for(var25 = 0; var25 < 20; ++var25) {
               var22 += var41.random.nextInt(6) - var41.random.nextInt(6);
               var24 += var41.random.nextInt(6) - var41.random.nextInt(6);
               if(var22 >= 0 && var24 >= 0 && var22 < var41.width && var24 < var41.depth) {
                  var26 = var53[var22 + var24 * var49] + 1;
                  if(var41.random.nextInt(4) == 0) {
                     var50.maybeGrowTree(var22, var26, var24);
                  }
               }
            }
         }
      }

      return var40;
   }

   private void populateOre(int var1, int var2, int var3, int var4) {
      byte var24 = (byte)var1;
      var3 = this.width;
      var4 = this.depth;
      int var5 = this.height;
      int var6 = var3 * var4 * var5 / 256 / 64 * var2 / 100;

      for(int var7 = 0; var7 < var6; ++var7) {
         float var8 = this.random.nextFloat() * (float)var3;
         float var9 = this.random.nextFloat() * (float)var5;
         float var10 = this.random.nextFloat() * (float)var4;
         int var11 = (int)((this.random.nextFloat() + this.random.nextFloat()) * 75.0F * (float)var2 / 100.0F);
         float var12 = this.random.nextFloat() * 3.1415927F * 2.0F;
         float var13 = 0.0F;
         float var14 = this.random.nextFloat() * 3.1415927F * 2.0F;
         float var15 = 0.0F;

         for(int var16 = 0; var16 < var11; ++var16) {
            var8 += MathHelper.sin(var12) * MathHelper.cos(var14);
            var10 += MathHelper.cos(var12) * MathHelper.cos(var14);
            var9 += MathHelper.sin(var14);
            var12 += var13 * 0.2F;
            var13 = (var13 *= 0.9F) + (this.random.nextFloat() - this.random.nextFloat());
            var14 = (var14 + var15 * 0.5F) * 0.5F;
            var15 = (var15 *= 0.9F) + (this.random.nextFloat() - this.random.nextFloat());
            float var17 = MathHelper.sin((float)var16 * 3.1415927F / (float)var11) * (float)var2 / 100.0F + 1.0F;

            for(int var18 = (int)(var8 - var17); var18 <= (int)(var8 + var17); ++var18) {
               for(int var19 = (int)(var9 - var17); var19 <= (int)(var9 + var17); ++var19) {
                  for(int var20 = (int)(var10 - var17); var20 <= (int)(var10 + var17); ++var20) {
                     float var21 = (float)var18 - var8;
                     float var22 = (float)var19 - var9;
                     float var23 = (float)var20 - var10;
                     if(var21 * var21 + var22 * var22 * 2.0F + var23 * var23 < var17 * var17 && var18 >= 1 && var19 >= 1 && var20 >= 1 && var18 < this.width - 1 && var19 < this.height - 1 && var20 < this.depth - 1) {
                        int var25 = (var19 * this.depth + var20) * this.width + var18;
                        if(this.blocks[var25] == a.e.ab) {
                           this.blocks[var25] = var24;
                        }
                     }
                  }
               }
            }
         }
      }

   }

   private long flood(int var1, int var2, int var3, int var4, int var5) {
      byte var20 = (byte)var5;
      ArrayList var21 = new ArrayList();
      byte var6 = 0;
      int var7 = 1;

      int var8;
      for(var8 = 1; 1 << var7 < this.width; ++var7) {
         ;
      }

      while(1 << var8 < this.depth) {
         ++var8;
      }

      int var9 = this.depth - 1;
      int var10 = this.width - 1;
      int var22 = var6 + 1;
      this.h[0] = ((var2 << var8) + var3 << var7) + var1;
      long var11 = 0L;
      var1 = this.width * this.depth;

      while(var22 > 0) {
         --var22;
         var2 = this.h[var22];
         if(var22 == 0 && var21.size() > 0) {
            this.h = (int[])var21.remove(var21.size() - 1);
            var22 = this.h.length;
         }

         var3 = var2 >> var7 & var9;
         int var13 = var2 >> var7 + var8;

         int var14;
         int var15;
         for(var15 = var14 = var2 & var10; var14 > 0 && this.blocks[var2 - 1] == 0; --var2) {
            --var14;
         }

         while(var15 < this.width && this.blocks[var2 + var15 - var14] == 0) {
            ++var15;
         }

         int var16 = var2 >> var7 & var9;
         int var17 = var2 >> var7 + var8;
         if(var16 != var3 || var17 != var13) {
            System.out.println("Diagonal flood!?");
         }

         boolean var23 = false;
         boolean var24 = false;
         boolean var18 = false;
         var11 += (long)(var15 - var14);

         for(var14 = var14; var14 < var15; ++var14) {
            this.blocks[var2] = var20;
            boolean var19;
            if(var3 > 0) {
               if((var19 = this.blocks[var2 - this.width] == 0) && !var23) {
                  if(var22 == this.h.length) {
                     var21.add(this.h);
                     this.h = new int[1048576];
                     var22 = 0;
                  }

                  this.h[var22++] = var2 - this.width;
               }

               var23 = var19;
            }

            if(var3 < this.depth - 1) {
               if((var19 = this.blocks[var2 + this.width] == 0) && !var24) {
                  if(var22 == this.h.length) {
                     var21.add(this.h);
                     this.h = new int[1048576];
                     var22 = 0;
                  }

                  this.h[var22++] = var2 + this.width;
               }

               var24 = var19;
            }

            if(var13 > 0) {
               byte var25 = this.blocks[var2 - var1];
               if((var20 == a.n.ab || var20 == a.o.ab) && (var25 == a.l.ab || var25 == a.m.ab)) {
                  this.blocks[var2 - var1] = (byte)a.e.ab;
               }

               if((var19 = var25 == 0) && !var18) {
                  if(var22 == this.h.length) {
                     var21.add(this.h);
                     this.h = new int[1048576];
                     var22 = 0;
                  }

                  this.h[var22++] = var2 - var1;
               }

               var18 = var19;
            }

            ++var2;
         }
      }

      return var11;
   }
}
