package com.binarylion
package letters

import scala.actors.Future
import scala.actors.Futures._
import scala.io.Source

object Letters {
  def emptyList = Map[Char, Int]().withDefaultValue(0)

  def makeLetterMap(word:String) = (emptyList /: word.toLowerCase) {
    case (acc, letter) => acc + (letter -> (acc(letter) + 1))
  }

  def foldLetterMaps(letterMaps:TraversableOnce[Map[Char, Int]]) = (emptyList /: letterMaps) {
    case (acc, letterMap) =>
      acc ++ letterMap.map{case (letter, count) => (letter -> (acc(letter) + count))}
  }

  def main(args:Array[String]):Unit = {
    val fs = (0 to 3) map { (idx) =>
      future {
        val h = Source.fromFile("src/main/resources/english." + idx)
        foldLetterMaps(h.getLines.filterNot(_ contains '\'').map(makeLetterMap(_)))
      }
    }

    val result = foldLetterMaps(fs.map(_()))
    val totalChars = result.values.sum
    println("Total letters: " + totalChars)
    result.keys.toList.sorted foreach { (letter) => 
      val freq = (result(letter).toDouble / totalChars.toDouble) * 100
      println("%s: \t%d\t\t%.2f%%" format (letter, result(letter), freq))
    }
  }
}
