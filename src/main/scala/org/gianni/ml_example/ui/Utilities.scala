/**
 *
 */
package org.gianni.ml_example.ui

import org.apache.spark.SparkContext

object Utilities {
  
  private def NL: String = "\n";
  private def IDENT: String = "\t";
  
  def dumpScalaContext(sc: SparkContext): String = {
    "applicationId: " + sc.applicationId + NL +
    "appName: " + sc.appName + NL +
    "defaultMinPartitions: " + sc.defaultMinPartitions  + NL +
    "defaultParallelism: " + sc.defaultParallelism + NL +
    "files: {" + sc.files.mkString(",") + "}" + NL +
    "getConf\n---------\n" + sc.getConf.getAll.map({ case (k, v) =>  k + ": " + v }).mkString(NL) +
    "sparkUser: " + sc.sparkUser + NL +
    "startTime: " + sc.startTime + NL +
    "jars: {" + sc.jars.mkString(",") + "}" + NL
  }
}