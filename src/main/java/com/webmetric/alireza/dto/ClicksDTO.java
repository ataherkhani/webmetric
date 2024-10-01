package com.webmetric.alireza.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClicksDTO {

    @SerializedName("impression_id")
    private String impressionId;
    private double revenue;
}
