typealias Population = Set<Cell>
fun populationOf(vararg cells: Cell) : Population = setOf(*cells)

class Life(val population: Population) {
    fun evolved() = Life(population.survivors.union(population.newBirths))
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
        = cell.neighbours.contains(this)

private val Population.newBirths: Population
        get() = this.flatMap { this deadNeighboursOf it }.filter { (this livingNeighboursOf it).size == 3 }.toSet()

private infix fun Population.deadNeighboursOf(cell: Cell) : Population
        = (cell.neighbours).filter { !this.contains(it) }.toSet()

private val Cell.neighbours : Population
    get() =  setOf( Cell(this.x-1, this.y-1), Cell(this.x-1, this.y), Cell(this.x-1, this.y+1),
                    Cell(this.x, this.y-1)                          , Cell(this.x, this.y+1),
                    Cell(this.x+1, this.y-1), Cell(this.x+1, this.y), Cell(this.x+1, this.y+1))
