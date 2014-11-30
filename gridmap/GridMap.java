package gridmap;

import java.util.ArrayList;

public class GridMap<T extends GridMapEntity> {
	private ArrayList<T> elementArray;
	private int width;
	private int height;

	public GridMap(int width, int height) {
		if (width < 0 || height < 0) {
			throw new IllegalArgumentException("Width(" + width + ") and height("
					+ height + ") must both be non-negative.");
		}
		this.width = width;
		this.height = height;
		this.elementArray = new ArrayList<T>();
		for (int i = 0; i < width * height; i++) {
			elementArray.add(null);
		}
	}

	public T get(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			throw new IndexOutOfBoundsException();
		}
		return elementArray.get(y * width + x);
	}

	public ArrayList<T> getAll() {
		ArrayList<T> result = new ArrayList<T>();
		for (T elem : elementArray) {
			if (elem != null) {
				result.add(elem);
			}
		}
		return result;
	}

	public boolean occupied(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			throw new IndexOutOfBoundsException();
		}
		return get(x, y) != null;
	}

	public void put(int x, int y, T element) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			throw new IndexOutOfBoundsException();
		}
		T previous = get(x, y);
		if (previous != null) {
			previous.setMap(null, GridMapEntity.INVALID, GridMapEntity.INVALID);
		}
		elementArray.set(y * width + x, element);
		if (element != null) {
			element.setMap(this, x, y);
		}
	}

	public void remove(int x, int y) {
		put(x, y, null);
	}

	public void move(int x, int y, int destX, int destY) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			throw new IndexOutOfBoundsException();
		} else if (destX < 0 || destX >= width || destY < 0 || destY >= height) {
			throw new IndexOutOfBoundsException();
		}
		T target = get(x, y);
		if (target == null) {
			throw new IllegalArgumentException(
					"No element exists at the given coordinates.");
		}
		move(target, destX, destY);
	}

	public void move(T target, int destX, int destY) {
		int targetIndex = elementArray.indexOf(target);
		if (targetIndex == -1) {
			throw new IndexOutOfBoundsException();
		} else if (destX < 0 || destX >= width || destY < 0 || destY >= height) {
			throw new IndexOutOfBoundsException();
		}
		T destinationElement = get(destX, destY);
		if (destinationElement != null) {
			throw new IllegalArgumentException(
					"Another element already occupies the destination.");
		}
		put(target.x, target.y, null);
		put(destX, destY, target);
	}
}
