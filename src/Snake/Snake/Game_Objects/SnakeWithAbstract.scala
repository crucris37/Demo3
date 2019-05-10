package Snake.Snake.Game_Objects

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


//    for (x <- ClassPlayer.SnakeBodyLengthMutable.indices) {
//      if (ClassPlayer.SnakeBodyLength(x).centerX != ClassPlayer.SnakeBodyLengthMutable(x).translateX.value && ClassPlayer.SnakeBodyLength(x).centerY != ClassPlayer.SnakeBodyLengthMutable(x).translateY.value) {
//        ClassPlayer.SnakeBodyLengthMutable -= ClassPlayer.SnakeBodyLengthMutable(x)
//      }
//    }
//
//    for (x <- ClassPlayer.SnakeBodyLengthMutable.indices) {
//      if (ClassPlayer.AllPellets(x).centerX != allPellets(x).translateX.value && ClassPlayer.AllPellets(x).centerY != allPellets(x).translateY.value) {
//      }
//    }


//def snakeMoving(): Unit = {
//    def update(): Unit = {
//      for (x <- ClassPlayer.SnakeBodyLengthMutable.length - 1 until 0 by -1) {
//        ClassPlayer.SnakeBodyLengthMutable(x).translateX.value = ClassPlayer.SnakeBodyLengthMutable(x - 1).translateX.value
//        ClassPlayer.SnakeBodyLengthMutable(x).translateY.value = ClassPlayer.SnakeBodyLengthMutable(x - 1).translateY.value
//      }
//      for (x <- ClassPlayer.SnakeBodyLengthMutable.length - 1 until 0 by -1) {
//        ClassPlayer.SnakeBodyLength(x).centerX = ClassPlayer.SnakeBodyLengthMutable(x).translateX.value
//        ClassPlayer.SnakeBodyLength(x).centerY = ClassPlayer.SnakeBodyLengthMutable(x).translateY.value
//      }
//    }
//
//    def DIED(): Unit = {
//      for (x <- ClassPlayer.SnakeBodyLengthMutable.reverse) {
//        if (ClassPlayer.SnakeBodyLengthMutable.length != 1) {
//          sceneGraphics.children.remove(x)
//        }
//      }
//      for(x <- ClassPlayer.SnakeBodyLength.reverse.indices){
//        if(x > 1){
//          ClassPlayer.SnakeBodyLength -= ClassPlayer.SnakeBodyLength(x)
//        }
//      }
//    }
//    //SnakeBodyLength = ArrayBuffer(player)
//
//  if (ClassPlayer.translate == 1 && ClassPlayer.previousKey != 3) {
//    ClassPlayer.previousKey = ClassPlayer.translate
//    ClassPlayer.ySum = ClassPlayer.GodSpeed * -ClassPlayer.grid
//    ClassPlayer.xSum = 0
//  } else if (ClassPlayer.translate == 2 && ClassPlayer.previousKey != 4) {
//    ClassPlayer.previousKey = ClassPlayer.translate
//    ClassPlayer.xSum = ClassPlayer.GodSpeed * -ClassPlayer.grid
//    ClassPlayer.ySum = 0
//  } else if (ClassPlayer.translate == 3 && ClassPlayer.previousKey != 1) {
//    ClassPlayer.previousKey = ClassPlayer.translate
//    ClassPlayer.ySum = ClassPlayer.GodSpeed * ClassPlayer.grid
//    ClassPlayer.xSum = 0
//  } else if (ClassPlayer.translate == 4 && ClassPlayer.previousKey != 2) {
//    ClassPlayer.previousKey = ClassPlayer.translate
//    ClassPlayer.xSum = ClassPlayer.GodSpeed * ClassPlayer.grid
//    ClassPlayer.ySum = 0
//  }
//  if (ClassPlayer.zoomTranslate % 2 == 0) {
//    ClassPlayer.GodSpeed = 2
//  } else if (ClassPlayer.zoomTranslate % 2 != 0) {
//    ClassPlayer.GodSpeed = 3
//  }
//
//  //    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  //    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  if (ClassPlayer.SnakeBodyLengthMutable.length != ClassPlayer.score) {
//    val body: Rectangle = new Rectangle() {
//      width = ClassPlayer.player.sides
//      height = ClassPlayer.player.sides
//      fill = Color.BlueViolet
//    }
//    body.translateX.value = -ClassPlayer.Size
//    body.translateY.value = -ClassPlayer.Size
//    ClassPlayer.SnakeBodyLength += new Body(body.translateX.value, body.translateY.value)
//    ClassPlayer.SnakeBodyLengthMutable += body
//    sceneGraphics.children.add(body)
//    println("Length of the Class Player Snake Length  " + ClassPlayer.SnakeBodyLength.length)
//    println("Class Player Zoom Translate              " + ClassPlayer.zoomTranslate)
//  }
//
//
//  if (player.translateX.value > windowWidth - ClassPlayer.Size) {
//    ClassPlayer.player.centerX = windowWidth - ClassPlayer.Size
//    //player.translateX.value = windowWidth - ClassPlayer.Size
//    ClassPlayer.translate = 0
//    DIED()
//  } else if (player.translateY.value > windowHeight - ClassPlayer.Size) {
//    ClassPlayer.player.centerY = windowHeight - ClassPlayer.Size
//    //player.translateY.value = windowHeight - ClassPlayer.Size
//    ClassPlayer.translate = 0
//    DIED()
//  } else if (0 > player.translateX.value) {
//    ClassPlayer.player.centerX = 0
//    //player.translateX.value = 0
//    ClassPlayer.translate = 0
//    DIED()
//  } else if (0 > player.translateY.value) {
//    ClassPlayer.player.centerY = 0
//    //player.translateY.value = 0
//    ClassPlayer.translate = 0
//    DIED()
//  }
//  ClassPlayer.player.centerX += ClassPlayer.xSum
//  ClassPlayer.player.centerY += ClassPlayer.ySum
//  update()
//  //removePellets()
//}

//  def removePellets(): Unit = {
//    ClassPlayer.removePellets()
//    for (x <- allPellets) {
//      var executed: Boolean = true
//      for (pellet <- ClassPlayer.AllPellets) {
//        if (pellet.centerX == x.translateX.value && pellet.centerY == x.translateY.value) {
//          executed = false
//        }
//      }
//      if (executed) {
//        sceneGraphics.children.remove(x)
//        createPelletsClass()
//        x.centerY.value = -100
//        ClassPlayer.score += 1
//      }
//    }
//  }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    translateX.value = ClassPlayer.player.centerX
//    translateY.value = ClassPlayer.player.centerY