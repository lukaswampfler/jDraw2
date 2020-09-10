package jdraw.framework;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;


public abstract class AbstractFigure implements Figure {
    
    public final List<FigureListener> listeners = new CopyOnWriteArrayList<>();

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

    abstract public void draw(Graphics g);
    abstract public void move(int dx, int dy);
    abstract public boolean contains(int x, int y);
    abstract public Rectangle getBounds();
    abstract public void setBounds(Point origin, Point corner);

    
    
}
