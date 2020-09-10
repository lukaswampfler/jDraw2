/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.awt.geom.Line2D.Double;

import jdraw.framework.AbstractFigure;
//import jdraw.framework.Figure;

/**
 * Represents rectangles in JDraw.
 * 
 * @author Christoph Denzler
 *
 */
public class Line extends AbstractFigure {

	/** Use the java.awt.geom.Line2D.Double in order to save/reuse code. */
	private final Double line;
	
	private int dx, dy;

	/**
	 * Create a new rectangle of the given dimension.
	 * @param x1 the x-coordinate of one corner of the line
	 * @param y1 the y-coordinate of the same corner 
	 * @param x2 coordinates of the other end point.
	 * @param y2 
	 */
	public Line(int x1, int y1, int x2, int y2) {
		this.line = new Double(x1, y1, x2, y2);
		int h = Math.abs(y1 - y2);
		int w = Math.abs(x1 - x2);
		this.rectangle = new Rectangle(x1, y1, w, h);
		this.dx = 0;
		this.dy = 0;
		
	}

	/**
	 * Draw the line to the given graphics context.
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		this.dx = (int)(line.x2-line.x1);
		this.dy = (int)(line.y2-line.y1);
		if(dx*dy > 0) g.drawLine(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height);
		else g.drawLine(rectangle.x, rectangle.y + rectangle.height, rectangle.x + rectangle.width, rectangle.y);
	}
	
	@Override
	public void setBounds(Point origin, Point corner) {
		line.setLine(origin, corner);
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

	@Override
    public List<Figure.Handle> getHandles() {
		if (dx * dy < 0){ // positive slope
			return List.of(Figure.Handle.NE, Figure.Handle.SW);
		} else{
			return List.of(Figure.Handle.NW, Figure.Handle.SE);
		}
	}


}
