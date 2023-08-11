import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EducationPlatform {
    // Define variables for user data
    private static String username;
    private static String password;

    // Define variables for course data
    private static String courseName;
    private static String courseDescription;
    private static int courseDuration;
    private static List<String> courseTopics = new ArrayList<String>();

    // Define variables for quiz data
    private static String quizName;
    private static int quizDuration;
    private static List<String> quizQuestions = new ArrayList<String>();
    private static String assignmentName;
    private static int assignmentDuration;
    private static List<String> assignmentQuestions = new ArrayList<String>();

    // Define variables for forum data
    private static String forumName;
    private static List<String> forumPosts = new ArrayList<String>();

    // Define variable for user progress
    private static double totalDuration = 0;
    private static double courseProgress = 0;
    private static double quizProgress = 0;
    private static double forumProgress = 0;
    private static double assignmentProgress = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;

        System.out.println("___________________     EDUCATION PLATFORM APP   __________________\n\n");
        System.out.println("  || THIS IS A PLACE WHERE YOU CAN KEEP TRACK ON YOUR DAILY ROUTINE TASK  ||\n ");
        System.out.println("  IF YOU ARE A NEW USER ENTER YOUR NEW USERNAME AND PASSWORD ");
        System.out.println("  IF YOU ARE AN EXISTING USER ENTER YOUR EXISTING USERNAME AND PASSWORD ");

        while (true) {
            if (!loggedIn) {
                System.out.print("   Enter username: ");
                String enteredUsername = scanner.nextLine();
                System.out.print("   Enter password: ");
                String enteredPassword = scanner.nextLine();

                // create file with username and password
                File inputFile = new File(enteredUsername + "_Education_Platform.txt");

                if (!inputFile.exists()) {
                    try {
                        FileWriter fw = new FileWriter(inputFile);
                        PrintWriter pw = new PrintWriter(fw);
                        pw.println("USERNAME = " + enteredUsername);
                        pw.println("PASSWORD = " + enteredPassword);
                        System.out.println(" NEW ACCOUNT CREATED!! ");
                        pw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // check if entered username and password match stored values
                try {
                    Scanner fileScanner = new Scanner(inputFile);

                    String storedUsername = fileScanner.nextLine().substring(11); // remove "USERNAME = " from the stored value
                    String storedPassword = fileScanner.nextLine().substring(11); // remove "PASSWORD = " from the stored value

                    if (enteredUsername.equals(storedUsername) && enteredPassword.equals(storedPassword)) {
                        System.out.println("\n\n ____________ WELCOME!! LOGGED IN SUCCESSFULLY AS " + enteredUsername + " _________\n\n");
                        loggedIn = true;
                        username = enteredUsername;
                        password = enteredPassword;
                    } else {
                        System.out.println("Incorrect username or password, please try again.\n");
                    }
                } catch (IOException e) {
                    System.out.println("Incorrect username or password, please try again.\n");
                }
            }

            // Display main menu
            displayMainMenu();

            // Handle user input
            String input = scanner.nextLine();

            if (input.matches("\\d+")) {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        createCourse();
                        break;
                    case 2:
                        createQuiz();
                        break;
                    case 3:
                        createForum();
                        break;
                    case 4:
                        createAssignment();
                        break;
                    case 5:
                        viewProgress();
                        break;
                    case 6:
                        System.out.println("\n\n ____________ THANK YOU FOR USING EDUCATION PLATFORM!! SEE YOU SOON! _________\n\n");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice, please try again.\n");
                        break;
                }
            } else {
                System.out.println("Invalid input, please try again.\n");
            }
        }
    }
    private static void displayMainMenu() {
        System.out.println("\n");
        System.out.println("MAIN MENU");
        System.out.println("1. Create a new course");
        System.out.println("2. Create a new quiz");
        System.out.println("3. Create a new forum");
        System.out.println("4. Create a new assignment");
        System.out.println("5. View your progress");
        System.out.println("6. Logout");
        System.out.println("Please enter your choice: ");
    }

    private static void createCourse() {
        Scanner scanner = new Scanner(System.in);
        // Prompt user for course information
        System.out.println("Please enter the course name:");
        courseName = scanner.nextLine();

        System.out.println("Please enter the course description:");
        courseDescription = scanner.nextLine();
        System.out.println("Please enter the course duration (in hours):");
        courseDuration = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left behind by nextInt()

        boolean isAddingTopics = true;
        while (isAddingTopics)
        {
            System.out.println("Please enter a course topic (or enter 'done' to finish adding topics):");
            String topic = scanner.nextLine();

            if (topic.equals("done")) {
                isAddingTopics = false;
            } else {
                courseTopics.add(topic);
            }
        }
// Calculate total course duration
        totalDuration += courseDuration;

// Update course progress
        courseProgress = (totalDuration / 100) * 25;


// Add course to file
        try {
            FileWriter fw = new FileWriter(username + "_Education_Platform.txt",true);
            PrintWriter pw = new PrintWriter(fw);

            pw.println("[Course]");
            pw.println("Name: " + courseName);
            pw.println("Description: " + courseDescription);
            pw.println("Duration: " + courseDuration + " hours");
            pw.println("Topics:");
            for (String topic : courseTopics) {
                pw.println("- " + topic);
            }
            pw.println();

            pw.close();
            fw.close();

            System.out.println("Course successfully created!");
        } catch (IOException e) {
            System.out.println("An error occurred while creating the course.");
            e.printStackTrace();
        }
    }

    private static void createQuiz() {
        Scanner scanner = new Scanner(System.in);
// Prompt user for quiz information
        System.out.println("Please enter the quiz name:");
        quizName = scanner.nextLine();
        System.out.println("Please enter the quiz duration (in minutes):");
        quizDuration = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left behind by nextInt()

        boolean isAddingQuestions = true;
        while (isAddingQuestions) {
            System.out.println("Please enter a quiz question (or enter 'done' to finish adding questions):");
            String question = scanner.nextLine();

            if (question.equals("done")) {
                isAddingQuestions = false;
            } else {
                quizQuestions.add(question);
            }
        }
// Calculate total quiz duration
        totalDuration += quizDuration;

// Update quiz progress
        quizProgress = (totalDuration / 100) * 50;

// Display quiz information


// Add quiz to file
        try {
            FileWriter fw = new FileWriter(username+"_Education_Platform.txt", true);
            PrintWriter pw = new PrintWriter(fw);

            pw.println("[Quiz]");
            pw.println("Name: " + quizName);
            pw.println("Duration: " + quizDuration + " minutes");
            pw.println("Questions:");
            for (String question : quizQuestions) {
                pw.println("- " + question);
            }
            pw.println();

            pw.close();
            fw.close();

            System.out.println("Quiz successfully created!");
        } catch (IOException e) {
            System.out.println("An error occurred while creating the quiz.");
            e.printStackTrace();
        }
    }

    private static void createForum() {
        Scanner scanner = new Scanner(System.in);
// Prompt user for forum information
        System.out.println("Please enter the forum name:");
        forumName = scanner.nextLine();
        boolean isAddingPosts = true;
        while (isAddingPosts) {
            System.out.println("Please enter a forum post (or enter 'done' to finish adding posts):");
            String post = scanner.nextLine();

            if (post.equals("done")) {
                isAddingPosts = false;
            } else {
                forumPosts.add(post);
            }
        }
// Update forum progress
        forumProgress = (totalDuration / 100) * 25;

// Add forum to file
        try {
            FileWriter fw = new FileWriter(username+"_Education_Platform.txt", true);
            PrintWriter pw = new PrintWriter(fw);

            pw.println("[Forum]");
            pw.println("Name: " + forumName);
            pw.println("Posts:");
            for (String post : forumPosts) {
                pw.println("- " + post);
            }
            pw.println();

            pw.close();
            fw.close();

            System.out.println("Forum successfully created!");
        } catch (IOException e) {
            System.out.println("An error occurred while creating the forum.");
            e.printStackTrace();
        }
    }
    private static void viewProgress() {
        System.out.println("Progress Summary:");
        System.out.println("Course progress: " + courseProgress + "%");
        System.out.println("Quiz progress: " + quizProgress + "%");
        System.out.println("Forum progress: " + forumProgress + "%");
        System.out.println("Assignment progress: " + assignmentProgress + "%");
        System.out.println("Total duration: " + totalDuration + " minutes");

        try {
            FileWriter writer = new FileWriter(username+"_Education_Platform.txt", true); // true flag for appending data
            writer.write("Progress Summary:\n");
            writer.write("Course progress: " + courseProgress + "% \n ");
            writer.write("Quiz progress: " + quizProgress + "% \n ");
            writer.write("Forum progress: " + forumProgress + "% \n ");
            writer.write("Assignment progress: " + assignmentProgress + "% \n ");
            writer.write("Total duration: " + totalDuration + " minutes \n\n ");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving progress summary: " + e.getMessage());
        }

    }
    private static void createAssignment() {
        Scanner scanner = new Scanner(System.in);
// Prompt user for quiz information
        System.out.println("Please enter the Assignment name :");
        assignmentName = scanner.nextLine();
        System.out.println("Please enter the assignment  duration (in minutes):");
        assignmentDuration = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left behind by nextInt()

        boolean isAddingQuestions = true;
        while (isAddingQuestions) {
            System.out.println("Please enter a assignment question (or enter 'done' to finish adding questions):");
            String question = scanner.nextLine();

            if (question.equals("done")) {
                isAddingQuestions = false;
            } else {
                assignmentQuestions.add(question);
            }
        }
// Calculate total quiz duration
        totalDuration += assignmentDuration;

// Update quiz progress
        assignmentProgress = (totalDuration / 100) * 50;

// Display quiz information


// Add quiz to file
        try {
            FileWriter fw = new FileWriter(username+"_Education_Platform.txt", true);
            PrintWriter pw = new PrintWriter(fw);

            pw.println("[Assignment]");
            pw.println("Name: " + assignmentName);
            pw.println("Duration: " + assignmentDuration + " minutes");
            pw.println("Questions:");
            for (String question : assignmentQuestions) {
                pw.println("- " + question);
            }
            pw.println();

            pw.close();
            fw.close();

            System.out.println("assignment successfully created!");
        } catch (IOException e) {
            System.out.println("An error occurred while creating the quiz.");
            e.printStackTrace();
        }
    }





}
