package com.binarylion
package letters

import scala.actors.Futures.future
import scala.io.Source

object Letters {
  type LetterMap = Map[Char, Int]

  def emptyMap:LetterMap = Map().withDefaultValue(0)

  /**
   * Takes a word and converts it into a Map with its letters as keys
   * and the number of times each letter appears in the word as values.
   * For example, the word 'zoo' would produce the following LetterMap:
   *
   *  Map('z' -> 1, 'o' -> 2)
   *
   * @param word the word to convert into a LetterMap
   * @return a populated LetterMap for the word
   */
  def makeLetterMap(word:String) = (emptyMap /: word.toLowerCase) {
    case (acc, letter) => acc + (letter -> (acc(letter) + 1))
  }

  /**
   * Takes a list of LetterMaps and folds them into a single LetterMap.
   *
   *  List(Map('z' -> 1, 'o' -> 2), Map('j' -> 1, 'o' -> 1, 'y' -> 1))
   * 
   * would produce:
   *
   *  Map('z' -> 1, 'o' -> 3, 'j' -> 1, 'y' -> 1)
   *
   * @param letterMaps the list of LetterMaps
   * @return a single LetterMap with keys/values combined from the LetterMaps
   */
  def foldLetterMaps(letterMaps:TraversableOnce[LetterMap]) = (emptyMap /: letterMaps) {
    case (acc, letterMap) =>
      acc ++ letterMap.map{case (letter, count) => (letter -> (acc(letter) + count))}
  }

  def main(args:Array[String]):Unit = {
    val fs = (0 to 3) map { (idx) =>
      future {
        val file = Source.fromFile("src/main/resources/english." + idx)
        foldLetterMaps(file.getLines.filterNot(_ contains '\'').map(makeLetterMap(_)))
      }
    }

    val result = foldLetterMaps(fs.map(_()))
    val totalLetters = result.values.sum
    println("Total letters: %d" format totalLetters)
    result.keys.toList.sorted foreach { (letter) => 
      val freq = (result(letter).toDouble / totalLetters.toDouble) * 100
      println("%s:\t%d\t\t%.2f%%" format (letter, result(letter), freq))
    }
  }
}
