package collision;

import body.Body;

public class CollisionRectangleCircle implements CollisionCallback {

	public static final CollisionRectangleCircle instance = new CollisionRectangleCircle();

	private CollisionRectangleCircle(){};
	
	@Override
	public void handleCollision( Manifold m, Body a, Body b ) {
		CollisionCircleRectangle.instance.handleCollision(m, b, a); 
		
		if ( m.contactCount > 0 )
			m.normal.neg2();
	}
	
}
