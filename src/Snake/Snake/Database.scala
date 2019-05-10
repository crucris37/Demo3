package Snake.Snake

import java.sql.{Connection, DriverManager, ResultSet}

object Database {
  val url = "jdbc:mysql://localhost/mysql?serverTimezone=UTC"
  val username = "root"
  val password = "secret123"
  var connection: Connection = DriverManager.getConnection(url, username, password)

  setupTable()

  def setupTable(): Unit = {
    val statement = connection.createStatement()
    statement.execute("CREATE TABLE IF NOT EXISTS players (username TEXT, color TEXT)")
  }

  def playerExists(username: String): Boolean = {
    val statement = connection.prepareStatement("SELECT * FROM players WHERE username=?")
    statement.setString(1, username)
    val result: ResultSet = statement.executeQuery()
    result.next()
  }

  def createPlayer(username: String, color: String): Unit = {
    val statement = connection.prepareStatement("INSERT INTO players VALUE (?, ?)")
    statement.setString(1, username)
    statement.setString(2, color)
    statement.execute()
  }

  def loadGame(username: String, game: SnakeGame): Unit = {
    val statement = connection.prepareStatement("SELECT * FROM players WHERE username=?")
    statement.setString(1, username)
    val result: ResultSet = statement.executeQuery()
    result.next()
    game.addPlayers(result.getString("username"), result.getString("color"))
  }
}