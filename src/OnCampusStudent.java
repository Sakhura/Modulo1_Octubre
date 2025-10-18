//**************************************************************************************************
// CLASS: OnCampusStudent (OnCampusStudent.java)
//
// DESCRIPCIÓN
// Clase concreta que representa un estudiante presencial. Hereda de Student e implementa
// el cálculo de matrícula específico para estudiantes en el campus, considerando residencia
// y cuotas de programa.
//
// INFORMACIÓN DEL CURSO Y DEL PROYECTO
// CSE205 Programación Orientada a Objetos y Estructuras de Datos, 2024
// Número de Proyecto: P02
//
// AUTORES:
// Andrés Perot a.perotquevedo@uandresbello.edu
// Rodrigo Yañez r.yaezsepulveda@uandresbello.edu
// Lorenzo Chacano l.chacanomuoz@uandresbello.edu
// Natalia San Miguel n.sanmiguelcornejo@uandresbello.edu
// Sabina Romero s.romerorodriguez1@uandresbello.cl
//**************************************************************************************************


public class OnCampusStudent extends Student {

    // Constantes para residencia
    public static final int RESIDENT = 1;
    public static final int NON_RESIDENT = 2;

    // Atributos
    private int mResidency;
    private double mProgramFee;

    // Constructor
    public OnCampusStudent(String pId, String pFirstName, String pLastName) {
        super(pId, pFirstName, pLastName);
    }

    @Override
    public void calcTuition() {
        double tuition;

        // Calcular tuición base según residencia
        if (mResidency == RESIDENT) {
            tuition = TuitionConstants.ONCAMP_RES_BASE;
        } else {
            tuition = TuitionConstants.ONCAMP_NONRES_BASE;
        }

        // Agregar costo por créditos adicionales si excede 18
        if (getCredits() > TuitionConstants.ONCAMP_MAX_CREDITS) {
            int additionalCredits = getCredits() - TuitionConstants.ONCAMP_MAX_CREDITS;
            tuition += additionalCredits * TuitionConstants.ONCAMP_ADD_CREDITS;
        }

        // Agregar program fee
        tuition += mProgramFee;

        setTuition(tuition);
    }

    // Getters y Setters
    public int getResidency() {
        return mResidency;
    }

    public void setResidency(int pResidency) {
        mResidency = pResidency;
    }

    public double getProgramFee() {
        return mProgramFee;
    }

    public void setProgramFee(double pProgramFee) {
        mProgramFee = pProgramFee;
    }
}