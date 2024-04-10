package hexmo.views.components;

import hexmo.views.Theme;
import org.helmo.swing.engine.Vector2f;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class HexSelector implements Drawable {
    public static final BasicStroke TILE_STROKE = new BasicStroke(10);
    private final Path2D shape = new Path2D.Double();

    private Vector2f pos;
    private final float size;

    public HexSelector(double size, Vector2f pos) {
        this.size = (float)size;
        this.pos = pos.mult(this.size);

        for(int i=1; i <= 7; ++i) {
            var deg = 60 * i - 30;
            var rad = Math.PI / 180 * deg;
            if(i == 1) {
                shape.moveTo(Math.cos(rad)*size, Math.sin(rad)*size);
            } else {
                shape.lineTo(Math.cos(rad)*size, Math.sin(rad)*size);
            }
        }
    }

    public void draw(Graphics2D renderer) {
        AffineTransform translation = new AffineTransform();
        translation.translate(pos.getX(), pos.getY());
        var transformedShape = shape.createTransformedShape(translation);

        Stroke oldStroke = renderer.getStroke();
        renderer.setStroke(TILE_STROKE);
        renderer.setColor(Theme.PRIMARY_COLOR);
        renderer.draw(transformedShape);
        renderer.setStroke(oldStroke);

        renderer.setColor(Theme.PRIMARY_COLORA.brighter());
        renderer.fill(transformedShape);
    }

    public void moveTo(Vector2f newPos) {
        this.pos = newPos.mult(size);
    }

    public Vector2f getPos() {
        return pos;
    }
}
