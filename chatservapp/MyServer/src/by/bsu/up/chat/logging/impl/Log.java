package by.bsu.up.chat.logging.impl;

import by.bsu.up.chat.logging.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.Date;

public class Log implements Logger {

    private static final String TEMPLATE = "[%s] %s";

    private String tag;

    private Log(Class<?> cls) {
        tag = String.format(TEMPLATE, cls.getName(), "%s");
    }

    @Override
    public void info(String message) {

        Timestamp time = new Timestamp(new Date().getTime());
        try (Writer writer = new OutputStreamWriter(new FileOutputStream("servlogs.txt",true),"UTF-8")){
            writer.write(time.toString()+": "+String.format(tag,message)+"\n");
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        System.out.println(String.format(tag, message));
    }

    @Override
    public void error(String message, Throwable e) {
        Timestamp time = new Timestamp(new Date().getTime());
        try (Writer writer = new OutputStreamWriter(new FileOutputStream("servlogs.txt",true),"UTF-8")){
            writer.write(time.toString()+": "+String.format(tag,message+"\n"));
        } catch (IOException er) {
            System.out.println(er.toString());
        }
        System.err.println(String.format(tag, message));
        e.printStackTrace(System.err);
    }

    public static Log create(Class<?> cls) {
        return new Log(cls);
    }
}
