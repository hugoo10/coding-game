package fr.kahlouch.codingame.codebuster.test;

public class Vector{
	private final Coord p1;
	private final Coord p2;
	private double size;
	private final Affine affine;

	public Vector(final double x1, final double y1, final double x2, final double y2){
		this.p1 = new Coord(x1, y1);
		this.p2 = new Coord(x2, y2);
		this.size = this.p1.distance(p2);
		this.affine = new Affine(this);
	}

	public Vector(final Coord p1, final Coord p2){
		this(p1.x, p1.y, p2.x, p2.y);
	}

	public Coord getP1(){
		return this.p1;
	}

	public Coord getP2(){
		return this.p2;
	}

	public void setP1(final Coord coord){
		this.setP1(coord.x, coord.y);
	}

	public void setP1(final double x, final double y){
		this.p1.setLocation(x, y); 
		updateSize();
		this.affine.update(this);
	}

	public void setP2(final Coord coord){
		this.setP2(coord.x, coord.y);
	}

	public void setP2(final double x, final double y){
		this.p2.setLocation(x, y); 
		updateSize();
		this.affine.update(this);
	}

	public double getSize(){
		return this.size;
	}

	private void updateSize(){
		this.size = this.p1.distance(p2);
	}

	public Affine getAffine(){
		return this.affine;
	}
}