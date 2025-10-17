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