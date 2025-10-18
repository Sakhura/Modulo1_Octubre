import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Locale;

/**
 * Interfaz Gráfica para el Sistema de Cálculo de Matrículas
 *
 * Proporciona una interfaz visual completa para:
 * - Cargar archivos de estudiantes
 * - Visualizar datos de estudiantes
 * - Calcular matrículas
 * - Guardar resultados
 * - Agregar/editar estudiantes manualmente
 */
public class TuitionSystemGUI extends JFrame {

    // Componentes de la GUI
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JButton loadFileButton;
    private JButton calculateButton;
    private JButton saveButton;
    private JButton addStudentButton;
    private JButton clearButton;
    private JButton sortButton;
    private JLabel statusLabel;
    private JLabel totalStudentsLabel;
    private JLabel totalTuitionLabel;
    private JTextArea logArea;
    private JProgressBar progressBar;

    // Datos del programa
    private ArrayList<Student> studentList;
    private File currentFile;

    // Colores del tema
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private final Color WARNING_COLOR = new Color(243, 156, 18);
    private final Color DANGER_COLOR = new Color(231, 76, 60);
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241);

    /**
     * Constructor principal
     */
    public TuitionSystemGUI() {
        studentList = new ArrayList<>();
        initializeGUI();
    }

    /**
     * Inicializa todos los componentes de la interfaz
     */
    private void initializeGUI() {
        setTitle("Sistema de Cálculo de Matrículas Universitarias");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Configurar Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Panel superior con botones principales
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        // Panel central con tabla
        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        // Panel inferior con información y logs
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        // Panel lateral con estadísticas
        JPanel sidePanel = createSidePanel();
        add(sidePanel, BorderLayout.EAST);

        // Configurar ventana
        setSize(1200, 700);
        setLocationRelativeTo(null);

        // Agregar ícono a la ventana
        ImageIcon icon = new ImageIcon("university_icon.png");
        if (icon.getImage() != null) {
            setIconImage(icon.getImage());
        }

        updateStatistics();
    }

    /**
     * Crea el panel superior con botones de acción
     */
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(PRIMARY_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Botón Cargar Archivo
        loadFileButton = createStyledButton("📁 Cargar Archivo", SUCCESS_COLOR);
        loadFileButton.addActionListener(e -> loadFile());
        panel.add(loadFileButton);

        // Botón Calcular Matrículas
        calculateButton = createStyledButton("🧮 Calcular Matrículas", PRIMARY_COLOR);
        calculateButton.addActionListener(e -> calculateTuition());
        calculateButton.setEnabled(false);
        panel.add(calculateButton);

        // Botón Ordenar
        sortButton = createStyledButton("🔤 Ordenar por ID", new Color(52, 73, 94));
        sortButton.addActionListener(e -> sortStudents());
        sortButton.setEnabled(false);
        panel.add(sortButton);

        // Botón Guardar
        saveButton = createStyledButton("💾 Guardar Resultados", SUCCESS_COLOR);
        saveButton.addActionListener(e -> saveResults());
        saveButton.setEnabled(false);
        panel.add(saveButton);

        panel.add(Box.createHorizontalStrut(20));

        // Botón Agregar Estudiante
        addStudentButton = createStyledButton("➕ Agregar Estudiante", new Color(155, 89, 182));
        addStudentButton.addActionListener(e -> showAddStudentDialog());
        panel.add(addStudentButton);

        // Botón Limpiar
        clearButton = createStyledButton("🗑️ Limpiar Todo", DANGER_COLOR);
        clearButton.addActionListener(e -> clearAll());
        panel.add(clearButton);

        return panel;
    }

    /**
     * Crea un botón con estilo personalizado
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    /**
     * Crea el panel central con la tabla de estudiantes
     */
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos de Estudiantes"));

        // Definir columnas de la tabla
        String[] columnNames = {
                "ID", "Apellido", "Nombre", "Tipo",
                "Créditos", "Residencia/Tech Fee", "Program Fee", "Matrícula"
        };

        // Crear modelo de tabla no editable excepto para ciertas columnas
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo permitir editar créditos y fees
                return column == 4 || column == 6;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4 || columnIndex == 6 || columnIndex == 7) {
                    return Double.class;
                }
                return String.class;
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setFillsViewportHeight(true);
        studentTable.setRowHeight(25);
        studentTable.setFont(new Font("Arial", Font.PLAIN, 12));
        studentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        studentTable.getTableHeader().setBackground(new Color(52, 73, 94));
        studentTable.getTableHeader().setForeground(Color.WHITE);

        // Configurar anchos de columna
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        studentTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        studentTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        studentTable.getColumnModel().getColumn(4).setPreferredWidth(60);
        studentTable.getColumnModel().getColumn(5).setPreferredWidth(120);
        studentTable.getColumnModel().getColumn(6).setPreferredWidth(80);
        studentTable.getColumnModel().getColumn(7).setPreferredWidth(100);

        // Agregar listener para cambios en la tabla
        tableModel.addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                updateStudentFromTable(e.getFirstRow());
            }
        });

        JScrollPane scrollPane = new JScrollPane(studentTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel inferior con logs y barra de progreso
     */
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Panel de estado
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Listo para cargar archivo");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusPanel.add(statusLabel, BorderLayout.WEST);

        progressBar = new JProgressBar();
        progressBar.setVisible(false);
        statusPanel.add(progressBar, BorderLayout.EAST);

        panel.add(statusPanel, BorderLayout.NORTH);

        // Área de logs
        logArea = new JTextArea(5, 0);
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("Registro de Actividad"));
        panel.add(logScroll, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel lateral con estadísticas
     */
    private JPanel createSidePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(250, 0));
        panel.setBorder(BorderFactory.createTitledBorder("Estadísticas"));
        panel.setBackground(BACKGROUND_COLOR);

        // Panel de total de estudiantes
        JPanel studentsPanel = createStatPanel("Total Estudiantes", "0");
        totalStudentsLabel = (JLabel) studentsPanel.getComponent(1);
        panel.add(studentsPanel);

        // Panel de matrícula total
        JPanel tuitionPanel = createStatPanel("Matrícula Total", "$0.00");
        totalTuitionLabel = (JLabel) tuitionPanel.getComponent(1);
        panel.add(tuitionPanel);

        panel.add(Box.createVerticalStrut(20));

        // Botón de generar reporte
        JButton reportButton = createStyledButton("📊 Generar Reporte", new Color(46, 204, 113));
        reportButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        reportButton.addActionListener(e -> generateReport());
        panel.add(reportButton);

        panel.add(Box.createVerticalGlue());

        return panel;
    }

    /**
     * Crea un panel de estadística individual
     */
    private JPanel createStatPanel(String title, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titleLabel.setForeground(Color.GRAY);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 18));
        valueLabel.setForeground(PRIMARY_COLOR);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Carga un archivo de estudiantes
     */
    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();

            try {
                studentList = readFile(currentFile);
                updateTable();

                statusLabel.setText("Archivo cargado: " + currentFile.getName());
                statusLabel.setForeground(SUCCESS_COLOR);
                logArea.append("✓ Archivo cargado exitosamente: " + currentFile.getName() + "\n");
                logArea.append("  - Total de estudiantes: " + studentList.size() + "\n");

                calculateButton.setEnabled(true);
                sortButton.setEnabled(true);

                updateStatistics();

            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this,
                        "Error al cargar el archivo: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                logArea.append("✗ Error al cargar archivo: " + e.getMessage() + "\n");
            }
        }
    }

    /**
     * Lee el archivo de estudiantes
     */
    private ArrayList<Student> readFile(File file) throws FileNotFoundException {
        ArrayList<Student> list = new ArrayList<>();
        Scanner in = new Scanner(file);
        in.useLocale(Locale.US);

        while (in.hasNext()) {
            String studentType = in.next();
            if (studentType.equals("C")) {
                list.add(readOnCampusStudent(in));
            } else {
                list.add(readOnlineStudent(in));
            }
        }

        in.close();
        return list;
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
     * Actualiza la tabla con los datos de estudiantes
     */
    private void updateTable() {
        tableModel.setRowCount(0);

        for (Student student : studentList) {
            Object[] row = new Object[8];
            row[0] = student.getId();
            row[1] = student.getLastName();
            row[2] = student.getFirstName();

            if (student instanceof OnCampusStudent) {
                OnCampusStudent ocs = (OnCampusStudent) student;
                row[3] = "OnCampus";
                row[4] = student.getCredits();
                row[5] = ocs.getResidency() == OnCampusStudent.RESIDENT ? "Residente" : "No Residente";
                row[6] = ocs.getProgramFee();
            } else {
                OnlineStudent ols = (OnlineStudent) student;
                row[3] = "Online";
                row[4] = student.getCredits();
                row[5] = ols.getTechFee() ? "Con Tech Fee" : "Sin Tech Fee";
                row[6] = 0.0;
            }

            row[7] = String.format("%.2f", student.getTuition());

            tableModel.addRow(row);
        }
    }

    /**
     * Calcula las matrículas de todos los estudiantes
     */
    private void calculateTuition() {
        progressBar.setVisible(true);
        progressBar.setMaximum(studentList.size());
        progressBar.setValue(0);

        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                int progress = 0;
                for (Student student : studentList) {
                    student.calcTuition();
                    progress++;
                    publish(progress);
                    Thread.sleep(50); // Simular procesamiento
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                int progress = chunks.get(chunks.size() - 1);
                progressBar.setValue(progress);
            }

            @Override
            protected void done() {
                progressBar.setVisible(false);
                updateTable();
                updateStatistics();
                saveButton.setEnabled(true);

                statusLabel.setText("Matrículas calculadas exitosamente");
                statusLabel.setForeground(SUCCESS_COLOR);
                logArea.append("✓ Matrículas calculadas para " + studentList.size() + " estudiantes\n");
            }
        };

        worker.execute();
    }

    /**
     * Ordena los estudiantes por ID
     */
    private void sortStudents() {
        Sorter.insertionSort(studentList, Sorter.SORT_ASCENDING);
        updateTable();

        statusLabel.setText("Estudiantes ordenados por ID");
        logArea.append("✓ Lista ordenada por ID de estudiante\n");
    }

    /**
     * Guarda los resultados en un archivo
     */
    private void saveResults() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setSelectedFile(new File("p02-tuition.txt"));

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File outputFile = fileChooser.getSelectedFile();

            try {
                PrintWriter out = new PrintWriter(outputFile);

                for (Student student : studentList) {
                    out.printf(Locale.US, "%-16s %-20s %-15s %8.2f%n",
                            student.getId(),
                            student.getLastName(),
                            student.getFirstName(),
                            student.getTuition());
                }

                out.close();

                statusLabel.setText("Resultados guardados en: " + outputFile.getName());
                statusLabel.setForeground(SUCCESS_COLOR);
                logArea.append("✓ Resultados guardados en: " + outputFile.getAbsolutePath() + "\n");

                JOptionPane.showMessageDialog(this,
                        "Resultados guardados exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this,
                        "Error al guardar el archivo: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                logArea.append("✗ Error al guardar archivo: " + e.getMessage() + "\n");
            }
        }
    }

    /**
     * Muestra el diálogo para agregar un nuevo estudiante
     */
    private void showAddStudentDialog() {
        AddStudentDialog dialog = new AddStudentDialog(this);
        dialog.setVisible(true);

        Student newStudent = dialog.getStudent();
        if (newStudent != null) {
            studentList.add(newStudent);
            updateTable();
            updateStatistics();

            calculateButton.setEnabled(true);
            sortButton.setEnabled(true);

            logArea.append("✓ Nuevo estudiante agregado: " +
                    newStudent.getFirstName() + " " + newStudent.getLastName() + "\n");
        }
    }

    /**
     * Limpia todos los datos
     */
    private void clearAll() {
        int result = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea limpiar todos los datos?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            studentList.clear();
            tableModel.setRowCount(0);
            updateStatistics();

            calculateButton.setEnabled(false);
            sortButton.setEnabled(false);
            saveButton.setEnabled(false);

            statusLabel.setText("Todos los datos han sido eliminados");
            logArea.append("✓ Todos los datos han sido limpiados\n");
        }
    }

    /**
     * Actualiza las estadísticas mostradas
     */
    private void updateStatistics() {
        totalStudentsLabel.setText(String.valueOf(studentList.size()));

        double total = 0;
        for (Student student : studentList) {
            total += student.getTuition();
        }
        totalTuitionLabel.setText(String.format("$%,.2f", total));
    }

    /**
     * Actualiza un estudiante desde los datos de la tabla
     */
    private void updateStudentFromTable(int row) {
        if (row >= 0 && row < studentList.size()) {
            Student student = studentList.get(row);

            // Actualizar créditos
            int credits = ((Number) tableModel.getValueAt(row, 4)).intValue();
            student.setCredits(credits);

            // Actualizar program fee si es OnCampus
            if (student instanceof OnCampusStudent) {
                double fee = ((Number) tableModel.getValueAt(row, 6)).doubleValue();
                ((OnCampusStudent) student).setProgramFee(fee);
            }

            // Recalcular matrícula
            student.calcTuition();
            tableModel.setValueAt(String.format("%.2f", student.getTuition()), row, 7);

            updateStatistics();
            logArea.append("✓ Estudiante actualizado: " + student.getId() + "\n");
        }
    }

    /**
     * Genera un reporte detallado
     */
    private void generateReport() {
        JDialog reportDialog = new JDialog(this, "Reporte Detallado", true);
        reportDialog.setLayout(new BorderLayout());

        JTextArea reportArea = new JTextArea(20, 50);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        reportArea.setEditable(false);

        StringBuilder report = new StringBuilder();
        report.append("=====================================\n");
        report.append("     REPORTE DE MATRÍCULAS\n");
        report.append("=====================================\n\n");

        // Estadísticas por tipo
        int onCampusCount = 0, onlineCount = 0;
        double onCampusTotal = 0, onlineTotal = 0;
        int residentCount = 0, nonResidentCount = 0;
        int techFeeCount = 0, noTechFeeCount = 0;

        for (Student student : studentList) {
            if (student instanceof OnCampusStudent) {
                onCampusCount++;
                onCampusTotal += student.getTuition();
                OnCampusStudent ocs = (OnCampusStudent) student;
                if (ocs.getResidency() == OnCampusStudent.RESIDENT) {
                    residentCount++;
                } else {
                    nonResidentCount++;
                }
            } else {
                onlineCount++;
                onlineTotal += student.getTuition();
                OnlineStudent ols = (OnlineStudent) student;
                if (ols.getTechFee()) {
                    techFeeCount++;
                } else {
                    noTechFeeCount++;
                }
            }
        }

        report.append("RESUMEN GENERAL\n");
        report.append("---------------\n");
        report.append(String.format("Total de estudiantes: %d\n", studentList.size()));
        report.append(String.format("Matrícula total: $%,.2f\n", onCampusTotal + onlineTotal));
        report.append(String.format("Matrícula promedio: $%,.2f\n\n",
                studentList.isEmpty() ? 0 : (onCampusTotal + onlineTotal) / studentList.size()));

        report.append("ESTUDIANTES ONCAMPUS\n");
        report.append("--------------------\n");
        report.append(String.format("Total: %d estudiantes\n", onCampusCount));
        report.append(String.format("  - Residentes: %d\n", residentCount));
        report.append(String.format("  - No residentes: %d\n", nonResidentCount));
        report.append(String.format("Matrícula total: $%,.2f\n", onCampusTotal));
        report.append(String.format("Matrícula promedio: $%,.2f\n\n",
                onCampusCount == 0 ? 0 : onCampusTotal / onCampusCount));

        report.append("ESTUDIANTES ONLINE\n");
        report.append("------------------\n");
        report.append(String.format("Total: %d estudiantes\n", onlineCount));
        report.append(String.format("  - Con tech fee: %d\n", techFeeCount));
        report.append(String.format("  - Sin tech fee: %d\n", noTechFeeCount));
        report.append(String.format("Matrícula total: $%,.2f\n", onlineTotal));
        report.append(String.format("Matrícula promedio: $%,.2f\n",
                onlineCount == 0 ? 0 : onlineTotal / onlineCount));

        reportArea.setText(report.toString());

        JScrollPane scrollPane = new JScrollPane(reportArea);
        reportDialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton saveReportButton = new JButton("Guardar Reporte");
        saveReportButton.addActionListener(e -> saveReport(report.toString()));
        buttonPanel.add(saveReportButton);

        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> reportDialog.dispose());
        buttonPanel.add(closeButton);

        reportDialog.add(buttonPanel, BorderLayout.SOUTH);

        reportDialog.pack();
        reportDialog.setLocationRelativeTo(this);
        reportDialog.setVisible(true);
    }

    /**
     * Guarda el reporte en un archivo
     */
    private void saveReport(String reportContent) {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setSelectedFile(new File("reporte_matriculas.txt"));

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                PrintWriter writer = new PrintWriter(fileChooser.getSelectedFile());
                writer.print(reportContent);
                writer.close();

                JOptionPane.showMessageDialog(this,
                        "Reporte guardado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this,
                        "Error al guardar el reporte: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Método principal
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TuitionSystemGUI gui = new TuitionSystemGUI();
            gui.setVisible(true);
        });
    }
}