package com.salsatechnology.facade;

import com.salsatechnology.dto.FilterProductDTO;
import com.salsatechnology.dto.ProductOrderDTO;
import com.salsatechnology.model.ProductOrder;
import com.salsatechnology.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ProductOrderFacade {
    private final ProductOrderService productOrderService;

    @Autowired
    public ProductOrderFacade(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }

    public void createOrder(ProductOrderDTO productOrderDTO) {
        productOrderService.createOrder(productOrderDTO);
    }

    public List<ProductOrder> getOrderList(FilterProductDTO filterProductDTO) {
        return productOrderService.getOrderList(filterProductDTO);
    }

    public void updateOrder(Long id, ProductOrderDTO productOrderDTO) {
        productOrderService.updateOrder(id, productOrderDTO);
    }

    public void deleteOrder(Long id) {
        productOrderService.deleteOrder(id);
    }
}
