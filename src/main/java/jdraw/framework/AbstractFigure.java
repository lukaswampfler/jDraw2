package jdraw.framework;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;


public abstract class AbstractFigure implements Figure {
    
    public final List<FigureListener> listeners = new CopyOnWriteArrayList<>();

    public Rectangle rectangle = null;

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
    public boolean contains(int x, int y) {
		return rectangle.contains(x, y);
	}

	@Override
    public Rectangle getBounds(){
        return rectangle.getBounds();
	}

    @Override
	public void move(int dx, int dy) {
		if (dx != 0 || dy != 0) { // notification only if there is a change
            rectangle.setLocation(rectangle.x + dx, rectangle.y + dy);
			propagateFigureEvent();
		}
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

    abstract public void draw(Graphics g);
    //abstract public void move(int dx, int dy);
    //abstract public boolean contains(int x, int y);
    //abstract public Rectangle getBounds();
    abstract public void setBounds(Point origin, Point corner);
    //abstract protected void propagateFigureEvent();
    
    
}
