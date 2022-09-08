package com.capitole.prices.services.impl;

import com.capitole.prices.errors.ApiException;
import com.capitole.prices.errors.CustomErrors;
import com.capitole.prices.models.Price;
import com.capitole.prices.models.Price.Currency;
import com.capitole.prices.repositories.PricesRepository;
import com.capitole.prices.services.PricesService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PricesServiceImpl implements PricesService {

  private final PricesRepository pricesRepository;

  @Autowired
  public PricesServiceImpl(PricesRepository pricesRepository) {
    this.pricesRepository = pricesRepository;
  }

  @Override
  public Price getPrice(Integer productId, Integer brandId, LocalDateTime applicationDatetime) throws ApiException {
    com.capitole.prices.repositories.entity.Price price;
    try {
      price = pricesRepository.getPrice(productId, brandId, Timestamp.from(applicationDatetime.toInstant(ZoneOffset.UTC)));
    } catch (ApiException ae) {
      throw ae;
    } catch (Exception e) {
      throw new ApiException(CustomErrors.UNEXPECTED_REPO_ERROR, e);
    }

    return Price.builder()
        .productId(price.getProductId())
        .brandId(price.getBrandId())
        .priceList(price.getPriceList())
        .price(price.getPrice())
        .currency(Currency.getEnumByCode(price.getCurrency()))
        .endDate(price.getEndDate().toLocalDateTime())
        .startDate(price.getStartDate().toLocalDateTime())
        .priority(price.getPriority())
        .build();
  }
}
