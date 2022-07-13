package galaxyraiders.helpers

import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

fun <T, U> List<T>.cartesianProduct(list: List<U>): List<Pair<T, U>> {
  return this.flatMap { lhs -> list.map { rhs -> Pair(lhs, rhs) } }
}

@JvmName("toStreamOfArgumentsObject")
fun <T> List<T>.toStreamOfArguments(): Stream<Arguments> {
  return this.map {
      t ->
    Arguments.of(t)
  }.stream()
}

@JvmName("toStreamOfArgumentsPairObjects")
fun <U, V> List<Pair<U, V>>.toStreamOfArguments(): Stream<Arguments> {
  return this.map {
      (u, v) ->
    Arguments.of(u, v)
  }.stream()
}

@JvmName("toStreamOfArgumentsTripleObjects")
fun <T, U, V> List<Triple<T, U, V>>.toStreamOfArguments(): Stream<Arguments> {
  return this.map {
      (t, u, v) ->
    Arguments.of(t, u, v)
  }.stream()
}
