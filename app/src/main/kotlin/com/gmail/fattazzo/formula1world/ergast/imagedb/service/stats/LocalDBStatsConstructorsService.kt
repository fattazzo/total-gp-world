/*
 * Project: total-gp-world
 * File: LocalDBStatsConstructorsService.kt
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
 * date: 21/08/17
 */
@EBean(scope = EBean.Scope.Singleton)
open class LocalDBStatsConstructorsService {

    fun loadWins(seasonStart: Int, seasonEnd: Int): List<StatsData> {
        var wins: MutableList<StatsData> = ArrayList()

        val sql = "SELECT COUNT(res.Id) as value, constr.name as label " +
                "FROM results res " +
                "inner join constructors constr on res.constructorId = constr.Id " +
                "inner join races race on race.Id = res.raceId " +
                "where race.year >= " + seasonStart + " and race.year <= " + seasonEnd +
                " and res.position = 1 " +
                "group by constr.Id " +
                "order by COUNT(res.Id) desc "

        try {
            ActiveAndroid.getDatabase().rawQuery(sql, null).use { c ->
                wins = ArrayList()
                while (c.moveToNext()) {
                    val value = c.getInt(c.getColumnIndex("value"))
                    val label = c.getString(c.getColumnIndex("label"))
                    wins.add(StatsData(value.toFloat(), label))
                }
            }
        } catch (e: Exception) {
            wins = ArrayList()
        }

        return wins
    }

    fun loadWinsNationality(seasonStart: Int, seasonEnd: Int): List<StatsData> {
        var wins: MutableList<StatsData> = ArrayList()

        val sql = "SELECT COUNT(res.Id) as value, constr.nationality as label " +
                "FROM results res " +
                "inner join constructors constr on res.constructorId = constr.Id " +
                "inner join races race on race.Id = res.raceId " +
                "where race.year >= " + seasonStart + " and race.year <= " + seasonEnd +
                " and res.position = 1 " +
                "group by constr.nationality " +
                "order by COUNT(res.Id) desc "

        try {
            ActiveAndroid.getDatabase().rawQuery(sql, null).use { c ->
                wins = ArrayList()
                while (c.moveToNext()) {
                    val value = c.getInt(c.getColumnIndex("value"))
                    val label = c.getString(c.getColumnIndex("label"))
                    wins.add(StatsData(value.toFloat(), label))
                }
            }
        } catch (e: Exception) {
            wins = ArrayList()
        }

        return wins
    }

    fun loadPodiums(seasonStart: Int, seasonEnd: Int): List<StatsData> {
        var podiums: MutableList<StatsData> = ArrayList()

        val sql = "SELECT constr.name as label, " +
                "COUNT(res.Id) as value, " +
                "sum(case when res.position = 1 then 1 else 0 end) as value2, " +
                "sum(case when res.position = 2 then 1 else 0 end) as value3, " +
                "sum(case when res.position = 3 then 1 else 0 end) as value4 " +
                "FROM results res " +
                "inner join constructors constr on res.constructorId = constr.Id " +
                "inner join races race on race.Id = res.raceId " +
                "where race.year >= " + seasonStart + " and race.year <= " + seasonEnd +
                " and res.position <= 3 " +
                "group by constr.Id " +
                "order by COUNT(res.Id) desc, sum(case when res.position = 1 then 1 else 0 end) desc," +
                "       sum(case when res.position = 2 then 1 else 0 end) desc," +
                "       sum(case when res.position = 3 then 1 else 0 end) desc "

        try {
            ActiveAndroid.getDatabase().rawQuery(sql, null).use { c ->
                podiums = ArrayList()
                while (c.moveToNext()) {
                    val label = c.getString(c.getColumnIndex("label"))
                    val value = c.getInt(c.getColumnIndex("value"))
                    val value2 = c.getInt(c.getColumnIndex("value2"))
                    val value3 = c.getInt(c.getColumnIndex("value3"))
                    val value4 = c.getInt(c.getColumnIndex("value4"))

                    podiums.add(StatsData(value.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), label))
                }
            }
        } catch (e: Exception) {
            podiums = ArrayList()
        }

        return podiums
    }

    fun loadNumbers(seasonStart: Int, seasonEnd: Int): List<StatsData> {
        var podiums: MutableList<StatsData> = ArrayList()

        val sql = "select count(constructor) as value,\"year\" as label " +
                "from(select distinct constructorId as constructor,\"year\" as \"year\" from driversConstructors " +
                "     where \"year\" >= ? and \"year\" <= ?) " +
                "group by \"year\" " +
                "order by \"year\" asc"

        try {
            ActiveAndroid.getDatabase().rawQuery(sql, arrayOf(seasonStart.toString(), seasonEnd.toString())).use { c ->
                podiums = ArrayList()
                while (c.moveToNext()) {
                    val label = c.getString(c.getColumnIndex("label"))
                    val value = c.getInt(c.getColumnIndex("value"))

                    podiums.add(StatsData(value.toFloat(), label))
                }
            }
        } catch (e: Exception) {
            podiums = ArrayList()
        }

        return podiums
    }
}
