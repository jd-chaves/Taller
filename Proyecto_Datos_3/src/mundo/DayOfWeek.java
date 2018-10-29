package mundo;

public enum DayOfWeek {
	LUNES,
	MARTES,
	MIERCOLES,
	JUEVES,
	VIERNES,
	SABADO,
	DOMINGO;
	
	public static DayOfWeek of(int i){
		switch(i){
		case 1: return LUNES;
		case 2: return MARTES;
		case 3: return MIERCOLES;
		case 4: return JUEVES;
		case 5: return VIERNES;
		case 6: return SABADO;
		case 7: return DOMINGO;
		default: throw new IndexOutOfBoundsException();
		}
	}
	
	public static DayOfWeek next(DayOfWeek day){
		switch (day) {
		case DOMINGO: return LUNES;
		case LUNES: return MARTES;
		case MARTES: return MIERCOLES;
		case MIERCOLES: return JUEVES;
		case JUEVES: return VIERNES;
		case VIERNES: return SABADO;
		case SABADO: return DOMINGO;
		default: throw new IndexOutOfBoundsException();
		}
	}
	
	public static DayOfWeek name(String s){
		switch(s){
		case "Lunes": return LUNES;
		case "Martes": return MARTES;
		case "Miercoles": return MIERCOLES;
		case "Jueves": return JUEVES;
		case "Viernes": return VIERNES;
		case "Sabado": return SABADO;
		case "Domingo": return DOMINGO;
		default: throw new IndexOutOfBoundsException();
		}
	}

}
