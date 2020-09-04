package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GSONCreator {
    public void writeJSON(List<MyFile> data) throws IOException {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter("./output/suffixingApp.json");
        for (MyFile currentFile: data) {
            writer.write(gson.toJson(currentFile));
        }
        writer.close();
    }
}
