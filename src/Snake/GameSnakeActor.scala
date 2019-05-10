package Snake

import Snake.Database.{createPlayer, playerExists, loadGame}
import akka.actor.{Actor, ActorRef}

class GameSnakeActor extends Actor {

//  var players: Map[String, ActorRef] = Map()
//  var towers: List[ActorRef] = List()

  val game: SnakeGame = new SnakeGame

  override def receive: Receive = {
    case message: AddPlayer =>
      if (playerExists(message.username)) {
        loadGame(message.username, game)
      } else {
        val colorPallet: List[String] = List("red", "blue", "green", "black", "orange", "purple", "grey")
        val randomInt = scala.util.Random
        val color: String = colorPallet(randomInt.nextInt(7))
        createPlayer(username = message.username, color)
        game.addPlayers(message.username, color)
      }


    case message: RemovePlayer => game.removeSnake(message.username)

    case message: MovePlayer =>
      if(game.allPlayers.contains(message.username)){
        println("The X location = " + message.x)
        println("The Y location = " + message.y)
        println("The Username   = " + message.username)
        if (message.x != 0 || message.y != 0) {
          game.allPlayers(message.username).translate = translate(message.x.toDouble, message.y.toDouble)
        }
      }


    case SendGameState => sender() ! GameState(game.gameState())

    case UpdateGame => game.update()
  }

  def translate(x: Double, y: Double): Int = {
    var translate = 0
    if (x == 1.0) {
      translate = 4
    } else if (x == -1.0) {
      translate = 2
    } else if (y == 1.0) {
      translate = 3
    } else {
      translate = 1
    }
    translate
  }

}
