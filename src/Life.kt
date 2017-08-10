typealias Population = Set<Cell>
fun populationOf(vararg cells: Cell) : Population = setOf(*cells)

class Life(val population: Population) {
    fun evolved(): Life {
        println("Pop: " + population)
        return Life(population.survivors.union(population.newBirths))
    }
}

private val Population.survivors : Population
        get() = this.filter { it shouldSurviveEvolutionOfPopulation this }.toSet()

private infix fun Cell.shouldSurviveEvolutionOfPopulation(population: Population)
        = this hasTwoOrThreeNeighboursInPopulation population

private infix fun Cell.hasTwoOrThreeNeighboursInPopulation(population: Population)
        = (population livingNeighboursOf this).size in 2..3

private infix fun Population.livingNeighboursOf(cell: Cell) : Population
        = this.filter { it isNeighbourOf cell }.toSet()

private infix fun Cell.isNeighbourOf(cell:Cell)
        = this != cell && this isInNeighbouringRangeOf cell

private infix fun Cell.isInNeighbouringRangeOf(cell: Cell)
        = this isInHorizontalNeighbouringRangeOf cell && this isInVerticalNeighbouringRangeOf cell

private infix fun Cell.isInHorizontalNeighbouringRangeOf(cell: Cell)
        = this.x - cell.x in -1..1

private infix fun Cell.isInVerticalNeighbouringRangeOf(cell: Cell)
        = this.y - cell.y in -1..1

private val Population.newBirths: Population
    get() {
        return this.flatMap { this deadNeighboursOf it }.filter {
            val livingNeighbours = (this livingNeighboursOf it)
            println("Cell: " + it)
            println("LivingNeighbours: " + livingNeighbours)
            (livingNeighbours.size == 3)
        }.toSet()
    }

private infix fun Population.deadNeighboursOf(cell: Cell) : Population {
    val deadNeighbours = (cell.neighbours).filter { !this.contains(it) }.toSet()
    println("Cell: " + cell)
    println("DNs: " + deadNeighbours)
    return deadNeighbours
}

private val Cell.neighbours : Population
    get() =  setOf( Cell(this.x-1, this.y-1), Cell(this.x-1, this.y), Cell(this.x-1, this.y+1),
                    Cell(this.x, this.y-1)                          , Cell(this.x, this.y+1),
                    Cell(this.x+1, this.y-1), Cell(this.x+1, this.y), Cell(this.x+1, this.y+1))