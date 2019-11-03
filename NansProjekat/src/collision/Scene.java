package collision;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import constraint.Constraint;
import math.Vec2;
import shapes.Circle;
import shapes.Shape;
import body.Body;

public class Scene {

	public static final Vec2 GRAVITY = new Vec2( 0.0f, 9.81f*80f );
	public ArrayList<Body> bodies = new ArrayList<Body>();
	public ArrayList<Manifold> contacts = new ArrayList<Manifold>();
	public ArrayList<Constraint> constraints = new ArrayList<Constraint>();

	private float dt;
	private float invDt;
	private int iterations;
	private ArrayList<Body> removeList = new ArrayList<Body>();

	public Scene( float dt, int iterations )
	{
		this.iterations = iterations;
		this.dt = dt;
		this.invDt = 1/dt;
	}

	public void step()
	{
		contacts.clear();
		for (int i = 0; i < bodies.size(); ++i)
		{
			Body A = bodies.get( i );
			
			for (int j = i + 1; j < bodies.size(); ++j)
			{
				Body B = bodies.get( j );

				if( ( A.isStatic() & B.isStatic() ) | A.ignoreCollision | B.ignoreCollision )
					continue;
				
				Manifold m = new Manifold( A, B );
				m.solve();
				
				if (m.contactCount > 0)
					contacts.add( m ); 
			}
		}
		
		for (int i = 0; i < contacts.size(); ++i)
			contacts.get( i ).initialize();
		
		for( int i = 0; i < bodies.size(); ++i )
			integrate( bodies.get( i ), dt );
		
		for( int j = 0; j < iterations; ++j)
		{
			for( int i = 0; i < contacts.size(); ++i )
				contacts.get( i ).applyImpulse();
			
			for( int i = 0; i < constraints.size(); ++i )
				constraints.get( i ).applyImpulse( invDt );
		}
		
		for (int i = 0; i < contacts.size(); ++i)
			contacts.get(i).positionalCorrection();
		

		for (int i = 0; i < bodies.size(); ++i)
		{
			Body b = bodies.get( i );
			b.force.set( 0, 0 );
			b.torque = 0;
		}
	}


	public Body add( Shape shape, int x, int y )
	{
		Body b = new Body( shape, x, y );
		bodies.add( 0, b );
		return b;
	}
	
	public void add( Constraint j )
	{
		constraints.add( j );
	}
	
	public void remove( Constraint j )
	{
		constraints.remove( j );
	}

	public void clear()
	{
		contacts.clear();
		bodies.clear();
	}
	

	public void integrate( Body b, float dt )
	{
		if (b.isStatic()) {
			return;
		}
		
		b.velocity.add3( b.force, b.invMass*dt );
		b.velocity.add3( GRAVITY, dt);
		b.angularVelocity += b.torque*b.invInertia*dt;

		b.position.add3( b.velocity, dt );
		b.setOrientation( b.orientation += b.angularVelocity*dt );		
	}

	public Vec2[] initializeCalculation( Body b, Vec2 force )
	{
		Vec2 velocity = new Vec2();
		velocity.add3( force, b.invMass*dt );
		Vec2 pos = new Vec2( b.position );
		Vec2[] initValues = { pos, velocity };
		return initValues;
	}
	
	public ArrayList<Vec2> calculatePoints( Vec2 position, Vec2 velocity, float dt, int iter )
	{
		ArrayList<Vec2> points = new ArrayList<Vec2>();
		for( int i = 0; i <= iter; ++i )
		{
			points.add( new Vec2( position ) );
			velocity.add3( GRAVITY, dt );
			position.add3( velocity, dt );
		}
		return points;
	}
	
	public void removeCircles()
	{
		for( int i = 0; i < bodies.size(); ++i ) 
		{
			Body b = bodies.get(i);
			if( !b.isStatic() & b.shape instanceof Circle)
				removeList.add( bodies.get( i ) );
		}
		
		bodies.removeAll( removeList );
		removeList.clear();
	}
	
	public void render( Graphics2D g ) 
	{
		for (int i = 0; i < bodies.size(); ++i )
			bodies.get( i ).shape.render( g );
		
		g.setColor( Color.white.brighter() );
		
		for (int i = 0; i < constraints.size(); ++i)
		{
			if( constraints.get(i).visible )
			{
				Body A = constraints.get(i).a;
				Body B = constraints.get(i).b;
				g.draw( new Line2D.Float( A.position.x, A.position.y, B.position.x, B.position.y ) );
			}
		}
		
		for( int i = 0; i < contacts.size(); ++i)
		{
			Manifold m = contacts.get( i );
			Vec2 pen = m.contact.add4( m.normal, m.penetration );
			g.setStroke( new BasicStroke(3));
			//g.draw( new Line2D.Float( m.contact.x, m.contact.y, pen.x, pen.y ));
			g.setStroke( new BasicStroke(1));
		}
	}
}