//**************************************************************************************************
// CLASS: OnlineStudent (OnlineStudent.java)
//
// DESCRIPCIÓN
// Clase concreta que representa un estudiante en línea. Hereda de Student e implementa
// el cálculo de matrícula basado en créditos y cuota tecnológica opcional.
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

public class OnlineStudent extends Student {

    // Atributos
    private boolean mTechFee;

    // Constructor
    public OnlineStudent(String pId, String pFirstName, String pLastName) {
        super(pId, pFirstName, pLastName);
    }

    @Override
    public void calcTuition() {
        double tuition = getCredits() * TuitionConstants.ONLINE_CREDIT_RATE;

        if (mTechFee) {
            tuition += TuitionConstants.ONLINE_TECH_FEE;
        }

        setTuition(tuition);
    }

    // Getters y Setters
    public boolean getTechFee() {
        return mTechFee;
    }

    public void setTechFee(boolean pTechFee) {
        mTechFee = pTechFee;
    }
}