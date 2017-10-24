package com.gmail.fattazzo.formula1world.ergast.json.objects

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class Season(season: String, private val url: String) {
    private val season: Int

    init {
        this.season = Integer.valueOf(season)!!
    }

    override fun toString(): String {
        return "Season{" +
                "season=" + season +
                ", url='" + url + '\'' +
                '}'
    }
}
