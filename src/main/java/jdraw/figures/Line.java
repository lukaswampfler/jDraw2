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
import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.geom.Line2D.Double;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureListener;

/**
 * Represents rectangles in JDraw.
 * 
 * @author Christoph Denzler
 *
 */
public class Line implements Figure {

	/** Use the java.awt.geom.Line2D.Double in order to save/reuse code. */
	private final Double line;

	private final Rectangle boundingBox;
	
	private final int dx, dy;

	/** list of listeners. */
	private final List<FigureListener> listeners = new CopyOnWriteArrayList<>();

	/**
	 * Create a new rectangle of the given dimension.
	 * @param x1 the x-coordinate of one corner of the line
	 * @param y1 the y-coordinate of the same corner 
	 * @param x2 coordinates of the other end point.
	 * @param y2 
	 */
	public Line(int x1, int y1, int x2, int y2) {
		this.line = new Double(x1, y1, x2, y2);
		this.dx = x2-x1;
		this.dy = y2-y1;
		int h = Math.max(y1, y2)- Math.min(y1,y2);
		int w = Math.max(x1, x2)- Math.min(x1,x2);
		this.boundingBox = new Rectangle(x1, y1, w, h);
	}

	/**
	 * Draw the line to the given graphics context.
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		//g.drawRect(boundingBox.x, boundingBox.y, (int)boundingBox.getWidth(), (int)boundingBox.getHeight());
		g.drawLine((int)(line.x1), (int)(line.y1), (int)(line.x2), (int)(line.y2));
		//System.out.println(line.x1 + " " + line.x2);
	}
	
	@Override
	public void setBounds(Point origin, Point corner) {
		line.setLine(origin, corner);
		boundingBox.setFrameFromDiagonal(origin, corner);
		propagateFigureEvent();
	}

	@Override
	public void move(int dx, int dy) {
		if (dx != 0 || dy != 0) { // notification only if there is a change
			line.x1 = line.getX1() + dx;
			line.x2 = line.getX2() + dx;
			line.y1 = line.getY1() + dy;
			line.y2 = line.getY2() + dy;
			boundingBox.setLocation(boundingBox.x + dx, boundingBox.y + dy);
			propagateFigureEvent();
		}
	}

	@Override
	public boolean contains(int x, int y) {
		return boundingBox.contains(x, y);
	}

	@Override
	public Rectangle getBounds() {
		return boundingBox.getBounds();
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
		if (dx * dy < 0){ // negative slope
			return List.of(Figure.Handle.NE, Figure.Handle.SW);
		} else{
			return List.of(Figure.Handle.NW, Figure.Handle.SE);
		}
	     }


}
