package com.capitole.prices.services;

import com.capitole.prices.errors.ApiException;
import com.capitole.prices.models.Price;
import java.time.LocalDateTime;

public interface PricesService {

  Price getPrice(Integer productId, Integer brandId, LocalDateTime applicationDatetime) throws ApiException;
}
