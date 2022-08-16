package fr.kahlouch.codingame.exporter.writer;

public class ConsoleStringWriter implements StringWriter{
    @Override
    public void write(String string) {
        System.out.println(string);
    }
}
