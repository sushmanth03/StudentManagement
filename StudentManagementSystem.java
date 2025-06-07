import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class StudentManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> students = StudentDataStore.loadStudents();

        while (true) {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students (Sorted by ID)");
            System.out.println("3. Delete Student by ID");
            System.out.println("4. Update Student by ID");
            System.out.println("5. Search Student by ID");
            System.out.println("6. Exit");

            int choice = readInt(scanner, "Enter your choice: ");

            if (choice == 1) {
                System.out.print("Enter Student ID: ");
                String id = scanner.nextLine().trim();

                System.out.print("Enter Student Name: ");
                String name = scanner.nextLine().trim();

                int age = readInt(scanner, "Enter Student Age: ");

                Student newStudent = new Student(id, name, age);
                if (newStudent.isValid()) {
                    boolean duplicate = false;
                    for (Student s : students) {
                        if (s.getId().equals(id)) {
                            System.out.println("Student ID already exists!");
                            duplicate = true;
                            break;
                        }
                    }
                    if (!duplicate) {
                        students.add(newStudent);
                        StudentDataStore.saveStudents(students);
                        System.out.println("Student added successfully!");
                    }
                } else {
                    System.out.println("Invalid student details. ID, Name must not be empty and Age must be positive.");
                }

            } else if (choice == 2) {
                if (students.isEmpty()) {
                    System.out.println("No students to display.");
                } else {
                    students.stream()
                        .filter(Student::isValid)
                        .sorted(Comparator.comparing(Student::getId))
                        .forEach(System.out::println);
                }

            } else if (choice == 3) {
                System.out.print("Enter Student ID to delete: ");
                String delId = scanner.nextLine().trim();

                boolean found = students.removeIf(s -> s.getId().equals(delId));
                if (found) {
                    StudentDataStore.saveStudents(students);
                    System.out.println("Student with ID " + delId + " deleted successfully.");
                } else {
                    System.out.println("Student with ID " + delId + " not found.");
                }

            } else if (choice == 4) {
                System.out.print("Enter Student ID to update: ");
                String updId = scanner.nextLine().trim();

                Student studentToUpdate = null;
                for (Student s : students) {
                    if (s.getId().equals(updId)) {
                        studentToUpdate = s;
                        break;
                    }
                }
                if (studentToUpdate != null) {
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine().trim();

                    int newAge = readInt(scanner, "Enter new age: ");

                    Student updated = new Student(updId, newName, newAge);
                    if (updated.isValid()) {
                        studentToUpdate.setName(newName);
                        studentToUpdate.setAge(newAge);
                        StudentDataStore.saveStudents(students);
                        System.out.println("Student with ID " + updId + " updated successfully.");
                    } else {
                        System.out.println("Invalid updated details, update aborted.");
                    }
                } else {
                    System.out.println("Student with ID " + updId + " not found.");
                }

            } else if (choice == 5) {
                System.out.print("Enter Student ID to search: ");
                String searchId = scanner.nextLine().trim();

                Student foundStudent = null;
                for (Student s : students) {
                    if (s.getId().equals(searchId)) {
                        foundStudent = s;
                        break;
                    }
                }
                if (foundStudent != null) {
                    System.out.println("Student Found:");
                    System.out.println(foundStudent);
                } else {
                    System.out.println("Student with ID " + searchId + " not found.");
                }

            } else if (choice == 6) {
                System.out.println("Exiting program.");
                break;
            } else {
                System.out.println("Invalid choice, try again.");
            }
        }

        scanner.close();
    }

    public static int readInt(Scanner scanner, String prompt) {
        int number;
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                number = Integer.parseInt(line);
                if (number > 0) break;
                else System.out.println("Number must be positive.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return number;
    }
}
