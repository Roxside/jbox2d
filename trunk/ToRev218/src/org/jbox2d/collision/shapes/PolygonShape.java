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

package org.jbox2d.collision.shapes;

import java.util.List;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.MassData;
import org.jbox2d.collision.OBB;
import org.jbox2d.collision.Segment;
import org.jbox2d.collision.SegmentCollide;
import org.jbox2d.collision.SupportsGenericDistance;
import org.jbox2d.collision.structs.ShapeType;
import org.jbox2d.collision.structs.TestSegmentResult;
import org.jbox2d.common.Mat22;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.XForm;


//Updated to rev 142 of Shape.cpp/.h / PolygonShape.cpp/.h

/** A convex polygon shape.  Create using Body.createShape(ShapeDef), not the ructor here. */
public class PolygonShape extends Shape{
	/** Dump lots of debug information. */
	private static boolean m_debug = false;

	/**
	 * Local position of the shape centroid in parent body frame.
	 */
	public final Vec2 m_centroid;
	
	/**
	 * The vertices of the shape.  Note: use getVertexCount(), not m_vertices.length, to get number of active vertices.
	 */
	public final Vec2 m_vertices[];
	
	/**
	 * The normals of the shape.  Note: use getVertexCount(), not m_normals.length, to get number of active normals.
	 */
	public final Vec2 m_normals[];
	
	/**
	 * Number of active vertices in the shape.
	 */
	public int m_vertexCount;

	public PolygonShape() {
		m_type = ShapeType.POLYGON_SHAPE;

		m_vertexCount = 0;
		m_vertices = new Vec2[Settings.maxPolygonVertices];
		m_normals = new Vec2[Settings.maxPolygonVertices];
		m_radius = Settings.polygonRadius;
	}
	
	/**
	 * Copy vertices. This assumes the vertices define a convex polygon.
	 * It is assumed that the exterior is the the right of each edge.
	 */
	public final void set( Vec2 vertices, int vertexCount){
		
	}

	/**
	 * Build vertices to represent an axis-aligned box.
	 * @param hx the half-width.
	 * @param hy the half-height.
	 */
	public final void setAsBox(float hx, float hy){
		
	}

	/**
	 * Build vertices to represent an oriented box.
	 * @param hx the half-width.
	 * @param hy the half-height.
	 * @param center the center of the box in local coordinates.
	 * @param angle the rotation of the box in local coordinates.
	 */
	public final void setAsBox(float hx, float hy, Vec2 center, float angle){{
		
	}

	/**
	 * Set this as a single edge.
	 * @param v1
	 * @param v2
	 */
	public final void setAsEdge( Vec2 v1,  Vec2 v2){
		
	}

	/**
	 * @see Shape#testPoint(XForm, Vec2)
	 */
	public final boolean testPoint( XForm transform,  Vec2 p){
		
	}

	/**
	 * @see Shape#computeAABB(AABB, XForm)
	 */
	public final void computeAABB(AABB aabb,  XForm transform){
		
	}

	/// @see Shape::ComputeMass
	public final void computeMass(MassData massData, float density) ;

	/// @see Shape::ComputeSubmergedArea
	public final float computeSubmergedArea(	 Vec2 normal,
									float offset,
									 XForm xf, 
									Vec2 c) {
		
	}

	/// @see Shape::ComputeSweepRadius
	public final float computeSweepRadius( Vec2 pivot){
		
	}

	/// Get the supporting vertex index in the given direction.
	public final int getSupport( Vec2 d){
		
	}

	/// Get the supporting vertex in the given direction.
	public final Vec2 getSupportVertex( Vec2 d){
		
	}

	/// Get the vertex count.
	public final int getVertexCount()  {
		return m_vertexCount;
	}

	/// Get a vertex by index.
	public final Vec2 getVertex(int index){
		
	}

	// djm pooled
	private final Vec2 d = new Vec2();
	/**
	 * @see Shape#updateSweepRadius(Vec2)
	 */
	@Override
	public void updateSweepRadius(final Vec2 center) {
		// Update the sweep radius (maximum radius) as measured from
		// a local center point.
		m_sweepRadius = 0.0f;
		for (int i = 0; i < m_vertexCount; ++i) {
			d.set(m_coreVertices[i]);
			d.subLocal(center);
			m_sweepRadius = MathUtils.max(m_sweepRadius, d.length());
		}
	}

	// djm pooled
	private final Vec2 pLocal = new Vec2();
	private final Vec2 temp = new Vec2();
	/**
	 * @see Shape#testPoint(XForm, Vec2)
	 */
	@Override
	public boolean testPoint(final XForm xf, final Vec2 p) {
		temp.set(p);
		temp.subLocal(xf.position);
		Mat22.mulTransToOut(xf.R, temp, pLocal);

		if (m_debug) {
			System.out.println("--testPoint debug--");
			System.out.println("Vertices: ");
			for (int i=0; i < m_vertexCount; ++i) {
				System.out.println(m_vertices[i]);
			}
			System.out.println("pLocal: "+pLocal);
		}

		for (int i = 0; i < m_vertexCount; ++i) {
			temp.set(pLocal);
			temp.subLocal( m_vertices[i]);
			final float dot = Vec2.dot(m_normals[i], temp);

			if (dot > 0.0f) {
				return false;
			}
		}

		return true;
	}


	// djm pooling
	private final Vec2 p1 = new Vec2();
	private final Vec2 p2 = new Vec2();
	private final Vec2 tsd = new Vec2();
	private final Vec2 temp2 = new Vec2();
	/**
	 * @see Shape#testSegment(XForm, TestSegmentResult, Segment, float)
	 */
	@Override
	public SegmentCollide testSegment(final XForm xf, final TestSegmentResult out, final Segment segment, final float maxLambda){
		float lower = 0.0f, upper = maxLambda;

		p1.set(segment.p1);
		p1.subLocal( xf.position);
		Mat22.mulTransToOut(xf.R, p1, p1 );
		p2.set(segment.p2);
		p2.subLocal(xf.position);
		Mat22.mulTransToOut(xf.R, p2, p2);
		tsd.set(p2);
		tsd.subLocal( p1);

		int index = -1;

		for (int i = 0; i < m_vertexCount; ++i)
		{
			// p = p1 + a * d
			// dot(normal, p - v) = 0
			// dot(normal, p1 - v) + a * dot(normal, d) = 0
			temp2.set(m_vertices[i]);
			temp2.subLocal( p1);
			final float numerator = Vec2.dot(m_normals[i], temp2);
			final float denominator = Vec2.dot(m_normals[i], tsd);

			// Note: we want this predicate without division:
			// lower < numerator / denominator, where denominator < 0
			// Since denominator < 0, we have to flip the inequality:
			// lower < numerator / denominator <==> denominator * lower > numerator.

			if (denominator < 0.0f && numerator < lower * denominator)
			{
				// Increase lower.
				// The segment enters this half-space.
				lower = numerator / denominator;
				index = i;
			}
			else if (denominator > 0.0f && numerator < upper * denominator)
			{
				// Decrease upper.
				// The segment exits this half-space.
				upper = numerator / denominator;
			}

			if (upper < lower)
			{
				return SegmentCollide.MISS_COLLIDE;
			}
		}

		assert(0.0f <= lower && lower <= maxLambda);

		if (index >= 0)
		{
			out.lambda = lower;
			Mat22.mulToOut(xf.R, m_normals[index], out.normal);
			return SegmentCollide.HIT_COLLIDE;
		}

		return SegmentCollide.STARTS_INSIDE_COLLIDE;
	}


	// djm pooling, somewhat hot
	private final Vec2 supportDLocal = new Vec2();
	/**
	 * Get the support point in the given world direction.
	 * Use the supplied transform.
	 * @see SupportsGenericDistance#support(Vec2, XForm, Vec2)
	 */
	// djm optimized
	public void support(final Vec2 dest, final XForm xf, final Vec2 d) {
		Mat22.mulTransToOut(xf.R, d, supportDLocal);

		int bestIndex = 0;
		float bestValue = Vec2.dot(m_coreVertices[0], supportDLocal);
		for (int i = 1; i < m_vertexCount; ++i) {
			final float value = Vec2.dot(m_coreVertices[i], supportDLocal);
			if (value > bestValue) {
				bestIndex = i;
				bestValue = value;
			}
		}

		XForm.mulToOut(xf, m_coreVertices[bestIndex], dest);
	}

	public final static Vec2 computeCentroid(final List<Vec2> vs) {
		final int count = vs.size();
		assert(count >= 3);

		final Vec2 c = new Vec2();
		float area = 0.0f;

		// pRef is the reference point for forming triangles.
		// It's location doesn't change the result (except for rounding error).
		final Vec2 pRef = new Vec2();
		//    #if 0
		//        // This code would put the reference point inside the polygon.
		//        for (int i = 0; i < count; ++i)
		//        {
		//            pRef += vs[i];
		//        }
		//        pRef *= 1.0f / count;
		//    #endif

		final float inv3 = 1.0f / 3.0f;

		for (int i = 0; i < count; ++i) {
			// Triangle vertices.
			final Vec2 p1 = pRef;
			final Vec2 p2 = vs.get(i);
			final Vec2 p3 = i + 1 < count ? vs.get(i+1) : vs.get(0);

			final Vec2 e1 = p2.sub(p1);
			final Vec2 e2 = p3.sub(p1);

			final float D = Vec2.cross(e1, e2);

			final float triangleArea = 0.5f * D;
			area += triangleArea;

			// Area weighted centroid
			//c += triangleArea * inv3 * (p1 + p2 + p3);
			c.x += triangleArea * inv3 * (p1.x + p2.x + p3.x);
			c.y += triangleArea * inv3 * (p1.y + p2.y + p3.y);

		}

		// Centroid
		assert(area > Settings.EPSILON);
		c.mulLocal(1.0f / area);
		return c;
	}

	// http://www.geometrictools.com/Documentation/MinimumAreaRectangle.pdf
	// djm this is only called in the ructor, so we can keep this
	// unpooled.  I'll still make optimizations though
	public static void computeOBB(final OBB obb, final Vec2[] vs){
		final int count = vs.length;
		assert(count <= Settings.maxPolygonVertices);
		final Vec2[] p = new Vec2[Settings.maxPolygonVertices + 1];
		for (int i = 0; i < count; ++i){
			p[i] = vs[i];
		}
		p[count] = p[0];

		float minArea = Float.MAX_VALUE;

		final Vec2 ux = new Vec2();
		final Vec2 uy = new Vec2();
		final Vec2 lower = new Vec2();
		final Vec2 upper = new Vec2();
		final Vec2 d = new Vec2();
		final Vec2 r = new Vec2();

		for (int i = 1; i <= count; ++i){
			final Vec2 root = p[i-1];
			ux.set(p[i]);
			ux.subLocal(root);
			final float length = ux.normalize();
			assert(length > Settings.EPSILON);
			uy.x = -ux.y;
			uy.y = ux.x;
			lower.x = Float.MAX_VALUE;
			lower.y = Float.MAX_VALUE;
			upper.x = -Float.MAX_VALUE; // djm wouldn't this just be Float.MIN_VALUE?
			upper.y = -Float.MAX_VALUE;

			for (int j = 0; j < count; ++j) {
				d.set(p[j]);
				d.subLocal(root);
				r.x = Vec2.dot(ux, d);
				r.y = Vec2.dot(uy, d);
				Vec2.minToOut(lower, r, lower);
				Vec2.maxToOut(upper, r, upper);
			}

			final float area = (upper.x - lower.x) * (upper.y - lower.y);
			if (area < 0.95f * minArea){
				minArea = area;
				obb.R.col1.set(ux);
				obb.R.col2.set(uy);

				final Vec2 center = new Vec2(0.5f * (lower.x + upper.x), 0.5f * (lower.y + upper.y));
				Mat22.mulToOut(obb.R, center, obb.center);
				obb.center.addLocal(root);
				//obb.center = root.add(Mat22.mul(obb.R, center));

				obb.extents.x = 0.5f * (upper.x - lower.x);
				obb.extents.y = 0.5f * (upper.y - lower.y);
			}
		}

		assert(minArea < Float.MAX_VALUE);
	}

	// djm pooling, hot method
	private final Mat22 caabbR = new Mat22();
	private final Vec2 caabbH = new Vec2();
	/**
	 * @see Shape#computeAABB(AABB, XForm)
	 */
	@Override
	public void computeAABB(final AABB aabb, final XForm xf) {
		/*Mat22 R = Mat22.mul(xf.R, m_obb.R);
		Mat22 absR = Mat22.abs(R);
		Vec2 h = Mat22.mul(absR, m_obb.extents);
		Vec2 position = xf.position.add(Mat22.mul(xf.R, m_obb.center));*/


		Mat22.mulToOut(xf.R, m_obb.R, caabbR);
		caabbR.absLocal();
		Mat22.mulToOut(caabbR, m_obb.extents, caabbH);
		// we treat the lowerbound like the position
		Mat22.mulToOut(xf.R, m_obb.center, aabb.lowerBound);
		aabb.lowerBound.addLocal(xf.position);

		aabb.upperBound.set(aabb.lowerBound);

		aabb.lowerBound.subLocal(caabbH);
		aabb.upperBound.addLocal(caabbH);
		//aabb.lowerBound = position.sub(caabbH);
		//aabb.upperBound = position.add(caabbH);//save a Vec2 creation, reuse temp
	}

	// djm pooling, hot method
	private final AABB sweptAABB1 = new AABB();
	private final AABB sweptAABB2 = new AABB();
	/**
	 * @see Shape#computeSweptAABB(AABB, XForm, XForm)
	 */
	@Override
	public void computeSweptAABB(final AABB aabb, final XForm transform1, final XForm transform2) {

		computeAABB(sweptAABB1, transform1);
		computeAABB(sweptAABB2, transform2);
		Vec2.minToOut(sweptAABB1.lowerBound, sweptAABB2.lowerBound, aabb.lowerBound);
		Vec2.maxToOut(sweptAABB1.upperBound, sweptAABB2.upperBound, aabb.upperBound);
		//System.out.println("poly sweepaabb: "+aabb.lowerBound+" "+aabb.upperBound);
	}

	/**
	 * @see Shape#computeMass(MassData)
	 */
	// djm not very hot method so I'm not pooling.  still optimized though
	@Override
	public void computeMass(final MassData massData) {
		// Polygon mass, centroid, and inertia.
		// Let rho be the polygon density in mass per unit area.
		// Then:
		// mass = rho * int(dA)
		// centroid.x = (1/mass) * rho * int(x * dA)
		// centroid.y = (1/mass) * rho * int(y * dA)
		// I = rho * int((x*x + y*y) * dA)
		//
		// We can compute these integrals by summing all the integrals
		// for each triangle of the polygon. To evaluate the integral
		// for a single triangle, we make a change of variables to
		// the (u,v) coordinates of the triangle:
		// x = x0 + e1x * u + e2x * v
		// y = y0 + e1y * u + e2y * v
		// where 0 <= u && 0 <= v && u + v <= 1.
		//
		// We integrate u from [0,1-v] and then v from [0,1].
		// We also need to use the Jacobian of the transformation:
		// D = cross(e1, e2)
		//
		// Simplification: triangle centroid = (1/3) * (p1 + p2 + p3)
		//
		// The rest of the derivation is handled by computer algebra.

		assert(m_vertexCount >= 3);

		final Vec2 center = new Vec2(0.0f, 0.0f);
		float area = 0.0f;
		float I = 0.0f;

		// pRef is the reference point for forming triangles.
		// It's location doesn't change the result (except for rounding error).
		final Vec2 pRef = new Vec2(0.0f, 0.0f);

		final float k_inv3 = 1.0f / 3.0f;

		final Vec2 e1 = new Vec2();
		final Vec2 e2 = new Vec2();

		for (int i = 0; i < m_vertexCount; ++i) {
			// Triangle vertices.
			final Vec2 p1 = pRef;
			final Vec2 p2 = m_vertices[i];
			final Vec2 p3 = i + 1 < m_vertexCount ? m_vertices[i+1] : m_vertices[0];

			e1.set(p2);
			e1.subLocal(p1);

			e2.set(p3);
			e2.subLocal(p1);

			final float D = Vec2.cross(e1, e2);

			final float triangleArea = 0.5f * D;
			area += triangleArea;

			// Area weighted centroid
			center.x += triangleArea * k_inv3 * (p1.x + p2.x + p3.x);
			center.y += triangleArea * k_inv3 * (p1.y + p2.y + p3.y);

			final float px = p1.x, py = p1.y;
			final float ex1 = e1.x, ey1 = e1.y;
			final float ex2 = e2.x, ey2 = e2.y;

			final float intx2 = k_inv3 * (0.25f * (ex1*ex1 + ex2*ex1 + ex2*ex2) + (px*ex1 + px*ex2)) + 0.5f*px*px;
			final float inty2 = k_inv3 * (0.25f * (ey1*ey1 + ey2*ey1 + ey2*ey2) + (py*ey1 + py*ey2)) + 0.5f*py*py;

			I += D * (intx2 + inty2);
		}

		// Total mass
		massData.mass = m_density * area;

		// Center of mass
		assert(area > Settings.EPSILON);
		center.mulLocal(1.0f / area);
		massData.center.set(center);

		// Inertia tensor relative to the local origin.
		massData.I = I*m_density;
	}

	/** Get the first vertex and apply the supplied transform. */
	public void getFirstVertexToOut(final XForm xf, final Vec2 out) {
		XForm.mulToOut(xf, m_coreVertices[0], out);
	}

	/** Get the oriented bounding box relative to the parent body. */
	public OBB getOBB() {
		return m_obb.clone();
	}

	/** Get the local centroid relative to the parent body. */
	public Vec2 getCentroid() {
		return m_centroid.clone();
	}

	/** Get the number of vertices. */
	public int getVertexCount() {
		return m_vertexCount;
	}

	/** Get the vertices in local coordinates. */
	public Vec2[] getVertices() {
		return m_vertices;
	}

	/**
	 * Get the core vertices in local coordinates. These vertices
	 * represent a smaller polygon that is used for time of impact
	 * computations.
	 */
	public Vec2[] getCoreVertices()	{
		return m_coreVertices;
	}

	/** Get the edge normal vectors.  There is one for each vertex. */
	public Vec2[] getNormals() {
		return m_normals;
	}

	/** Get the centroid and apply the supplied transform. */
	public Vec2 centroid(final XForm xf) {
		return XForm.mul(xf, m_centroid);
	}
}
