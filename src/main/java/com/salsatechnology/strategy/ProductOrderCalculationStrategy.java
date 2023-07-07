package com.salsatechnology.strategy;

import com.salsatechnology.model.ProductOrder;

public interface ProductOrderCalculationStrategy {
    void calculate(ProductOrder productOrder);
}
