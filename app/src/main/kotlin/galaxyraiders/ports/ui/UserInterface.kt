package galaxyraiders.ports.ui

interface UserInterface {
  fun build(): Pair<Controller, Visualizer>

  fun start()
}
