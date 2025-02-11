package com.example;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.*;

public class DocumentSimilarityReducer extends Reducer<Text, Text, Text, Text> {

    private final Map<String, Set<String>> documentWordMap = new HashMap<>();

    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        HashSet<String> words = new HashSet<>();
        for (Text word : values) {
            words.add(word.toString());
        }
        documentWordMap.put(key.toString(), words);
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        List<String> documents = new ArrayList<>(documentWordMap.keySet());

        for (int i = 0; i < documents.size(); i++) {
            for (int j = i + 1; j < documents.size(); j++) {
                String doc1 = documents.get(i);
                String doc2 = documents.get(j);

                Set<String> words1 = documentWordMap.get(doc1);
                Set<String> words2 = documentWordMap.get(doc2);

                Set<String> intersection = new HashSet<>(words1);
                intersection.retainAll(words2);

                Set<String> union = new HashSet<>(words1);
                union.addAll(words2);

                double similarity = (double) intersection.size() / union.size();
                String result = String.format("Similarity: %.4f", similarity);
                context.write(new Text(doc1 + "," + doc2), new Text(result));
            }
        }
    }
}
