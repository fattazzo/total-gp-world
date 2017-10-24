package com.gmail.fattazzo.formula1world.ergast.json.parser

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class ExcludeFieldFromJson