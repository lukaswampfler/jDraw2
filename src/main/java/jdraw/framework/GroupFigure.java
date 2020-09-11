package jdraw.framework;


import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;

import java.util.stream.Stream;


public class GroupFigure extends AbstractFigure implements FigureGroup{ 
    public List<Figure> figureList = new ArrayList<>();

    public GroupFigure(List<Figure> fl){
        this.figureList = fl;
        this.rectangle = getBounds();
    }


    @Override
    public Stream<Figure> getFigureParts(){
        Stream.Builder<Figure> builder = Stream.builder();
        for (Figure f: figureList){
            builder.add(f);
        }
        return builder.build();
    }


    @Override
    public Rectangle getBounds(){
        if (figureList.size() > 0){
            Rectangle rect = new Rectangle(figureList.get(0).getBounds());
            for (Figure f: figureList){
                rect.add(f.getBounds());
            }
        return rect;
        }
        else return null;
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
            rectangle.setLocation(rectangle.x + dx, rectangle.y + dy);
			propagateFigureEvent();
		}
	}


    @Override
	public AbstractFigure clone() {
        GroupFigure gf = (GroupFigure)super.clone();
        List<Figure> newList = new ArrayList<Figure>();
        for (Figure f: figureList){
            newList.add(f.clone());
        }
        gf.figureList = newList;
        return gf;
        };


    // this method is not yet implemented
    public void setBounds(Point origin, Point corner){
        return;
    }


    
}
