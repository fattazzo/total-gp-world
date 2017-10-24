package com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats

/**
 * @author fattazzo
 *
 *
 * date: 07/09/17
 */
class StatsSeasonData internal constructor(val season: Int, val racesCompleted: Int, val rounds: Int, val drivers: Int, val constructors: Int, val winningDrivers: Int,
                                           val winningConstructos: Int, val podiumDrivers: Int, val podiumConstructors: Int, val winningDriverPoints: Float, val pointsAssigned: Float)
