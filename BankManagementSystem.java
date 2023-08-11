import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BankManagementSystem {

    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final double INTEREST_RATE = 0.05;
    private static String[] args;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean isLoggedIn = false;
        String username = null;

        while (!isLoggedIn) {
            System.out.print("Enter username: ");
            String inputUsername = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(inputUsername) && parts[1].equals(password)) {
                        isLoggedIn = true;
                        username = inputUsername;
                        break;
                    }
                }
                if (!isLoggedIn) {
                    System.out.println("Invalid username or password, please try again.");
                }
            } catch (IOException e) {
                System.out.println("Error reading accounts file: " + e.getMessage());
            }
        }

        boolean quit = false;
        while (!quit) {
            System.out.println("Welcome, " + username + "!");
            System.out.println("Please choose an option:");
            System.out.println("1. View balance");
            System.out.println("2. Withdraw money");
            System.out.println("3. Deposit money");
            System.out.println("4. Transfer money to fixed deposit");
            System.out.println("5. Quit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    double balance = getBalance(username);
                    System.out.printf("Current balance: %.2f%n", balance);
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    scanner.nextLine(); // consume the newline character
                    if (withdraw(username, withdrawAmount)) {
                        System.out.println("Withdrawal successful.");
                    } else {
                        System.out.println("Withdrawal failed, insufficient balance.");
                    }
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine(); // consume the newline character
                    deposit(username, depositAmount);
                    System.out.println("Deposit successful.");
                    break;
                case 4:
                    System.out.print("Enter amount to transfer to fixed deposit: ");
                    double transferAmount = scanner.nextDouble();
                    scanner.nextLine(); // consume the newline character
                    if (transferToFixedDeposit(username, transferAmount)) {
                        System.out.println("Transfer successful.");
                    } else {
                        System.out.println("Transfer failed, insufficient balance.");
                    }
                    break;
                case 5:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }

        scanner.close();
    }

    private static double getBalance(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return Double.parseDouble(parts[2]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading accounts file:");
            System.out.println(e.getMessage());
        }
        return 0.0; // default to zero balance if account not found or file error
    }
    private static double getFixedDepositBalance(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return Double.parseDouble(parts[2]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading accounts file:");
            System.out.println(e.getMessage());
        }
        return 0.0; // default to zero balance if account not found or file error
    }

    private static boolean withdraw(String username, double amount) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            StringBuilder lines = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    double balance = Double.parseDouble(parts[2]);
                    if (balance >= amount) {
                        balance -= amount;
                        line = parts[0] + "," + parts[1] + "," + balance + "," + parts[3];
                    } else {
                        return false; // insufficient balance
                    }
                }
                lines.append(line).append(System.lineSeparator());
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
                writer.write(lines.toString());
            }
        } catch (IOException e) {
            System.out.println("Error updating accounts file: " + e.getMessage());
            return false;
        }
        return true;
    }

    private static void deposit(String username, double amount) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            StringBuilder lines = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    double balance = Double.parseDouble(parts[2]);
                    balance += amount;
                    line = parts[0] + "," + parts[1] + "," + balance + "," + parts[3];
                }
                lines.append(line).append(System.lineSeparator());
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
                writer.write(lines.toString());
            }
        } catch (IOException e) {
            System.out.println("Error updating accounts file: " + e.getMessage());
        }
    }

    private static boolean transferToFixedDeposit(String username, double amount) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            StringBuilder lines = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    double balance = Double.parseDouble(parts[2]);
                    if (balance >= amount) {
                        balance -= amount;
                        double fixedDepositBalance = Double.parseDouble(parts[3]);
                        fixedDepositBalance += amount;
                        line = parts[0] + "," + parts[1] + "," + balance + "," + fixedDepositBalance;
                    } else {
                        return false; // insufficient balance
                    }
                }
                lines.append(line).append(System.lineSeparator());
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
                writer.write(lines.toString());
            }
        } catch (IOException e) {
            System.out.println("Error updating accounts file: " + e.getMessage());
            return false;
        }
        return true;
    }

    private static void addInterest() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            StringBuilder lines = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                double balance = Double.parseDouble(parts[2]);
                double interest = balance * INTEREST_RATE;
                balance += interest;
                line = parts[0] + "," + parts[1] + "," + balance + "," + parts[3];
                lines.append(line).append(System.lineSeparator());
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
                writer.write(lines.toString());
            }
        } catch (IOException e) {
            System.out.println("Error updating accounts file: " + e.getMessage());
        }
    }



    public static void main1(String[] args) {
        BankManagementSystem.args = args;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Bank Management System");

        while (true) {
            System.out.println("Please enter your username:");
            String username = scanner.nextLine();
            System.out.println("Please enter your password:");
            String password = scanner.nextLine();

            if (authenticate(username, password)) {
                double balance = getBalance(username);
                double fixedDepositBalance = getFixedDepositBalance(username);

                System.out.printf("Welcome %s! Your current balance is %.2f and fixed deposit balance is %.2f\n",
                        username, balance, fixedDepositBalance);

                while (true) {
                    System.out.println("Please select an option:");
                    System.out.println("1. View Balance");
                    System.out.println("2. Withdraw Money");
                    System.out.println("3. Deposit Money");
                    System.out.println("4. Transfer to Fixed Deposit");
                    System.out.println("5. Quit");
                    int option = scanner.nextInt();
                    scanner.nextLine(); // consume new line

                    switch (option) {
                        case 1:
                            balance = getBalance(username);
                            System.out.printf("Your current balance is %.2f\n", balance);
                            break;
                        case 2:
                            System.out.println("Please enter the amount to withdraw:");
                            double withdrawAmount = scanner.nextDouble();
                            scanner.nextLine(); // consume new line
                            boolean success = withdraw(username, withdrawAmount);
                            if (success) {
                                System.out.printf("Successfully withdrew %.2f from your account\n", withdrawAmount);
                            } else {
                                System.out.println("Insufficient balance in your account");
                            }
                            break;
                        case 3:
                            System.out.println("Please enter the amount to deposit:");
                            double depositAmount = scanner.nextDouble();
                            scanner.nextLine(); // consume new line
                            deposit(username, depositAmount);
                            System.out.printf("Successfully deposited %.2f into your account\n", depositAmount);
                            break;
                        case 4:
                            System.out.println("Please enter the amount to transfer to fixed deposit:");
                            double transferAmount = scanner.nextDouble();
                            scanner.nextLine(); // consume new line
                            success = transferToFixedDeposit(username, transferAmount);
                            if (success) {
                                System.out.printf("Successfully transferred %.2f to your fixed deposit account\n",
                                        transferAmount);
                            } else {
                                System.out.println("Insufficient balance in your account");
                            }
                            break;
                        case 5:
                            System.out.println("Thank you for using Bank Management System");
                            return;
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                    addInterest();
                }
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        }
    }

    private static boolean authenticate(String username, String password) {
        boolean b = false;
        return b;
    }


}