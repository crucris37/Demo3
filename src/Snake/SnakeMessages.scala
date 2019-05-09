package Snake

case object SendGameState
case class GameState(gameState: String)

case object UpdateGame
case class AddPlayer(username: String)
case class RemovePlayer(username: String)
case class MovePlayer(username: String, x: Double, y:Double)
case class StopPlayer(username: String)