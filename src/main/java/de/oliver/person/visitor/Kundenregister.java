package de.oliver.person.visitor;

import de.oliver.core.Buch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kundenregister {

	private final Map<Integer,BesucherStatus> alleBesucher;

	public Kundenregister() {
		alleBesucher = new HashMap<>();
	}

	public boolean addBesucher(Besucher besucher) {
		var alterEintrag=alleBesucher.put(besucher.getID(),new BesucherStatus());
		if (alterEintrag!= null){
			alleBesucher.put(besucher.getID(),alterEintrag);
			return false;
		}
		return true;
	}
	public Double getStrafe(Besucher besucher){
		try{
			return alleBesucher.get(besucher.getID()).getStrafe();
		}catch (NullPointerException ne){
			throw  new IllegalArgumentException(ne.getMessage());
		}
	}

	public boolean bezahlen(Besucher besucher){
		alleBesucher.get(besucher.getID()).bezahlen();
		return true;
	}

	public Double erhoeheStrafe(Besucher kunde, Double kosten) {
		var status = alleBesucher.get(kunde.getID());
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
