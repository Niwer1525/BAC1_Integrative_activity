package hexmo.views.components;

import org.helmo.swing.engine.Vector2f;

import java.awt.geom.AffineTransform;

public final class Camera {
    private Vector2f pos;
    private Vector2f scale;
    private final Vector2f rotation;

    public Camera(Vector2f pos, Vector2f scale, Vector2f rotation) {
        this.pos = pos;
        this.scale = scale;
        this.rotation = rotation;
    }

    public float getPosX() {
        return this.pos.getX();
    }

    public float getPosY() {
        return this.pos.getY();
    }

    public float getScaleX() {
        return this.scale.getX();
    }

    public float getScaleY() {
        return this.scale.getY();
    }

    public void scaleOf(float v) {
        this.scale = this.scale.add(v);
    }

    public double getRotX() {
        return this.rotation.getX();
    }

    public double getRotY() {
        return this.rotation.getY();
    }

    public void applyTo(AffineTransform affineTransform) {
        affineTransform.rotate(this.getRotX(), this.getRotY());
        affineTransform.scale(this.getScaleX(), this.getScaleY());
        affineTransform.translate(this.getPosX(), this.getPosY());
    }

    public Vector2f getPos() {
        return this.pos;
    }

    public void move(Vector2f dist, float speed) {
        this.pos = pos.add(dist.mult(speed));
    }
}
