package com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats

/**
 * @author fattazzo
 *
 *
 * date: 09/10/17
 */
class StatsCircuitsData {

    var id: Int = 0
        private set
    var name: String? = null
        private set

    var count: Int = 0

    var driverId: Int = 0
    var driverName: String? = null

    var constructorId: Int = 0
    var constructorName: String? = null
    var constructorRef: String? = null

    var type: Type? = null
        private set

    val winnerName: String
        get() {
            when (type) {
                StatsCircuitsData.Type.CONSTRUCTORS_WINNER -> return constructorName.orEmpty()
                StatsCircuitsData.Type.DRIVERS_WINNER -> return driverName.orEmpty()
                else -> return ""
            }
        }

    constructor(id: Int, name: String, driverId: Int, driverName: String?, constructorId: Int, constructorName: String?, constructorRef: String?, type: Type) {
        this.id = id
        this.name = name

        this.driverId = driverId
        this.driverName = driverName

        this.constructorId = constructorId
        this.constructorName = constructorName
        this.constructorRef = constructorRef

        this.type = type
    }

    constructor(id: Int, name: String, count: Int) {
        this.id = id
        this.name = name

        this.type = Type.COUNT

        this.count = count
    }

    enum class Type {
        COUNT, DRIVERS_WINNER, CONSTRUCTORS_WINNER
    }
}
