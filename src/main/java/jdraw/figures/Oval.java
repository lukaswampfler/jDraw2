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
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureListener;

/**
 * Represents ovals in JDraw.
 * 
 * @author Christoph Denzler
 *
 */
public class Oval implements Figure {

	/** Use the java.awt. in order to save/reuse code. */
	private final Double oval;
	
	/** list of listeners. */
	private final List<FigureListener> listeners = new CopyOnWriteArrayList<>();

	/**
	 * Create a new oval of the given dimension.
	 * @param x the x-coordinate of the upper left corner of the oval
	 * @param y the y-coordinate of the upper left corner of the oval
	 * @param w the oval's width
	 * @param h the oval's height
	 */
	public Oval(int x, int y, int w, int h) {
		oval = new Double(x, y, w, h);
	}

	/**
	 * Draw the rectangle to the given graphics context.
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	//TODO
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval((int)(oval.x), (int)(oval.y), (int)(oval.width), (int)(oval.height));
		g.setColor(Color.BLUE);
		g.drawOval((int)(oval.x), (int)(oval.y), (int)(oval.width), (int)(oval.height));
	}
	
	@Override
	public void setBounds(Point origin, Point corner) {
		oval.setFrameFromDiagonal(origin, corner);
		propagateFigureEvent();
	}

	@Override
	public void move(int dx, int dy) {
		if (dx != 0 || dy != 0) { // notification only if there is a change
			oval.x = oval.x + dx;
			oval.y = oval.y + dy;
			propagateFigureEvent();
		}
	}

	@Override
	public boolean contains(int x, int y) {
		return oval.contains(x, y);
	}

	@Override
	public Rectangle getBounds() {
		return oval.getBounds();
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


}
