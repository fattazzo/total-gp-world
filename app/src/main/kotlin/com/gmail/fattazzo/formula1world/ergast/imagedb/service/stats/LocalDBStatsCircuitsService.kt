/*
 * Project: total-gp-world
 * File: LocalDBStatsCircuitsService.kt
 *
 * Created by fattazzo
 * Copyright © 2018 Gianluca Fattarsi. All rights reserved.
 *
 * MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats

import com.activeandroid.ActiveAndroid
import org.androidannotations.annotations.EBean
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 10/10/17
 */
@EBean(scope = EBean.Scope.Singleton)
open class LocalDBStatsCircuitsService {

    fun loadCount(seasonStart: Int, seasonEnd: Int): List<StatsCircuitsData> {
        var counts: MutableList<StatsCircuitsData> = ArrayList()

        val sql = "select cir.Id," +
                "   cir.name, " +
                "   count(race.Id) " +
                "from races race inner join circuits cir on race.circuitId = cir.Id " +
                "where \"year\" >= ? and \"year\" <= ?) " +
                "group by race.circuitId " +
                "order by cir.country,cir.location"

        try {
            ActiveAndroid.getDatabase().rawQuery(sql, arrayOf(seasonStart.toString(), seasonEnd.toString())).use { c ->
                counts = ArrayList()
                while (c.moveToNext()) {
                    val id = c.getInt(0)
                    val name = c.getString(1)
                    val count = c.getInt(2)

                    counts.add(StatsCircuitsData(id, name, count))
                }
            }
        } catch (e: Exception) {
            counts = ArrayList()
        }

        return counts
    }

    fun loadWinner(season: Int, type: StatsCircuitsData.Type): List<StatsCircuitsData> {
        var circuits: MutableList<StatsCircuitsData>  = ArrayList()

        val sql = "select cir.Id, " +
                "   cir.name, " +
                "   dr.Id, " +
                "   dr.forename || ' ' || dr.surname, " +
                "   cs.Id, " +
                "   cs.name, " +
                "   cs.constructorRef " +
                "from races race inner join circuits cir on race.circuitId = cir.Id  " +
                "                left join results res on res.raceId = race.Id and res.\"position\" = 1 " +
                "                left join drivers dr on dr.Id = res.driverId " +
                "                left join constructors cs on cs.Id = res.constructorId " +
                "where \"year\" = ? " +
                "group by race.circuitId " +
                "order by race.round"

        try {
            ActiveAndroid.getDatabase().rawQuery(sql, arrayOf(season.toString())).use { c ->
                circuits = ArrayList()
                while (c.moveToNext()) {
                    val id = c.getInt(0)
                    val name = c.getString(1)
                    val driverId = c.getInt(2)
                    val driverName = c.getString(3)
                    val constructorId = c.getInt(4)
                    val constructorName = c.getString(5)
                    val constructorRef = c.getString(6)

                    circuits.add(StatsCircuitsData(id, name, driverId, driverName, constructorId, constructorName, constructorRef, type))
                }
            }
        } catch (e: Exception) {
            circuits = ArrayList()
        }

        return circuits
    }
}
