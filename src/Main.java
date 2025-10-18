//**************************************************************************************************
// CLASS: Main (Main.java)
//
// DESCRIPCIÓN
// Clase principal que controla el flujo del programa de cálculo de matrículas.
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Locale;

public class Main {

    /**.
     * Punto de entrada del programa
     */
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    /**
     * Método principal que coordina el flujo del programa
     */
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

    /**
     * Calcula la matrícula para cada estudiante en la lista
     */
    private void calcTuition(ArrayList<Student> pStudentList) {
        for (Student student : pStudentList) {
            student.calcTuition();
        }
    }

    /**
     * Lee el archivo de estudiantes y crea la lista de objetos Student
     */
    private ArrayList<Student> readFile() throws FileNotFoundException {
        ArrayList<Student> studentList = new ArrayList<>();
        Scanner in = new Scanner(new File("p02-students.txt"));

        // Importante: usar Locale.US para asegurar punto como separador decimal
        in.useLocale(Locale.US);

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

    /**
     * Lee un estudiante OnCampus del scanner
     */
    private OnCampusStudent readOnCampusStudent(Scanner pIn) {
        String id = pIn.next();
        String lname = pIn.next();
        String fname = pIn.next();
        OnCampusStudent student = new OnCampusStudent(id, fname, lname);
        String res = pIn.next();
        double fee = pIn.nextDouble();
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

    /**
     * Lee un estudiante Online del scanner
     */
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

    /**
     * Escribe el archivo de salida con las matrículas calculadas
     */
    private void writeFile(ArrayList<Student> pStudentList) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new File("p02-tuition.txt"));

        // Importante: usar Locale.US para asegurar punto como separador decimal
        for (Student student : pStudentList) {
            out.printf(Locale.US, "%-16s %-20s %-15s %8.2f%n",
                    student.getId(),
                    student.getLastName(),
                    student.getFirstName(),
                    student.getTuition());
        }

        out.close();
    }
}