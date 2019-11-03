package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import math.MathUtill;

public class Circle extends Shape {
	
	public float r;
	private float rx, ry;
	
	public Circle( float r ) {
		this.r = r;
	}

	@Override
	public Type getType() {
		return Type.Circle;
	}

	@Override
	public void computeMass( float density )
	{
		body.mass = MathUtill.PI * r * r * density;
		body.invMass = (body.mass != 0.0f) ? 1.0f / body.mass : 0.0f;
		body.inertia = body.mass * r * r ;
		body.invInertia = (body.inertia != 0.0f) ? 1.0f / body.inertia : 0.0f;
	}

	@Override
	public void setOrientation(float radians) 
	{
		u.set( radians );
		rx = (float)StrictMath.cos( body.orientation )*r;
		ry = (float)StrictMath.sin( body.orientation )*r;
	}

	@Override
	public  void render( Graphics2D g )
	{
		g.setColor( body.color );
		g.fill( new Ellipse2D.Float( body.position.x - r, body.position.y - r, 2*r, 2*r ) );
		g.setColor( Color.black );
		g.draw( new Ellipse2D.Float( body.position.x - r, body.position.y - r, 2*r, 2*r ) );
		g.draw( new Line2D.Float( body.position.x, body.position.y, body.position.x + rx, body.position.y + ry ) );
		g.fill( new Ellipse2D.Float( body.position.x - 2, body.position.y - 2, 4, 4 ) );
	}

}
