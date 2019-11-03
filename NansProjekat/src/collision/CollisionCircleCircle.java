package collision;

import math.Vec2;
import shapes.Circle;
import body.Body;


public class CollisionCircleCircle implements CollisionCallback {

	public static final CollisionCircleCircle instance = new CollisionCircleCircle();

	private CollisionCircleCircle(){};
	
	@Override
	public void handleCollision( Manifold m, Body a, Body b )
	{
		Circle ca = (Circle)a.shape;
		Circle cb = (Circle)b.shape;
		
		m.contactCount = 0;

		Vec2 normal = b.position.sub( a.position );

		float distSq = normal.lengthSq();
		float radius = ca.r + cb.r;

		if( distSq >= radius*radius )
		{
			m.contactCount = 0;
			return;
		}
		
		float distance = (float)StrictMath.sqrt( distSq );
		
		m.contactCount = 1;
		
		
		if (distance == 0.0f)
		{
			m.penetration = ( ca.r ) ;
			m.normal.set( 1.0f, 0.0f );
			m.contact.set( a.position );
		}
		else
		{
			m.penetration = radius - distance;
			m.normal.set( normal ).div2( distance );
			m.contact.set( m.normal ).mul2( ca.r  ).add2( a.position );
		}
	}
}
