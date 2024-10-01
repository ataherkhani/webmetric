package com.webmetric.alireza.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "impressions")
@Getter
@Setter
public class Impression {

      @Id
      @Column(name = "id")
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private String id;

      @Column(name = "impressionId")
      private String impressionId;

      @Column(name = "appId")
      private int appId;

      @Column(name = "countryCode")
      private String countryCode;

      @Column(name = "advertiserId")
      private int advertiserId;

}
