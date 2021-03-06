/*
 * Licensed to Intel Corporation under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * Intel Corporation licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intel.analytics.bigdl.models.inception

import scopt.OptionParser

object Options {

  case class TrainParams(
    folder: String = "./",
    checkpoint: Option[String] = None,
    modelSnapshot: Option[String] = None,
    stateSnapshot: Option[String] = None,
    coreNumber: Int = -1,
    nodeNumber: Int = -1,
    classNumber: Int = 1000,
    batchSize: Int = -1,
    learningRate: Double = 0.01,
    env: String = "local",
    overWriteCheckpoint: Boolean = false,
    maxEpoch: Option[Int] = None,
    maxIteration: Int = 62000
  )

  val trainParser = new OptionParser[TrainParams]("BigDL Inception Example") {
    opt[String]('f', "folder")
      .text("url of hdfs folder store the hadoop sequence files")
      .action((x, c) => c.copy(folder = x))
    opt[String]("model")
      .text("model snapshot location")
      .action((x, c) => c.copy(modelSnapshot = Some(x)))
    opt[String]("state")
      .text("state snapshot location")
      .action((x, c) => c.copy(stateSnapshot = Some(x)))
    opt[String]("checkpoint")
      .text("where to cache the model")
      .action((x, c) => c.copy(checkpoint = Some(x)))
    opt[Int]('c', "core")
      .text("cores number on each node")
      .action((x, c) => c.copy(coreNumber = x))
      .required()
    opt[Int]('n', "nodeNumber")
      .text("nodes number to train the model")
      .action((x, c) => c.copy(nodeNumber = x))
      .required()
    opt[Int]('e', "maxEpoch")
      .text("epoch numbers")
      .action((x, c) => c.copy(maxEpoch = Some(x)))
    opt[Int]('i', "maxIteration")
      .text("iteration numbers")
      .action((x, c) => c.copy(maxIteration = x))
    opt[Double]('l', "learningRate")
      .text("inital learning rate")
      .action((x, c) => c.copy(learningRate = x))
      .required()
    opt[Int]('b', "batchSize")
      .text("batch size")
      .action((x, c) => c.copy(batchSize = x))
      .required()
    opt[Int]("classNum")
      .text("class number")
      .action((x, c) => c.copy(classNumber = x))
    opt[Unit]("overWrite")
      .text("overwrite checkpoint files")
      .action( (_, c) => c.copy(overWriteCheckpoint = true) )
    opt[String]("env")
      .text("execution environment")
      .validate(x => {
        if (Set("local", "spark").contains(x.toLowerCase)) {
          success
        } else {
          failure("env only support local|spark")
        }
      })
      .action((x, c) => c.copy(env = x.toLowerCase()))
      .required()
  }

  case class TestParams(
    folder: String = "./",
    model: String = "",
    coreNumber: Int = -1,
    nodeNumber: Int = -1,
    batchSize: Option[Int] = None,
    env: String = "local"
  )

  val testParser = new OptionParser[TestParams]("BigDL Inception Test Example") {
    opt[String]('f', "folder")
      .text("url of hdfs folder store the hadoop sequence files")
      .action((x, c) => c.copy(folder = x))
    opt[String]("model")
      .text("model snapshot location")
      .action((x, c) => c.copy(model = x))
      .required()
    opt[Int]('c', "core")
      .text("cores number on each node")
      .action((x, c) => c.copy(coreNumber = x))
      .required()
    opt[Int]('n', "nodeNumber")
      .text("nodes number to train the model")
      .action((x, c) => c.copy(nodeNumber = x))
      .required()
    opt[Int]('b', "batchSize")
      .text("batch size")
      .action((x, c) => c.copy(batchSize = Some(x)))
    opt[String]("env")
      .text("execution environment")
      .validate(x => {
        if (Set("local", "spark").contains(x.toLowerCase)) {
          success
        } else {
          failure("env only support local|spark")
        }
      })
      .action((x, c) => c.copy(env = x.toLowerCase()))
      .required()
  }
}
