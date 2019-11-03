package shapes;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import math.Vec2;

public class Rectangle extends Shape{

	public Vec2[] vertices = Vec2.arrayOf(4);
	private Vec2[] v = Vec2.arrayOf(4);
	public float halfWidth, halfHeight;
	
	public Rectangle( float halfWidth, float halfHeight ) 
	{
		this.halfWidth = halfWidth;
		this.halfHeight = halfHeight;
		setVertices();
	}
	
	private void setVertices()
	{
		vertices[0].set( -halfWidth, -halfHeight );
		vertices[1].set( halfWidth, -halfHeight );
		vertices[2].set( halfWidth, halfHeight );
		vertices[3].set( -halfWidth, halfHeight );	
	}
	
	@Override
	public Type getType() 
	{
		return Type.Rectangle;
	}

	@Override
	public void computeMass( float density )
	{
		float area = 4 * halfWidth * halfHeight;
		float d = (float)StrictMath.sqrt( halfWidth*halfWidth + halfHeight*halfHeight );
		body.mass = area * density;
		body.invMass = (body.mass != 0.0f) ? 1.0f / body.mass : 0.0f;
		body.inertia = body.mass * d * d;
		body.invInertia = (body.inertia != 0.0f) ? 1.0f / body.inertia : 0.0f;
	}

	@Override
	public void setOrientation(float radians)
	{
		u.set( radians );
		for (int i = 0; i < 4; i++) {
			v[i].set( vertices[i] );
			u.mul2( v[i] );
			v[i].add2( body.position );
		}
	}

	@Override
	public void render( Graphics2D g ) 
	{
		Path2D.Float path = new Path2D.Float();
		for (int i = 0; i < 4; i++) 
		{
			if (i == 0) 
				path.moveTo( v[i].x, v[i].y );
			else 
				path.lineTo( v[i].x, v[i].y );
		}
		path.closePath();

		g.setColor( body.color );
		g.fill( path );
		g.draw( path );
		//g.setColor( Color.red );
		//g.fill( new Ellipse2D.Float( body.position.x - 2, body.position.y - 2, 4, 4 ) );
	}

}
