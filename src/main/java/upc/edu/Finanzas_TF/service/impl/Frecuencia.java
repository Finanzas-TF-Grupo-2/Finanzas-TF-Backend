package upc.edu.Finanzas_TF.service.impl;

public enum Frecuencia {

    DIARIA(1),
    SEMANAL(7),
    QUINCENAL(15),
    MENSUAL(30),
    BIMESTRAL(60),
    TRIMESTRAL(90),
    CUATRIMESTRAL(120),
    SEMESTRAL(180),
    ANUAL(360);

    private final int dias;

    Frecuencia(int dias) {
        this.dias = dias;
    }

    public int getDias() {
        return dias;
    }

    public static Frecuencia fromString(String str) {
        switch (str.toUpperCase()) {
            case "DIARIA":
                return DIARIA;
            case "SEMANAL":
                return SEMANAL;
            case "QUINCENAL":
                return QUINCENAL;
            case "MENSUAL":
                return MENSUAL;
            case "BIMESTRAL":
                return BIMESTRAL;
            case "TRIMESTRAL":
                return TRIMESTRAL;
            case "CUATRIMESTRAL":
                return CUATRIMESTRAL;
            case "SEMESTRAL":
                return SEMESTRAL;
            case "ANUAL":
                return ANUAL;
            default:
                throw new IllegalArgumentException("Frecuencia desconocida: " + str);
        }
    }
}
