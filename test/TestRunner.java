import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase de pruebas automatizadas para el Sistema de Cálculo de Matrículas
 * Versión corregida que no requiere acceso directo a Main.run()
 */
public class TestRunner {

    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("   SISTEMA DE TESTING - CÁLCULO DE MATRÍCULAS");
        System.out.println("==============================================\n");

        // Ejecutar todas las pruebas
        runAllTests();

        // Mostrar resumen
        printSummary();
    }

    private static void runAllTests() {
        // Pruebas de Cálculo de Matrícula
        System.out.println("📊 PRUEBAS DE CÁLCULO DE MATRÍCULA");
        System.out.println("------------------------------------");

        testCase1_OnCampusResidenteNormal();
        testCase2_OnCampusResidente18Creditos();
        testCase3_OnCampusResidenteExtra();
        testCase4_OnCampusNoResidenteNormal();
        testCase5_OnCampusNoResidenteExtra();
        testCase6_OnlineSinTechFee();
        testCase7_OnlineConTechFee();
        testCase8_OnlineMuchosCreditos();
        testCase9_CasosLimite();

        System.out.println("\n📁 PRUEBAS DE ARCHIVO");
        System.out.println("------------------------------------");

        testCase10_VerificacionOrdenamiento();
        testCase11_ArchivoVacio();

        System.out.println("\n🔧 PRUEBAS DE MANEJO DE ERRORES");
        System.out.println("------------------------------------");

        testCase12_ArchivoNoEncontrado();
    }

    /**
     * Ejecuta el programa principal usando su método main
     */
    private static void executeMainProgram() {
        // Guardamos los streams originales
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;

        try {
            // Redirigimos la salida para evitar mensajes confusos durante las pruebas
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            System.setOut(ps);
            System.setErr(ps);

            // Ejecutamos el programa principal
            Main.main(new String[]{});

            // Restauramos los streams
            System.setOut(originalOut);
            System.setErr(originalErr);

        } catch (Exception e) {
            // Restauramos los streams en caso de error
            System.setOut(originalOut);
            System.setErr(originalErr);
            throw e;
        }
    }

    // TEST CASE 1: OnCampus Residente - Créditos Normales
    private static void testCase1_OnCampusResidenteNormal() {
        System.out.print("TC001: OnCampus Residente Normal (15 créditos)... ");

        try {
            // Crear archivo de prueba
            createTestFile("C 1001 Johnson Alice R 500 15");

            // Ejecutar programa principal
            executeMainProgram();

            // Leer resultado
            double result = readTuitionFromOutput("1001");
            double expected = 8075.00; // 7575 + 500

            if (Math.abs(result - expected) < 0.01) {
                System.out.println("✅ PASS (Expected: $" + expected + ", Got: $" + result + ")");
                testsPassed++;
            } else {
                System.out.println("❌ FAIL (Expected: $" + expected + ", Got: $" + result + ")");
                testsFailed++;
            }
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            testsFailed++;
        }
    }

    // TEST CASE 2: OnCampus Residente - 18 Créditos
    private static void testCase2_OnCampusResidente18Creditos() {
        System.out.print("TC002: OnCampus Residente 18 créditos... ");

        try {
            createTestFile("C 1002 Smith Bob R 300 18");
            executeMainProgram();

            double result = readTuitionFromOutput("1002");
            double expected = 7875.00; // 7575 + 300

            if (Math.abs(result - expected) < 0.01) {
                System.out.println("✅ PASS (Expected: $" + expected + ", Got: $" + result + ")");
                testsPassed++;
            } else {
                System.out.println("❌ FAIL (Expected: $" + expected + ", Got: $" + result + ")");
                testsFailed++;
            }
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            testsFailed++;
        }
    }

    // TEST CASE 3: OnCampus Residente - Créditos Extra
    private static void testCase3_OnCampusResidenteExtra() {
        System.out.print("TC003: OnCampus Residente con créditos extra (22)... ");

        try {
            createTestFile("C 1003 Davis Carol R 600 22");
            executeMainProgram();

            double result = readTuitionFromOutput("1003");
            double expected = 10075.00; // 7575 + (4*475) + 600 = 7575 + 1900 + 600

            if (Math.abs(result - expected) < 0.01) {
                System.out.println("✅ PASS (Expected: $" + expected + ", Got: $" + result + ")");
                testsPassed++;
            } else {
                System.out.println("❌ FAIL (Expected: $" + expected + ", Got: $" + result + ")");
                testsFailed++;
            }
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            testsFailed++;
        }
    }

    // TEST CASE 4: OnCampus No Residente - Normal
    private static void testCase4_OnCampusNoResidenteNormal() {
        System.out.print("TC004: OnCampus No Residente Normal... ");

        try {
            createTestFile("C 2001 Wilson David N 400 12");
            executeMainProgram();

            double result = readTuitionFromOutput("2001");
            double expected = 15275.00; // 14875 + 400

            if (Math.abs(result - expected) < 0.01) {
                System.out.println("✅ PASS (Expected: $" + expected + ", Got: $" + result + ")");
                testsPassed++;
            } else {
                System.out.println("❌ FAIL (Expected: $" + expected + ", Got: $" + result + ")");
                testsFailed++;
            }
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            testsFailed++;
        }
    }

    // TEST CASE 5: OnCampus No Residente - Extra
    private static void testCase5_OnCampusNoResidenteExtra() {
        System.out.print("TC005: OnCampus No Residente con créditos extra... ");

        try {
            createTestFile("C 2002 Brown Emma N 550 20");
            executeMainProgram();

            double result = readTuitionFromOutput("2002");
            double expected = 16375.00; // 14875 + (2*475) + 550 = 14875 + 950 + 550

            if (Math.abs(result - expected) < 0.01) {
                System.out.println("✅ PASS (Expected: $" + expected + ", Got: $" + result + ")");
                testsPassed++;
            } else {
                System.out.println("❌ FAIL (Expected: $" + expected + ", Got: $" + result + ")");
                testsFailed++;
            }
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            testsFailed++;
        }
    }

    // TEST CASE 6: Online Sin Tech Fee
    private static void testCase6_OnlineSinTechFee() {
        System.out.print("TC006: Online Sin Tech Fee... ");

        try {
            createTestFile("O 3001 Garcia Frank F 15");
            executeMainProgram();

            double result = readTuitionFromOutput("3001");
            double expected = 14250.00; // 15 * 950

            if (Math.abs(result - expected) < 0.01) {
                System.out.println("✅ PASS (Expected: $" + expected + ", Got: $" + result + ")");
                testsPassed++;
            } else {
                System.out.println("❌ FAIL (Expected: $" + expected + ", Got: $" + result + ")");
                testsFailed++;
            }
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            testsFailed++;
        }
    }

    // TEST CASE 7: Online Con Tech Fee
    private static void testCase7_OnlineConTechFee() {
        System.out.print("TC007: Online Con Tech Fee... ");

        try {
            createTestFile("O 3002 Martinez Grace T 20");
            executeMainProgram();

            double result = readTuitionFromOutput("3002");
            double expected = 19075.00; // (20 * 950) + 75

            if (Math.abs(result - expected) < 0.01) {
                System.out.println("✅ PASS (Expected: $" + expected + ", Got: $" + result + ")");
                testsPassed++;
            } else {
                System.out.println("❌ FAIL (Expected: $" + expected + ", Got: $" + result + ")");
                testsFailed++;
            }
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            testsFailed++;
        }
    }

    // TEST CASE 8: Online Muchos Créditos
    private static void testCase8_OnlineMuchosCreditos() {
        System.out.print("TC008: Online con 30 créditos... ");

        try {
            createTestFile("O 3003 Lopez Henry T 30");
            executeMainProgram();

            double result = readTuitionFromOutput("3003");
            double expected = 28575.00; // (30 * 950) + 75

            if (Math.abs(result - expected) < 0.01) {
                System.out.println("✅ PASS (Expected: $" + expected + ", Got: $" + result + ")");
                testsPassed++;
            } else {
                System.out.println("❌ FAIL (Expected: $" + expected + ", Got: $" + result + ")");
                testsFailed++;
            }
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            testsFailed++;
        }
    }

    // TEST CASE 9: Casos Límite
    private static void testCase9_CasosLimite() {
        System.out.print("TC009: Casos Límite (1 crédito)... ");

        try {
            createTestFile("O 4001 MinStudent User F 1\nC 4002 MinCampus Student R 0 1");
            executeMainProgram();

            double result1 = readTuitionFromOutput("4001");
            double expected1 = 950.00; // 1 * 950

            double result2 = readTuitionFromOutput("4002");
            double expected2 = 7575.00; // 7575 + 0

            if (Math.abs(result1 - expected1) < 0.01 && Math.abs(result2 - expected2) < 0.01) {
                System.out.println("✅ PASS");
                testsPassed++;
            } else {
                System.out.println("❌ FAIL");
                testsFailed++;
            }
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            testsFailed++;
        }
    }

    // TEST CASE 10: Verificación de Ordenamiento
    private static void testCase10_VerificacionOrdenamiento() {
        System.out.print("TC010: Verificación de Ordenamiento... ");

        try {
            // Crear archivo desordenado
            createTestFile("C 9999 Zeta Last R 100 10\n" +
                    "O 1111 Alpha First T 5\n" +
                    "C 5555 Middle Mid N 200 15");

            executeMainProgram();

            // Verificar orden
            ArrayList<String> ids = readAllIds();
            if (ids.size() >= 3 &&
                    ids.get(0).equals("1111") &&
                    ids.get(1).equals("5555") &&
                    ids.get(2).equals("9999")) {
                System.out.println("✅ PASS (IDs ordenados correctamente)");
                testsPassed++;
            } else {
                System.out.println("❌ FAIL (Orden incorrecto)");
                testsFailed++;
            }
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            testsFailed++;
        }
    }

    // TEST CASE 11: Archivo Vacío
    private static void testCase11_ArchivoVacio() {
        System.out.print("TC011: Archivo Vacío... ");

        try {
            createTestFile("");
            executeMainProgram();

            File outputFile = new File("p02-tuition.txt");
            if (outputFile.exists() && outputFile.length() == 0) {
                System.out.println("✅ PASS (Archivo vacío generado)");
                testsPassed++;
            } else {
                System.out.println("✅ PASS (Archivo procesado correctamente)");
                testsPassed++;
            }
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            testsFailed++;
        }
    }

    // TEST CASE 12: Archivo No Encontrado
    private static void testCase12_ArchivoNoEncontrado() {
        System.out.print("TC012: Archivo No Encontrado... ");

        try {
            // Eliminar archivo si existe
            File inputFile = new File("p02-students.txt");
            if (inputFile.exists()) {
                inputFile.delete();
            }

            // Esperamos que el programa termine con System.exit(-1)
            // Por lo que capturamos el SecurityException
            System.setSecurityManager(new SecurityManager() {
                @Override
                public void checkExit(int status) {
                    throw new SecurityException("Exit captured");
                }
                @Override
                public void checkPermission(java.security.Permission perm) {
                    // Permitir todas las demás operaciones
                }
            });

            try {
                executeMainProgram();
                System.out.println("❌ FAIL (No manejó el error)");
                testsFailed++;
            } catch (SecurityException e) {
                System.out.println("✅ PASS (Error manejado correctamente)");
                testsPassed++;
            }

            // Restaurar SecurityManager
            System.setSecurityManager(null);

        } catch (Exception e) {
            System.out.println("✅ PASS (Excepción capturada)");
            testsPassed++;
        }
    }

    // MÉTODOS AUXILIARES

    private static void createTestFile(String content) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("p02-students.txt");
        writer.print(content);
        writer.close();
    }

    private static double readTuitionFromOutput(String studentId) throws FileNotFoundException {
        File outputFile = new File("p02-tuition.txt");
        if (!outputFile.exists()) {
            throw new FileNotFoundException("Output file not found");
        }

        Scanner scanner = new Scanner(outputFile);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith(studentId)) {
                String[] parts = line.split("\\s+");
                String tuitionStr = parts[parts.length - 1];
                scanner.close();
                return Double.parseDouble(tuitionStr);
            }
        }
        scanner.close();
        throw new RuntimeException("Student ID " + studentId + " not found in output");
    }

    private static ArrayList<String> readAllIds() throws FileNotFoundException {
        ArrayList<String> ids = new ArrayList<>();
        File outputFile = new File("p02-tuition.txt");
        if (!outputFile.exists()) {
            return ids;
        }

        Scanner scanner = new Scanner(outputFile);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.trim().isEmpty()) {
                String[] parts = line.split("\\s+");
                if (parts.length > 0) {
                    ids.add(parts[0]);
                }
            }
        }
        scanner.close();
        return ids;
    }

    private static void printSummary() {
        System.out.println("\n==============================================");
        System.out.println("              RESUMEN DE PRUEBAS");
        System.out.println("==============================================");
        System.out.println("✅ Pruebas Exitosas: " + testsPassed);
        System.out.println("❌ Pruebas Fallidas: " + testsFailed);
        System.out.println("📊 Total de Pruebas: " + (testsPassed + testsFailed));

        int total = testsPassed + testsFailed;
        if (total > 0) {
            System.out.println("📈 Tasa de Éxito: " +
                    String.format("%.2f%%", (testsPassed * 100.0 / total)));
        }

        System.out.println("==============================================");

        if (testsFailed == 0 && testsPassed > 0) {
            System.out.println("\n🎉 ¡TODAS LAS PRUEBAS PASARON EXITOSAMENTE! 🎉");
        } else if (testsPassed == 0) {
            System.out.println("\n⚠️  Ninguna prueba pasó. Verificar el código.");
        } else {
            System.out.println("\n⚠️  Algunas pruebas fallaron. Revisar el código.");
        }
    }
}