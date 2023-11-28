package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

//Classe de teste
public class Teste {
	public static void main(String[] args) {
		Score registScore = Score.getInstance();
//		registScore.addNewScore();
		 // Chamadas consecutivas com valores diferentes
			registScore.addNewScore("Joao", 5);
		       registScore.addNewScore("Maria", 10);
     
        registScore.addNewScore("Carlos", 15);
        registScore.addNewScore("Ana", 20);
       
        registScore.addNewScore("Pedro", 25);
        registScore.addNewScore("Sofia", 30);
      
        registScore.addNewScore("Miguel", 35);
        registScore.addNewScore("Ines", 40);
        
        registScore.addNewScore("Ricardo", 45);
        registScore.addNewScore("Andreia", 50);
        
        registScore.addNewScore("Hugo", 55);
        registScore.addNewScore("Luisa", 60);
        
        registScore.addNewScore("Rui", 65);
        registScore.addNewScore("Catarina", 70);
        
        registScore.addNewScore("Tiago", 75);
        registScore.addNewScore("Beatriz", 80);
        
        registScore.addNewScore("Daniel", 85);
        registScore.addNewScore("Patricia", 90);
        
        registScore.addNewScore("Nuno", 95);
        registScore.addNewScore("Vera", 100);
        
        registScore.addNewScore("Joana", 105);
        registScore.addNewScore("Eduardo", 110);
        
        registScore.addNewScore("Marta", 115);
        registScore.addNewScore("Rodrigo", 120);
        
        registScore.addNewScore("Sara", 125);
        registScore.addNewScore("Jorge", 130);
        
        registScore.addNewScore("Teresa", 135);
        registScore.addNewScore("Goncalo", 140);
        
        registScore.addNewScore("Filipa", 145);
        registScore.addNewScore("Bruno", 150);
		
		
	}
}
