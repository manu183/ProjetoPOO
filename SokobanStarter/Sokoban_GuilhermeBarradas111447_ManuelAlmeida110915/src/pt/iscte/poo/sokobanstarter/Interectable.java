package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public interface Interectable {
	public Point2D interact(GameElement gameElement);// A interecao ira retornar a nova posicao do gameElement
}