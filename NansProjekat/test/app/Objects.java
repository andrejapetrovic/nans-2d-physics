package app;

import java.awt.Color;

import math.MathUtill;
import shapes.Circle;
import shapes.Rectangle;
import body.Body;
import collision.Scene;
import constraint.Constraint;

public class Objects {

	public static void load( Scene scene )
	{
		int width = App.WIDTH;
		int height = App.HEIGHT;
		
		Body b = null;
		b = scene.add( new Rectangle( width*0.5f , 14 ), width/2, height - 14 );
		b.setStatic();
		b.color = Color.DARK_GRAY;

		b = scene.add( new Rectangle( 9, 190 ), width-75, height - 190 - 28 );
		b.setStatic(); 
		
		b = scene.add( new Rectangle( 85, 9 ), width-150, height - 440 );
		b.setOrientation( MathUtill.PI/6);
		b.setStatic();
		
		b = scene.add( new Rectangle( 9, 75 ), width-220, height - 480 );
		b.setStatic(); 
		
		b = scene.add( new Rectangle( 26, 5 ), width-236-33, height - 440 );
		b.setStatic();
		b.color = new Color( 255, 127, 80, 120 );
		b.ignoreCollision = true;
		
		b = scene.add( new Rectangle( 7, 5), width-236, height - 440   ); // gore desno
		b.setStatic();
		//b.color = new Color( 120, 156, 255);
		b.color = new Color( 255, 127, 80);
		
		b = scene.add( new Rectangle( 1, 1), width-236 - 20, height - 440 ); // gore mid desno
		b.setStatic();
		b.ignoreCollision = true;
				
		b = scene.add( new Rectangle( 1, 1), width-236 - 46, height - 440 ); //gore mid levo
		b.setStatic();
		b.ignoreCollision = true;		
		
		b = scene.add( new Rectangle( 4, 5 ), width-300, height - 440   ); // gore levo
		b.setStatic();
		b.color = new Color( 255, 127, 80 );
		
		b = scene.add( new Rectangle(3,3), width-246, height - 400 );// mid desno  // y = 40; x = 10 d = sqrt 2600 = 41
		b.setDensity( 350 );
		
		b = scene.add( new Rectangle(3,3), width-269, height - 400 );//mid 
		b.setStatic();
		b.setDensity( 100 );
		b.ignoreCollision = true;
		
		b = scene.add( new Rectangle(3,3), width-290, height - 400 ); // mid levo
		b.setStatic();
		b.setDensity( 100 );
		
		b = scene.add( new Rectangle(3,3), width-246, height - 370 );//dole desno //y = 30 x = 0 d = 30
		b.setStatic();
		b.setDensity( 300 );
		
		b = scene.add( new Rectangle(3,3), width-269, height - 370 );//dole mid
		b.setStatic();
		b.ignoreCollision = true;
		b.setDensity( 100 );
				
		b = scene.add( new Rectangle(3,3), width-290, height - 370 );//dole levo
		b.setStatic();
		b.setDensity( 300 );
		
		Body[] parts = new Body[11];
		for( int i = 0; i < 11; i++ )
			parts[i] = scene.bodies.get( i );
		for( int i = 5; i >= 0; i-- )
		{
			parts[i].color = new Color( 0, 0, 0 , 0 );
			parts[i].invMass = parts[5].invMass; 
			parts[i].restitution = 0.5f;
		//	parts[i].invInertia = 0;
		}
		
		scene.add( new Constraint( parts[9], parts[5], 37));
		scene.add( new Constraint( parts[8], parts[5], 37));
		scene.add( new Constraint( parts[8], parts[4], 37));
		scene.add( new Constraint( parts[7], parts[4], 37));
		scene.add( new Constraint( parts[7], parts[3], 37));
		scene.add( new Constraint( parts[6], parts[3], 37));
		
		scene.add( new Constraint( parts[4], parts[2], 30));
		scene.add( new Constraint( parts[4], parts[0], 30));
		scene.add( new Constraint( parts[5], parts[2], 23));
		scene.add( new Constraint( parts[3], parts[0], 23));
		
		scene.add( new Constraint( parts[5], parts[1], 30));
		scene.add( new Constraint( parts[3], parts[1], 30));
		
		scene.add( new Constraint( parts[8], parts[5], 35));
		scene.add( new Constraint( parts[7], parts[3], 35));
		
	
	}
	
}
