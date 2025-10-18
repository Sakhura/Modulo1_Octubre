import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Diálogo para agregar nuevos estudiantes al sistema
 * Permite seleccionar el tipo de estudiante y configurar sus propiedades
 */
public class AddStudentDialog extends JDialog {

    private Student student = null;

    // Componentes comunes
    private JTextField idField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JSpinner creditsSpinner;
    private JComboBox<String> typeCombo;

    // Componentes para OnCampus
    private JRadioButton residentRadio;
    private JRadioButton nonResidentRadio;
    private JSpinner programFeeSpinner;

    // Componentes para Online
    private JCheckBox techFeeCheckBox;

    // Paneles para cada tipo
    private JPanel onCampusPanel;
    private JPanel onlinePanel;

    /**
     * Constructor
     */
    public AddStudentDialog(Frame parent) {
        super(parent, "Agregar Nuevo Estudiante", true);
        initializeDialog();
    }

    /**
     * Inicializa todos los componentes del diálogo
     */
    private void initializeDialog() {
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Título
        JLabel titleLabel = new JLabel("Ingrese los datos del estudiante");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 15, 5);
        mainPanel.add(titleLabel, gbc);

        // Tipo de estudiante
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(new JLabel("Tipo de Estudiante:"), gbc);

        gbc.gridx = 1;
        typeCombo = new JComboBox<>(new String[]{"OnCampus", "Online"});
        typeCombo.addActionListener(e -> togglePanels());
        mainPanel.add(typeCombo, gbc);

        // ID del estudiante
        gbc.gridy = 2;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("ID del Estudiante:"), gbc);

        gbc.gridx = 1;
        idField = new JTextField(15);
        mainPanel.add(idField, gbc);

        // Nombre
        gbc.gridy = 3;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        firstNameField = new JTextField(15);
        mainPanel.add(firstNameField, gbc);

        // Apellido
        gbc.gridy = 4;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Apellido:"), gbc);

        gbc.gridx = 1;
        lastNameField = new JTextField(15);
        mainPanel.add(lastNameField, gbc);

        // Créditos
        gbc.gridy = 5;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Créditos:"), gbc);

        gbc.gridx = 1;
        creditsSpinner = new JSpinner(new SpinnerNumberModel(12, 1, 30, 1));
        mainPanel.add(creditsSpinner, gbc);

        // Panel específico del tipo
        JPanel typeSpecificPanel = new JPanel(new CardLayout());

        // Panel para OnCampus
        onCampusPanel = createOnCampusPanel();
        typeSpecificPanel.add(onCampusPanel, "OnCampus");

        // Panel para Online
        onlinePanel = createOnlinePanel();
        typeSpecificPanel.add(onlinePanel, "Online");

        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(typeSpecificPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("Agregar");
        okButton.addActionListener(e -> addStudent());
        buttonPanel.add(okButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> {
            student = null;
            dispose();
        });
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Configurar ventana
        pack();
        setLocationRelativeTo(getParent());
        setResizable(false);

        // Agregar validación de ID único
        idField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validateId();
            }
        });
    }

    /**
     * Crea el panel específico para estudiantes OnCampus
     */
    private JPanel createOnCampusPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Opciones OnCampus"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Residencia
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Residencia:"), gbc);

        gbc.gridx = 1;
        residentRadio = new JRadioButton("Residente");
        residentRadio.setSelected(true);
        panel.add(residentRadio, gbc);

        gbc.gridx = 2;
        nonResidentRadio = new JRadioButton("No Residente");
        panel.add(nonResidentRadio, gbc);

        ButtonGroup residencyGroup = new ButtonGroup();
        residencyGroup.add(residentRadio);
        residencyGroup.add(nonResidentRadio);

        // Program Fee
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Program Fee:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        programFeeSpinner = new JSpinner(new SpinnerNumberModel(500.0, 0.0, 2000.0, 50.0));
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(programFeeSpinner, "$#,##0.00");
        programFeeSpinner.setEditor(editor);
        panel.add(programFeeSpinner, gbc);

        return panel;
    }

    /**
     * Crea el panel específico para estudiantes Online
     */
    private JPanel createOnlinePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Opciones Online"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Tech Fee
        gbc.gridx = 0;
        gbc.gridy = 0;
        techFeeCheckBox = new JCheckBox("Incluir Tech Fee ($75)");
        techFeeCheckBox.setSelected(false);
        panel.add(techFeeCheckBox, gbc);

        // Información adicional
        gbc.gridy = 1;
        JLabel infoLabel = new JLabel("<html><i>Tarifa: $950 por crédito</i></html>");
        infoLabel.setForeground(Color.GRAY);
        panel.add(infoLabel, gbc);

        return panel;
    }

    /**
     * Alterna entre los paneles según el tipo de estudiante seleccionado
     */
    private void togglePanels() {
        Container parent = onCampusPanel.getParent();
        CardLayout layout = (CardLayout) parent.getLayout();
        String selected = (String) typeCombo.getSelectedItem();
        layout.show(parent, selected);
    }

    /**
     * Valida que el ID sea único (simplificado para este ejemplo)
     */
    private void validateId() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            idField.setBackground(new Color(255, 200, 200));
        } else {
            idField.setBackground(Color.WHITE);
        }
    }

    /**
     * Valida los campos del formulario
     */
    private boolean validateFields() {
        // Validar ID
        if (idField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El ID del estudiante es requerido",
                    "Error de Validación",
                    JOptionPane.ERROR_MESSAGE);
            idField.requestFocus();
            return false;
        }

        // Validar que el ID sea numérico
        try {
            Long.parseLong(idField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El ID debe ser un número",
                    "Error de Validación",
                    JOptionPane.ERROR_MESSAGE);
            idField.requestFocus();
            return false;
        }

        // Validar nombre
        if (firstNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El nombre es requerido",
                    "Error de Validación",
                    JOptionPane.ERROR_MESSAGE);
            firstNameField.requestFocus();
            return false;
        }

        // Validar apellido
        if (lastNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El apellido es requerido",
                    "Error de Validación",
                    JOptionPane.ERROR_MESSAGE);
            lastNameField.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Crea y agrega el estudiante
     */
    private void addStudent() {
        if (!validateFields()) {
            return;
        }

        String id = idField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        int credits = (Integer) creditsSpinner.getValue();

        if (typeCombo.getSelectedItem().equals("OnCampus")) {
            OnCampusStudent ocStudent = new OnCampusStudent(id, firstName, lastName);
            ocStudent.setCredits(credits);
            ocStudent.setResidency(residentRadio.isSelected() ?
                    OnCampusStudent.RESIDENT : OnCampusStudent.NON_RESIDENT);
            ocStudent.setProgramFee((Double) programFeeSpinner.getValue());
            student = ocStudent;
        } else {
            OnlineStudent olStudent = new OnlineStudent(id, firstName, lastName);
            olStudent.setCredits(credits);
            olStudent.setTechFee(techFeeCheckBox.isSelected());
            student = olStudent;
        }

        dispose();
    }

    /**
     * Retorna el estudiante creado
     */
    public Student getStudent() {
        return student;
    }
}