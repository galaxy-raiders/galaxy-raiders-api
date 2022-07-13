package galaxyraiders.core.physics

open class Object2D(
  initialPosition: Point2D,
  initialVelocity: Vector2D,
  val radius: Double,
  val mass: Double
) {
  var center: Point2D = initialPosition
    private set

  var velocity: Vector2D = initialVelocity
    protected set

  fun move() {
    this.center += this.velocity
  }

  fun shift(force: Vector2D) {
    this.velocity += force
  }

  fun distance(p: Point2D): Double {
    return this.center.distance(p)
  }

  fun impacts(p: Point2D): Boolean {
    return this.distance(p) <= this.radius
  }

  fun distance(so: Object2D): Double {
    if (so == this) return 0.0

    val distanceBetweenCenters = this.center.distance(so.center)
    val sumRadiuses = this.radius + so.radius

    val objectsInsideEachOther = distanceBetweenCenters < sumRadiuses

    return when (objectsInsideEachOther) {
      true -> 0.0
      false -> distanceBetweenCenters - sumRadiuses
    }
  }

  fun impacts(so: Object2D): Boolean {
    if (so == this) return false
    return this.distance(so) <= 0.0
  }

  override fun toString(): String {
    return "Object2D at ${this.center}"
  }

  /**
   * Collides with another space object.
   *
   * @param target Another space object we are colliding with.
   * @param coefficientRestitution Ratio of conservation of momentum during the collision.
   *
   * @see https://arxiv.org/abs/1909.10053
   */
  fun collideWith(target: Object2D, coefficientRestitution: Double) {
    assert(
      coefficientRestitution >= 0.0 && coefficientRestitution <= 1.0,
      { "Coefficient of restitution must be in the interval [0.0, 1.0]" }
    )

    val deltaVelocity = calculateImpactVelocityVariation(target)

    val impactImpulse = calculateImpactImpulse(target, coefficientRestitution, deltaVelocity)

    val ourVelocityChange = impactImpulse / this.mass
    this.shift(ourVelocityChange)

    val theirVelocityChange = -impactImpulse / target.mass
    target.shift(theirVelocityChange)
  }

  /**
   * Calculates the variation of velocity between two objects in a
   * collision. This variations affects only the direction of impact.
   *
   * @param target Another space object we are colliding with.
   *
   * @see https://arxiv.org/abs/1909.10053
   */
  private fun calculateImpactVelocityVariation(target: Object2D): Vector2D {
    val impactVersor = this.center.impactDirection(target.center)

    val ourImpactVelocity = this.velocity.vectorProject(impactVersor)
    val theirImpactVelocity = target.velocity.vectorProject(impactVersor)

    val deltaVelocity = theirImpactVelocity - ourImpactVelocity
    return deltaVelocity
  }

  /**
   * Calculates the impulse, i.e., the variation of momentum in a
   * collision with another space object.
   *
   * @param target Another space object we are colliding with.
   * @param coefficientRestitution Ratio of conservation of momentum during the collision.
   * @param deltaVelocity Variation of velocity.
   *
   * @see https://en.wikipedia.org/wiki/Coefficient_of_restitution
   * @see https://en.wikipedia.org/wiki/Inelastic_collision#Formula
   * @see https://en.wikipedia.org/wiki/Coefficient_of_restitution#Derivation
   */
  private fun calculateImpactImpulse(
    target: Object2D,
    coefficientRestitution: Double,
    deltaVelocity: Vector2D
  ): Vector2D {
    return (
      ((this.mass * target.mass) / (this.mass + target.mass)) *
        (1 + coefficientRestitution) * deltaVelocity
      )
  }
}
