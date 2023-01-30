package ru.job4j.concurrent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Predicate;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class ParseFile {

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String getContent(Predicate<Character> filter) throws IOException {
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            char symbol;
            while ((symbol = (char) reader.read()) != -1) {
                if (filter.test(symbol)) {
                    rsl.append(symbol);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsl.toString();
    }
}
