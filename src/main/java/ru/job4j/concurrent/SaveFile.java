package ru.job4j.concurrent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class SaveFile {
    
    private final File file;    
    
    public SaveFile(File file) {        
        this.file = file;
    }

    public synchronized void saveContent(String content) throws IOException {
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(file))) {
            buffer.write(content);    
        }                
    }
}