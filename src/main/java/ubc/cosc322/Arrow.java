package ubc.cosc322;

public class Arrow {
    public int x;
    public int y;
    public int player;

    public Arrow(int x, int y, int player) {
        this.x = x;
        this.y = y;
        this.player = player;
    }

    public Arrow(int[] pos, int player) {
        this.x = pos[0];
        this.y = pos[1];
        this.player = player;
    }

    public int[] getPos() {
        int[] pos = new int[2];
        pos[0] = this.x;
        pos[1] = this.y;
        return pos;
    }

    public void setPos(int[] pos) {
        this.x = pos[0];
        this.y = pos[1];
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public String toString() {
        return "Arrow: " + this.x + ", " + this.y + ", " + this.player;
    }
}
