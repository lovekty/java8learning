package me.tony.java8.scala

import me.tony.java8.Base
import me.tony.java8.Base._
import org.junit.Test

class ScalaTest extends Base {
  def method(param: String): String = {
    param
  }

  val function: (String) => String = (param: String) => param

  @Test
  def showFunction(): Unit = {
    LOGGER.info("{}", function)
  }

  @Test
  def collectionApi(): Unit = {
    val myList = List("hello", "world", "my", "name", "is", "tony")
    myList.filter(str => str.length >= 5).foreach(str => LOGGER.info(str))
    val str = "Scala is a scalable language and it has a lot fun"
    str.split(" ").map(str => str.toUpperCase).foreach(str => LOGGER.info(str))
    val myIntList = List(1, 9, 8, 9, 0, 6, 1, 8)
    val result = myIntList.reduce((a, b) => a * 10 + b)
    LOGGER.info("{}", result)
  }
}

