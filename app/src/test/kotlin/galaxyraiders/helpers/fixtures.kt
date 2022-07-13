package galaxyraiders.helpers

import galaxyraiders.core.physics.Point2D
import galaxyraiders.core.physics.Vector2D

fun listPairsXAndY(): List<Pair<Double, Double>> {
  return listOf(
    Pair(0.0, 0.0), // Origin
    Pair(1.0, 2.0), // 1st quadrant
    Pair(-1.0, 2.0), // 2nd quadrant
    Pair(-1.0, -2.0), // 3rd quadrant
    Pair(1.0, -2.0), // 4th quadrant
  )
}

fun listPoints(): List<Point2D> {
  return listPairsXAndY().map {
      (x, y) ->
    Point2D(x, y)
  }
}

fun listPairPoints(): List<Pair<Point2D, Point2D>> {
  return listPoints().cartesianProduct(listPoints())
}

fun listPairsDxAndDy(): List<Pair<Double, Double>> {
  return listOf(
    // Pythagorean triples
    // https://en.wikipedia.org/wiki/Pythagorean_triple
    Pair(3.0, 4.0), // 1st quadrant
    Pair(-5.0, 12.0), // 2nd quadrant
    Pair(-8.0, -15.0), // 3rd quadrant
    Pair(7.0, -24.0), // 4th quadrant
  )
}

fun listVectors(): List<Vector2D> {
  return listPairsDxAndDy().map {
      (dx, dy) ->
    Vector2D(dx, dy)
  }
}
