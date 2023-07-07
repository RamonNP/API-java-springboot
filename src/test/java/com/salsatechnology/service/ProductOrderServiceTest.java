package com.salsatechnology.service;

import com.salsatechnology.dto.FilterProductDTO;
import com.salsatechnology.dto.ProductOrderDTO;
import com.salsatechnology.model.ProductOrder;
import com.salsatechnology.model.ProductType;
import com.salsatechnology.repository.ProductOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductOrderServiceTest {

    @Mock
    private ProductOrderRepository productOrderRepository;

    private ProductOrderService productOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productOrderService = new ProductOrderService(productOrderRepository);
    }

    @Test
    void testCreateOrder() {
        ProductOrderDTO productOrderDTO = new ProductOrderDTO();
        productOrderDTO.setUserName("John Doe");
        productOrderDTO.setProductType(ProductType.SURFBOARD);
        productOrderDTO.setTimeHour(4);

        ArgumentCaptor<ProductOrder> captor = ArgumentCaptor.forClass(ProductOrder.class);

        productOrderService.createOrder(productOrderDTO);

        verify(productOrderRepository).save(captor.capture());

        ProductOrder savedOrder = captor.getValue();
        assertEquals("John Doe", savedOrder.getUserName());
        assertEquals(ProductType.SURFBOARD, savedOrder.getProductType());
        assertEquals(4, savedOrder.getTimeHour());
        assertEquals(5000L, savedOrder.getProductValue());
        assertEquals(20000L, savedOrder.getProductTotal());
        assertEquals(3120L, savedOrder.getUserAmount());
    }

    @Test
    void testCreateOrder_InvalidProductType() {
        ProductOrderDTO productOrderDTO = new ProductOrderDTO();
        productOrderDTO.setUserName("John Doe");
        productOrderDTO.setProductType(null);
        productOrderDTO.setTimeHour(4);

        assertThrows(IllegalArgumentException.class, () -> productOrderService.createOrder(productOrderDTO));

        verifyNoInteractions(productOrderRepository);
    }

    @Test
    void testGetOrderList() {
        List<ProductOrder> orders = new ArrayList<>();
        orders.add(createProductOrder("John Doe", ProductType.SURFBOARD, 4));
        orders.add(createProductOrder("Jane Smith", ProductType.BEACH_CHAIR, 2));

        when(productOrderRepository.findAll()).thenReturn(orders);

        List<ProductOrder> result = productOrderService.getOrderList(new FilterProductDTO(null, null));

        assertEquals(2, result.size());
        assertEquals(orders, result);
    }

    @Test
    void testGetOrderList_FilterByUserName() {
        List<ProductOrder> orders = new ArrayList<>();
        orders.add(createProductOrder("John Doe", ProductType.SURFBOARD, 4));
        orders.add(createProductOrder("Jane Smith", ProductType.BEACH_CHAIR, 2));

        when(productOrderRepository.findByUserName("John Doe")).thenReturn(orders.subList(0, 1));

        List<ProductOrder> result = productOrderService.getOrderList(new FilterProductDTO("userName", "John Doe"));

        assertEquals(1, result.size());
        assertEquals(orders.subList(0, 1), result);
    }

    @Test
    void testGetOrderList_InvalidFilterField() {
        assertThrows(IllegalArgumentException.class, () -> productOrderService.getOrderList(new FilterProductDTO("invalidField", "John Doe")));

        verifyNoInteractions(productOrderRepository);
    }
    @Test
    void testGetOrderList_FilterByProductType() {
        List<ProductOrder> orders = new ArrayList<>();
        orders.add(createProductOrder("John Doe", ProductType.SURFBOARD, 4));
        orders.add(createProductOrder("Jane Smith", ProductType.BEACH_CHAIR, 2));
        orders.add(createProductOrder("Mike Johnson", ProductType.SURFBOARD, 3));

        when(productOrderRepository.findByProductType(ProductType.valueOf("SURFBOARD"))).thenReturn(
                List.of(orders.get(0), orders.get(2)));

        List<ProductOrder> result = productOrderService.getOrderList(new FilterProductDTO("productType", "SURFBOARD"));

        assertEquals(2, result.size());
        assertEquals(List.of(orders.get(0), orders.get(2)), result);
    }

    @Test
    void testGetOrderList_NoFilter() {
        List<ProductOrder> orders = new ArrayList<>();
        orders.add(createProductOrder("John Doe", ProductType.SURFBOARD, 4));
        orders.add(createProductOrder("Jane Smith", ProductType.BEACH_CHAIR, 2));

        when(productOrderRepository.findAll()).thenReturn(orders);

        List<ProductOrder> result = productOrderService.getOrderList(new FilterProductDTO(null, null));

        assertEquals(2, result.size());
        assertEquals(orders, result);
    }

    private ProductOrder createProductOrder(String userName, ProductType productType, int timeHour) {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setUserName(userName);
        productOrder.setProductType(productType);
        productOrder.setTimeHour(timeHour);
        productOrder.setProductValue(0L);
        productOrder.setProductTotal(0L);
        productOrder.setUserAmount(0L);
        return productOrder;
    }
}