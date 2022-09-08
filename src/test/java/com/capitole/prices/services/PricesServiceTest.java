package com.capitole.prices.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.capitole.prices.errors.ApiException;
import com.capitole.prices.errors.CustomErrors;
import com.capitole.prices.models.Price.Currency;
import com.capitole.prices.repositories.PricesRepository;
import com.capitole.prices.repositories.entity.Price;
import com.capitole.prices.services.impl.PricesServiceImpl;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PricesServiceTest {

  @Mock
  private PricesRepository pricesRepositoryMock;

  @InjectMocks
  private PricesServiceImpl pricesServiceMock;

  @BeforeEach
  public void setup() {
    pricesRepositoryMock = mock(PricesRepository.class);
    pricesServiceMock = new PricesServiceImpl(pricesRepositoryMock);
  }

  @Test
  public void getPrice_success() throws ApiException {
    Instant now = Instant.now();
    Price price = new Price(1, 1, 35345, Timestamp.from(now), Timestamp.from(now), 1, 1, 35.1, "EUR");

    when(pricesRepositoryMock.getPrice(1, 1, Timestamp.from(now)))
        .thenReturn(price);

    com.capitole.prices.models.Price expectedPrice =
        new com.capitole.prices.models.Price(1, 35345, 1, 1, LocalDateTime.ofInstant(now, ZoneOffset.UTC),
            LocalDateTime.ofInstant(now, ZoneOffset.UTC), 35.1,
            Currency.EUR);
    com.capitole.prices.models.Price actualPrice = pricesServiceMock.getPrice(1, 1, LocalDateTime.ofInstant(now, ZoneOffset.UTC));

    Assertions.assertEquals(expectedPrice.getProductId(), actualPrice.getProductId());
    Assertions.assertEquals(expectedPrice.getBrandId(), actualPrice.getBrandId());
    Assertions.assertEquals(expectedPrice.getPrice(), actualPrice.getPrice());
  }

  @Test
  public void getPrice_fail_notFoundPrice() throws ApiException {
    Instant now = Instant.now();

    when(pricesRepositoryMock.getPrice(1, 1, Timestamp.from(now)))
        .thenThrow(new ApiException(CustomErrors.PRICE_NOT_FOUND, null));

    ApiException ae = assertThrows(ApiException.class, () -> {
      pricesServiceMock.getPrice(1, 1, LocalDateTime.ofInstant(now, ZoneOffset.UTC));
    });

    Assertions.assertEquals(ae.getCustomError().getStatus(), CustomErrors.PRICE_NOT_FOUND.getStatus());
  }

  @Test
  public void getPrice_fail_unexpectedError() throws ApiException {
    Instant now = Instant.now();

    when(pricesRepositoryMock.getPrice(1, 1, Timestamp.from(now)))
        .thenThrow(new RuntimeException("Random error"));

    ApiException ae = assertThrows(ApiException.class, () -> {
      pricesServiceMock.getPrice(1, 1, LocalDateTime.ofInstant(now, ZoneOffset.UTC));
    });

    Assertions.assertEquals(ae.getCustomError().getStatus(), CustomErrors.UNEXPECTED_REPO_ERROR.getStatus());
  }
}
