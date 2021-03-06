import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class LogStatistic {
    private String request;
    private Integer mesCounter;
    static FileWriter writer;

    LogStatistic(String request, Integer mesCounter) {
        this.request = request;
        this.mesCounter = mesCounter;


    }

    public static void write(String txt, List<LogStatistic> lstl) {
        try {
            writer = new FileWriter(txt, true);
            LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Europe/Minsk"));
            Timestamp timeStamp = Timestamp.valueOf(ldt);
            writer.append(" Chat log time : ").append(timeStamp.toString()).append("\n");
            for (LogStatistic ls : lstl) {
                writer.append(ls.toString()).append("\n");
            }
            writer.append("---------------------------------------------------\n");
            writer.flush(); // при работе с буфпот выталкивает
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String toString() {
        return this.request + " " + this.mesCounter;
    }

}
