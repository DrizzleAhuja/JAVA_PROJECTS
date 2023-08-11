import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.*;

public class PasswordGenerator {


    public static void main(String[] args) throws InterruptedException, IOException {

        Scanner sc = new Scanner(System.in);
        File accountFile = new File("password.txt");
        if (!accountFile.exists()) {
            // If the account file does not exist, create it
            accountFile.createNewFile();
        }
        File accountFile1 = new File("password1.txt");
        if (!accountFile1.exists()) {
            // If the account file does not exist, create it
            accountFile1.createNewFile();
        }
        File accountFile2 = new File("log.txt");
        if (!accountFile2.exists()) {
            // If the account file does not exist, create it
            accountFile2.createNewFile();
        }
            String username;

        String inputString = "               Welcome to Password Generator!           ";
        char[] words = inputString.toCharArray();
        for (char word : words) {
            System.out.print(word + " ");
            Thread.sleep(200);
        }
        System.out.println("");
        System.out.println("Enter username");
        username= sc.nextLine();
        // Step I: Generating a Password




        // Ask the user if they have forgotten their password
        System.out.print("Have you forgotten your password? (Yes/No): ");
        String answer = sc.next();
        if (answer.equalsIgnoreCase("Yes")) {
            // Search the log file for the most recent password selected by the user
            BufferedReader reader = new BufferedReader(new FileReader("log.txt"));
            String line;
            String lastSelectedPassword = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains(username+" selected password: ")) {
                    lastSelectedPassword = line.substring(line.indexOf(": ") + 2);
                }
            }
            reader.close();

            if (lastSelectedPassword != null) {
                System.out.println("Your last selected password was: " + lastSelectedPassword);
            } else {
                System.out.println("No password was found in the log file.");
                System.out.println("Do you want to create a new password? (Yes/No): ");
                String ans = sc.next();
                if (ans.equalsIgnoreCase("Yes")) {
                    String[] password = generatePassword(sc);
                    for (String s : password) {
                        // Step II: Checking Password Strength
                        int passwordStrengthScore = calculatePasswordStrength(s);

                        // Display and store the chosen password
                        displayAndStorePasswords(s, passwordStrengthScore);
                    }
                    for (int i = 0; i < password.length; i++) {
                        // Step IV: Choosing Password from File
                        choosePasswordFromFile(password[i], username);
                        // Exit the loop after choosing a password
                        if (i == 0) break;
                    }
                } else {
                    System.out.println("Thank you for using our password generator system. Goodbye!");
                    System.exit(0);
                }

            }
        }
        else{
            String[] password = generatePassword(sc);

            for (String s : password) {
                // Step II: Checking Password Strength
                int passwordStrengthScore = calculatePasswordStrength(s);

                // Display and store the chosen password
                displayAndStorePasswords(s, passwordStrengthScore);
            }
            for(int i=0;i<password.length;i++) {
                // Step IV: Choosing Password from File
                choosePasswordFromFile(password[i],username);
                // Exit the loop after choosing a password
                if (i == 0) break;
            }

        }

        sc.close();
    }

    private static String[] generatePassword(Scanner a) throws InterruptedException {


        System.out.println("");
        System.out.println("Answer the following questions to generate your password.");

        // Step II: Interactive program that asks the user questions
        boolean useUppercase = askYesNoQuestion(a, "Do you want to use uppercase letters?");
        boolean useLowercase = askYesNoQuestion(a, "Do you want to use lowercase letters?");
        boolean useNumbers = askYesNoQuestion(a, "Do you want to use numbers?");
        boolean useSymbols = askYesNoQuestion(a, "Do you want to use symbols?");

        System.out.print("Enter the desired length of the password: ");
        int passwordLength = a.nextInt();

        // Step III: Generating passwords based on user preferences
        String password1 = generatePassword(passwordLength, useUppercase, useLowercase, useNumbers, useSymbols);
        String password2 = generatePassword(passwordLength, useUppercase, useLowercase, useNumbers, useSymbols);
        String password3 = generatePassword(passwordLength, useUppercase, useLowercase, useNumbers, useSymbols);

        System.out.println("Generated Passwords:");
        System.out.println("1. " + password1);
        System.out.println("2. " + password2);
        System.out.println("3. " + password3);




        return new String[]{ password1, password2, password3 };
    }



    private static boolean askYesNoQuestion(Scanner scanner, String question) {
        System.out.print(question + " (Yes/No): ");
        String answer = scanner.next();
        return answer.equalsIgnoreCase("Yes");
    }

    private static String generatePassword(int length, boolean useUppercase, boolean useLowercase, boolean useNumbers, boolean useSymbols) {
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        // Generate at least one uppercase letter
        if (useUppercase) {
            password.append((char) (random.nextInt(26) + 'A'));
        }

        // Generate random characters based on user preferences
        String allCharacters = "";
        if (useUppercase) allCharacters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (useLowercase) allCharacters += "abcdefghijklmnopqrstuvwxyz";
        if (useNumbers) allCharacters += "0123456789";
        if (useSymbols) allCharacters += "!@#$%^&*()-=_+[]{}|;':,.<>/?";




        for (int i = password.length(); i < length; i++) {
            password.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
        }

        return password.toString();
    }

    private static int calculatePasswordStrength(String password) {
        int score = 0;

        // Check password length
        if (password.length() >= 8) score++;
        if (password.length() >= 16) score++;

        // Check if password starts with uppercase letter
        if (Character.isUpperCase(password.charAt(0))) score++;

        // Check if password ends with lowercase letter
        if (Character.isLowerCase(password.charAt(password.length() - 1))) score++;

        // Check if password has at least 3 numbers
        int countNumbers = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) countNumbers++;
        }
        if (countNumbers >= 3) score++;

        // Check if password has at least 3 symbols
        int countSymbols = 0;
        String symbols = "!@#$%^&*()-=_+[]{}|;':,.<>/?";
        for (char c : password.toCharArray()) {
            if (symbols.indexOf(c) != -1) countSymbols++;
        }
        if (countSymbols >= 3) score++;

        return score;
    }

    private static void displayAndStorePasswords(String password, int passwordStrengthScore) {
        try {
            // Display passwords
            System.out.println("Generated Password: " + password);
            System.out.println("Password Strength: " + getPasswordStrengthMessage(passwordStrengthScore));

            // Check if password is already stored in the file
            boolean passwordExists = false;
            BufferedReader reader = new BufferedReader(new FileReader("passwords.txt"));
            String line = reader.readLine();
            while (line != null) {
                if (line.equals(password)) {
                    passwordExists = true;
                    break;
                }
                line = reader.readLine();
            }
            reader.close();

            // Store password in a file if it doesn't already exist
            if (!passwordExists) {
                FileWriter writer = new FileWriter("passwords.txt", true);
                writer.write(password + "\n");
                writer.close();

            } else {
                System.out.println("Password already exists in file.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while storing passwords.");
        }
    }


    private static String getPasswordStrengthMessage(int score) {
        if (score <= 2) {
            return "Weak password";
        } else if (score == 3) {
            return "Medium password";
        } else if (score == 4) {
            return "Good password";
        } else {
            return "Great password";
        }
    }

    private static String choosePasswordFromFile(String pass, String username) {

        String chosenPassword = null;
        try {
            Scanner scanner = new Scanner(System.in);
            File file = new File("passwords.txt");
            Scanner fileScanner = new Scanner(file);

            System.out.println("Choose a password from the file:");

            int count = 1;
            while (fileScanner.hasNextLine()) {
                String password = fileScanner.nextLine();
                System.out.println(count + ". " + password);
                count++;
            }

            System.out.print("Enter the number of the password you want to choose: ");
            int choice = scanner.nextInt();
            fileScanner.close();

            fileScanner = new Scanner(file);
            count = 1;
            while (fileScanner.hasNextLine()) {
                if (count == choice) {
                    chosenPassword = fileScanner.nextLine();
                    System.out.println("Chosen Password: " + chosenPassword);

                    // Write the chosen password to a new file
                    File fileName = new File("passwords1.txt");

                    try (PrintWriter writer = new PrintWriter(fileName)) {
                        writer.println("Chosen Password: " + chosenPassword);

                        System.out.println("Chosen password has been saved to " + fileName);
                    } catch (FileNotFoundException e) {
                        System.out.println("Error writing to file: " + fileName);
                    }

                    FileWriter writer = new FileWriter("log.txt", true);
                    try (PrintWriter writer1 = new PrintWriter(writer)) {

                        writer1.println(username+" selected password: "+ chosenPassword);
                    }
                    break;

                }
                fileScanner.nextLine();
                count++;
            }

            fileScanner.close();
            File f = new File("passwords.txt");
            PrintWriter writer = new PrintWriter(f);
            writer.print("");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
        }
        return chosenPassword;
    }}