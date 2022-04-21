package de.oliver.person.visitor;

import java.util.LinkedList;
import java.util.List;

public class Kundenregister {

	private List<Besucher> alleBesucher;

	public Kundenregister() {
		alleBesucher = new LinkedList<>();
	}

	public boolean addBesucher(Besucher besucher) {
		return alleBesucher.add(besucher);
	}
	public Double getStrafe(Besucher besucher){
		try{
			return alleBesucher.get(besucher).getStrafe();
		}catch (NullPointerException ne){
			throw  new IllegalArgumentException(ne.getMessage());
		}
	}

	public boolean bezahlen(Besucher besucher){
		alleBesucher.get(besucher).bezahlen();
		return true;
	}

	public Double erhoeheStrafe(Besucher kunde, Double kosten) {
		var status = alleBesucher.get(kunde);
		return status.erhoeheStrafe(kosten);
	}

	private class BesucherStatus {
		private List<Buch> ausgelieheneBuecher;
		private Double strafe;

		private BesucherStatus() {
		}

		void bezahlen() {
			this.strafe =0.0;
		}

		public List<Buch> getAusgelieheneBuecher() {
			return ausgelieheneBuecher;
		}

		public Double erhoeheStrafe(Double kosten) {
			this.strafe+= kosten;
			return strafe;
		}

		public Double getStrafe() {
			return strafe;
		}
	}
}

}
