package hexmo.views.components;

import hexmo.views.Theme;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class RoseBackground implements Drawable {
    public static final BasicStroke LINE_STROKE = new BasicStroke(2);
    private final java.util.List<Shape> shape = new ArrayList<>();


    public RoseBackground(double size) {
        final float sanitizedSize = Math.abs((float)size);

        double angleDeg = 0;
        Point2D origin = new Point2D.Double(0,0);
        Point2D first = new Point2D.Double(Math.cos(Math.toRadians(angleDeg))*sanitizedSize, Math.sin(Math.toRadians(angleDeg))*sanitizedSize);
        Point2D second = new Point2D.Double(Math.cos(Math.toRadians(angleDeg+60))*sanitizedSize, Math.sin(Math.toRadians(angleDeg+60))*sanitizedSize);

        for(int i=1; i <= 5; ++i) {
            var triangle =  new Path2D.Double();
            triangle.moveTo(origin.getX(), origin.getY());
            triangle.lineTo(first.getX(), first.getY());
            triangle.lineTo(second.getX(), second.getY());
            triangle.lineTo(origin.getX(),origin.getY());
            shape.add(triangle);

            first = second;
            angleDeg += 60;
            second = new Point2D.Double(
            			Math.cos(Math.toRadians(angleDeg+60))*sanitizedSize, 
            			Math.sin(Math.toRadians(angleDeg+60))*sanitizedSize);
        }

        var triangle =  new Path2D.Double();
        triangle.moveTo(origin.getX(), origin.getY());
        triangle.lineTo(first.getX(), first.getY());
        triangle.lineTo(second.getX(), second.getY());
        triangle.lineTo(origin.getX(),origin.getY());
        shape.add(triangle);
    }

    @Override
    public void draw(Graphics2D renderer) {
        var oldStroke = renderer.getStroke();
        var oldColor = renderer.getColor();

        renderer.setStroke(LINE_STROKE);
        renderer.setColor(Theme.BLUE);
        for(var triangle : shape) {
            renderer.fill(triangle);
            if(renderer.getColor() == Theme.RED) {
                renderer.setColor(Theme.BLUE);
            } else {
                renderer.setColor(Theme.RED);
            }
        }

        renderer.setColor(oldColor);
        renderer.setStroke(oldStroke);
    }
}
