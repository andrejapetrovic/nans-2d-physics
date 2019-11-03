package math;

import java.util.Random;

public class MathUtill
{
	public static final float PI = (float)StrictMath.PI;
	public static final float EPSILON = 0.0001f;
	public static final float EPSILON_SQ = EPSILON * EPSILON;
	public static final float PENETRATION_CORRECTION = 0.4f;

	public static float clamp( float min, float max, float value )
	{
		return Math.max( min, Math.min( max, value ) );
	}

	public static int round( float a )
	{
		return (int)(a + 0.5f);
	}

	public static float random( float min, float max )
	{
		return( new Random().nextFloat()*( max - min ) + min );
	}

	public static int random( int min, int max )
	{
		return( new Random().nextInt( ( max - min ) + 1 ) + min );
	}
}
