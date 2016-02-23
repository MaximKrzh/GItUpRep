import org.json.simple.parser.ParseException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class UserInterface {

    private MessageStorage ms ;

    public UserInterface (){
        this.ms = new MessageStorage();
    }

    MessageStorage getMessageStorage (){
        return this.ms;
    }

    //Scanner sc;
    //public UserInterface (){
    //    this.ms = new MessageStorage();
    //    this.sc = new Scanner(System.in);
    //}

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
            this.ms.getLogstatlist().add(new LogStatistic("Error. Message length ", 1));
        }
    }

    boolean deleteMessage() {
        Integer id;
        Scanner sc = new Scanner(System.in);
        try {
            id = sc.nextInt();
            return (this.ms.deleteMessage(id));
        } catch (InputMismatchException e) {
            this.ms.getLogstatlist().add(new LogStatistic("Error. InputMismatchException ", 1));
            System.out.println("inavlid data");
        }
        return false;
    }

    void findByRegex() {
        String regex;
        Scanner sc = new Scanner(System.in);
        regex = sc.nextLine();
        ArrayList<Message> aml = this.ms.findByRegex(regex);
        //sc.close();
        System.out.println(aml!=null ? aml : "Error. Wrong regex");

    }

    void findText() {
        String text;
        Scanner sc = new Scanner(System.in);
        text = sc.nextLine();
        //sc.close();
        System.out.println(this.ms.findText(text));
    }

    void findByAuthor() {
        String author;
        Scanner sc = new Scanner(System.in);
        author = sc.nextLine();
       // sc.close();
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

    void findByTimeRange()  {
        String from, to;
        Scanner sc = new Scanner(System.in);
        from = sc.nextLine();
        to = sc.nextLine();
        //sc.close();
        try {
            System.out.println(ms.findByTimeRange(from, to));
        } catch (IllegalArgumentException e) {
            System.out.println("inavlid data");
        }
    }

    public void run() {

        UserInterface ui = new UserInterface();
        Scanner sc = new Scanner(System.in);
        int i;
        System.out.println("Welcome to chat");

        while (true) {

            System.out.println(" 1 - add message | 2 - delete message | 3 - print all messages |" +
                    " 4 - find text | 5 - find by regex \n 6 - find by author | 7 - write to file " +
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
                    ui.getMessageStorage().getMessageList().forEach(System.out::println);

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
                    ui.write("test.json");
                }
                break;
                case 8: {
                    ui.read("test.json");
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
                LogStatistic.write("log.txt", ui.getMessageStorage().getLogstatlist());
                sc.close();
                break;
            }
        }
        System.out.println("end");
    }
}
