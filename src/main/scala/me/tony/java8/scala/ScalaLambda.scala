package me.tony.java8.scala

import org.junit.Test

class ScalaLambda {
  def method(param: String): String = {
    param
  }

  val function: (String) => String = (param: String) => param

  @Test
  def showFunction(): Unit = {
    println(this.function)
  }
}

