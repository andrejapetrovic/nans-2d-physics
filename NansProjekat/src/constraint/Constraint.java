package constraint;

import body.Body;
import math.MathUtill;
import math.Vec2;

public class Constraint {

	public Body a,b;
	public float distance;
	public float k;
	public boolean visible = true;
	
	public Constraint( Body a, Body b, float distance )
	{
		this.a = a;
		this.b = b;
		this.distance = distance;
		this.k = 0.0015f;
	}
	
	public void applyImpulse( float invDt )
	{
		
		Vec2 axis =  b.position.sub( a.position );
		float currentDistance = axis.length();
		axis.div2( currentDistance );
		
		float relVel = Vec2.dot(  b.velocity.sub( a.velocity ), axis );
		float relDist = currentDistance - distance;
		float remove = relVel + relDist*invDt;
		float impulse = remove / ( a.invMass + b.invMass );
		
		Vec2 vecImpulse =  axis.mul( impulse*k );

		a.velocity.add3( vecImpulse, a.invMass );
		b.velocity.add3( vecImpulse.neg(), b.invMass );
	}
}
