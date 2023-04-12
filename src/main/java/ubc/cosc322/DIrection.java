package ubc.cosc322;

enum Direction {
	NORTH(-1, 0),
    SOUTH(1, 0),
    EAST(0, 1),
    WEST(0, -1),
    NORTH_EAST(-1, 1),
    NORTH_WEST(-1, -1),
    SOUTH_EAST(1, 1),
    SOUTH_WEST(1, -1);
    
    private int rowChange;
    private int columnChange;
    
    Direction(int rowChange, int columnChange) {
        this.rowChange = rowChange;
        this.columnChange = columnChange;
    }
    
    public int[] move(int[] position) {
        int[] newPosition = new int[2];
        newPosition[0] = position[0] + rowChange;
        newPosition[1] = position[1] + columnChange;
        return newPosition;
    }
}