package com.webmetric.alireza.entity;




import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "clicks")
@Getter
@Setter
public class Click {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "impressionId")
    private String impressionId;

    @Column(name = "revenue")
    private double revenue;
}
