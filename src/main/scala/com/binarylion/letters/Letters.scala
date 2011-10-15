package com.binarylion
package letters

import scala.actors.Future
import scala.actors.Futures._
import scala.collection.mutable.Map
import scala.io.Source

object Letters {
  def emptyList = Map[Char, Int]().withDefaultValue(0)

  /**
   * Converts a word into a map with characters for keys
   * and the number of times that character appears in the
   * word as values. For example, 'zoo' would produce:
   *
   * Map('z' -> 1, 'o' -> 2)
   *
   * @param word the word to convert
   * @return a map containing the count of each letter in the word
   */
  def convertWord(word:String) = {
    (emptyList /: word.toLowerCase) {
      case (a, c) => a ++ Map(c -> (a(c) + 1))
    }
  }

  /**
   * Takes a List of Map[Char, Int] and folds them into a single
   * Map[Char, Int].
   */
  def foldMaps(maps:TraversableOnce[Map[Char, Int]]) = (emptyList /: maps) {
    case (acc, map) =>
      for((key, value) <- map) acc(key) += value
      acc
  }

  def main(args:Array[String]):Unit = {
    val fs = (0 to 3) map { (idx) =>
      future {
        val h = Source.fromFile("src/main/resources/english." + idx)
        foldMaps(h.getLines.filterNot(_ contains '\'').map(convertWord(_)))
      }
    }

    val result = foldMaps(fs.map(_()))
    val totalChars = result.values.sum
    println("Total letters: " + totalChars)
    result.keys.toList.sorted foreach { (letter) => 
      val freq = (result(letter).toDouble / totalChars.toDouble) * 100
      println("%s: \t%d\t\t%.2f%%" format (letter, result(letter), freq))
    }
  }
}
