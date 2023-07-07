package com.salsatechnology.controller;

import com.salsatechnology.dto.FilterProductDTO;
import com.salsatechnology.dto.ProductOrderDTO;
import com.salsatechnology.facade.ProductOrderFacade;
import com.salsatechnology.model.ProductOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/orders")
public class ProductOrderController {

	private static final Logger logger = LoggerFactory.getLogger(ProductOrderController.class);

	private final ProductOrderFacade productOrderFacade;

	@Autowired
	public ProductOrderController(ProductOrderFacade productOrderFacade) {
		this.productOrderFacade = productOrderFacade;
	}

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public void createOrder(@RequestBody @Valid ProductOrderDTO productOrderDTO) {
		logger.info("Received request to create order: {}", productOrderDTO);
		productOrderFacade.createOrder(productOrderDTO);
		logger.info("Order created successfully.");
	}

	@PostMapping("/list")
	public List<ProductOrder> getOrderList(@RequestBody @Valid FilterProductDTO filterProductDTO) {
		logger.info("Received request to get order list with filter: {}", filterProductDTO);
		List<ProductOrder> orderList = productOrderFacade.getOrderList(filterProductDTO);
		logger.info("Retrieved order list successfully.");
		return orderList;
	}

	@PutMapping("/{id}")
	public void updateOrder(@PathVariable Long id, @RequestBody @Valid ProductOrderDTO productOrderDTO) {
		logger.info("Received request to update order with ID {}: {}", id, productOrderDTO);
		productOrderFacade.updateOrder(id, productOrderDTO);
		logger.info("Order updated successfully.");
	}

	@DeleteMapping("/{id}")
	public void deleteOrder(@PathVariable Long id) {
		logger.info("Received request to delete order with ID: {}", id);
		productOrderFacade.deleteOrder(id);
		logger.info("Order deleted successfully.");
	}
}
