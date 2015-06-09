package org.gianni.ml_example.ui

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.gianni.ml_example.log4j.Logger
import org.apache.spark.mllib.linalg._
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors

object ConsoleDriver extends Logger {
  def main(args: Array[String]) = {

    val conf = new SparkConf().setAppName("MDA Matrix").setMaster("local[4]").set("spark.ui.port", "4444")
    val sc = new SparkContext(conf)
    
    // org.apache.log4j.Logger.getRootLogger.setLevel(org.apache.log4j.Level.WARN)
    // debug("testing the logging feature...")
    
    // quick KMeans based on IRIS
    val raw = sc.textFile("data/iris/iris.data").map(line => line.split(",")).filter(row => row.size == 5)
    val data = raw.map(row => Vectors.dense(row.dropRight(1).map(_.toDouble))).cache
    val labels = raw.map(_(4))
    
    val model = KMeans.train(data, 3, 50, 5)
    val clusters = model.predict(data)
    
    val result = raw zip clusters map { case (a, c) => a :+ c mkString(",") }
    result.saveAsTextFile("file:/Users/mattg/Projects/Spark/ml_example/tmp/out")
    
    println("Configuration\n------------------------")
    println(Utilities.dumpScalaContext(sc))
  }
}