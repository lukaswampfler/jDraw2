/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import java.awt.geom.Ellipse2D.Double;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import jdraw.framework.AbstractFigure;


/**
 * Represents ovals in JDraw.
 * 
 * @author Christoph Denzler
 *
 */
public class Oval extends AbstractFigure {

	/** Use the java.awt. in order to save/reuse code. */
	private final Double oval;
	
	/** list of listeners. */
	//private final List<FigureListener> listeners = new CopyOnWriteArrayList<>();

	/**
	 * Create a new oval of the given dimension.
	 * @param x the x-coordinate of the upper left corner of the oval
	 * @param y the y-coordinate of the upper left corner of the oval
	 * @param w the oval's width
	 * @param h the oval's height
	 */
	public Oval(int x, int y, int w, int h) {
		oval = new Double(x, y, w, h);
		this.rectangle = new Rectangle(x, y, w, h);
	}

	/**
	 * Draw the rectangle to the given graphics context.
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval((int)(rectangle.x), (int)(rectangle.y), (int)(rectangle.width), (int)(rectangle.height));
		g.setColor(Color.BLUE);
		g.drawOval((int)(rectangle.x), (int)(rectangle.y), (int)(rectangle.width), (int)(rectangle.height));
	}
	
	@Override
	public void setBounds(Point origin, Point corner) {
		rectangle.setFrameFromDiagonal(origin, corner);
		propagateFigureEvent();
	}

	/*@Override
	public void move(int dx, int dy) {
		if (dx != 0 || dy != 0) { // notification only if there is a change
			rectangle.setLocation(rectangle.x + dx, rectangle.y + dy);
			propagateFigureEvent();
		}
	}

	@Override
	public boolean contains(int x, int y) {
		return rectangle.contains(x, y);
	}

	@Override
	public Rectangle getBounds() {
		return rectangle.getBounds();
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
	}*/


}
