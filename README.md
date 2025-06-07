# Student Management System (Java + GUI)

This is a Java-based Student Management System that supports both **console** and **GUI** interfaces. It allows users to **add**, **view**, **update**, **delete**, **search**, and **sort** student records. All data is saved permanently using local file storage.

## Features

- Add new student (ID, Name, Age)
- Prevent empty or duplicate entries
- Display all students sorted by ID
- Update and delete student by ID
- Search student by ID
- GUI with buttons for user-friendly interaction
- Automatically saves and loads student data
- Stores data in `.csv` (readable) and `.dat` (serialized) files

## Technologies Used

- Java (JDK 8+)
- Swing (GUI)
- File I/O
- Serialization

## How to Run

### Console Version

```bash
javac Student.java StudentDataStore.java StudentManagementSystem.java
java StudentManagementSystem
