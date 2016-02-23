import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageStorage {

    private List<Message> ml = new ArrayList<>();
    private List<LogStatistic> logstatlist = new ArrayList<>();
    private FileWriter file;


    List<LogStatistic> getLogstatlist (){
        return this.logstatlist;
    }

    List<Message> getMessageList (){
        return this.ml;
    }


    void write(String filename) throws ParseException {
        try {

            this.file = new FileWriter(filename);
            JSONObject obj = new JSONObject();
            JSONArray list = new JSONArray();
            for (Message m : ml) {
                list.add(m);
            }
            obj.put("messages", list);
            file.write(list.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            logstatlist.add(new LogStatistic(e.toString(), 1));
            e.printStackTrace();
        }
    }

    void read(String filename) {

        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader(filename));
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray msg = (JSONArray) jsonObject.get("messages");
            for (Object jo : msg) {
                JSONObject o = (JSONObject) jo;
                ml.add(Message.jsonToMessage(o));
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            logstatlist.add(new LogStatistic("File not found", 1));
        } catch (IOException | ParseException e) {
            System.out.println("Invalid name");
            logstatlist.add(new LogStatistic("Invalid file name", 1));
        }

    }

    void addMessage(Message m) {
        ml.add(m);
        logstatlist.add(new LogStatistic("add ", ml.size()));
    }

    void deleteMessage(Message m) {
        ml.remove(m);
    }

    boolean deleteMessage(Integer id) {
        int counter = 0;
        Iterator<Message> iterator = ml.iterator();

        while (iterator.hasNext()) {

            Message mp = iterator.next();

            if (mp.getId().equals(id)) {

                iterator.remove();
                counter++;
                logstatlist.add(new LogStatistic("deleted ", counter));
                return true;
            }
        }
        logstatlist.add(new LogStatistic("deleted ", counter));
        return false;
    }

    ArrayList<Message> findByAuthor(String author) {

        ArrayList<Message> alm = new ArrayList<>();

        for (Message message : ml) {
            if (message.getAuthor().compareTo(author) == 0)
                alm.add(message);
        }

        logstatlist.add(new LogStatistic("foundByAuthor", alm.size()));
        return alm;
    }

    ArrayList<Message> findText(String text) {
        ArrayList<Message> alm = new ArrayList<>();
        String[] strarr;
        for (Message message : ml) {
            strarr = message.getMessageText().split("[^0-9a-zA-Zа-яА-Я]+");
            for (String str : strarr) {
                if (str.compareTo(text) == 0)
                    alm.add(message);
            }
        }
        logstatlist.add(new LogStatistic("foundByText " + text, alm.size()));
        return alm;
    }

    ArrayList<Message> findByRegex(String regex) {
        ArrayList<Message> alm = new ArrayList<>();
        try {

            Pattern p = Pattern.compile(regex);
            Matcher m;
            for (Message message : ml) {
                m = p.matcher(message.getMessageText());
                if (m.find())
                    alm.add(message);
            }
            logstatlist.add(new LogStatistic("foundByRegex " + regex, alm.size()));

            return alm;
        } catch (IllegalArgumentException e) {

            logstatlist.add(new LogStatistic("Error. Illegal argument ex ", 1));
        }
        return null;
    }

    ArrayList<Message> findByTimeRange(String from, String to) {
        ArrayList<Message> alm = new ArrayList<>();
        Timestamp f = Timestamp.valueOf(from);
        Timestamp t = Timestamp.valueOf(to);

        for (Message m : ml) {
            if (m.getTimeStamp().compareTo(f) >= 0 && m.getTimeStamp().compareTo(t) <= 0) {
                alm.add(m);
            }
        }
        logstatlist.add(new LogStatistic("foundByTimeRange from " +
                from + " to " + to, alm.size()));

        return alm;
    }

}
