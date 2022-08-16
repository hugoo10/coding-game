package fr.kahlouch.codingame.exporter.writer;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class ClipboardStringWriter implements StringWriter{
    @Override
    public void write(String string) {
        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(new StringSelection(string), null);
    }
}
