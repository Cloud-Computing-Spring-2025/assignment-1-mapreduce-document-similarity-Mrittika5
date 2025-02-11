package com.example;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

public class DocumentSimilarityMapper extends Mapper<Object, Text, Text, Text> {

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] parts = line.split(" ", 2);
        if (parts.length < 2) return;

        String documentId = parts[0];
        String content = parts[1];

        HashSet<String> words = new HashSet<>();
        StringTokenizer tokenizer = new StringTokenizer(content);
        while (tokenizer.hasMoreTokens()) {
            words.add(tokenizer.nextToken().toLowerCase());
        }

        for (String word : words) {
            context.write(new Text(documentId), new Text(word));
        }
    }
}
