package com.gmail.fattazzo.formula1world.ergast.json.parser;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class FieldExclusionStrategy implements ExclusionStrategy {
 
   public boolean shouldSkipField(FieldAttributes f) {
      return f.getAnnotation(ExcludeFieldFromJson.class) != null;
   }
 
   public boolean shouldSkipClass(Class<?> clazz) {
      return false;
   }
 
}