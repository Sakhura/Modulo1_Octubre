//**************************************************************************************************
// CLASS: Student
//
// DESCRIPTION
// Student is an abstract class and is the superclass for the OnCampusStudent and OnlineStudent
// classes. Remember that in a class hierarchy, the superclass declares data and methods that are
// common to all types of students.
//
// AUTHOR
// Kevin R. Burger (burgerk@asu.edu)
// Computer Science & Engineering
// School of Computing, Informatics, and Decision Systems Engineering
// Fulton Schools of Engineering
// Arizona State University, Tempe, AZ 85287-8809
//**************************************************************************************************
public abstract class Student implements Comparable<Student> {

        // Atributos
        private String mId;
        private String mFirstName;
        private String mLastName;
        private int mCredits;
        private double mTuition;

        // Constructor
        public Student(String pId, String pFirstName, String pLastName) {
            mId = pId;
            mFirstName = pFirstName;
            mLastName = pLastName;
        }

        // Método abstracto
        public abstract void calcTuition();

        // El método compareTo ya está implementado

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