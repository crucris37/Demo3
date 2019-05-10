package Snake.Game_Objects

import scalafx.scene.shape.Rectangle
import scala.collection.mutable.{ArrayBuffer, Map}


class AbstractSnake(val windowWidth: Int, val windowHeight: Int) {

  var color: String = "red"
  var GodSpeed: Int = 9
  var zoomTranslate: Int = 0
  var translate: Int = 0
  val grid: Int = 1
  var executed: Boolean = true

  var Size: Int = 20

  var previousKey: Int = -1

  var xSum: Int = 0
  var ySum: Int = 0

  val player = new Body((Math.random() * windowWidth).toInt + Size, (Math.random() * windowHeight).toInt + Size)

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  var bodyOfPLayer: ArrayBuffer[Body] = ArrayBuffer(player)
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  val AllPellets: ArrayBuffer[Pellet] = ArrayBuffer()

  def distanceFormula(x: Double, y: Double): Double = {
    Math.sqrt(Math.pow(player.centerX - x, 2) + Math.pow(player.centerY - y, 2))
  }

  var SnakeBodyLength: ArrayBuffer[Body] = ArrayBuffer()

  var SnakeBodyLengthMutable: ArrayBuffer[Rectangle] = ArrayBuffer()


  def calculateDistance(x1: Double, y1: Double, x2: Double, y2: Double): Double = {
    Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))
  }


  def snakeMoving(): Unit = {


//    if (translate == 1) {
    if (translate == 1 && previousKey != 3) {
      previousKey = translate
      xSum = 0
      ySum = GodSpeed * -grid
//    } else if (translate == 2) {
    } else if (translate == 2 && previousKey != 4) {
      previousKey = translate
      ySum = 0
      xSum = GodSpeed * -grid
//    } else if (translate == 3) {
    } else if (translate == 3 && previousKey != 1) {
      previousKey = translate
      xSum = 0
      ySum = GodSpeed * grid
//    } else if (translate == 4) {
    } else if (translate == 4 && previousKey != 2) {
      previousKey = translate
      ySum = 0
      xSum = GodSpeed * grid
    }

    if (player.centerX > windowWidth - Size) {
      translate = 0
      player.centerX = windowWidth - Size
    } else if (player.centerY > windowHeight - Size) {
      translate = 0
      player.centerY = windowHeight - Size
    } else if (0 > player.centerX) {
      translate = 0
      player.centerX = 0
    } else if (0 > player.centerY) {
      translate = 0
      player.centerY = 0
    }
    player.centerX += xSum
    player.centerY += ySum

  }
}
