package com.salsatechnology.strategy;


import com.salsatechnology.model.ProductOrder;

public class BeachChairCalculationStrategy implements ProductOrderCalculationStrategy {
    @Override
    public void calculate(ProductOrder productOrder) {
        double productValue = 35.0;
        double productTotal = productValue * productOrder.getTimeHour();
        double userAmount = productTotal * 0.05;

        productOrder.setProductValue((long) (productValue * 100));
        productOrder.setProductTotal((long) (productTotal * 100));
        productOrder.setUserAmount((long) (userAmount * 100));
    }
}