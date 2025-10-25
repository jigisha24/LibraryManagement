import java.sql.*;
import java.util.Scanner;

public class Library {
    private Connection conn;
    private Scanner sc;

    public Library() {
        conn = DBConnection.getConnection();
        sc = new Scanner(System.in);
    }

    public void addBook() {
        try {
            System.out.print("Enter book title: ");
            String title = sc.nextLine();
            System.out.print("Enter author: ");
            String author = sc.nextLine();
            String sql = "INSERT INTO books (title, author, issued) VALUES (?, ?, false)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.executeUpdate();
            System.out.println("✅ Book added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void issueBook() {
        try {
            System.out.print("Enter book ID to issue: ");
            int id = sc.nextInt();
            String sql = "UPDATE books SET issued = true WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "📘 Book issued!" : "❌ Book not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook() {
        try {
            System.out.print("Enter book ID to return: ");
            int id = sc.nextInt();
            String sql = "UPDATE books SET issued = false WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "📗 Book returned!" : "❌ Book not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewBooks() {
        try {
            String sql = "SELECT * FROM books";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println("\n--- Library Books ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        " | Title: " + rs.getString("title") +
                        " | Author: " + rs.getString("author") +
                        " | Issued: " + rs.getBoolean("issued"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}