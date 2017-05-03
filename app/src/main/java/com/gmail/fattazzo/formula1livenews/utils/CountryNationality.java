package com.gmail.fattazzo.formula1livenews.utils;

import com.google.gson.annotations.SerializedName;

/**
 * @author fattazzo
 *         <p/>
 *         date: 19/04/17
 */
public class CountryNationality {

    @SerializedName("num_code")
    private String code;
    @SerializedName("alpha_2_code")
    private String alpha2Code;
    @SerializedName("alpha_3_code")
    private String alpha3Code;
    @SerializedName("en_short_name")
    private String enShortName;
    @SerializedName("nationality")
    private String nationality;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public String getEnShortName() {
        return enShortName;
    }

    public void setEnShortName(String enShortName) {
        this.enShortName = enShortName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
