package com.webmetric.alireza.dto;


import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendationResultDTO {

    @SerializedName("impression_id")
    private String impressionId;

    @SerializedName("app_id")
    private int appId;

    @SerializedName("country_code")
    private String countryCode;

    @SerializedName("advertiser_id")
    private int advertiserId;

    @SerializedName("revenue")
    private double revenue;
}
