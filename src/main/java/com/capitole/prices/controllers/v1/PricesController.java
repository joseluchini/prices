package com.capitole.prices.controllers.v1;

import com.capitole.prices.errors.ApiException;
import com.capitole.prices.models.Price;
import com.capitole.prices.services.PricesService;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/prices")
public class PricesController {

  private final PricesService pricesService;

  @Autowired
  public PricesController(PricesService pricesService) {
    this.pricesService = pricesService;
  }

  @GetMapping
  public ResponseEntity<Object> getPrice(@RequestParam("product_id") Integer productId, @RequestParam("brand_id") Integer brandId,
      @RequestParam("app_datetime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDatetime)
      throws ApiException {

    Price price = pricesService.getPrice(productId, brandId, applicationDatetime);

    return ResponseEntity.ok(Map.of(
        "product_id", price.getProductId(),
        "price", price.getPrice(),
        "brand_id", price.getBrandId(),
        "currency", price.getCurrency())
    );
  }
}

