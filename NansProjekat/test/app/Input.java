package app;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import math.MathUtill;
import math.Vec2;
import shapes.Circle;
import body.Body;
import collision.Scene;

public class Input extends KeyMouseAdapter {

	private Scene scene;
	private Point startingPoint = null;
	private Point endingPoint = null;
	private Body b;
	private final Vec2 force = new Vec2();
	private ArrayList<Vec2> points = new ArrayList<Vec2>();
	private float h = 1.0f/120.0f;
	
	public Input( Scene scene, App app ) 
	{
		this.scene = scene;
		app.addMouseListener( this );
		app.addMouseMotionListener( this );
		app.addKeyListener( this );
	}
	

	@Override
	public void mousePressed(MouseEvent e) 
	{
		if(e.getButton() == MouseEvent.BUTTON1 ) 
		{
			startingPoint = e.getPoint();
			endingPoint = e.getPoint();
			force.set( 0, 0 );
			b = new Body( new Circle( Ball2D.RADIUS ), startingPoint.x , startingPoint.y );
			Vec2[] initValues = scene.initializeCalculation(  b, force );
			points = scene.calculatePoints( initValues[0], initValues[1], h, 120 );
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if( startingPoint != null )
		{
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			Body body = scene.add( b.shape , startingPoint.x, startingPoint.y);
			body.applyForce( force );
			body.torque = force.x*-6;
			body.color = new Color( 120, 156, 255); 
			body.color = new Color( 255, 155, 50); 
			
			body.setOrientation( MathUtill.random( -MathUtill.PI, MathUtill.PI ) );
		
			startingPoint = null;
		}		
		}
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if( startingPoint != null)
		{
		force.set( endingPoint.x - startingPoint.x, endingPoint.y - startingPoint.y );
		force.mul2( 8500000f );
		Vec2[] initValues = scene.initializeCalculation(  b, force );
		points = scene.calculatePoints( initValues[0], initValues[1], h, 120 );
		endingPoint = e.getPoint();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) 
	{
		if(arg0.getKeyCode() == KeyEvent.VK_SPACE){
			scene.removeCircles();
		}
	}
	
	public void renderMouseActions( Graphics2D g )
	{
        if ( startingPoint != null & endingPoint != null) 
        {
        	g.setColor( Color.black );
            g.drawLine( startingPoint.x, startingPoint.y, endingPoint.x, endingPoint.y );
            g.setColor( new Color( 120, 120, 255) );
            for ( int i = 0; i < points.size(); ++i )
            {
            	if( h*i == Math.floor( h*i ) )
            		g.fill( new Ellipse2D.Float( points.get(i).x - 4, points.get(i).y - 4, 8, 8 ));
            	else
            		g.fill( new Ellipse2D.Float( points.get(i).x - 2, points.get(i).y - 2, 4, 4 ));
            }
        }
	}
	
}
