package gridmap;

public class GridMapEntity {
	public static final int INVALID = -1;
	
	protected GridMap<?> map;
	protected int x, y;
	
	/* Package private */
	void setMap(GridMap<?> map, int x, int y) {
		this.map = map;
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
