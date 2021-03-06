/*
 * Java port of Bullet (c) 2008 Martin Dvorak <jezek2@advel.cz>
 *
 * Bullet Continuous Collision Detection and Physics Library
 * Copyright (c) 2003-2008 Erwin Coumans  http://www.bulletphysics.com/
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from
 * the use of this software.
 * 
 * Permission is granted to anyone to use this software for any purpose, 
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 * 
 * 1. The origin of this software must not be misrepresented; you must not
 *    claim that you wrote the original software. If you use this software
 *    in a product, an acknowledgment in the product documentation would be
 *    appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 *    misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 */

package com.bulletphysics.collision.shapes;

import javax.vecmath.Vector3d;

import com.bulletphysics.collision.broadphase.BroadphaseNativeType;
import com.bulletphysics.linearmath.Transform;
import com.bulletphysics.linearmath.TransformUtil;
import com.bulletphysics.linearmath.VectorUtil;

/**
 * StaticPlaneShape simulates an infinite non-moving (static) collision plane.
 * 
 * @author jezek2
 */
public class StaticPlaneShape extends ConcaveShape {

	protected final Vector3d localAabbMin = new Vector3d();
	protected final Vector3d localAabbMax = new Vector3d();
	
	protected final Vector3d planeNormal = new Vector3d();
	protected double planeConstant;
	protected final Vector3d localScaling = new Vector3d(0f, 0f, 0f);

	public StaticPlaneShape(Vector3d planeNormal, double planeConstant) {
		this.planeNormal.normalize(planeNormal);
		this.planeConstant = planeConstant;
	}

	public Vector3d getPlaneNormal(Vector3d out) {
		out.set(planeNormal);
		return out;
	}

	public double getPlaneConstant() {
		return planeConstant;
	}
	
	@Override
	public void processAllTriangles(TriangleCallback callback, Vector3d aabbMin, Vector3d aabbMax) {
		Vector3d tmp = new Vector3d();
		Vector3d tmp1 = new Vector3d();
		Vector3d tmp2 = new Vector3d();

		Vector3d halfExtents = new Vector3d();
		halfExtents.sub(aabbMax, aabbMin);
		halfExtents.scale(0.5f);

		double radius = halfExtents.length();
		Vector3d center = new Vector3d();
		center.add(aabbMax, aabbMin);
		center.scale(0.5f);

		// this is where the triangles are generated, given AABB and plane equation (normal/constant)

		Vector3d tangentDir0 = new Vector3d(), tangentDir1 = new Vector3d();

		// tangentDir0/tangentDir1 can be precalculated
		TransformUtil.planeSpace1(planeNormal, tangentDir0, tangentDir1);

		Vector3d projectedCenter = new Vector3d();
		tmp.scale(planeNormal.dot(center) - planeConstant, planeNormal);
		projectedCenter.sub(center, tmp);

		Vector3d[] triangle = new Vector3d[] { new Vector3d(), new Vector3d(), new Vector3d() };

		tmp1.scale(radius, tangentDir0);
		tmp2.scale(radius, tangentDir1);
		VectorUtil.add(triangle[0], projectedCenter, tmp1, tmp2);

		tmp1.scale(radius, tangentDir0);
		tmp2.scale(radius, tangentDir1);
		tmp.sub(tmp1, tmp2);
		VectorUtil.add(triangle[1], projectedCenter, tmp);

		tmp1.scale(radius, tangentDir0);
		tmp2.scale(radius, tangentDir1);
		tmp.sub(tmp1, tmp2);
		triangle[2].sub(projectedCenter, tmp);

		callback.processTriangle(triangle, 0, 0);

		tmp1.scale(radius, tangentDir0);
		tmp2.scale(radius, tangentDir1);
		tmp.sub(tmp1, tmp2);
		triangle[0].sub(projectedCenter, tmp);

		tmp1.scale(radius, tangentDir0);
		tmp2.scale(radius, tangentDir1);
		tmp.add(tmp1, tmp2);
		triangle[1].sub(projectedCenter, tmp);

		tmp1.scale(radius, tangentDir0);
		tmp2.scale(radius, tangentDir1);
		VectorUtil.add(triangle[2], projectedCenter, tmp1, tmp2);

		callback.processTriangle(triangle, 0, 1);
	}

	@Override
	public void getAabb(Transform t, Vector3d aabbMin, Vector3d aabbMax) {
		aabbMin.set(-1e30f, -1e30f, -1e30f);
		aabbMax.set(1e30f, 1e30f, 1e30f);
	}

	@Override
	public BroadphaseNativeType getShapeType() {
		return BroadphaseNativeType.STATIC_PLANE_PROXYTYPE;
	}

	@Override
	public void setLocalScaling(Vector3d scaling) {
		localScaling.set(scaling);
	}

	@Override
	public Vector3d getLocalScaling(Vector3d out) {
		out.set(localScaling);
		return out;
	}

	@Override
	public void calculateLocalInertia(double mass, Vector3d inertia) {
		//moving concave objects not supported
		inertia.set(0f, 0f, 0f);
	}

	@Override
	public String getName() {
		return "STATICPLANE";
	}

}
