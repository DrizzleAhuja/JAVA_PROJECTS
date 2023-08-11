import java.io.*;
import java.util.Scanner;

public class SocialMediaApp {
    private static final String USERS_FILE = "users1.txt";
    private static final String FRIEND_REQUESTS_FILE = "friend_requests.txt";
    private static final String FRIENDS_FILE = "friends.txt";
    private static final String POSTS_FILE = "posts.txt";
    private static final String COMMENTS_FILE = "comments.txt";
    private static final String NOTIFICATIONS_FILE = "notifications.txt";
    private static BufferedReader reader;

    public static void main(String[] args) throws IOException {
        createFile("users1.txt");
        createFile("friend_requests.txt");
        createFile("friends.txt");
        createFile("posts.txt");
        createFile("comments.txt");
        createFile("notifications.txt");
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (choice != 7) {
            System.out.println("Social Media Application");
            System.out.println("1. Create Profile");
            System.out.println("2. Login to Account");
            System.out.println("3. Connect with Friends");
            System.out.println("4. Share Post");
            System.out.println("5. Comment on Post");
            System.out.println("6. View Notifications");
            System.out.println("7. Quit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    createProfile(scanner);
                    break;
                case 2:
                    logintoProfile(scanner);
                    break;
                case 3:
                    connectWithFriends(scanner);
                    break;
                case 4:
                    sharePost(scanner);
                    break;
                case 5:
                    commentOnPost(scanner);
                    break;
                case 6:
                    viewNotifications(scanner);
                    break;
                case 7:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    private static void createProfile(Scanner scanner) throws IOException {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // check if the user already has a profile
        BufferedReader reader = new BufferedReader(new FileReader(new File(USERS_FILE)));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            if (tokens[0].equals(username)) {
                System.out.println("A profile already exists for this username.");
                return;
            }
        }
        reader.close();

        // create the user's profile
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your profile picture URL: ");
        String profilePictureUrl = scanner.nextLine();
        System.out.print("Enter your bio: ");
        String bio = scanner.nextLine();

        // save the user's information to file
        FileWriter writer = new FileWriter(new File(USERS_FILE), true);
        writer.write(username + "," + password + "," + name + "," + profilePictureUrl + "," + bio + "\n");
        writer.close();
        System.out.println("Profile created successfully.");
    }
    private static void logintoProfile( Scanner scanner) throws IOException{
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        BufferedReader reader = new BufferedReader(new FileReader(new File(USERS_FILE)));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            if (tokens[0].equals(username) && tokens[1].equals(password)) {
                System.out.println("Welcome "+username);
                return;
            }
        }
        reader.close();
    }

    private static void connectWithFriends(Scanner scanner) throws IOException {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        // display the user's current friends
        reader = new BufferedReader(new FileReader(new File(FRIENDS_FILE)));
        System.out.println("Current friends:");
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            if (tokens[0].equals(name)) {
                System.out.println(tokens[1]);
            } else if (tokens[1].equals(name)) {
                System.out.println(tokens[0]);
            }
        }
        reader.close();

        // prompt the user to add or remove friends
        System.out.println("1. Send Friend Request");
        System.out.println("2. Remove Friend");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        switch (choice) {
            case 1:
                sendFriendRequest(scanner, name);
                break;
            case 2:
                removeFriend(scanner, name);
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }}


    private static void sendFriendRequest(Scanner scanner, String senderName) throws IOException {
        System.out.print("Enter the name of the person you want to send a friend request to: ");
        String receiverName = scanner.nextLine();

        // check if the friend request has already been sent
        BufferedReader reader = new BufferedReader(new FileReader(new File(FRIEND_REQUESTS_FILE)));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            if (tokens[0].equals(senderName) && tokens[1].equals(receiverName)) {
                System.out.println("A friend request has already been sent to this person.");
                reader.close();
                return;
            }
        }
        reader.close();

        // save the friend request to file
        FileWriter writer = new FileWriter(new File(FRIEND_REQUESTS_FILE), true);
        writer.write(senderName + "," + receiverName + "\n");
        writer.close();
        System.out.println("Friend request sent successfully.");
    }

    private static void removeFriend(Scanner scanner, String userName) throws IOException {
        System.out.print("Enter the name of the person you want to remove from your friends list: ");
        String friendName = scanner.nextLine();

        // remove the friend from the user's friends list
        File inputFile = new File(FRIENDS_FILE);
        File tempFile = new File("temp.txt");
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            if (!(tokens[0].equals(userName) && tokens[1].equals(friendName)) &&
                    !(tokens[0].equals(friendName) && tokens[1].equals(userName))) {
                writer.write(line + "\n");
            }
        }
        reader.close();
        writer.close();
        inputFile.delete();
        tempFile.renameTo(inputFile);

        System.out.println("Friend removed successfully.");
    }

    private static void sharePost(Scanner scanner) throws IOException {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        // create the post
        System.out.print("Enter the post content: ");
        String postContent = scanner.nextLine();
        int postId = getPostId();
        FileWriter writer = new FileWriter(new File(POSTS_FILE), true);
        writer.write(postId + "," + name + "," + postContent + "\n");
        writer.close();

        // notify the user's friends
        BufferedReader reader = new BufferedReader(new FileReader(new File(FRIENDS_FILE)));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            if (tokens[0].equals(name)) {
                notifyFriend(tokens[1], "You have a new post from " + name);
            } else if (tokens[1].equals(name)) {
                notifyFriend(tokens[0], "You have a new post from " + name);
            }
        }
        reader.close();
        System.out.println("Post shared successfully.");
    }

    private static int getPostId() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(POSTS_FILE)));
        int maxId = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            int postId = Integer.parseInt(tokens[0]);
            if (postId > maxId) {
                maxId = postId;
            }
        }
        reader.close();
        return maxId + 1;
    }

    private static void notifyFriend(String friendName, String notification) throws IOException {
        FileWriter writer = new FileWriter(new File(NOTIFICATIONS_FILE), true);
        writer.write(friendName + "," + notification + "\n");
        writer.close();
    }
    private static void commentOnPost(Scanner scanner) throws IOException {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        // display the available posts
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(POSTS_FILE)))) {
            String line;
            System.out.println("Available posts:");
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                System.out.println(tokens[0] + ". " + tokens[2] + " (by " + tokens[1] + ")");
            }
        } catch (FileNotFoundException e) {
            System.err.println("Posts file not found: " + POSTS_FILE);
            return;
        }

        // prompt the user to choose a post to comment on
        int postId = -1;
        do {
            System.out.print("Enter the post ID you want to comment on: ");
            try {
                postId = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Invalid post ID. Please enter a number.");
            }
        } while (postId < 0);

        // prompt the user to enter their comment
        System.out.print("Enter your comment: ");
        String comment = scanner.nextLine();

        // add the comment to the post's comments file
        try (FileWriter writer = new FileWriter(new File(COMMENTS_FILE), true)) {
            writer.write(postId + "," + name + "," + comment + "\n");
        } catch (IOException e) {
            System.err.println("Failed to write comment to file: " + COMMENTS_FILE);
            return;
        }

        // notify the post's author about the comment
        String authorName = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(POSTS_FILE)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (Integer.parseInt(tokens[0]) == postId) {
                    authorName = tokens[1];
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Posts file not found: " + POSTS_FILE);
            return;
        }

        if (authorName != null) {
            notifyFriend(authorName, name + " commented on your post (ID: " + postId + ")");
        } else {
            System.err.println("Failed to find author of post " + postId);
        }

        System.out.println("Comment added successfully.");
    }




    private static void viewNotifications(Scanner scanner) throws IOException {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        // display the user's notifications
        BufferedReader reader = new BufferedReader(new FileReader(new File(NOTIFICATIONS_FILE)));
        String line;
        System.out.println("Notifications:");
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            if (tokens[0].equals(username)) {
                System.out.println(tokens[1]);
            }
        }
        reader.close();
    }

    private static void createFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
    }
}