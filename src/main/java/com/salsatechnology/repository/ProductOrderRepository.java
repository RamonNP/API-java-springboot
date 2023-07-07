package com.salsatechnology.repository;

import com.salsatechnology.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salsatechnology.model.ProductOrder;

import java.util.List;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    List<ProductOrder> findByUserName(String userName);
    List<ProductOrder> findByProductType(ProductType productType);
    List<ProductOrder> findByTimeHour(Integer timeHour);
    List<ProductOrder> findByProductValue(Long productValue);
    List<ProductOrder> findByProductTotal(Long productTotal);
    List<ProductOrder> findByUserAmount(Long userAmount);

}
