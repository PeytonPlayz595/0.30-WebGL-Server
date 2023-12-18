package com.mojang.minecraft.model;

import com.mojang.util.MathHelper;

public final class Vector3D {

   public float x;
   public float y;
   public float z;


   public Vector3D(float var1, float var2, float var3) {
      this.x = var1;
      this.y = var2;
      this.z = var3;
   }

   public final Vector3D add(float var1, float var2, float var3) {
      return new Vector3D(this.x + var1, this.y + var2, this.z + var3);
   }

   public final float distanceSquared(Vector3D var1) {
      float var2 = var1.x - this.x;
      float var3 = var1.y - this.y;
      float var4 = var1.z - this.z;
      return MathHelper.sqrt(var2 * var2 + var3 * var3 + var4 * var4);
   }

   public final float distance(Vector3D var1) {
      float var2 = var1.x - this.x;
      float var3 = var1.y - this.y;
      float var4 = var1.z - this.z;
      return var2 * var2 + var3 * var3 + var4 * var4;
   }

   public final Vector3D getXIntersection(Vector3D var1, float var2) {
      float var3 = var1.x - this.x;
      float var4 = var1.y - this.y;
      float var5 = var1.z - this.z;
      return var3 * var3 < 1.0E-7F?null:((var2 = (var2 - this.x) / var3) >= 0.0F && var2 <= 1.0F?new Vector3D(this.x + var3 * var2, this.y + var4 * var2, this.z + var5 * var2):null);
   }

   public final Vector3D getYIntersection(Vector3D var1, float var2) {
      float var3 = var1.x - this.x;
      float var4 = var1.y - this.y;
      float var5 = var1.z - this.z;
      return var4 * var4 < 1.0E-7F?null:((var2 = (var2 - this.y) / var4) >= 0.0F && var2 <= 1.0F?new Vector3D(this.x + var3 * var2, this.y + var4 * var2, this.z + var5 * var2):null);
   }

   public final Vector3D getZIntersection(Vector3D var1, float var2) {
      float var3 = var1.x - this.x;
      float var4 = var1.y - this.y;
      float var5;
      return (var5 = var1.z - this.z) * var5 < 1.0E-7F?null:((var2 = (var2 - this.z) / var5) >= 0.0F && var2 <= 1.0F?new Vector3D(this.x + var3 * var2, this.y + var4 * var2, this.z + var5 * var2):null);
   }

   public final String toString() {
      return "(" + this.x + ", " + this.y + ", " + this.z + ")";
   }
}
