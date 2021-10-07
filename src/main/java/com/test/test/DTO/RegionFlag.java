package com.test.test.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
/**
 * Used for interacting with Web app
 * */
public class RegionFlag{
    @JsonProperty(value="featureName")
    @SerializedName(value = "featureName")
    public String featureName;
    @JsonProperty(value="hasAsiaFlag")
    @SerializedName(value = "hasAsiaFlag")
    public boolean hasAsiaFlag;
    @JsonProperty(value="hasKoreaFlag")
    @SerializedName(value = "hasKoreaFlag")
    public boolean hasKoreaFlag;
    @JsonProperty(value="hasEuropeFlag")
    @SerializedName(value = "hasEuropeFlag")
    public boolean hasEuropeFlag;
    @JsonProperty(value="hasJapanFlag")
    @SerializedName(value = "hasJapanFlag")
    public boolean hasJapanFlag;
    @JsonProperty(value="hasAmericaFlag")
    @SerializedName(value = "hasAmericaFlag")
    public boolean hasAmericaFlag;
    public RegionFlag(){

    }
}