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