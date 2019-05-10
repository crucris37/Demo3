package Snake.Snake

import java.net.InetSocketAddress
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.io.{IO, Tcp}
import akka.util.ByteString
import play.api.libs.json.{JsValue, Json}

class TCPSocketServer(gameActor: ActorRef) extends Actor {

  import Tcp._
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 8000))

  var webServers: Set[ActorRef] = Set()
  var buffer: String = ""
  val delimiter: String = "~"

  override def receive: Receive = {

    case b: Bound => println("Listening on port = " + b.localAddress)

    case c: Connected =>
      println("Client Connected: " + c.remoteAddress)
      this.webServers = this.webServers + sender()
      sender() ! Register(self)


    case PeerClosed =>
      println("Client Disconnected: " + sender())
      this.webServers = this.webServers - sender()


    case r: Received =>
      buffer += r.data.utf8String
      println(buffer)
      while (buffer.contains(delimiter)) {
        val curr = buffer.substring(0, buffer.indexOf(delimiter))
        buffer = buffer.substring(buffer.indexOf(delimiter) + 1)
        handleMessageFromWebServer(curr)
        println(curr)
      }

    case SendGameState => gameActor ! SendGameState

    case gs: GameState => this.webServers.foreach((client: ActorRef) => client ! Write(ByteString(gs.gameState + delimiter)))
  }

  def handleMessageFromWebServer(messageString: String): Unit = {
    val message: JsValue = Json.parse(messageString)
    val username  = (message \ "username").as[String]
    val messageType = (message \ "action").as[String]

    messageType match {
      case "connected"    => gameActor ! AddPlayer(username)
      case "disconnected" => gameActor ! RemovePlayer(username)
      case "move" =>
        println(message)
        val x = (message \ "x").as[Double]
        val y = (message \ "y").as[Double]
        println("X inside TCP SocketServer = " + x)
        println("Y inside TCP SocketServer = " + y)
        gameActor ! MovePlayer(username, x, y)
    }
  }
}


object TCPSocketServer {

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem()

    import actorSystem.dispatcher
    import scala.concurrent.duration._

    val gameActor = actorSystem.actorOf(Props(classOf[GameSnakeActor]))
    val server = actorSystem.actorOf(Props(classOf[TCPSocketServer], gameActor))
    //                                 delay           refresh time
    actorSystem.scheduler.schedule(16.millisecond, 300.millisecond, gameActor, UpdateGame)
    actorSystem.scheduler.schedule(32.millisecond, 300.millisecond, server, SendGameState)
    //    actorSystem.scheduler.schedule(16.millisecond, 32.millisecond, server, SendGameState)
  }
}