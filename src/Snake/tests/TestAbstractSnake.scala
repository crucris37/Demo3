package Snake.tests

import Snake.Game_Objects.AbsSnake
import org.scalatest.FunSuite

class TestAbstractSnake extends FunSuite {
  test("This tests the distance between the snakes") {
    val x1: Double = 30.0
    val x2: Double = 40.0
    val x3: Double = 35.0
    val x4: Double = 55.0

    assert(AbsSnake.calculateDistance(x1, x2, x3, x4) == 15.811388300841896)

    val distancex1: Double = 5.0
    val distancey1: Double = 15.0
    val distancex2: Double = 7.0
    val distancey2: Double = 35.0

    assert(AbsSnake.calculateDistance(distancex1, distancey1, distancex2, distancey2) == 20.09975124224178)

    assert(AbsSnake.calculateDistance(10.0, 6.0, 20.0, 16.0) == 14.142135623730951)

    assert(AbsSnake.calculateDistance(5.0, 5.0, 5.0, 5.0) == 0)

    assert(AbsSnake.calculateDistance(17.0, 25.0, 8.0, 13.0) == 15)


  }
}
