package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.example.model.CommitItem;
import org.example.model.DataItem;
import org.example.util.SparkUtil;
import org.example.util.WordUtil;
import scala.Tuple2;

import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main implements Serializable {
    public static void main(String[] args) {

        String currentPath = Paths.get("").toAbsolutePath().toString();

        ObjectMapper mapper = new ObjectMapper();
        String filePath = currentPath + "/10K.github.jsonl";

        JavaSparkContext context = SparkUtil.getSparkContext();

         context.textFile(filePath)
                .map(s -> mapper.readValue(s, DataItem.class))
                .map(DataItem::getPayload)
                .map(p -> ObjectUtils.firstNonNull(p.getCommits(), new ArrayList<CommitItem>()))
                .flatMap(List::iterator)
                .mapToPair(commit -> new Tuple2<>(commit.getAuthor().getName(), WordUtil.getWords(commit.getMessage()))) // implement Comparable for Author
                .reduceByKey((acc, commitMessage) -> { acc.addAll(commitMessage); return acc;}) // replace by aggregate?
                .map(pair -> new Tuple2<>(pair._1, nGram(pair._2, 3)))
                //.sortBy()
                .map(pair -> String.format("%s,%s\n", pair._1, String.join(",", pair._2)))
                .saveAsTextFile(currentPath + "/output.txt");
    }

    public static List<String> nGram(List<String> content, int n) {
        List<String> result = new ArrayList<>();
        for (int offset = 0; offset + n < content.size(); offset++) {
            for (int j = 0; j < n; j++) {
                result.add(content.get(j + offset));
            }
        }
        return result;
    }
}