package com.irr310.client.graphics.ether.activities.worldmap;

import com.irr310.common.tools.Log;
import com.irr310.common.tools.Vec2;
import com.irr310.common.world.system.WorldSystem;

/* define scrollable zone for a given WorldMap*/
public class ScrollZone 
{
	/* basically, scrollzone is a rectangle define with 2 Point (max = Top Left corner / min = Bot Right corner) */
	private Vec2 min;
	private Vec2 max;
	
	public ScrollZone() 
	{
		min = new Vec2(0,0);
		max = new Vec2(1,1);
	};
	
	/* update zone */
	public void setZone(Vec2 zone)
	{
		min = min.min(zone);
		max = max.max(zone);
	}
	
	public Vec2 getMin(float zoom)
	{
		return min.multiply(zoom);
	}
	
	public Vec2 getMax(float zoom)
	{
		return max.multiply(zoom);
	}
}