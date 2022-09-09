package com.capitole.prices.repositories.impl;

import com.capitole.prices.errors.ApiException;
import com.capitole.prices.errors.CustomErrors;
import com.capitole.prices.repositories.PricesRepository;
import com.capitole.prices.repositories.entity.Price;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryRepository implements PricesRepository {

  @PersistenceContext
  private EntityManager entityManager;

  public Price getPrice(Integer productId, Integer brandId, Timestamp applicationDatetime) throws ApiException {
    List<Price> resultPrices = entityManager.createQuery("SELECT p FROM Price p WHERE p.productId = :productId AND "
                + "p.brandId = :brandId AND p.startDate <= :startDate AND p.endDate >= :endDate ORDER BY p.priority DESC",
            Price.class)
        .setParameter("productId", productId)
        .setParameter("brandId", brandId)
        .setParameter("startDate", applicationDatetime)
        .setParameter("endDate", applicationDatetime)
        .setMaxResults(1).getResultList();

    if (resultPrices.isEmpty()) {
      throw new ApiException(CustomErrors.PRICE_NOT_FOUND, null);
    }

    return resultPrices.get(0);

  }
}
