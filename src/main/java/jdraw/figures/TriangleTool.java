/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;

/**
 * This tool defines a mode for drawing rectangles.
 *
 * @see jdraw.framework.Figure
 *
 * @author  Christoph Denzler
 */
public class TriangleTool implements DrawTool {
  
	/** 
	 * the image resource path. 
	 */
	private static final String IMAGES = "/images/";

	/**
	 * The context we use for drawing.
	 */
	private final DrawContext context;

	/**
	 * The context's view. This variable can be used as a shortcut, i.e.
	 * instead of calling context.getView().
	 */
	private final DrawView view;

	/**
	 * Temporary variable. During rectangle creation (during a
	 * mouse down - mouse drag - mouse up cycle) this variable refers
	 * to the new rectangle that is inserted.
	 */
	private Triangle newTri = null;

	/**
	 * Temporary variable.
	 * During rectangle creation this variable refers to the point the
	 * mouse was first pressed.
	 */
	private Point anchor = null;


	/**
	 * How many clicks were made in triangle mode?
	 */
	private int numClicks = 0;


	/**
	 * Create a new triangle tool for the given context.
	 * @param context a context to use this tool in.
	 */
	public TriangleTool(DrawContext context) {
		this.context = context;
		this.view = context.getView();
	}

	/**
	 * Deactivates the current mode by resetting the cursor
	 * and clearing the status bar.
	 * @see jdraw.framework.DrawTool#deactivate()
	 */
	@Override
	public void deactivate() {
		this.context.showStatusText("");
	}

	/**
	 * Activates the Triangle Mode. There will be a
	 * specific menu added to the menu bar that provides settings for
	 * Triangle attributes
	 */
	@Override
	public void activate() {
		this.context.showStatusText("Triangle Mode");
	}

	/**
	 * Initializes a new Triangle object by setting an anchor
	 * point where the mouse was pressed. A new Triangle is then
	 * added to the model.
	 * @param x x-coordinate of mouse
	 * @param y y-coordinate of mouse
	 * @param e event containing additional information about which keys were pressed.
	 * 
	 * @see jdraw.framework.DrawTool#mouseDown(int, int, MouseEvent)
	 */
	@Override
	public void mouseDown(int x, int y, MouseEvent e) {
		if (newTri != null && numClicks == 0)  {
			throw new IllegalStateException();
		} else {
			if (newTri == null){
				newTri = new Triangle(new int[] {x}, new int[] {y}, 1);
				numClicks ++;
				anchor = new Point(x, y);
		}
			else{
				numClicks = (numClicks+1)%3;
				newTri.tri.addPoint(x,y);
			}
			System.out.println(numClicks);
		}
		//
		//newRect = new Rect(x, y, 0, 0);
		if(numClicks == 0){
			view.getModel().addFigure(newTri);
			newTri = null;
		}
	}

	/**
	 * During a mouse drag, the Rectangle will be resized according to the mouse
	 * position. The status bar shows the current size.
	 * 
	 * @param x   x-coordinate of mouse
	 * @param y   y-coordinate of mouse
	 * @param e   event containing additional information about which keys were
	 *            pressed.
	 * 
	 * @see jdraw.framework.DrawTool#mouseDrag(int, int, MouseEvent)
	 */
	@Override
	// ToDO
	public void mouseDrag(int x, int y, MouseEvent e) {
		//anchor = new Point(newTri.xpoints[0], newTri.ypoints[0]);
		if (newTri != null){
			newTri.setBounds(anchor, new Point(x, y));
			java.awt.Rectangle r = newTri.getBounds();
			this.context.showStatusText("w: " + r.width + ", h: " + r.height);
		}
		else{
			return;
		}
	}

	/**
	 * When the user releases the mouse, the Rectangle object is updated
	 * according to the color and fill status settings.
	 * 
	 * @param x   x-coordinate of mouse
	 * @param y   y-coordinate of mouse
	 * @param e   event containing additional information about which keys were
	 *            pressed.
	 * 
	 * @see jdraw.framework.DrawTool#mouseUp(int, int, MouseEvent)
	 */
	@Override
	public void mouseUp(int x, int y, MouseEvent e) {
		//newTri = null;
		anchor = null;
		this.context.showStatusText("Triangle Mode");
	}

	@Override
	public Cursor getCursor() {
		return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
	}
	
	@Override
	public Icon getIcon() {
		return new ImageIcon(getClass().getResource(IMAGES + "Dreieck.png"));
	}

	@Override
	public String getName() {
		return "Triangle";
	}

}
