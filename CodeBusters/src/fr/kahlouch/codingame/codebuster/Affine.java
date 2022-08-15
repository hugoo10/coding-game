package fr.kahlouch.codingame.codebuster;

public class Affine{
	private double a;
	private double b;

	public Affine(final double a, final double b){
		this.a = a;
		this.b = b;
	}

	public Affine(final Vector vector){
		this.a = (vector.getP2().x- vector.getP1().x)/(vector.getP2().y - vector.getP1().y);
		this.b = vector.getP1().y - a * vector.getP1().x; 
	}

	public void update(final Vector vector){
		this.a = (vector.getP2().x- vector.getP1().x)/(vector.getP2().y - vector.getP1().y);
		this.b = vector.getP1().y - a * vector.getP1().x; 
	}

	public double distanceToPoint(final Coord p){
		return Math.abs(a * p.x - p.y + b)/Math.sqrt(1 + Math.pow(a, 2));
	}
}