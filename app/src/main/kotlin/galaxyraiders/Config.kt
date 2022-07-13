package galaxyraiders

enum class OperationMode { Text, Web }

class Config(val prefix: String) {
  inline fun <reified T : Any> get(variable: String): T {
    val env = System.getenv(prefix + variable)
      ?: throw AssertionError("Variable $variable is empty")

    return when (T::class) {
      Int::class -> env.toInt()
      Long::class -> env.toLong()
      Double::class -> env.toDouble()
      String::class -> env.toString()
      OperationMode::class -> OperationMode.valueOf(env)
      else -> throw AssertionError("Invalid conversion")
    } as T
  }
}
