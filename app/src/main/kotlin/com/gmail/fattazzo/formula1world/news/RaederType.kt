package com.gmail.fattazzo.formula1world.news

import com.google.gson.annotations.SerializedName

/**
 * @author fattazzo
 *
 *
 * date: 20/07/17
 */
enum class RaederType {

    @SerializedName("formula1_it")
    FORMULA1_IT,
    @SerializedName("circus_f1")
    CIRCUS_F1,
    @SerializedName("formula1_com")
    FORMULA1_COM,
    @SerializedName("autosport_com")
    AUTOSPORT_COM,
    @SerializedName("planetf1_com")
    PLANETF1_COM
}
