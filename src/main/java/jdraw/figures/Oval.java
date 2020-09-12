/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import jdraw.framework.AbstractFigure;


/**
 * Represents ovals in JDraw.
 * 
 * @author Christoph Denzler
 *
 */
public class Oval extends AbstractFigure {

	/** Use the java.awt. in order to save/reuse code. */
	private Ellipse2D.Double oval;	// XXX ich habe nicht Double importiert sondern nur noch Ellipse2D, damit der Code verständlicher wird, denn bei einem Typ Double denk man ja nciht gerade an eine Ellipse.
	
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
		// XXX habe ich nicht erklärt, aber effektiv ist der Parameter g vom Typ Graphics2D
		Graphics2D g2 = (Graphics2D) g;
		//     und auf g2 kann man draw(shape) und fill(shape) aufrufen, und das oval ist so ein shape.
		//     => eine Basisklasse AbstractShapeFigure würde sich also anbieten.
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

	
	
	protected void propagateFigureEvent() {
		FigureEvent fe = new FigureEvent(this);
		for (FigureListener listener : listeners) {
			listener.figureChanged(fe);
		}
	}*/

	@Override
	public AbstractFigure clone() {
		// no try-catch necessary: because Exception-handling already in super-call??
		// XXX => genau! Bei der Methode clone in der Basisklasse wird das "throws CloneNotSupprtedException" ja nicht
		//        mehr deklariert. Das ist gemäss Liskov erlaubt! Und aus diesem Grund musst Du hier jetzt kein try-catch mehr machen.
		Oval f = (Oval) super.clone();
		f.oval = (Ellipse2D.Double) this.oval.clone();
		return f;
	}

}
