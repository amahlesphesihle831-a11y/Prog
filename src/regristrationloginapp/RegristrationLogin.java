/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package regristrationloginapp;

/**
 *
 * @author Amahle Hlongwane
 */
public class RegristrationLogin {
    private String messageID;
    private String recipient;
    private String message;

    // these are shared across all Message objects
    private static ArrayList<Message> sentMessages = new ArrayList<>();
    private static int totalSent = 0;

    public Message(String messageID, String recipient, String message) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.message = message;
    }

    // checks if ID is 10 chars or less
    public boolean checkMessageID() {
        if (messageID.length() <= 10) {
            return true;
        }
        return false;
    }

    // checks if the number starts with + and has 9-11 digits after
    public String checkRecipientCell() {
        if (!recipient.startsWith("+")) {
            return "Cell phone number is incorrectly formatted or does not contain an international code.";
        }
        String digits = recipient.substring(1);
        if (digits.matches("\\d{9,11}")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code.";
    }

    // checks if message is 250 chars or less
    public String checkMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        }
        int overBy = message.length() - 250;
        return "Message exceeds 250 characters by " + overBy + "; please reduce the size.";
    }

    // hash = first 2 digits of ID : totalSent : firstword+lastword in CAPS
    public String getMessageHash() {
        String clean = message.trim();

        // if there is no space in the message (single word)
        if (clean.length() == 0 || !clean.contains(" ")) {
            return messageID.substring(0, 2) + ":" + totalSent + ":" + clean.toUpperCase();
        }

        String first = clean.substring(0, clean.indexOf(" "));
        String last = clean.substring(clean.lastIndexOf(" ") + 1);

        return messageID.substring(0, 2) + ":" + totalSent + ":" + (first + last).toUpperCase();
    }

    // handles send, disregard or store
    public String sendMessage(String choice) {
        if (choice.equalsIgnoreCase("send")) {
            sentMessages.add(this);
            totalSent++;
            return "Message successfully sent";
        } else if (choice.equalsIgnoreCase("disregard")) {
            return "Message disregarded. Nothing was sent or stored.";
        } else if (choice.equalsIgnoreCase("store")) {
            storeMessage();
            return "Message successfully stored";
        }
        return "Invalid choice";
    }

    // prints all messages that were sent
    public static String printMessages() {
        String result = "";
        for (int i = 0; i < sentMessages.size(); i++) {
            Message m = sentMessages.get(i);
            result += "Message ID: " + m.messageID + "\n";
            result += "Message Hash: " + m.getMessageHash() + "\n";
            result += "Recipient: " + m.recipient + "\n";
            result += "Message: " + m.message + "\n";
            result += "----------------------\n";
        }
        if (sentMessages.size() == 0) {
            result = "No messages have been sent yet.";
        }
        return result;
    }

    // returns how many messages were sent
    public static int returnTotalMessages() {
        return totalSent;
    }

    // saves the message to message.json (appends)
    public void storeMessage() {
        try {
            FileWriter fw = new FileWriter("message.json", true);
            fw.write("{\n");
            fw.write("  \"messageID\": \"" + messageID + "\",\n");
            fw.write("  \"messageHash\": \"" + getMessageHash() + "\",\n");
            fw.write("  \"recipient\": \"" + recipient + "\",\n");
            fw.write("  \"message\": \"" + message + "\"\n");
            fw.write("}\n");
            fw.close();
        } catch (Exception e) {
            System.out.println("Could not save message to file.");
        }
    }

    // getters
    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessage()   { return message; }
   
    
}
import java.util.Random;
import java.util.Scanner;

public class QuickChat {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        // login part
        boolean loggedIn = false;
        int attempts = 0;

        while (!loggedIn) {
            System.out.print("Username: ");
            String user = sc.nextLine();
            System.out.print("Password: ");
            String pass = sc.nextLine();

            loggedIn = Login.loginUser(user, pass);

            if (!loggedIn) {
                attempts++;
                System.out.println("Login failed. Try again.");
                if (attempts >= 3) {
                    System.out.println("Too many attempts. Exiting.");
                    System.exit(0);
                }
            }
        }

        System.out.println("Welcome to QuickChat!\n");

        // menu loop
        int option;
        do {
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");

            String input = sc.nextLine();
            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number (1, 2, or 3).");
                continue;
            }

            if (option == 1) {
                System.out.print("How many messages do you want to enter? ");
                String countInput = sc.nextLine();

                int count;
                try {
                    count = Integer.parseInt(countInput);
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                    continue;
                }

                if (count <= 0) {
                    System.out.println("Enter a number greater than 0.");
                    continue;
                }

                for (int i = 1; i <= count; i++) {
                    System.out.println("\n--- Message " + i + " ---");

                    // random 10 digit ID
                    long randomId = 1000000000L + rand.nextInt(1000000000);
                    String id = String.valueOf(randomId);

                    System.out.print("Recipient number (+27...): ");
                    String recipient = sc.nextLine();

                    System.out.print("Message (max 250 chars): ");
                    String text = sc.nextLine();

                    Message msg = new Message(id, recipient, text);

                    // show validation
                    System.out.println("ID check: " + (msg.checkMessageID() ? "Valid" : "Too long"));
                    System.out.println(msg.checkRecipientCell());
                    System.out.println(msg.checkMessageLength());

                    System.out.print("Send, Disregard or Store? ");
                    String choice = sc.nextLine();
                    System.out.println(msg.sendMessage(choice));

                    // show details if sent
                    if (choice.equalsIgnoreCase("send")) {
                        System.out.println("\nMessage ID: " + msg.getMessageID());
                        System.out.println("Message Hash: " + msg.getMessageHash());
                        System.out.println("Recipient: " + msg.getRecipient());
                        System.out.println("Message: " + msg.getMessage());
                    }
                }

                System.out.println("\nTotal messages sent so far: " + Message.returnTotalMessages());

            } else if (option == 2) {
                System.out.println("\n--- Recently Sent Messages ---");
                System.out.println(Message.printMessages());

            } else if (option == 3) {
                System.out.println("Goodbye!");

            } else {
                System.out.println("Invalid option.");
            }

        } while (option != 3);

        sc.close();
    }
