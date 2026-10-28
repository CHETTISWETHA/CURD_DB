package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

    static final String URL = "jdbc:mysql://localhost:3306/curd-bd";
    static final String USER = "root";
    static final String PASSWORD = "root";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try (Connection cn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.println("Database connected successfully!");

            while (true) {
                System.out.println("\n--- STUDENT CRUD OPERATIONS ---");
                System.out.println("1. Insert Student");
                System.out.println("2. Display Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1:
                        System.out.print("Enter name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter gender: ");
                        String gender = sc.nextLine();

                        System.out.print("Enter age: ");
                        int age = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Aadhar: ");
                        String aadhar = sc.nextLine();

                        System.out.print("Enter address: ");
                        String address = sc.nextLine();

                        String insertSql = "INSERT INTO student (name, gender, age, aadhar, address) VALUES (?, ?, ?, ?, ?)";

                        try (PreparedStatement ps = cn.prepareStatement(insertSql)) {
                            ps.setString(1, name);
                            ps.setString(2, gender);
                            ps.setInt(3, age);
                            ps.setString(4, aadhar);
                            ps.setString(5, address);

                            ps.executeUpdate();
                            System.out.println("Student inserted successfully!");
                        }
                        break;

                    case 2:
                        String selectSql = "SELECT * FROM student";

                        try (PreparedStatement ps = cn.prepareStatement(selectSql);
                             ResultSet rs = ps.executeQuery()) {

                            while (rs.next()) {
                                System.out.println(
                                        "ID: " + rs.getInt("id") +
                                                ", Name: " + rs.getString("name") +
                                                ", Gender: " + rs.getString("gender") +
                                                ", Age: " + rs.getInt("age") +
                                                ", Aadhar: " + rs.getString("aadhar") +
                                                ", Address: " + rs.getString("address")
                                );
                            }
                        }
                        break;

                    case 3:
                        System.out.print("Enter student ID to update: ");
                        int updateId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter new name: ");
                        String newName = sc.nextLine();

                        System.out.print("Enter new gender: ");
                        String newGender = sc.nextLine();

                        System.out.print("Enter new age: ");
                        int newAge = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter new Aadhar: ");
                        String newAadhar = sc.nextLine();

                        System.out.print("Enter new address: ");
                        String newAddress = sc.nextLine();

                        String updateSql = "UPDATE student SET name=?, gender=?, age=?, aadhar=?, address=? WHERE id=?";

                        try (PreparedStatement ps = cn.prepareStatement(updateSql)) {
                            ps.setString(1, newName);
                            ps.setString(2, newGender);
                            ps.setInt(3, newAge);
                            ps.setString(4, newAadhar);
                            ps.setString(5, newAddress);
                            ps.setInt(6, updateId);

                            int rows = ps.executeUpdate();

                            if (rows > 0) {
                                System.out.println("Student updated successfully!");
                            } else {
                                System.out.println("Student ID not found.");
                            }
                        }
                        break;

                    case 4:
                        System.out.print("Enter student ID to delete: ");
                        int deleteId = sc.nextInt();
                        sc.nextLine();

                        String deleteSql = "DELETE FROM student WHERE id=?";

                        try (PreparedStatement ps = cn.prepareStatement(deleteSql)) {
                            ps.setInt(1, deleteId);

                            int rows = ps.executeUpdate();

                            if (rows > 0) {
                                System.out.println("Student deleted successfully!");
                            } else {
                                System.out.println("Student ID not found.");
                            }
                        }
                        break;

                    case 5:
                        System.out.println("Program exited.");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}