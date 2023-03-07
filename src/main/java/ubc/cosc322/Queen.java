package ubc.cosc322;

public class Queen {
    public int x;
    public int y;
    public int player;
    private int[] oldPos = new int[2];
    private int[] nextPos = new int[2];
    private boolean dead;

    public Queen(int x, int y, int player) {
        this.x = x;
        this.y = y;
        this.player = player;
    }

    public Queen(int[] pos, int player) {
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

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public String toString() {
        return "Queen: " + this.x + ", " + this.y + ", " + this.player;
    }

    public int[] getOldPos() {
        return oldPos;
    }

    public void setOldPos(int[] oldPos) {
        this.oldPos = oldPos;
    }

    public int[] getNextPos() {
        return nextPos;
    }

    public void setNextPos(int[] nextPos) {
        this.nextPos = nextPos;
    }

    public boolean canMove(int x, int y) {
        if (x < 1 || x > 10 || y < 1 || y > 10) { // if the position is out of bounds, we can't move there
            return false;
        }
        if (x == this.x && y == this.y) { // if the position is the same as the current position, we can't move there
            return false;
        }
        // check if there is a non empty position between the current position and the specified position
        // if there is, we can't move there
        // if there isn't, we can move there
        int[] direction = new int[] {x - this.x, y - this.y}; // the direction we are moving in
        int[] pos = new int[] {this.x, this.y}; // the position we are currently checking
        while (pos[0] != x || pos[1] != y) { // while we haven't reached the specified position
            pos[0] += direction[0] / Math.max(Math.abs(direction[0]), 1); // move the position by the direction
            pos[1] += direction[1] / Math.max(Math.abs(direction[1]), 1); // move the position by the direction
            if (pos[0] == this.x && pos[1] == this.y) { // if the position is the same as the current position, we can't move there
                return false;
            }
            if (pos[0] == x && pos[1] == y) { // if the position is the same as the specified position, we can't move there
                return false;
            }
            if (pos[0] < 1 || pos[0] > 10 || pos[1] < 1 || pos[1] > 10) { // if the position is out of bounds, we can't move there
                return false;
            }
        }
        return true;
    }

    public boolean canShootArrow(int x, int y, int distance, int[][] board) {
        if (x < 1 || x > 10 || y < 1 || y > 10) { // if the position is out of bounds, we can't shoot there
            return false;
        }
        if (x == this.x && y == this.y) { // if the position is the same as the current position, we can't shoot there
            return false;
        }
        // we can shoot in any direction, shoot a ray from the specified position by the specified distance and see if it hits any non-empty positions
        // if it hits a non-empty position, we can't shoot there
        // if it doesn't hit any non-empty positions, we can shoot there
        int[][] directions = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}}; // all 8 directions
        for (int[] direction : directions) {
            int[] pos = new int[] {x, y}; // the position we are currently checking
            for (int i = 0; i < distance; i++) { // shoot a ray by the specified distance
                pos[0] += direction[0]; // move the position by the direction
                pos[1] += direction[1]; // move the position by the direction
                if (pos[0] < 1 || pos[0] > 10 || pos[1] < 1 || pos[1] > 10) { // if the position is out of bounds, we can't shoot there
                    break;
                }
                if (pos[0] == this.x && pos[1] == this.y) { // if the position is the same as the current position, we can't shoot there
                    break;
                }
                if (pos[0] == x && pos[1] == y) { // if the position is the same as the specified position, we can't shoot there
                    break;
                }
                if (board[pos[0]][pos[1]] != 0) { // if the position is not empty, we can't shoot there
                    return false;
                }
            }
        }
        return true;
    }

    public int distanceTo(int x, int y) {
        return Math.abs(this.x - x) + Math.abs(this.y - y);
    }

    public boolean isDead() {
        return dead;
    }

    private void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean canDie(int[][] board) {
        // TODO: if queen has no more moves or arrows, she dies
        // check if queen has any moves
        // if not, set dead to true
        boolean[] moves = new boolean[8]; // all 8 directions
        int[][] directions = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}}; // all 8 directions
        for (int[] dir: directions) {
            if (this.canMove(this.x + dir[0], this.y + dir[1])) {
                moves[0] = true;
            } else {
                moves[0] = false;
            }
        }
        boolean hasMoves = false;
        for (boolean move : moves) {
            if (move) {
                hasMoves = true;
                break;
            }
        }
        setDead(!hasMoves);
        return this.isDead();
    }
}
