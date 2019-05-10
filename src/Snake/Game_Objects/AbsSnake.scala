package Snake.Game_Objects

object AbsSnake {
  def calculateDistance(x1: Double, y1: Double, x2: Double, y2: Double): Double = {
    Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))
  }
  var GodSpeed: Int = 9
  var translate: Int = 0

  val grid: Int = 1
  var previousKey: Int = -1

  var xSum: Int = 0
  var ySum: Int = 0

  def snakeMoving(): Unit = {


    //    if (translate == 1) {
    if (translate == 1 && previousKey != 3) {
      previousKey = translate
      xSum = 0
      ySum = GodSpeed * -grid
      //    } else if (translate == 2) {
    } else if (translate == 2 && previousKey != 4) {
      previousKey = translate
      ySum = 0
      xSum = GodSpeed * -grid
      //    } else if (translate == 3) {
    } else if (translate == 3 && previousKey != 1) {
      previousKey = translate
      xSum = 0
      ySum = GodSpeed * grid
      //    } else if (translate == 4) {
    } else if (translate == 4 && previousKey != 2) {
      previousKey = translate
      ySum = 0
      xSum = GodSpeed * grid
    }


  }
}
