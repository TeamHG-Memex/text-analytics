#!/usr/bin/env bash
~/spark/bin/spark-submit \
  --master spark://mark-XPS-8700:7077 \
  --class ta.MyWord2vec \
  --executor-memory 8G \
   target/scala-2.10/text-analytics_2.10-1.0.jar blobs/text8
