package com.test.test.Tools;

import com.test.test.DTO.FlagRawInfo;
import com.test.test.DTO.RegionFlag;
/**
 * Tool for converting from microservice API to web API format
 * */
public class FlagConverter{
    public static RegionFlag ConvertFromRawToRegionFlag(FlagRawInfo flagRawInfo){
        RegionFlag regionFlag=new RegionFlag();
        regionFlag.featureName=flagRawInfo.name;
        regionFlag.hasAsiaFlag=(flagRawInfo.value&1) ==1;
        regionFlag.hasKoreaFlag=(flagRawInfo.value&2)==2;
        regionFlag.hasEuropeFlag=(flagRawInfo.value&4)==4;
        regionFlag.hasJapanFlag=(flagRawInfo.value&8)==8;
        regionFlag.hasAmericaFlag=(flagRawInfo.value&16)==16;
        return regionFlag;
    }
    public static FlagRawInfo ConvertFromRegionFlagToRaw(RegionFlag regionFlag){
        FlagRawInfo flagRawInfo=new FlagRawInfo();
        flagRawInfo.name=regionFlag.featureName;
        if(regionFlag.hasAsiaFlag)flagRawInfo.value+=1;
        if(regionFlag.hasKoreaFlag)flagRawInfo.value+=2;
        if(regionFlag.hasEuropeFlag)flagRawInfo.value+=4;
        if(regionFlag.hasJapanFlag)flagRawInfo.value+=8;
        if(regionFlag.hasAmericaFlag)flagRawInfo.value+=16;
        return flagRawInfo;
    }
}
