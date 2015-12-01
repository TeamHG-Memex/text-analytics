/**
  * To run this by submitting to Spark cluster, you need to
  * 
  * 1) Start Spark server: ~/spark/sbin/start-all.sh
  * 2) Build the code: sbt package
  * 3) Submit: ./my-submit.sh
  * 
  * To run this class directly from IntelliJ, prepare the following configuration
  *
  * Main class: ta.MyWord2vec
  * Program arguments: local[*] blobs/text8
  */
package ta

import org.apache.spark._
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}

object MyWord2vec {
  def main(args: Array[String]) {
    if (args.length < 1) {
      println("Need file(s) to learn from")
      System.exit(1)
    }
    doTraining(args(0), args(1))
  }

  def doTraining(masterUrl: String, trainingFile: String): Unit = {

    val conf = new SparkConf().setAppName("Training word2vec on " + trainingFile).setMaster(masterUrl)
    val sc = new SparkContext(conf)

    val input = sc.textFile(trainingFile).map(line => line.split(" ").toSeq)

    val word2vec = new Word2Vec()




    val model = word2vec.fit(input)

    val synonyms = model.findSynonyms("china", 40)

    for ((synonym, cosineSimilarity) <- synonyms) {
      println(s"$synonym $cosineSimilarity")
    }

    // Save and load model
    model.save(sc, "blobs/myModelPath")
    val sameModel = Word2VecModel.load(sc, "blobs/myModelPath")
    val synonymsFromLoad = sameModel.findSynonyms("china", 40)
    println("These synonyms are from the saved model, so should be the same --- ")
    for ((synonym, cosineSimilarity) <- synonyms) {
      println(s"$synonym $cosineSimilarity")
    }
  }
}

