package com.capitole.prices.repositories.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Price {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Integer id;
  @Column(name = "brand_id")
  private Integer brandId;
  @Column(name = "product_id")
  private Integer productId;
  @Column(name = "start_date")
  private Timestamp startDate;
  @Column(name = "end_date")
  private Timestamp endDate;
  @Column(name = "price_list")
  private Integer priceList;
  @Column(name = "priority")
  private Integer priority;
  @Column(name = "price")
  private Double price;
  @Column(name = "currency")
  private String currency;

}

