package Snake.Snake.Game_Objects

//import demo.snake._
import Snake.Desktop._
import javafx.scene.input.KeyCode
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Rectangle}

import scala.collection.mutable.ArrayBuffer

class Snake() {
  //val list: List[Any] = List(Color.Violet, Color.BlueViolet, Color.Blue, Color.AliceBlue, Color.Orange, Color.Yellow)


  var GodSpeed: Int = 2
  var zoomTranslate: Int = 0
  var translate: Int = 0
  val grid: Int = 2
  var Size: Double = 20
  var score: Int = 1
  var previousKey: Int = -1
  var xSum: Int = 0
  var ySum: Int = 0
  val pelletRadius: Double = 10


  val player: Rectangle = new Rectangle() {
    width = Size
    height = Size
    fill = Color.BlueViolet
  }


  def createPelletS(width: Double, height: Double): Unit = {
    for (x <- 1 to 10) {
      val newCircle: Circle = new Circle() {
        centerX = Math.random() * windowWidth
        centerY = Math.random() * windowHeight
        radius = pelletRadius
        fill = Color.AliceBlue
      }
      sceneGraphics.children.add(newCircle)
      allPellets += newCircle
    }
  }

  var SnakeBodyLength: ArrayBuffer[Rectangle] = ArrayBuffer(player)

  // Get the value for intersection START
  var radiusOfPlayer: Double = Size / 2
  var centerX: Double = player.translateX.value + radiusOfPlayer
  var centerY: Double = player.translateY.value + radiusOfPlayer

  def calculateDistance(x1: Double, y1: Double, x2: Double, y2: Double): Double = {
    Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))
  }

  this.player.translateX.value = Math.random() * (windowWidth - Size)
  this.player.translateY.value = Math.random() * (windowHeight - Size)


  def removePellets(): Unit = {
    for (pellet <- allPellets) {
      centerX = player.translateX.value + radiusOfPlayer
      centerY = player.translateY.value + radiusOfPlayer
      val distance: Double = calculateDistance(centerX, centerY, pellet.centerX.value, pellet.centerY.value)
      val radiusSquared = radiusOfPlayer + pellet.radius.value
      if (radiusSquared > distance) {
        sceneGraphics.children.remove(pellet)
        createPelletsClass(windowWidth, windowHeight)
        pellet.centerY.value = -100
        score += 1
      }
    }
  }


  def createPelletsClass(width: Double, height: Double): Unit = {
    val newCircle: Circle = new Circle() {
      centerX = Math.random() * windowWidth
      centerY = Math.random() * windowHeight
      radius = pelletRadius
      fill = Color.Violet
    }
    sceneGraphics.children.add(newCircle)
    allPellets += newCircle
  }


  def snakeMoving(): Unit = {

    def update(): Unit = {
      for (x <- SnakeBodyLength.length - 1 until 0 by -1) {
        if (x != 0) {
          SnakeBodyLength(x).translateX.value = SnakeBodyLength(x - 1).translateX.value
          SnakeBodyLength(x).translateY.value = SnakeBodyLength(x - 1).translateY.value
        }
      }
    }

    def DIED(): Unit = {
      for (bodyPart <- SnakeBodyLength.reverse) {
        if (SnakeBodyLength.length != 2) {
          sceneGraphics.children.remove(bodyPart)
          score -= 1
          SnakeBodyLength -= bodyPart
        }
      }
      //SnakeBodyLength = ArrayBuffer(player)
    }

    if (translate == 1 && previousKey != 3) {
      previousKey = translate
      ySum = GodSpeed * -grid
      xSum = 0
    } else if (translate == 2 && previousKey != 4) {
      previousKey = translate
      xSum = GodSpeed * -grid
      ySum = 0
    } else if (translate == 3 && previousKey != 1) {
      previousKey = translate
      ySum = GodSpeed * grid
      xSum = 0
    } else if (translate == 4 && previousKey != 2) {
      previousKey = translate
      xSum = GodSpeed * grid
      ySum = 0
    }
    if (zoomTranslate % 2 == 0) {
      GodSpeed = 2
    } else if (zoomTranslate % 2 != 0) {
      GodSpeed = 3
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    if (SnakeBodyLength.length != score) {
      val body: Rectangle = new Rectangle() {
        width = Size
        height = Size
        fill = Color.BlueViolet
      }
      body.translateX.value = -radiusOfPlayer * 2
      body.translateY.value = -radiusOfPlayer * 2
      SnakeBodyLength += body
      sceneGraphics.children.add(body)
      println(SnakeBodyLength.length)
      println(zoomTranslate)
    }


    if (player.translateX.value > windowWidth - Size) {
      player.translateX.value = windowWidth - Size
      translate = 0
      DIED()
    } else if (player.translateY.value > windowHeight - Size) {
      player.translateY.value = windowHeight - Size
      translate = 0
      DIED()
    } else if (0 > player.translateX.value) {
      player.translateX.value = 0
      translate = 0
      DIED()
    } else if (0 > player.translateY.value) {
      player.translateY.value = 0
      translate = 0
      DIED()
    }
    player.translateX.value += xSum
    player.translateY.value += ySum
    update()
    removePellets()
  }

  def inputKey(keyCode: KeyCode): Unit = {
    keyCode.getName match {
      case "Up" => translate = 1
      case "Left" => translate = 2
      case "Down" => translate = 3
      case "Right" => translate = 4
      case "Space" => zoomTranslate += 1
      case _ => println(keyCode.getName + " pressed with no action" + player.translateX.value.toString)
    }
  }

  sceneGraphics.children.add(player)
}