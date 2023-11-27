package org.example.util;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class SparkUtil {

    public static JavaSparkContext getSparkContext() {
        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName("N-gram parser");
        sparkConf.setMaster("local");
        return new JavaSparkContext(sparkConf);
    }
}
