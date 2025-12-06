package Lab.DinosaurGame;

public class Cactus {
    private String name = "cocus1b.png";
    private final int Y = 20;
    private int X = 100;
    private int hitBox = 2;
    private int idx = 0;

    public Cactus(String name, int x, int idx) {
        this.name = name;
        this.X = x;
        this.idx = idx;
        hitBox = calcHitBox();
    }

    private int calcHitBox() {
        int ret = 2;
        switch (name) {
            case "cocus1c.png":
            case "cocus1s.png":
                break;
            case "cocus2s.png":
                ret = 4;
                break;
            case "cocus3.png":
                ret = 6;
                break;
            case "cocus3s.png":
                ret = 5;
                break;
        }

        return ret;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public int getIdx() {
        return idx;
    }

    public int getHitBox() {
        return hitBox;
    }
}
