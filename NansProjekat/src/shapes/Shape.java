package shapes;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import math.Mat22;
import body.Body;

public abstract class Shape {
	
	public final Mat22 u = new Mat22();
	public Body body;
	
	public enum Type {
		Circle, Rectangle
	}
	
	public abstract Type getType();
	public abstract void computeMass( float density );
	public abstract void setOrientation( float radians );
	public abstract void render( Graphics2D g );
}
