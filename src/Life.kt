
class Life(val population: Population) {
    fun evolved() = Life(population.survivors and population.newBirths)
}

// Population typealias and extensions
typealias Population = Set<Cell>

fun populationOf(vararg cells: Cell) : Population = setOf(*cells)

private infix fun Population.and(population: Population) : Population = this.union(population)

private val Population.survivors : Population
        get() = this.filter { it shouldSurviveEvolutionOfPopulation this }.toSet()

private val Population.newBirths: Population
    get() = this.flatMap { this deadNeighboursOf it }.filter { (this livingNeighboursOf it).size == 3 }.toSet()

private infix fun Population.livingNeighboursOf(cell: Cell) : Population
        = this.filter { it isNeighbourOf cell }.toSet()

private infix fun Cell.isNeighbourOf(cell:Cell)
        = cell.neighbours.contains(this)



private infix fun Population.deadNeighboursOf(cell: Cell) : Population
        = (cell.neighbours).filter { !this.contains(it) }.toSet()

// Cell extensions

private infix fun Cell.shouldSurviveEvolutionOfPopulation(population: Population)
        = this hasTwoOrThreeNeighboursInPopulation population

private infix fun Cell.hasTwoOrThreeNeighboursInPopulation(population: Population)
        = (population livingNeighboursOf this).size in 2..3

private val Cell.neighbours : Population
    get() =  setOf( this.above.left, this.above, this.above.right,
                    this.left,                   this.right,
                    this.below.left, this.below, this.below.right)

private val Cell.above  get() = Cell(this.x,    this.y-1)
private val Cell.below  get() = Cell(this.x,    this.y+1)
private val Cell.left   get() = Cell(this.x-1,  this.y)
private val Cell.right  get() = Cell(this.x+1,  this.y)
