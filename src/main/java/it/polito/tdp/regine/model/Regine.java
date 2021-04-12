package it.polito.tdp.regine.model;

import java.util.ArrayList;
import java.util.List;

public class Regine {
	
	public List<Integer> regine (int N){
		List<Integer> risultato= new ArrayList<>();
		cerca(new ArrayList<Integer>(),N);
		return risultato;
		
	}

	// N è il numero di righe e colonne della scacchiera
	//   (righe e colonne numerate da 0 a N-1)
	// ad ogni livello posizioniamo una regina in una nuova riga
	
	// soluzione parziale: lista delle colonne in cui mettere le regine (prime righe)
	// 		List<Integer>
	// livello = quante righe sono già piene
	// livello = 0 => nessuna riga piena (devo mettere la regina nella riga 0)
	// livello = 3 => 3 righe piene (0, 1, 2), devo mettere la regina nella riga 3
	// [0]
	//     [0, 2]
	//            [0, 2, 1]
	
	private int N;
	private List<Integer> soluzione;
	
	public List<Integer> risolvi(int N){  //procedura che attiva la chiamata alla procedura ricorsiva 
		this.N=N;
		//caso iniziale
		List<Integer> parziale= new ArrayList<Integer>();
		this.soluzione=null;
		cerca(parziale,0);
		return this.soluzione;		
	}
	
	private boolean cerca(List<Integer>parziale, int livello) {
		if(livello==N) {
			this.soluzione=new ArrayList<>(parziale);  //this.soluzione.addAll(parziale);  è necessario perchè alla fine per via del backtracking parziale è sempre vuota
			return true;
			// caso terminale
		} else {
			for(int colonna=0; colonna<N; colonna++) { //questo ciclo mi permette di fare più tentativi, quindi io provo tutte le posizioni possibili ma valide
				// if la mossa nella casella [livello][colonna] è valida
				// se sì, aggiungi a parziale e fai ricorsione
				//questo è uno schema di ricorsione STANDARD: 
				//aggiungo, provo, tolgo
				if(posValida(parziale,colonna)) { //faccio ricorsione se la colonna in cui voglio inserire la regina è compatibile con quelle precedenti
					parziale.add(colonna);  //ho aggiunto la colonna perchè rispetta tutti i vincoli
					boolean trovato=cerca(parziale,livello+1); //vedo le conseguenze di aver messo la regina nella colonna sulle righe successive
					if(trovato)
						return true; //così facendo non va avanti a trovare altre soluzioni
					parziale.remove(parziale.size()-1);  //backtracking, se il tentativo non va a buon fine con quella colonna, perciò riprovo con le altre colonne possibili finchè non trovo la soluzione finale
				}
			}
			return false;
		}
	}

	private boolean posValida(List<Integer> parziale, int colonna) {
		int livello=parziale.size();
		//controlla se la regina viene mangiata in verticale (in orizzontale per costruzione non può essere mangiata)
		if(parziale.contains(colonna))
			return false;
		//controlla se la regina viene mangiata in diagonale
		//le diagonali hanno la proprietà che riga+colonna=numero costante, quindi prendo le regine posizionate e se la somma (o la differenza) delle loro righe e colonne è uguale alla posizione della regina che sto per posizionare non va bene
		for(int r=0; r<livello;r++) {
			int c=parziale.get(r);
			
			if(r+c==livello+colonna || r-c==livello-colonna)
				return false;
		}
		
		return true;
	}
	
	
	
}
