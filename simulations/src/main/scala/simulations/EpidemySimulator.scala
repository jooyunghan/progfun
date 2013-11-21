package simulations

import math.random
import scala.util.Random

class EpidemySimulator extends Simulator {

  def randomBelow(i: Int) = (random * i).toInt

  protected[simulations] object SimConfig {
    val population: Int = 300
    val roomRows: Int = 8
    val roomColumns: Int = 8
    val prevalenceRate: Double = 0.01
    // to complete: additional parameters of simulation
  }

  import SimConfig._

  val persons: List[Person] = (1 to population).toList map { new Person(_) }

  trait Mobility {
    def apply(p: Person): Int
  }
  object NormalMobility extends Mobility {
    def apply(p: Person) = 5
  }
  object ReduceMobility extends Mobility {
    def apply(p: Person) =
      if (p.visiblyInfectious) 20 else 10
  }
  val mobility: Mobility = NormalMobility

  {
    // initial move
    persons.foreach(_.willMove)

    // initial infection
    Random.shuffle(persons).take((population * prevalenceRate).toInt).foreach(_.infect)
  }

  case class Direction(dr: Int, dc: Int)
  val directions = Vector(Direction(1, 0), Direction(-1, 0), Direction(0, 1), Direction(0, -1))
  def chooseAny[T](elements: Seq[T]): T = {
    elements(randomBelow(elements.size))
  }

  case class Room(row: Int, col: Int) {
    def neighbors = {
      directions map (this + _)
    }
    def +(d: Direction) = {
      Room((row + d.dr + roomRows) % roomRows,
        (col + d.dc + roomColumns) % roomColumns)
    }
    def clean() = {
      peopleHere.filter(_.visiblyInfectious).isEmpty
    }
    def hasInfectedPeople() = {
      peopleHere.filter(_.infected).isEmpty.unary_!
    }
    def peopleHere() = {
      persons.filter(_.room == this)
    }
  }

  class Person(val id: Int) {
    var infected = false
    var sick = false
    var immune = false
    var dead = false
    var hasVaccine = false

    // demonstrates random number generation
    var row: Int = randomBelow(roomRows)
    var col: Int = randomBelow(roomColumns)

    //
    // to complete with simulation logic
    //
    def willMove() {
      afterDelay(randomBelow(mobility(this)) + 1) { move }
    }
    def move() {
      if (!dead) {
        val rooms = cleanRooms()
        if (!rooms.isEmpty) {
          moveTo(chooseAny(rooms))
        }
        willMove()
      }
    }
    def moveTo(room: Room) {
      //println(s"($row, $col) => (${room.row}, ${room.col})")
      row = room.row
      col = room.col
      probablyInfected()
    }
    def probablyInfected() {
      if (room.hasInfectedPeople && randomBelow(10) < 4) {
        infect()
      }
    }
    def infect() {
      if (!infected && !immune && !hasVaccine) {
        infected = true
        afterDelay(6) { becomeSick }
        afterDelay(14) { probablyDie }
        afterDelay(16) { becomeImmune }
        afterDelay(18) { becomeHealthy }
      }
    }
    def becomeSick() {
      sick = true
    }
    def probablyDie() {
      if (randomBelow(4) == 0) {
        dead = true
      }
    }
    def becomeImmune() {
      if (!dead) {
        immune = true
        sick = false
      }
    }
    def becomeHealthy() {
      if (!dead) {
        immune = false
        sick = false
        infected = false
      }
    }
    // no side effect
    def cleanRooms() = room.neighbors.filter(_.clean)
    def room = Room(row, col)
    def visiblyInfectious: Boolean = dead || sick
  }

}
