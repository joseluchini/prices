package com.capitole.prices.repositories;

import com.capitole.prices.errors.ApiException;
import com.capitole.prices.repositories.entity.Price;
import java.sql.Timestamp;

public interface PricesRepository {

  Price getPrice(Integer productId, Integer brandId, Timestamp applicationDatetime) throws ApiException;

}
