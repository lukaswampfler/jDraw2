package jdraw.framework;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;


public abstract class AbstractFigure implements Figure {
// XXX ich würde diese Klasse in das Paket jdraw.figures verschieben, denn es ist ja ein Implementierungsdetail,
//     also die Basisklasse Deiner Implementierungen und gehört nicht in das Framework.
    
    public List<FigureListener> listeners = new CopyOnWriteArrayList<>();
    // XXX public? => das würde ich private deklarieren.

    public Rectangle rectangle = null;
    // XXX dieses Feld würde ich auch private deklarieren.
    //     Das Feld sollte mit dem Konstruktor gesetzt werden und sollte eigentlich nicht von 
    //     anderen Klassen unkontrolliert geändert werden. Die Frage ist aber ob Du das überhaupt
    //     noch brauchst. Viele Deiner Figuren verwenden ein Shape Objekt, und diese Shape-Objekte
    //     bieten eine Methode getBounds an die verwendet werden kann um ein Rechteck zu bekommen.
    //     Rectangle ist selber auch ein Shape-Objekt.
    //     Du könntest also neben AbstractFigure (das auch als Basisklasse für Group verwendet wird)
    //     eine Basisklasse AbstractShapeFigure definieren, welches ein shape enthält.

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
    	// XXX der Typ shape enthält auch ein contains, d.h. diese Methode könnte auch in eine AbstractShapeFigure verschoben werden.
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
	public AbstractFigure clone() {
		try {
			AbstractFigure f = (AbstractFigure) super.clone();
			f.rectangle = (Rectangle) this.rectangle.clone();
			f.listeners = new CopyOnWriteArrayList<FigureListener>(f.listeners);
			// sind Listeners wie Strings unveränderlich und müssen deshalb nicht noch extra
			// geklont werden?
			// XXX => nein, über diese Listener wissen wir eigentlich nichts. Aber: Diese Listener werden
			//    extern in der Figur registriert, d.h. diese Listener sind Externe Informationen und
			//    daher würde ich diese GAR NICHT kopieren, d.h. ich würde schreiben
			//    f.listeners = new CopyOnWriteArrayList<>();
			return f;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}

	}

    protected void propagateFigureEvent() {
		FigureEvent fe = new FigureEvent(this);
		for (FigureListener listener : listeners) {
			listener.figureChanged(fe);
		}
	}

    // XXX es ist nicht nötig diese abstrakten Methoden zu wiederholen in der abstrakten Klasse.
    abstract public void draw(Graphics g);
    //abstract public void move(int dx, int dy);
    //abstract public boolean contains(int x, int y);
    //abstract public Rectangle getBounds();
    abstract public void setBounds(Point origin, Point corner);
    //abstract protected void propagateFigureEvent();
    
    
}
