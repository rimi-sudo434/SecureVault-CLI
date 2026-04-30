import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("=== SECURE PASSWORD VAULT SYSTEM ===");

        System.out.print("Enter Master Password: ");
        String pass = sc.nextLine();

        if (!pass.equals("admin123")) {
            System.out.println("Access Denied!");
            return;
        }

        System.out.println("Login Successful!");

        menu();
    }

    public static void menu() {

        while (true) {

            System.out.println("\n--- MENU ---");
            System.out.println("1. Add Password");
            System.out.println("2. View Passwords");
            System.out.println("3. Search Password");
            System.out.println("4. Delete Password");
            System.out.println("5. Exit");

            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                addPassword();
            } 
            else if (choice == 2) {
                viewPasswords();
            } 
            else if (choice == 3) {
                searchPassword();
            } 
            else if (choice == 4) {
                deletePassword();
            } 
            else if (choice == 5) {
                System.out.println("Exiting...");
                break;
            } 
            else {
                System.out.println("Invalid choice!");
            }
        }
    }

    // ADD PASSWORD
    public static void addPassword() {

        System.out.print("Enter Site: ");
        String site = sc.nextLine();

        System.out.print("Enter Username: ");
        String user = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        String data = site + " | " + user + " | " + password;

        try {
            FileWriter fw = new FileWriter("Storage.txt", true);
            fw.write(data + "\n");
            fw.close();

            System.out.println("Password Saved Successfully!");
        } catch (Exception e) {
            System.out.println("Error saving file!");
        }
    }

    // VIEW PASSWORDS
    public static void viewPasswords() {

        System.out.println("\n--- ALL SAVED PASSWORDS ---");

        try {
            File file = new File("Storage.txt");

            if (!file.exists()) {
                System.out.println("No data found!");
                return;
            }

            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                System.out.println(reader.nextLine());
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error reading file!");
        }
    }

    // SEARCH PASSWORD
    public static void searchPassword() {

        System.out.print("Enter Site to Search: ");
        String searchSite = sc.nextLine();

        try {
            File file = new File("Storage.txt");

            if (!file.exists()) {
                System.out.println("No data found!");
                return;
            }

            Scanner reader = new Scanner(file);
            boolean found = false;

            while (reader.hasNextLine()) {
                String line = reader.nextLine();

                if (line.contains(searchSite)) {
                    System.out.println("FOUND: " + line);
                    found = true;
                }
            }

            reader.close();

            if (!found) {
                System.out.println("No matching site found!");
            }

        } catch (Exception e) {
            System.out.println("Error searching file!");
        }
    }

    // DELETE PASSWORD
    public static void deletePassword() {

        System.out.print("Enter Site to Delete: ");
        String deleteSite = sc.nextLine();

        try {
            File inputFile = new File("Storage.txt");
            File tempFile = new File("temp.txt");

            Scanner reader = new Scanner(inputFile);
            FileWriter writer = new FileWriter(tempFile);

            boolean found = false;

            while (reader.hasNextLine()) {
                String line = reader.nextLine();

                if (!line.contains(deleteSite)) {
                    writer.write(line + "\n");
                } else {
                    found = true;
                }
            }

            reader.close();
            writer.close();

            inputFile.delete();
            tempFile.renameTo(inputFile);

            if (found) {
                System.out.println("Password Deleted Successfully!");
            } else {
                System.out.println("Site not found!");
            }

        } catch (Exception e) {
            System.out.println("Error deleting file!");
        }
    }
}