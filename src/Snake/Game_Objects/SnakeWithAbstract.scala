package Snake.Game_Objects

import Snake.Desktop.sceneGraphics
import javafx.scene.input.KeyCode
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Rectangle}

import scala.collection.mutable.ArrayBuffer


class SnakeWithAbstract {
  val ClassPlayer: AbstractSnake = new AbstractSnake(800, 600)
  //var SnakeBodyLength: ArrayBuffer[Rectangle] = ArrayBuffer(player)
  val player: Rectangle = new Rectangle() {
    width = ClassPlayer.player.sides
    height = ClassPlayer.player.sides
    fill = Color.BlueViolet
  }
  ClassPlayer.SnakeBodyLengthMutable += player

  this.player.translateX.value = ClassPlayer.player.centerX
  this.player.translateY.value = ClassPlayer.player.centerY


  def createPelletsClass(): Unit = {
    if (ClassPlayer.AllPellets.nonEmpty) {
      println(ClassPlayer.AllPellets.length)
    }
    for (x <- ClassPlayer.AllPellets) {
      val newCircle: Circle = new Circle() {
        centerX = x.centerX
        centerY = x.centerY
        radius = x.radius
        fill = Color.AliceBlue
      }
      sceneGraphics.children.add(newCircle)
      //      allPellets += newCircle
    }
  }


  def inputKey(keyCode: KeyCode): Unit = {
    keyCode.getName match {
      case "Up" => ClassPlayer.translate = 1
      case "Left" => ClassPlayer.translate = 2
      case "Down" => ClassPlayer.translate = 3
      case "Right" => ClassPlayer.translate = 4
      case "Space" => ClassPlayer.zoomTranslate += 1
      case _ => println(keyCode.getName + " pressed with no action" + player.translateX.value.toString)
    }
  }

  var tr = true
  var listOfRectangle: ArrayBuffer[Rectangle] = ArrayBuffer()

  def snakeMoving(): Unit = {
    ClassPlayer.snakeMoving()
    if (tr) {
      listOfRectangle += player
      for (x <- ClassPlayer.bodyOfPLayer) {
        val body: Rectangle = new Rectangle() {
          width = ClassPlayer.player.sides
          height = ClassPlayer.player.sides
          fill = Color.BlueViolet
        }
        body.translateX.value = x.centerX
        body.translateY.value = x.centerY
        sceneGraphics.children.add(body)
        listOfRectangle += body
      }
      tr = false
    }


    for (x <- ClassPlayer.bodyOfPLayer.indices) {
      listOfRectangle(x).translateX.value = ClassPlayer.bodyOfPLayer(x).centerX
      listOfRectangle(x).translateY.value = ClassPlayer.bodyOfPLayer(x).centerY
      println(x)
      println("x value = " + listOfRectangle(x).translateX.value)
      println("y value = " + listOfRectangle(x).translateY.value)
      println("///////////////////////////")
    }

    //    this.player.translateX.value = ClassPlayer.player.centerX
    //    this.player.translateY.value = ClassPlayer.player.centerY
  }

  sceneGraphics.children.add(player)
}