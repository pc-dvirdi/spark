/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
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

package org.apache.spark.scheduler

import org.apache.spark.executor.ExecutorExitCode

/**
 * Represents an explanation for a executor or whole slave failing or exiting.
 */
private[spark]
class ExecutorLossReason(val message: String) extends Serializable {
  override def toString: String = message
}

private[spark]
case class ExecutorExited(exitCode: Int, isNormalExit: Boolean, reason: String)
  extends ExecutorLossReason(reason)

private[spark] object ExecutorExited {
  def apply(exitCode: Int, isNormalExit: Boolean): ExecutorExited = {
    ExecutorExited(exitCode, isNormalExit, ExecutorExitCode.explainExitCode(exitCode))
  }
}

private[spark]
case class SlaveLost(_message: String = "Slave lost")
  extends ExecutorLossReason(_message)
