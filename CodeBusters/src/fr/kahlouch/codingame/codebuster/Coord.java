package fr.kahlouch.codingame.codebuster;

import java.awt.geom.Point2D;

public class Coord extends Point2D.Double{
	private static final long serialVersionUID = 1L;

	public Coord(){
		super();
	}

	public Coord(final double x, final double y){
		super(x, y);
	}

	public Coord(final Coord p){
		this(p.x, p.y);
	}

	public double distanceToLine(final Affine affine){
		return affine.distanceToPoint(this);
	}

	public String toIntString(){
		return ((int)Math.round(x)) + " " + ((int)Math.round(y));
	}
}
