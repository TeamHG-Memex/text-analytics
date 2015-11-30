package ta

import org.apache.spark._
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}

object MyWord2vec {
  def main(args: Array[String]) {
    if (args.length < 1) {
      println("need file(s) to learn from")
      System.exit(1)
    }
    doTraining(args(0))
  }

  def doTraining(trainingFile: String): Unit = {

    val conf = new SparkConf().setAppName("Training word2vec on " + trainingFile)
    val sc = new SparkContext(conf)

    val input = sc.textFile(trainingFile).map(line => line.split(" ").toSeq)

    val word2vec = new Word2Vec()




    val model = word2vec.fit(input)

    val synonyms = model.findSynonyms("china", 40)

    for ((synonym, cosineSimilarity) <- synonyms) {
      println(s"$synonym $cosineSimilarity")
    }

    // Save and load model
    model.save(sc, "myModelPath")
    val sameModel = Word2VecModel.load(sc, "myModelPath")
    val synonymsFromLoad = sameModel.findSynonyms("china", 40)
    println("These synonyms are from the saved model, so should be the same --- ")
    for ((synonym, cosineSimilarity) <- synonyms) {
      println(s"$synonym $cosineSimilarity")
    }
  }
}

