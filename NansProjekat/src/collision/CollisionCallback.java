package collision;

import body.Body;

public interface CollisionCallback {
	
	static CollisionCallback[][] dispatch =
		{
		{ CollisionCircleCircle.instance, CollisionCircleRectangle.instance },
		{ CollisionRectangleCircle.instance, CollisionRectangleRectangle.instance }
		};
	
	public void handleCollision( Manifold m, Body a, Body b );
	
}
