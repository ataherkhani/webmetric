package com.webmetric.alireza.dto;


import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendedResultDTO {


    @SerializedName("app_id")
    private int appId;

    @SerializedName("country_code")
    private String countryCode;

    @SerializedName("recommended_advertiser_ids")
    private String recommendedAdvertiserIds;

}
