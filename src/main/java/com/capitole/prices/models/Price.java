package com.capitole.prices.models;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Price {

  private Integer brandId;
  private Integer productId;
  private Integer priceList;
  private Integer priority;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private Double price;
  private Currency currency;

  @AllArgsConstructor
  @Getter
  public enum Currency {
    EUR("EUR");
    private String code;

    public static Currency getEnumByCode(String code) {
      if (!Objects.equals(code, "")) {
        for (Currency curr : values()) {
          if (curr.getCode().equalsIgnoreCase(code)) {
            return curr;
          }
        }
      }
      return null;
    }

  }

}
