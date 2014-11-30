package release;

import game.Game;
import game.Player;

import java.awt.Point;
import java.util.ArrayList;

public interface Release {
	public ArrayList<Point> getReleaseTargets(Game g, Player p, Point center,
			Game.GameInterface iface);

	public ArrayList<Point> getPlayableSpots(Game g, Player p);

	public boolean canReleaseAtCenter(Game g, Player p, Point center);

	public boolean canRelease(Game g, Player p);
}
