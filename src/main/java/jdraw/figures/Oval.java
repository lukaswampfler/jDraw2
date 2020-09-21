/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import java.awt.geom.Ellipse2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import jdraw.figures.AbstractFigure;


/**
 * Represents ovals in JDraw.
 * 
 * @author Christoph Denzler
 *
 */
public class Oval extends AbstractFigure {

	/** Use the java.awt. in order to save/reuse code. */
	private Ellipse2D.Double oval;
	
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
		oval = new Ellipse2D.Double(x, y, w, h);
		this.rectangle = new Rectangle(x, y, w, h);
	}

	/**
	 * Draw the rectangle to the given graphics context.
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLUE);
		g2.draw(oval);
		g2.setColor(Color.GREEN);
		g2.fill(oval);
	}
	
	@Override
	public void setBounds(Point origin, Point corner) {
		super.setBounds(origin, corner);
		oval.setFrameFromDiagonal(origin, corner);
		propagateFigureEvent();
	}

	@Override
	public void move(int dx, int dy) {
		if (dx != 0 || dy != 0) { // notification only if there is a change
			super.move(dx, dy);
			Rectangle r = this.getRectangle();
			oval.setFrameFromDiagonal(r.getX()+dx, r.getY()+dy, r.getX()+dx+r.getWidth(), r.getY()+dy+r.getHeight());
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
/*
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

	
	
	protected void propagateFigureEvent() {
		FigureEvent fe = new FigureEvent(this);
		for (FigureListener listener : listeners) {
			listener.figureChanged(fe);
		}
	}*/

	@Override
	public AbstractFigure clone() {
		// no try-catch necessary: because Exception-handling already in super-call!
		Oval f = (Oval) super.clone();
			f.oval = (Ellipse2D.Double) this.oval.clone();
			return f;
	}

}
