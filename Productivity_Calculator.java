import java.io.*;
import java.util.*;

public class Productivity_Calculator{
    public static void main(String args[]) {
        ArrayList<String> tasks = new ArrayList<>();
        ArrayList<Integer> durations = new ArrayList<>();
        ArrayList<String> categories = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        boolean loggedIn = false;
        String username = "";
        String password = "";

        System.out.println("___________________     PRODUCTIVITY CALCULATOR APP     __________________\n\n");
        System.out.println("      ******************************************************************\n");
        System.out.println("  || THIS IS A PLACE WHERE YOU CAN KEEP TRACK OF YOUR DAILY ROUTINE TASKS  ||\n ");
        System.out.println("  \n**  IF YOU ARE A NEW USER, PRESS 'YES' ");
        System.out.println("  \n**  IF YOU ARE AN EXISTING USER, PRESS 'NO'\n ");

        System.out.print("Are you a new user? (yes/no): ");
        String newUserChoice = scanner.nextLine();

        if (newUserChoice.equalsIgnoreCase("yes")) {
            System.out.print("Enter your new username: ");
            username = scanner.nextLine();
            System.out.print("Enter your new password: ");
            password = scanner.nextLine();

            // create file with username and password
            File inputFile = new File(username + "_Productivity_Calculator.txt");

         if (!inputFile.exists()) {
              try {
                 FileWriter fw = new FileWriter(inputFile);
                 PrintWriter pw = new PrintWriter(fw);
                 pw.println("USERNAME = " + username);
                 pw.println("PASSWORD = " + password);
                 System.out.println("\n* NEW ACCOUNT CREATED! *\n");
                 pw.close();
                } 
                catch (IOException e) {
                  e.printStackTrace();
               }
            }


            loggedIn = true;
        } 
        else if (newUserChoice.equalsIgnoreCase("no")) {
            while (!loggedIn) {
                System.out.print("Enter your existing username: ");
                username = scanner.nextLine();
                System.out.print("Enter your existing password: ");
                password = scanner.nextLine();

                // check if entered username and password match stored values
                try {
                    File inputFile = new File(username + "_Productivity_Calculator.txt");
                    Scanner fileScanner = new Scanner(inputFile);

                    String storedUsername = fileScanner.nextLine().substring(11); // remove "USERNAME = " from the stored value
                    String storedPassword = fileScanner.nextLine().substring(11); // remove "PASSWORD = " from the stored value

                    if (username.equals(storedUsername) && password.equals(storedPassword)) {
                        System.out.println("\n\n _______ WELCOME!! LOGGED IN SUCCESSFULLY AS " + username + " ______\n\n");
                        loggedIn = true;
                    } else {
                        System.out.println("\nIncorrect username or password. Please try again.\n");
                    }
                } catch (IOException e) {
                    System.out.println("\nIncorrect username or password. Please try again.\n");
                }
            }
        } 
        else {
            System.out.println("\nInvalid input. Please try again.\n");
        }
        if (loggedIn) {
                System.out.println("BEGIN BY ADDING YOUR FIRST TASK");

                System.out.println("In which category do you want to add the task?");
                System.out.println("1. Health");
                System.out.println("2. Lifestyle");
                System.out.println("3. Miscellaneous");
                int categoryChoice = scanner.nextInt();
                scanner.nextLine();
                String category = "";

                switch (categoryChoice) {
                    case 1:
                        category = "Health";
                        break;
                    case 2:
                        category = "Lifestyle";
                        break;
                    case 3:
                        category = "Miscellaneous";
                        break;
                    default:
                        System.out.println("Invalid category choice.");
                    break;
                }

                System.out.println("Enter task name:");
                String task = scanner.nextLine();
                System.out.println("Enter task duration in minutes:");
                int duration = scanner.nextInt();
                scanner.nextLine();
                tasks.add(task);
                durations.add(duration);
                categories.add(category);


                // write task to file
                try {
                    FileWriter fw = new FileWriter(username + "_Productivity_Calculator.txt", true);
                    PrintWriter pw = new PrintWriter(fw);
                    pw.println("CATEGORY: " + category);
                    pw.println("TASK: " + task);
                    pw.println("DURATION: " + duration + " minutes");
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("\nTASK ADDED SUCCESSFULLY");


                int choice = 0;
                while (choice != 5) {
                 

                   System.out.println("\nWhat would you like to do next?");
                   System.out.println("1. Add Task");
                   System.out.println("2. Update Task");
                   System.out.println("3. Delete Task");
                   System.out.println("4. Display Tasks");
                   System.out.println("5. Exit");
                   System.out.print("\nEnter your choice: ");
                   choice = scanner.nextInt();
                   scanner.nextLine();
                   

                   if (choice == 1) {
                     System.out.println("Enter task name:");
                     task = scanner.nextLine();
                     System.out.println("Enter task duration in minutes:");
                     duration = scanner.nextInt();
                     scanner.nextLine();
                     System.out.println("");
                     System.out.println("\nIn which category do you want to add the task?");
                     System.out.println("1. Health");
                     System.out.println("2. Lifestyle");
                     System.out.println("3. Miscellaneous");
                     categoryChoice = scanner.nextInt();
                     scanner.nextLine();
                     category = "";

                     switch (categoryChoice) {
                         case 1:
                         category = "Health";
                         break;
                         case 2:
                         category = "Lifestyle";
                         break;
                         case 3:
                         category = "Miscellaneous";
                         break;
                         default:
                         System.out.println("Invalid category choice.");
                         break;
                        }
                     tasks.add(task);
                     durations.add(duration);
                     categories.add(category);
                     System.out.println("TASK ADDED SUCCCESSFULLY");

                     try {
                         FileWriter fw = new FileWriter(username + "_Productivity_Calculator.txt", true);
                         PrintWriter pw = new PrintWriter(fw);
                         pw.println("TASK: " + task);
                         pw.println("DURATION: " + duration + " minutes");
                         pw.close();
                        } 
                     catch (IOException e) {
                         e.printStackTrace();
                        }
                   }
                 else if (choice == 2) {
                     System.out.println("Enter the task name to update:");
                     String taskToUpdate = scanner.nextLine();
                     int taskIndex = tasks.indexOf(taskToUpdate);

                     if (taskIndex != -1) {
                         System.out.println("Enter the new task name:");
                         String newTaskName = scanner.nextLine();
                         System.out.println("Enter the new task duration in minutes:");
                         int newDuration = scanner.nextInt();
                         scanner.nextLine();
        

                         tasks.set(taskIndex, newTaskName);
                         durations.set(taskIndex, newDuration);

                         System.out.println("\nTASK UPDATED SUCCESSFULLY");

                         try {
                              FileWriter fw = new FileWriter(username + "_Productivity_Calculator.txt");
                              PrintWriter pw = new PrintWriter(fw);

                             for (int i = 0; i < tasks.size(); i++) {
                                  pw.println("CATEGORY: " + categories.get(i));
                                  pw.println("TASK: " + tasks.get(i));
                                  pw.println("DURATION: " + durations.get(i) + " minutes");
                                 pw.println("-----------------------------");
                                }

                             pw.close();
                            }
                            catch (IOException e) {
                              e.printStackTrace();
                            }
                        }
                        else {
                          System.out.println("Task not found.");
                        }
                    }
                 else if (choice == 3) {
                     System.out.println("Enter the task name to delete:");
                     String taskToDelete = scanner.nextLine();
                     int taskIndex = tasks.indexOf(taskToDelete);

                     if (taskIndex != -1) {
                         tasks.remove(taskIndex);
                         durations.remove(taskIndex);
                         categories.remove(taskIndex);

                         System.out.println("\nTASK DELETED SUCCESSFULLY");

                         try {
                             FileWriter fw = new FileWriter(username + "_Productivity_Calculator.txt");
                             PrintWriter pw = new PrintWriter(fw);

                             for (int i = 0; i < tasks.size(); i++) {
                                 pw.println("CATEGORY: " + categories.get(i));
                                 pw.println("TASK: " + tasks.get(i));
                                 pw.println("DURATION: " + durations.get(i) + " minutes");
                                 pw.println("-----------------------------");
                                }

                             pw.close();
                            }
                            catch (IOException e) {
                              e.printStackTrace();
                            }
                        } 
                        else {
                         System.out.println("Task not found.");
                        }

                    }
                    else if (choice == 4) {
                     System.out.println("\nTASKS:");

                     if (tasks.isEmpty()) {
                         System.out.println("No tasks found.");
                        }
                        else {
                          System.out.println("Category\tTask\t\t\tDuration");
                          System.out.println("-----------------------------------------------");

                          try {
                             FileWriter fw = new FileWriter(username + "_Productivity_Calculator.txt", true);
                             PrintWriter pw = new PrintWriter(fw);

                             pw.println("\nTASKS:");
                             pw.println("Category\tTask\t\t\tDuration");
                             pw.println("-----------------------------------------------");

                             for (int i = 0; i < tasks.size(); i++) {
                                  String categoryFormatted = String.format("%-15s", categories.get(i));
                                  String taskFormatted = String.format("%-30s", tasks.get(i));
                                  String durationFormatted = String.format("%d minutes", durations.get(i));

                                  System.out.println(categoryFormatted + taskFormatted + durationFormatted);
                                  pw.println(categoryFormatted + taskFormatted + durationFormatted);
                                }

                             pw.close();
                            }
                            catch (IOException e) {
                             e.printStackTrace();
                            }
                        }
                    }
                    else if (choice == 5) {
                      System.out.println("EXITING APPLICATION");
                      int totalDuration = 0;
                      for (int d : durations) {
                          totalDuration += d;
                        }
                     int productiveHours = totalDuration / 60;
                     int productiveMinutes = totalDuration % 60;
                     int nonProductiveHours = (24 * 60 - totalDuration) / 60;
                     int nonProductiveMinutes = (24 * 60 - totalDuration) % 60;
                     System.out.println("TOTAL TIME: 24 hours 0 minutes");
                     System.out.println("Productive Time: " + productiveHours + " hours " + productiveMinutes + " minutes");
                     System.out.println("Non-Productive Time: " + nonProductiveHours + " hours " + nonProductiveMinutes + " minutes");
                     try{
                          FileWriter fw = new FileWriter(username + "_Productivity_Calculator.txt", true);
                          PrintWriter pw = new PrintWriter(fw);
                          pw.println("Total productive time: " + productiveHours + " hours " + productiveMinutes + " minutes");
                          pw.println("Total non-productive time: " + nonProductiveHours + " hours " + nonProductiveMinutes + " minutes");
                          pw.close();
                        }
                        catch (IOException e) {
                         e.printStackTrace();
                        }
                        System.out.println("Thank you for using the productivity calculator!");
                        System.exit(0);
                    }
                    else {
                      System.out.println("Invalid input, please try again.");
                    }
                }
            }
        }
    }

