/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Polygon;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureListener;

/**
 * Represents triangles in JDraw.
 * 
 * @author Christoph Denzler
 *
 */
public class Triangle extends Polygon implements Figure {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Use the java.awt.Polygon in order to save/reuse code. */
	public final Polygon tri;
	private final Rectangle box;
	
	/** list of listeners. */
	private final List<FigureListener> listeners = new CopyOnWriteArrayList<>();

	/**
	 * Create a new rectangle of the given dimension.
	 * @param x the x-coordinate of the upper left corner of the rectangle
	 * @param y the y-coordinate of the upper left corner of the rectangle
	 * @param w the rectangle's width
	 * @param h the rectangle's height
	 */
	public Triangle(int[] x, int[] y, int n) {
		this.tri = new Polygon(x, y, n);
		this.box = tri.getBounds();
	}

	/**
	 * Draw the rectangle to the given graphics context.
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.drawPolygon(tri);
	}
	
	@Override
	// Was macht diese Methode genau?
	public void setBounds(Point origin, Point corner) {
		/*for (int i = 0 ; i < tri.npoints; i++){
		
			tri.xpoints[i] += corner.x - origin.x;
			tri.ypoints[i] += corner.y - origin.y;
		}*/
		box.setFrameFromDiagonal(origin, corner);
		propagateFigureEvent();
	}

	@Override
	public void move(int dx, int dy) {
		if (dx != 0 || dy != 0) { // notification only if there is a change
			tri.translate(dx,  dy);
			box.translate(dx, dy);
			propagateFigureEvent();
		}
	}

	@Override
	public boolean contains(int x, int y) {
		return tri.contains(x, y);
	}

	@Override
	public Rectangle getBounds() {
		return tri.getBounds();
	}

	@Override
	public void addFigureListener(FigureListener listener) {
		if (listener != null && !listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeFigureListener(FigureListener listener) {
		listeners.remove(listener);
	}

	@Override
	public Figure clone() {
		return null;
	}
	
	protected void propagateFigureEvent() {
		FigureEvent fe = new FigureEvent(this);
		for (FigureListener listener : listeners) {
			listener.figureChanged(fe);
		}
	}

	@Override
    public List<Figure.Handle> getHandles() {
		return List.of(Figure.Handle.N, Figure.Handle.SW, Figure.Handle.SE);
	}


}
