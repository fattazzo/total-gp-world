package com.gmail.fattazzo.formula1world.ergast.json.objects

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class FinishingStatus(private val statusId: Int, private val count: Int, private val status: String) {

    override fun toString(): String {
        return "FinishingStatus{" +
                "statusId=" + statusId +
                ", count=" + count +
                ", status='" + status + '\'' +
                '}'
    }
}
