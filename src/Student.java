//**************************************************************************************************
// CLASS: Student (Student.java)
//
// DESCRIPCIÓN
// Clase abstracta base que representa un estudiante genérico. Define la estructura común
// para todos los tipos de estudiantes e implementa la interfaz Comparable para permitir
// ordenamiento por ID.
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


public abstract class Student implements Comparable<Student> {

    // Atributos privados
    private String mId;
    private String mFirstName;
    private String mLastName;
    private int mCredits;
    private double mTuition;

    /**
     * Constructor
     */
    public Student(String pId, String pFirstName, String pLastName) {
        mId = pId;
        mFirstName = pFirstName;
        mLastName = pLastName;
    }

    /**
     * Método abstracto para calcular tuición
     */
    public abstract void calcTuition();

    /**
     * Implementación del método compareTo de Comparable
     */
    @Override
    public int compareTo(Student pStudent) {
        return getId().compareTo(pStudent.getId());
    }

    // Getters
    public int getCredits() {
        return mCredits;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getId() {
        return mId;
    }

    public String getLastName() {
        return mLastName;
    }

    public double getTuition() {
        return mTuition;
    }

    // Setters
    public void setCredits(int pCredits) {
        mCredits = pCredits;
    }

    public void setFirstName(String pFirstName) {
        mFirstName = pFirstName;
    }

    public void setId(String pId) {
        mId = pId;
    }

    public void setLastName(String pLastName) {
        mLastName = pLastName;
    }

    public void setTuition(double pTuition) {
        mTuition = pTuition;
    }
}