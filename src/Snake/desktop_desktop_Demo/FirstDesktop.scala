package Snake.desktop_desktop_Demo

import io.socket.client.{IO, Socket}
import io.socket.emitter.Emitter
import javafx.application.Platform
import javafx.scene.input.{KeyCode, KeyEvent}
import play.api.libs.json.{JsValue, Json}
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.scene.{Group, Scene}

class HandleMessageFromPython() extends Emitter.Listener {
  override def call(objects: Object*): Unit = {
    Platform.runLater(() => {
      var message: String = objects.apply(0).toString
      var gameState: JsValue = Json.parse(message)

      var idToBody: Map[String, List[Map[String, Double]]] = Map()
      var idToColor: Map[String, String] = Map()

      var players = (gameState \ "players").as[List[Map[String, JsValue]]]
      for (initialKey <- players) {
        var nameOfPlayer = initialKey("id").as[String]
        val bodyList = initialKey("body").as[List[Map[String, Double]]]
        val colorName: String = initialKey("color").as[String]
        idToBody = idToBody + (nameOfPlayer -> bodyList)
        idToColor = idToColor + (nameOfPlayer -> colorName)
      }
      FirstSocket.registerAndMaintainPLayer(idToBody, idToColor)
    })
  }
}

object FirstSocket extends JFXApp {
  var socket: Socket = IO.socket("http://localhost:8080/")
  val messages = new HandleMessageFromPython
  socket.on("gameState", messages)
  socket.connect()
  socket.emit("register", "charles")
  var sceneGraphics: Group = new Group {}

  var allPlayers: Map[String, Rectangle] = Map()


  def returnColorOption(color: String): Color = {
    if (color == "red") {
      Color.Red
    } else if (color == "blue") {
      Color.Blue
    } else if (color == "green") {
      Color.Green
    } else if (color == "black") {
      Color.Black
    } else if (color == "orange") {
      Color.Orange
    } else if (color == "purple") {
      Color.Purple
    } else {
      Color.Grey
    }
  }

  var mapOfUserToRectangle: Map[String, Rectangle] = Map()
  var idToBodyInput_2: Map[String, List[Map[String, Double]]] = Map()

  def registerAndMaintainPLayer(idToBodyInput: Map[String, List[Map[String, Double]]], idToColorInput: Map[String, String]): Unit = {
    for ((idOfUser, list) <- idToBodyInput) {
      if (!mapOfUserToRectangle.contains(idOfUser)) {
        var xVar, yVar = 0.0
        for (elements <- list) {
          for ((x, y) <- elements) {
            if (x == "x") xVar = y
            if (x == "y") yVar = y
          }
          val player: Rectangle = new Rectangle() {
            width = 20
            height = 20
            fill = returnColorOption(idToColorInput(idOfUser))
          }
          player.translateX.value = xVar
          player.translateY.value = yVar
          println("xVar = " + xVar + "| yVar = " + yVar + "| idOfUser = " + idOfUser)
          mapOfUserToRectangle = mapOfUserToRectangle + (idOfUser -> player)
          sceneGraphics.children.add(player)
        }
      }
    }

    for ((user, rectangle) <- mapOfUserToRectangle) {
      if (idToBodyInput.contains(user)) {
        for ((idOFUser, list) <- idToBodyInput) {
          if (user == idOFUser) {
            for (element <- list) {
              for ((x, y) <- element) {
                if (x == "x") {
                  rectangle.translateX.value = y
                }
                if (x == "y") {
                  rectangle.translateY.value = y
                }
              }
            }
          }
        }
      }
      else {
        sceneGraphics.children.remove(rectangle)
        mapOfUserToRectangle = mapOfUserToRectangle - user
      }
    }
    idToBodyInput_2 = idToBodyInput
  }

  def inputKey(keyCode: KeyCode): Unit = {
    keyCode.getName match {
      case "Up" => socket.emit("keyState", "w", "charles");
      case "Left" => socket.emit("keyState", "a", "charles");
      case "Down" => socket.emit("keyState", "s", "charles");
      case "Right" => socket.emit("keyState", "d", "charles");
      case _ => println("charles" + " pressed with no action")
    }
  }


  this.stage = new PrimaryStage {
    this.title = "Snake Game!"
    scene = new Scene(1000, 800) {
      content = List(sceneGraphics)
      addEventHandler(KeyEvent.KEY_PRESSED, (event: KeyEvent) => inputKey(event.getCode))
    }
  }
}
