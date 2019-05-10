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

class HandleMessageFromPython2() extends Emitter.Listener {
  override def call(objects: Object*): Unit = {
    Platform.runLater(() => {
      var message: String = objects.apply(0).toString
      var gameState: JsValue = Json.parse(message)

      var idToBody: Map[String, List[Map[String, Double]]] = Map()
      var idToColor: Map[String, String] = Map()

      var players = (gameState \ "players").as[List[Map[String, JsValue]]]
      for (initialKey <- players) {
        var nameOfPlayer = initialKey("id").as[String]
        //      println(nameOfPlayer)
        val bodyList = initialKey("body").as[List[Map[String, Double]]]
        //      println(bodyList)
        val colorName: String = initialKey("color").as[String]
        idToBody = idToBody + (nameOfPlayer -> bodyList)
        idToColor = idToColor + (nameOfPlayer -> colorName)
        //    println(idToBody)
        //    println(idToColor)
      }
      SecondDesktop.registerAndMaintainPLayer(idToBody, idToColor)
    })
  }
}

object SecondDesktop extends JFXApp {
  var socket2: Socket = IO.socket("http://localhost:8080/")
  val messages = new HandleMessageFromPython2
  socket2.on("gameState", messages)
  socket2.connect()
  socket2.emit("register", "ricky")
  var sceneGraphics: Group = new Group {}

  var allPlayers: Map[String, Rectangle] = Map()


  def returnColorOption(color: String): Color = {
    //    List("red", "blue", "green", "black", "orange", "purple", "grey")
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

  //  var listOfRectangle: List[Rectangle] = List()
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
      case "Up" => socket2.emit("keyState", "w", "ricky");
      case "Left" => socket2.emit("keyState", "a", "ricky");
      case "Down" => socket2.emit("keyState", "s", "ricky");
      case "Right" => socket2.emit("keyState", "d", "ricky");
      case _ => println("charles" + " pressed with no action")
    }
  }


  this.stage = new PrimaryStage {
    this.title = "Snake Game!"
    //    val abstractSnake = new SnakeWithAbstract()
    scene = new Scene(1000, 800) {
      content = List(sceneGraphics)
      addEventHandler(KeyEvent.KEY_PRESSED, (event: KeyEvent) => inputKey(event.getCode))
    }
  }
}