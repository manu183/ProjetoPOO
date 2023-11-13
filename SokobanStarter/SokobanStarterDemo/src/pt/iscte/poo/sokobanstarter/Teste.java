package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

//Classe de teste
public class Teste {
	public static void main(String[] args) {
//		Point2D initialPos =new Point2D(2,4);
//		GameElement empilhadora = new Empilhadora(initialPos);
//		System.out.println(empilhadora.getPosition());
//		System.out.println(empilhadora.calculateFinalPosition(initialPos, Direction.DOWN));
//		GameEngine.getInstance().start();
		HashMap<Point2D, ArrayList<String>> mapa = new HashMap<>();

		Point2D point = new Point2D(4,5);
		List<String> elementos = new ArrayList<>();
		elementos.add("Olá");
        mapa.put(point, (ArrayList<String>) elementos);
//		if (mapa.containsKey(point)) {
//		    ArrayList<String> temp = mapa.get(point);
//		    temp.add("Ola");
//		    mapa.put(point,temp);
//		} else {
//		    System.out.println("Chave não encontrada no mapa.");
//		}
		System.out.println(mapa);
	}
}
