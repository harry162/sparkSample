package com.intuit.sparkApp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Queue;
import org.apache.ivy.Main;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.*;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import com.intuit.object.Customer;
import com.intuit.object.Sales;
import scala.Tuple2;
import org.apache.spark.streaming.api.java.JavaDStream;

public class SparkApp {

  private static transient JavaSparkContext spark;

  public static void main(String[] args) {


    // configure spark
    SparkConf sparkConf = new SparkConf().setAppName("Read Text to RDD").setMaster("local[2]")
        .set("spark.executor.memory", "2g");
    // start a spark context
    // spark = new JavaSparkContext(sparkConf);
    try (JavaStreamingContext spark = new JavaStreamingContext(sparkConf, new Duration(2000))) {

      JavaRDD<Customer> cRDD = spark.textFileStream("Resources/Customer.txt")
          .map(new Function<String, Customer>() {
            public Customer call(String line) throws Exception {
              String[] parts = line.split("#");
              Customer c = new Customer();
              c.setCustomerId(parts[0].trim());
              c.setCustomerName(parts[1].trim());
              c.setAddress(parts[2].trim());
              return c;
            }
          });


      Queue<JavaRDD<Customer>> rddQueue = new LinkedList<>();
      rddQueue.add(cRDD);

      JavaDStream<Customer> records = jsc.queueStream(rddQueue);

      records.foreachRDD(rdd -> System.out.println(rdd.count()));
      jsc.start();
      jsc.awaitTermination();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    JavaRDD<Sales> sRDD = spark.textFile("Resources/Sales.txt").map(new Function<String, Sales>() {
      public Sales call(String line) throws Exception {
        String[] parts = line.split("#");
        Sales c = new Sales();
        c.setTimeStamp(parts[0].trim());
        c.setCustomerId(parts[1].trim());
        c.setSalesPrice(parts[2].trim());
        return c;
      }
    });



  }
}
