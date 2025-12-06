package Lab.Soldier;

public class Arrows {
    private int x;
    private int y;
    private int angle;

    public Arrows(int x, int y, int angle) {
        this.x = x;
        this.y = y;
        this.angle = (int) Math.toRadians(angle);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
}
