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