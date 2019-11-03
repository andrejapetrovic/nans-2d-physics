package body;

import java.awt.Color;

import math.MathUtill;
import math.Vec2;
import shapes.Shape;

public class Body {

	public final Vec2 position = new Vec2();
	public final Vec2 velocity = new Vec2();
	public final Vec2 force = new Vec2();
	public float angularVelocity;
	public float torque;
	public float orientation;
	public float mass, invMass, inertia, invInertia;
	public float friction;
	public float restitution;
	public final Shape shape;
	public Color color;
	public boolean ignoreCollision;
	public boolean isStatic = false;
	
	public Body( Shape shape, int x, int y )
	{
		position.set(x, y);
		this.shape = shape;
		shape.body = this;
		setDefaultValues();
	}
	
	public void setDefaultValues()
	{
		velocity.set(0, 0);
		force.set(0, 0);
		torque = 0;
		orientation = 0;
		friction = 0.5f;
		angularVelocity = 0;
		restitution = 0.6f;
		color = new Color( 224, 224, 224);
		ignoreCollision = false;
		
		setDensity( 30f );
		setOrientation( orientation );	
	}

	public void applyImpulse( Vec2 impulse, Vec2 contactVector )
	{
		velocity.add3( impulse, invMass );
		angularVelocity += invInertia * Vec2.cross( contactVector, impulse );
	}
	
	public void applyForce( Vec2 f )
	{
		force.add2( f );
	}
	
	
	public void setStatic() 
	{
		velocity.set( 0, 0 );
		mass = 0;
		invMass = 0;
		inertia = 0;
		invInertia = 0;
		isStatic = true;
	}
	
	public boolean setDensity( float density ) 
	{
		shape.computeMass( density );
		isStatic = false;
		return isStatic;
	}
	
	public void setOrientation( float radians )
	{
		orientation = radians;
		shape.setOrientation( radians );
	}
	
	@Override
	public String toString() 
	{
		return shape.getType().toString();
	}
	
	public boolean isStatic() 
	{
		return isStatic;
	}
	
}
