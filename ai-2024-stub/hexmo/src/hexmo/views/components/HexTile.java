package hexmo.views.components;

import hexmo.supervisors.commons.TileType;
import hexmo.views.Theme;
import org.helmo.swing.engine.Vector2f;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class HexTile implements Drawable {
    public static final BasicStroke TILE_STROKE = new BasicStroke(10);
    private final Path2D shape = new Path2D.Double();
    private final TileType type;

    private final Vector2f pos;

    public HexTile(TileType type, double size, Vector2f pos) {
        this.type = type;
        final float sanitizedSize = Math.abs((float)size);
        this.pos = pos.mult(sanitizedSize);

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
        AffineTransform at = new AffineTransform();
        at.translate(pos.getX(), pos.getY());
        var toRender = shape.createTransformedShape(at);

        Stroke oldStroke = renderer.getStroke();
        renderer.setStroke(TILE_STROKE);
        renderer.setColor(Theme.TILES.get(type).darker());
        renderer.draw(toRender);
        renderer.setStroke(oldStroke);

        renderer.setColor(Theme.TILES.get(type).brighter());
        renderer.fill(toRender);
    }

}
