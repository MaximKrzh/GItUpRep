package by.bsu.up.chat.storage;

import by.bsu.up.chat.Constants;
import by.bsu.up.chat.common.models.Message;
import by.bsu.up.chat.logging.Logger;
import by.bsu.up.chat.logging.impl.Log;
import by.bsu.up.chat.utils.MessageHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InMemoryMessageStorage implements MessageStorage {

    private static final String DEFAULT_PERSISTENCE_FILE = "messages227.srg";

    private static final Logger logger = Log.create(InMemoryMessageStorage.class);

    private List<Message> messages;//= new ArrayList<>();

    public InMemoryMessageStorage() {
        messages = new ArrayList<>();
        loadHistory();
    }

    @Override
    public synchronized List<Message> getPortion(Portion portion) {
        int from = portion.getFromIndex();
        if (from < 0) {
            throw new IllegalArgumentException(String.format("Portion from index %d can not be less then 0", from));
        }
        int to = portion.getToIndex();
        if (to != -1 && to < portion.getFromIndex()) {
            throw new IllegalArgumentException(String.format("Porting last index %d can not be less then start index %d", to, from));
        }
        to = Math.max(to, messages.size());
        return messages.subList(from, to);
    }


    @Override
    public void addMessage(Message message) {
        messages.add(message);
        updateHistory();
    }

    @Override
    public boolean updateMessage(Message message) {
        Boolean checker=false;
        messages.forEach( m -> {if(m.getId().equals(message.getId()) ) {m.setText(message.getText());m.setEdited(true);
                                updateHistory();checker.parseBoolean("true");}}
        );
        return checker;
    }

    @Override
    public synchronized boolean removeMessage(String messageId) {
        Boolean checker=false;
        messages.forEach( m -> { if(m.getId().equals(messageId)){ m.setText("deleted");
                                m.setDeleted(true); updateHistory();checker.parseBoolean("true");}});
        return checker;
    }

    @Override
    public boolean updateHistory() {

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(DEFAULT_PERSISTENCE_FILE), "UTF-8")) {
            JSONArray array = MessageHelper.getJsonArrayOfMessages(messages);
            writer.write(array.toString());
            return true;
        } catch (IOException e) {
            logger.error("Could not parse message.", e);
            return false;
        }

    }

    @Override
    public void loadHistory() {
        StringBuilder str = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(DEFAULT_PERSISTENCE_FILE))) {
            while (reader.ready()) {
                str.append(reader.readLine());
            }
        } catch (IOException e) {
            logger.error("Could not parse message.", e);
        }
        JSONArray jArr = new JSONArray();
        try {
            if (str.toString().isEmpty()==false)
            jArr = (JSONArray) new JSONParser().parse(str.toString());

        } catch (ParseException e) {
            logger.error("Parsing message failed.", e);
        }

        for (int i = 0; i < jArr.size(); i++) {
            JSONObject jsonObject = (JSONObject) jArr.get(i);
            Message m = new Message(
                    (String) jsonObject.get(Constants.Message.FIELD_ID),
                    (String) jsonObject.get(Constants.Message.FIELD_AUTHOR),
                    (String) jsonObject.get(Constants.Message.FIELD_TIMESTAMP),
                    (String) jsonObject.get(Constants.Message.FIELD_TEXT),
                    Boolean.parseBoolean(jsonObject.get(Constants.Message.FIELD_EDITED).toString()),
                    Boolean.parseBoolean(jsonObject.get(Constants.Message.FIELD_DELETED).toString())
                    );
            messages.add(m);
        }
    }

    @Override
    public int size() {
        return messages.size();
    }
}
