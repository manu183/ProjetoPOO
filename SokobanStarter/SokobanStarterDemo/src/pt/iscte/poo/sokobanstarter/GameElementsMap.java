package pt.iscte.poo.sokobanstarter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.iscte.poo.utils.Point2D;

public class GameElementsMap {
	private Map<Point2D, List<GameElement>> map;


    public GameElementsMap() {
    	map = new HashMap<>();
    }

    public void addElement(GameElement gameElement) {
        Point2D position = gameElement.getPosition();
        map.computeIfAbsent(position, k -> new ArrayList<>()).add(gameElement);
    }

    public List<GameElement> getElementsAt(Point2D position) {
        return map.getOrDefault(position, new ArrayList<>());
    }

    public void removeElement(GameElement gameElement) {
        Point2D position = gameElement.getPosition();
        List<GameElement> elementsAtPosition = map.get(position);

        if (elementsAtPosition != null) {
            elementsAtPosition.remove(gameElement);
            if (elementsAtPosition.isEmpty()) {
            	map.remove(position);
            }
        }
    }

}
