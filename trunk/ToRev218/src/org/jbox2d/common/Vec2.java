/*
 * JBox2D - A Java Port of Erin Catto's Box2D
 * 
 * JBox2D homepage: http://jbox2d.sourceforge.net/
 * Box2D homepage: http://www.box2d.org
 * 
 * This software is provided 'as-is', without any express or implied
 * warranty.  In no event will the authors be held liable for any damages
 * arising from the use of this software.
 * 
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 * 
 * 1. The origin of this software must not be misrepresented; you must not
 * claim that you wrote the original software. If you use this software
 * in a product, an acknowledgment in the product documentation would be
 * appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 * misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 */

package org.jbox2d.common;

/**
 * A 2-dimensional vector class.  Used heavily in JBox2d.
 * djm: added ToOut methods
 */
public class Vec2 {
	/** Should we count Vec2 creations? */
	static public boolean watchCreations = true;
	/**
	 * Running count of Vec2 creations.  Must be zeroed out
	 * manually (perhaps at start of time step).  Incremented
	 * in Vec2 constructor if watchCreations flag is true.
	 * <BR><BR>
	 * Mainly used for optimization purposes, since temporary
	 * Vec2 creation is often a bottleneck.
	 */
	static public int creationCount = 0;

	public float x, y;

	public Vec2() {
		this(0, 0);
	}

	public Vec2(float x, float y) {
		if (Vec2.watchCreations) {
			++Vec2.creationCount;
		}
		this.x = x;
		this.y = y;
		// testbed.PTest.debugCount++;
	}

	public Vec2( Vec2 toCopy) {
		this(toCopy.x, toCopy.y);
	}

	/** Zero out this vector. */
	public final void setZero() {
		x = 0.0f;
		y = 0.0f;
	}

	/** Set the vector component-wise. */
	public final void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/** Set this vector to another vector. */
	public final Vec2 set(Vec2 v) {
		this.x = v.x;
		this.y = v.y;
		return this;
	}

	/** Return the sum of this vector and another; does not alter either one. */
	public final Vec2 add(Vec2 v) {
		return new Vec2(x + v.x, y + v.y);
	}
	
	

	/** Return the difference of this vector and another; does not alter either one. */
	public final Vec2 sub(Vec2 v) {
		return new Vec2(x - v.x, y - v.y);
	}

	/** Return this vector multiplied by a scalar; does not alter this vector. */
	public final Vec2 mul(float a) {
		return new Vec2(x * a, y * a);
	}

	/** Return the negation of this vector; does not alter this vector. */
	public final Vec2 negate() {
		return new Vec2(-x, -y);
	}

	/** Flip the vector and return it - alters this vector. */
	public final Vec2 negateLocal() {
		x = -x;
		y = -y;
		return this;
	}

	/** Add another vector to this one and returns result - alters this vector. */
	public final Vec2 addLocal(Vec2 v) {
		x += v.x;
		y += v.y;
		return this;
	}
	
	/** Adds values to this vector and returns result - alters this vector. */
	public final Vec2 addLocal( float x, float y) {
		this.x+=x;
		this.y+=y;
		return this;
	}

	/** Subtract another vector from this one and return result - alters this vector. */
	public final Vec2 subLocal(Vec2 v) {
		x -= v.x;
		y -= v.y;
		return this;
	}

	/** Multiply this vector by a number and return result - alters this vector. */
	public final Vec2 mulLocal(float a) {
		x *= a;
		y *= a;
		return this;
	}

	/** Return the length of this vector. */
	public final float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/** Return the squared length of this vector. */
	public final float lengthSquared() {
		return (x*x + y*y);
	}

	/** Normalize this vector and return the length before normalization.  Alters this vector. */
	public final float normalize() {
		float length = length();
		if (length < Settings.EPSILON) {
			return 0f;
		}

		float invLength = 1.0f / length;
		x *= invLength;
		y *= invLength;
		return length;
	}

	/** True if the vector represents a pair of valid, non-infinite floating point numbers. */
	public final boolean isValid() {
		return x != Float.NaN && x != Float.NEGATIVE_INFINITY
		&& x != Float.POSITIVE_INFINITY && y != Float.NaN
		&& y != Float.NEGATIVE_INFINITY && y != Float.POSITIVE_INFINITY;
	}

	/** Return a new vector that has positive components. */
	public final Vec2 abs() {
		return new Vec2(Math.abs(x), Math.abs(y));
	}

	/* djm created */
	public final void absLocal(){
		x = Math.abs(x);
		y = Math.abs(y);
	}

	@Override
	/** Return a copy of this vector. */
	public final Vec2 clone() {
		return new Vec2(x, y);
	}

	@Override
	public final String toString() {
		return "(" + x + "," + y + ")";
	}

	/*
	 * Static
	 */

	public final static Vec2 abs(Vec2 a) {
		return new Vec2(Math.abs(a.x), Math.abs(a.y));
	}

	/* djm created */
	public final static void absToOut(Vec2 a, Vec2 out){
		out.x = Math.abs( a.x);
		out.y = Math.abs( a.y);
	}

	public final static float dot(Vec2 a, Vec2 b) {
		return a.x * b.x + a.y * b.y;
	}

	public final static float cross(Vec2 a, Vec2 b) {
		return a.x * b.y - a.y * b.x;
	}

	public final static Vec2 cross(Vec2 a, float s) {
		return new Vec2(s * a.y, -s * a.x);
	}

	/* djm created */
	public final static void crossToOut(Vec2 a, float s, Vec2 out){
		float tempy = -s * a.x;
		out.x = s * a.y;
		out.y = tempy;
	}

	public final static Vec2 cross(float s, Vec2 a) {
		return new Vec2(-s * a.y, s * a.x);
	}

	/* djm created */
	public final static void crossToOut(float s, Vec2 a, Vec2 out){
		float tempY = s * a.x;
		out.x = -s * a.y;
		out.y = tempY;
	}
	
	public final static void negateToOut(Vec2 a, Vec2 out){
		out.x = -a.x;
		out.y = -a.y;
	}

	public final static Vec2 min(Vec2 a, Vec2 b) {
		return new Vec2(a.x < b.x ? a.x : b.x, a.y < b.y ? a.y : b.y);
	}

	public final static Vec2 max(Vec2 a, Vec2 b) {
		return new Vec2(a.x > b.x ? a.x : b.x, a.y > b.y ? a.y : b.y);
	}

	/* djm created */
	public final static void minToOut(Vec2 a, Vec2 b, Vec2 out) {
		out.x = a.x < b.x ? a.x : b.x;
		out.y = a.y < b.y ? a.y : b.y;
	}

	/* djm created */
	public final static void maxToOut(Vec2 a, Vec2 b, Vec2 out) {
		out.x = a.x > b.x ? a.x : b.x;
		out.y = a.y > b.y ? a.y : b.y;
	}
}