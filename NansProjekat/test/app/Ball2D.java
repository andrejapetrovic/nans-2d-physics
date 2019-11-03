package app;


import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import shapes.Circle;

public class Ball2D extends Circle 
{
	public static final int RADIUS = 21;
	AffineTransform oldtransform;
    AffineTransform transform;
    BufferedImage img = App.img;
	
	public Ball2D(float r) {
		super(r);
	}
	
	@Override
	public void setOrientation(float radians) {
		u.set( radians );
		transform = new AffineTransform();
		transform.rotate( body.orientation, body.position.x, body.position.y );
	}

	@Override
	public  void render( Graphics2D g )
	{
		oldtransform = g.getTransform();
		g.setTransform( transform );
	    g.drawImage( img, (int)(body.position.x - r), (int)(body.position.y - r), (int)(r*2), (int)(r*2), null );
	    g.setTransform( oldtransform );
	}

	
	
}
