import org.json.simple.parser.ParseException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.InputMismatchException;
import java.util.Scanner;


public class UserInterface { //наследование

    MessageStorage ms = new MessageStorage();

    void add() {
        String author, messageText;
        Scanner sc = new Scanner(System.in);
        Integer id = Double.valueOf(Math.random() * 100000).intValue();
        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Europe/Minsk"));
        Timestamp timeStamp = Timestamp.valueOf(ldt);
        System.out.println("enter nickname :");
        author = sc.nextLine();
        System.out.println("enter message :");
        messageText = sc.nextLine();
        if (messageText.length() > 0) {
            this.ms.addMessage(new Message(id, author, messageText, timeStamp));
        } else {
            this.ms.logstatlist.add(new LogStatistic("Error. Message length ", 0));
        }
    }

    boolean deleteMessage() {
        Integer id;
        Scanner sc = new Scanner(System.in);
        try {
            id = sc.nextInt();
            return (this.ms.deleteMessage(id));

        } catch (InputMismatchException e) {
            this.ms.logstatlist.add(new LogStatistic("Error. InputMismatchException ", 1));
            System.out.println("inavlid data");
        }
        return false;
    }

    void findByRegex() {
        String regex;
        Scanner sc = new Scanner(System.in);
        regex = sc.nextLine();
        System.out.println(this.ms.findByRegex(regex));

    }

    void findText() {
        String text;
        Scanner sc = new Scanner(System.in);
        text = sc.nextLine();
        System.out.println(this.ms.findText(text));
    }

    void findByAuthor() {
        String author;
        Scanner sc = new Scanner(System.in);
        author = sc.nextLine();
        System.out.println(this.ms.findByAuthor(author));

    }

    void read(String str) {
        this.ms.read(str);

    }

    void write(String str) {

        try {
            this.ms.write(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    void findByTimeRange() throws IllegalArgumentException {
        String from, to;
        Scanner sc = new Scanner(System.in);
        from = sc.nextLine();
        to = sc.nextLine();
        try {
            System.out.println(ms.findByTimeRange(from, to));
        } catch (IllegalArgumentException e) {
            System.out.println("inavlid data");
        }
    }

    public static void main(String[] args) {

        UserInterface ui = new UserInterface();
        Scanner sc = new Scanner(System.in);
        int i;
        System.out.println("Welcome to chat");

        while (true) {

            System.out.println("1 - add message | 2 - delete message | 3 - print all messages |" +
                    " 4 - find text | 5 - find by regex \n6 - find by author | 7 - write to file " +
                    "| 8 - read from file | 9 - find by time range | 0 - end program ");

            try {
                i = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid date");
                i = 1001;
            }

            switch (i) {
                case 1: {
                    ui.add();
                    System.out.println("added");
                }
                break;
                case 2: {
                    System.out.println(ui.deleteMessage() ? "deleted" : "not found");
                }
                break;
                case 3: {
                    ui.ms.ml.forEach(System.out::println);
                }
                break;
                case 4: {
                    ui.findText();
                }
                break;
                case 5: {
                    ui.findByRegex();
                }
                break;
                case 6: {
                    ui.findByAuthor();
                }
                break;
                case 7: {
                    ui.write("e:\\GitUp\\project\\test1.json");
                }
                break;
                case 8: {
                    ui.read("e:\\GitUp\\project\\test1.json");
                }
                break;
                case 9: {
                    ui.findByTimeRange();
                }
                break;
                case 1001: {
                    sc = new Scanner(System.in);
                    break;
                }
            }
            if (i == 0) {
                LogStatistic.write("e:\\GitUp\\project\\log.txt", ui.ms.logstatlist);
                break;
            }
        }

        System.out.println("end");
    }
}
