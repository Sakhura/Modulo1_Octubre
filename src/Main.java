import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    private void run() {
        ArrayList<Student> studentList = null;

        try {
            studentList = readFile();
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, could not open 'p02-students.txt' for reading. Stopping.");
            System.exit(-1);
        }

        calcTuition(studentList);

        Sorter.insertionSort(studentList, Sorter.SORT_ASCENDING);

        try {
            writeFile(studentList);
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, could not open 'p02-tuition.txt' for writing. Stopping.");
            System.exit(-1);
        }
    }

    private void calcTuition(ArrayList<Student> pStudentList) {
        for (Student student : pStudentList) {
            student.calcTuition();
        }
    }

    private ArrayList<Student> readFile() throws FileNotFoundException {
        ArrayList<Student> studentList = new ArrayList<>();
        Scanner in = new Scanner(new File("p02-students.txt"));

        // Scanner se usa punto como separador decimal
        in.useLocale(java.util.Locale.US);

        while (in.hasNext()) {
            String studentType = in.next();
            if (studentType.equals("C")) {
                studentList.add(readOnCampusStudent(in));
            } else {
                studentList.add(readOnlineStudent(in));
            }
        }

        in.close();
        return studentList;
    }

    private OnCampusStudent readOnCampusStudent(Scanner pIn) {
        String id = pIn.next();
        String lname = pIn.next();
        String fname = pIn.next();
        OnCampusStudent student = new OnCampusStudent(id, fname, lname);
        String res = pIn.next();

        // Leer fee como String y convertir
        String feeStr = pIn.next();
        double fee = Double.parseDouble(feeStr.replace(",", "."));

        int credits = pIn.nextInt();

        if (res.equals("R")) {
            student.setResidency(OnCampusStudent.RESIDENT);
        } else {
            student.setResidency(OnCampusStudent.NON_RESIDENT);
        }

        student.setProgramFee(fee);
        student.setCredits(credits);
        return student;
    }

    private OnlineStudent readOnlineStudent(Scanner pIn) {
        String id = pIn.next();
        String lname = pIn.next();
        String fname = pIn.next();
        OnlineStudent student = new OnlineStudent(id, fname, lname);
        String fee = pIn.next();
        int credits = pIn.nextInt();

        if (fee.equals("T")) {
            student.setTechFee(true);
        } else {
            student.setTechFee(false);
        }

        student.setCredits(credits);
        return student;
    }

    private void writeFile(ArrayList<Student> pStudentList) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new File("p02-tuition.txt"));

        for (Student student : pStudentList) {
            // Debug: imprimir en consola , no lo toquen
            System.out.printf("Escribiendo: %-16s %-20s %-15s %8.2f%n",
                    student.getId(),
                    student.getLastName(),
                    student.getFirstName(),
                    student.getTuition());

            // Escribir al archivo
            out.printf("%-16s %-20s %-15s %8.2f%n",
                    student.getId(),
                    student.getLastName(),
                    student.getFirstName(),
                    student.getTuition());
        }

        out.close();
        System.out.println("Archivo cerrado correctamente.");
    }
}

//**Crea un archivo `p02-students.txt` en la raíz del proyecto con datos de prueba:
// C 8230123345450 Flintstone Fred R 750 12
// C 3873472785863 Simpson Lisa R 500 18
// O 2873472978693 Szyslak Moe F 24
// C 4834324308675 Flintstone Wilma N 450 6
// O 1384349045225 Szyslak Barney T 30
