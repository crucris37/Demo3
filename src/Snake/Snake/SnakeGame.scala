package Snake.Snake

import Snake.Game_Objects.{AbstractSnake, Pellet}
import play.api.libs.json.{JsValue, Json}
import scalafx.scene.Group

import scala.collection.mutable.ArrayBuffer


class SnakeGame {
  val windowWidth: Int = 1000
  val windowHeight: Int = 800

  val allPellets: ArrayBuffer[Pellet] = ArrayBuffer()
  var allPlayers: Map[String, AbstractSnake] = Map()
  var sceneGraphics: Group = new Group {}
  var lastUpdateTime: Long = System.nanoTime()

  def addPlayers(userName: String, color: String): Unit = {
    val snake = new AbstractSnake(windowWidth, windowHeight)
    snake.color = color
    allPlayers += userName -> snake
  }

  def removeSnake(userName: String): Unit = {
    allPlayers -= userName
  }

  def gameState(): String = {
    val gameState: Map[String, JsValue] = Map(
      "players" -> Json.toJson(this.allPlayers.map({ case (k, v) => Json.toJson(Map(
        "id" -> Json.toJson(k),
        "x" -> Json.toJson(v.player.centerX),
        "y" -> Json.toJson(v.player.centerY),
        "v_x" -> Json.toJson(v.xSum),
        "v_y" -> Json.toJson(v.ySum),
        "color" -> Json.toJson(v.color),
        "body" -> Json.toJson(v.bodyOfPLayer.map({ case z => Json.toJson(Map(
          "x" -> z.centerX,
          "y" -> z.centerY
        ))
        }
        ))
      ))
      })),
    )
//    println(gameState)
    Json.stringify(Json.toJson(gameState))
  }

  def update(): Unit = {
    // Moves the snake accordingly
    for ((k, v) <- allPlayers) {
      v.snakeMoving()
    }
    for ((outerKey, outerPlayer) <- allPlayers) {
      for ((innerKey, innerPlayer) <- allPlayers) {
        if (innerKey != outerKey) {
          for (bodyParts <- innerPlayer.bodyOfPLayer) {
            if (outerPlayer.distanceFormula(bodyParts.centerX, bodyParts.centerY) < outerPlayer.Size) {
              val randomInt = scala.util.Random
              val randomInt2 = scala.util.Random
              val width = randomInt.nextInt(windowWidth)
              val height = randomInt2.nextInt(windowHeight)
              outerPlayer.player.centerX = width
              outerPlayer.player.centerY = height
            }
          }
        }
      }
    }
  }
}
