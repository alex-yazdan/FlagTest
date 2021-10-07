package com.test.test.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Used for calling Flag microService
 *
 * @author : Alex
 * */
public class FlagRawInfo{

    @JsonProperty(value="name")
    @SerializedName(value = "name")
    public String name;

    @JsonProperty(value="value")
    @SerializedName(value = "value")
    public int value;

    @Override
    public String toString() {
        return "FlagRawInfo{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
