package collision;

import javax.swing.text.Position;

import math.MathUtill;
import math.Vec2;
import shapes.Circle;
import shapes.Rectangle;
import body.Body;

public class CollisionCircleRectangle implements CollisionCallback
{

	public static final CollisionCircleRectangle instance = new CollisionCircleRectangle();

	private CollisionCircleRectangle(){};
	
	@Override
	public void handleCollision( Manifold m, Body a, Body b )
	{
		Circle A = (Circle)a.shape;
		Rectangle B = (Rectangle)b.shape;
		
		m.contactCount = 0;
		
		Vec2 center = B.u.transpose2().mul2( a.position.sub( b.position ) );
		center.add2( b.position );
		
		Vec2 closest = new Vec2();
		closest.x = MathUtill.clamp( b.position.x - B.halfWidth, b.position.x + B.halfWidth, center.x );
		closest.y = MathUtill.clamp( b.position.y - B.halfHeight, b.position.y + B.halfHeight, center.y );
		
		boolean inside = false;

		
		if( center.x == closest.x & center.y == closest.y )
		{
			inside = true;		
		    if ( Math.abs( b.position.x - center.x ) < Math.abs( b.position.y - center.y))
		    {
		      if( closest.x < b.position.x ) 
		        closest.x = b.position.x + B.halfWidth;
		      else
		        closest.x = b.position.x - B.halfHeight;
		    }
		     else
		    {
		      if( closest.y < b.position.y )
		        closest.y = b.position.y + B.halfHeight;
		      else
		        closest.y = b.position.y - B.halfHeight;
		    }
		}
		
		
		Vec2 normal =  center.sub( closest );
		float distSq = normal.lengthSq(); 
		float r = A.r;
		
		if( distSq > r*r & !inside )
			return;
		
		float d = (float)StrictMath.sqrt( distSq );
		
		if( !inside )
		{
			m.penetration = r - d;
			m.normal.set( ( normal ).div( d ).neg());
			B.u.mul2( m.normal ) ;
			m.contact.set( a.position ).add3( m.normal, r );
			m.contactCount = 1;
		}
		else
		{
			m.penetration = r;
			m.normal.set( ( normal ).div( d ).neg());
			B.u.mul2( m.normal ) ;
			m.contact.set( b.position ).add3( m.normal, r );
			m.contactCount = 1;
		}
		
		
	}

}
