import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class StudentManagementGUI extends JFrame {
    private ArrayList<Student> students;

    private JTextArea displayArea;
    private JButton addButton, displayButton, updateButton, deleteButton, searchButton, sortButton;

    public StudentManagementGUI() {
        setTitle("Student Management System");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window

        students = StudentDataStore.loadStudents();

        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel panel = new JPanel();

        addButton = new JButton("Add Student");
        displayButton = new JButton("Display Students");
        updateButton = new JButton("Update Student");
        deleteButton = new JButton("Delete Student");
        searchButton = new JButton("Search Student");
        sortButton = new JButton("Sort Students by ID");

        panel.add(addButton);
        panel.add(displayButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(searchButton);
        panel.add(sortButton);

        add(panel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addStudent());
        displayButton.addActionListener(e -> displayStudents());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        searchButton.addActionListener(e -> searchStudent());
        sortButton.addActionListener(e -> sortStudents());
    }

    private void addStudent() {
        String id = JOptionPane.showInputDialog(this, "Enter Student ID:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID cannot be empty.");
            return;
        }
        if (findStudentById(id) != null) {
            JOptionPane.showMessageDialog(this, "Student ID already exists.");
            return;
        }

        String name = JOptionPane.showInputDialog(this, "Enter Student Name:");
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty.");
            return;
        }

        String ageStr = JOptionPane.showInputDialog(this, "Enter Student Age:");
        if (ageStr == null || ageStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Age cannot be empty.");
            return;
        }

        try {
            int age = Integer.parseInt(ageStr);
            if(age <= 0){
                JOptionPane.showMessageDialog(this, "Age must be positive.");
                return;
            }
            Student s = new Student(id, name, age);
            students.add(s);
            StudentDataStore.saveStudents(students);
            JOptionPane.showMessageDialog(this, "Student added successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid age entered.");
        }
    }

    private void displayStudents() {
        if (students.isEmpty()) {
            displayArea.setText("No students to display.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ID | Name | Age\n");
        sb.append("--------------------------\n");
        for (Student s : students) {
            sb.append(s).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    private void updateStudent() {
        String id = JOptionPane.showInputDialog(this, "Enter Student ID to update:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID cannot be empty.");
            return;
        }

        Student s = findStudentById(id);
        if (s == null) {
            JOptionPane.showMessageDialog(this, "Student not found.");
            return;
        }

        String newName = JOptionPane.showInputDialog(this, "Enter new name:", s.getName());
        if (newName == null || newName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty.");
            return;
        }

        String ageStr = JOptionPane.showInputDialog(this, "Enter new age:", s.getAge());
        if (ageStr == null || ageStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Age cannot be empty.");
            return;
        }

        try {
            int newAge = Integer.parseInt(ageStr);
            if(newAge <= 0){
                JOptionPane.showMessageDialog(this, "Age must be positive.");
                return;
            }
            s.setName(newName);
            s.setAge(newAge);
            StudentDataStore.saveStudents(students);
            JOptionPane.showMessageDialog(this, "Student updated successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid age entered.");
        }
    }

    private void deleteStudent() {
        String id = JOptionPane.showInputDialog(this, "Enter Student ID to delete:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID cannot be empty.");
            return;
        }

        Student s = findStudentById(id);
        if (s == null) {
            JOptionPane.showMessageDialog(this, "Student not found.");
            return;
        }

        students.remove(s);
        StudentDataStore.saveStudents(students);
        JOptionPane.showMessageDialog(this, "Student deleted successfully.");
    }

    private void searchStudent() {
        String id = JOptionPane.showInputDialog(this, "Enter Student ID to search:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID cannot be empty.");
            return;
        }

        Student s = findStudentById(id);
        if (s == null) {
            JOptionPane.showMessageDialog(this, "Student not found.");
            displayArea.setText("");
        } else {
            displayArea.setText("Student Found:\n\n" + s);
        }
    }

    private void sortStudents() {
        students.sort(Comparator.comparing(Student::getId));
        StudentDataStore.saveStudents(students);
        JOptionPane.showMessageDialog(this, "Students sorted by ID.");
        displayStudents();
    }

    private Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentManagementGUI().setVisible(true);
        });
    }
}
