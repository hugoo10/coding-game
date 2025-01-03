package fr.kahlouch.coding_game.exporter_plugin.writer;

public class ConsoleStringWriter implements StringWriter{
    @Override
    public void write(String string) {
        System.out.println(string);
    }
}
