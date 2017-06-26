package me.tony.java8.scala

import org.junit.Test

class ScalaLambda {
  def method(param: String): String = {
    param
  }

  val function: (String) => String = (param: String) => param
}

object ScalaLambda {

  @Test
  def showFunction: Unit = {
    val ins = new ScalaLambda
//    println(classOf[ins.function])
  }

}