import junit.framework.Assert.assertTrue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert
import org.junit.Test

class GameOfLifeTest {

    @Test fun thatInitialLiveCellsAreAliveBeforeEvolution() {
        val expectedLiveCells = populationOf(Cell(0, 1), Cell(1, 0))
        val initialLiveCells = populationOf(Cell(0, 1), Cell(1, 0))
        val life = Life(initialLiveCells)

        assertThat(life.population, equalTo(expectedLiveCells))
    }

    @Test fun thatInitialDeadCellsAreDeadBeforeEvolution() {
        val expectedDeadCells = populationOf(Cell(1, 1), Cell(0, 0))
        val initialLiveCells = populationOf(Cell(0, 1), Cell(1, 0))
        val life = Life(initialLiveCells)

        assertTrue(life.population.intersect(expectedDeadCells).isEmpty())
    }

    @Test fun thatLiveCellWithFewerThanTwoNeighboursShouldDieAfterEvolutionDueToUnderpopulation() {
        val initialLiveCells = populationOf(Cell(0, 1), Cell(1, 1))
        val life = Life(initialLiveCells)

        val evolvedLife = life.evolved()

        assertTrue(evolvedLife.population.isEmpty())
    }

    @Test fun thatLiveCellWithTwoNeighboursShouldSurviveEvolution() {
        val initialLiveCells = populationOf(Cell(0, 0), Cell(0, 2), Cell(1, 1))
        val life = Life(initialLiveCells)

        val evolvedLife = life.evolved()

        assertThat(evolvedLife.population.contains(Cell(1,1)), equalTo(true))
    }

    @Test fun thatLiveCellWithThreeLiveNeighboursShouldSurviveEvolution() {
        val initialLiveCells = populationOf(Cell(0, 0), Cell(0, 2), Cell(1, 1), Cell(2, 0))
        val life = Life(initialLiveCells)

        val evolvedLife = life.evolved()

        assertThat(evolvedLife.population.contains(Cell(1, 1)), equalTo(true))
    }

    @Test fun thatLiveCellWithMoreThanThreeLiveNeighboursShouldDieAfterEvolution() {
        val initialLiveCells = populationOf(Cell(0, 0), Cell(0, 2), Cell(1, 1), Cell(2, 0), Cell(2, 2))
        val life = Life(initialLiveCells)

        val evolvedLife = life.evolved()

        assertThat(evolvedLife.population.contains(Cell(1,1)), equalTo(false))
    }

    @Test fun thatNewCellIsBornAtPositionWithThreeLiveNeighboursAfterEvolution() {
        val initialLiveCells = populationOf(Cell(0, 0), Cell(0, 2), Cell(2, 0))
        val expectedLiveCellsAfterEvolution = populationOf(Cell(1, 1))
        val life = Life(initialLiveCells)

        val evolvedLife = life.evolved()

        assertThat(evolvedLife.population, equalTo(expectedLiveCellsAfterEvolution))
    }

    @Test fun testBlinkerOscillator() {
        val verticalBlinker =   populationOf(Cell(1, 0), Cell(1, 1), Cell(1, 2))
        val horizontalBlinker = populationOf(Cell(0, 1), Cell(1, 1), Cell(2, 1))

        var life = Life(horizontalBlinker)

        life = life.evolved()

        Assert.assertTrue(life.population == verticalBlinker)

        life = life.evolved()

        Assert.assertTrue(life.population == horizontalBlinker)
    }
}

