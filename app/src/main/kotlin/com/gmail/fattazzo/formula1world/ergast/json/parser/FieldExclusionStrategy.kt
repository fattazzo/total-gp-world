package com.gmail.fattazzo.formula1world.ergast.json.parser

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes

class FieldExclusionStrategy : ExclusionStrategy {

    override fun shouldSkipField(f: FieldAttributes): Boolean {
        return f.getAnnotation(ExcludeFieldFromJson::class.java) != null
    }

    override fun shouldSkipClass(clazz: Class<*>): Boolean {
        return false
    }

}