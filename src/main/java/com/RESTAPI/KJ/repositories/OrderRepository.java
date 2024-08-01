package com.RESTAPI.KJ.repositories;

import com.RESTAPI.KJ.models.Order;
import com.RESTAPI.KJ.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository("orderRepository")
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByDateAndAmountAndCurrencyCodeAndTransactionType(LocalDate date, Double amount, String currencyCode, TransactionType transactionType);
}
