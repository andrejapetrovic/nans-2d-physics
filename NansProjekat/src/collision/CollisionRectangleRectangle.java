package collision;

import body.Body;

public class CollisionRectangleRectangle implements CollisionCallback {

	public static final CollisionRectangleRectangle instance = new CollisionRectangleRectangle();
	
	private CollisionRectangleRectangle(){};
	
	@Override
	public void handleCollision( Manifold m, Body a, Body b ){}
}
