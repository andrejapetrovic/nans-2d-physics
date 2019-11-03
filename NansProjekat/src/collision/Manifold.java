package collision;


import math.MathUtill;
import math.Vec2;
import body.Body;

public class Manifold
{

	private Body a;
	private Body b;
	public float penetration;
	public final Vec2 normal = new Vec2();
	public final Vec2 contact = new Vec2();
	public int contactCount;
	public float e;
	public float df;
	public float sf;


	public Manifold( Body a, Body b )
	{
		this.a = a;
		this.b = b;
	}

	public void solve()
	{
		int ia = a.shape.getType().ordinal();
		int ib = b.shape.getType().ordinal();
		
		CollisionCallback.dispatch[ia][ib].handleCollision( this, a, b );

	}

	public void initialize()
	{
		e = StrictMath.min( a.restitution, b.restitution );

		sf = (float)StrictMath.sqrt( a.friction * b.friction );
		df = (float)StrictMath.sqrt( a.friction * b.friction );
		
	}

	public void applyImpulse()
	{		
		Vec2 ra = contact.sub( a.position );
		Vec2 rb = contact.sub( b.position );
		
		//Vec2 rv = b.velocity.sub( a.velocity );
		Vec2 rv = b.velocity.add( Vec2.cross2( b.angularVelocity, rb ) ).sub( a.velocity ).sub( Vec2.cross2( a.angularVelocity, ra ) );
		float contactVel = Vec2.dot( rv, normal );

		if (contactVel > 0)
			return;

		float raCrossN = Vec2.cross( ra, normal );
		float rbCrossN = Vec2.cross( rb, normal );
		float invMassSum = a.invMass + b.invMass + (raCrossN * raCrossN) * a.invInertia + (rbCrossN * rbCrossN) * b.invInertia;

		float j = -(1.0f + e) * contactVel;
		j /= invMassSum;
		
		Vec2 impulse = normal.mul( j );
		a.applyImpulse( impulse.neg(), ra );
		b.applyImpulse( impulse, rb );
			
		
		Vec2 t = new Vec2( rv );
		t.add3( normal, -Vec2.dot( rv, normal ) );
		t.normalize();
		
		float raCrossT = Vec2.cross( ra, t );
		float rbCrossT = Vec2.cross( rb, t );
		
		float invMassSumT = a.invMass + b.invMass + (raCrossT * raCrossT) * a.invInertia + (rbCrossT * rbCrossT) * b.invInertia;
		
		//rv = b.velocity.sub( a.velocity );
		rv = b.velocity.add( Vec2.cross2( b.angularVelocity, rb ) ).sub( a.velocity ).sub( Vec2.cross2( a.angularVelocity, ra ) );
		
		float jt = -Vec2.dot( rv, t );
		jt /= invMassSumT;
		
		Vec2 tangentImpulse;
		if (StrictMath.abs( jt ) < j * sf)
		{
			tangentImpulse = t.mul( jt );
		}
		else
		{
			tangentImpulse = t.mul( -j ).mul( df );
		}
		
		a.applyImpulse( tangentImpulse.neg(), ra );
		b.applyImpulse( tangentImpulse, rb );
	}
	
	public void positionalCorrection()
	{
		float correction = penetration / (a.invMass + b.invMass) * MathUtill.PENETRATION_CORRECTION;

		a.position.add3( normal, -a.invMass * correction );
		b.position.add3( normal, b.invMass * correction );
	}

}
