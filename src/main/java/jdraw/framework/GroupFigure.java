package jdraw.framework;


import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;

import java.util.stream.Stream;

// XXX diese Klasse würde ich auch in das Paket jdraw.figures verschieben.
public class GroupFigure extends AbstractFigure implements FigureGroup{ 
    public List<Figure> figureList = new ArrayList<>();

    public GroupFigure(List<Figure> fl){
        this.figureList = fl;
        this.rectangle = getBounds();// XXX eigentlich wird dieses Feld in der Basisklasse nicht mehr verwendet.
    }


    @Override
    public Stream<Figure> getFigureParts(){
        Stream.Builder<Figure> builder = Stream.builder();
        for (Figure f: figureList){
            builder.add(f);
        }
        return builder.build();
        // XXX sorry, Streams haben wir noch nicht diskutiert.
        // return figureList.stream()
        // wäre auch eine Möglichkeit gewesen.
    }


	@Override
	public Rectangle getBounds() {
		if (figureList.size() > 0) {
			Rectangle rect = new Rectangle(figureList.get(0).getBounds());
			for (Figure f : figureList) {
				rect.add(f.getBounds());
			}
			return rect;
		} else
			return null;
		// XXX ich glaube wenn null zurück kommt dann wird es bei den Aufrufern zu einer NPE kommen.
		//     Ich würde im Konstruktor sicherstellen, dass die dort übergebene Figurenliste eine Grösse > 0 hat.
	}


    public void draw(Graphics g){
        for (Figure f: figureList){
            f.draw(g);
        }
    }

    @Override
	public void move(int dx, int dy) {
        if (dx != 0 || dy != 0) { // notification only if there is a change
            for (Figure f: figureList){
                f.move(dx, dy);
            }
            rectangle.setLocation(rectangle.x + dx, rectangle.y + dy); // XXX ok, aber dieses Rectangle wird nicht mehr benötigt.
			propagateFigureEvent(); // XXX dieser Aufruf ist jedoch weiterhin nötig! Das ist gut so.
		}
	}


	@Override
	public AbstractFigure clone() {
		// XXX den Resultattyp könntest Du zu GrouupFigure anpassen (oder auch bei Figure lassen).
		GroupFigure gf = (GroupFigure) super.clone();
		List<Figure> newList = new ArrayList<Figure>();
		for (Figure f : figureList) {
			newList.add(f.clone());
		}
		gf.figureList = newList;
		// XXX genau!
		return gf;
	};

    // this method is not yet implemented
    public void setBounds(Point origin, Point corner){
        return;
    }


    
}
