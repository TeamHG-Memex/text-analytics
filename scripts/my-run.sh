#!/usr/bin/env bash
# This script will run the shell with enough memory to execute the my-word2vec.scala.script

~/spark/bin/spark-shell --executor-memory 8G --master spark://mark-XPS-8700:7077 
