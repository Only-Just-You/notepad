package org.example.notepad;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class MyFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().endsWith(".txt");
    }

    @Override
    public String getDescription() {
        return "记事本文件（*.txt）";
    }
}
