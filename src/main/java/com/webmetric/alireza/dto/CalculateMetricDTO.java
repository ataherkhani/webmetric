package com.webmetric.alireza.dto;


import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculateMetricDTO {

    @SerializedName("app_id")
    private int appId;

    @SerializedName("country_code")
    private String countryCode;

    @SerializedName("impressions")
    private int impressionCount;

    @SerializedName("clicks")
    private int clickCount;

    @SerializedName("revenue")
    private double revenueSum;
}
