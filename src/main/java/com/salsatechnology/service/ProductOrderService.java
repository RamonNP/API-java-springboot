package com.salsatechnology.service;

import com.salsatechnology.dto.FilterProductDTO;
import com.salsatechnology.dto.ProductOrderDTO;
import com.salsatechnology.model.ProductOrder;
import com.salsatechnology.model.ProductType;
import com.salsatechnology.repository.ProductOrderRepository;
import com.salsatechnology.strategy.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductOrderService {

	private static final Logger logger = LoggerFactory.getLogger(ProductOrderService.class);

	private final ProductOrderRepository productOrderRepository;
	private Map<ProductType, ProductOrderCalculationStrategy> calculationStrategyMap;

	@Transactional
	public void createOrder(ProductOrderDTO productOrderDTO) {
		logger.info("Creating order: {}", productOrderDTO);
		ProductOrder productOrder = createProductOrder(productOrderDTO);
		productOrderRepository.save(productOrder);
		logger.info("Order created successfully.");
	}

	private ProductOrder createProductOrder(ProductOrderDTO productOrderDTO) {
		logger.debug("Creating product order: {}", productOrderDTO);
		calculationStrategyMap = createCalculationStrategyMap();
		ProductOrder productOrder = new ProductOrder();
		productOrder.setUserName(productOrderDTO.getUserName());
		productOrder.setProductType(productOrderDTO.getProductType());
		productOrder.setTimeHour(productOrderDTO.getTimeHour());

		ProductType productType = productOrderDTO.getProductType();
		if (calculationStrategyMap.containsKey(productType)) {
			ProductOrderCalculationStrategy calculationStrategy = calculationStrategyMap.get(productType);
			calculationStrategy.calculate(productOrder);
		} else {
			throw new IllegalArgumentException("Invalid product type.");
		}

		logger.debug("Product order created: {}", productOrder);
		return productOrder;
	}

	private Map<ProductType, ProductOrderCalculationStrategy> createCalculationStrategyMap() {
		if(calculationStrategyMap != null){
			return calculationStrategyMap;
		}
		Map<ProductType, ProductOrderCalculationStrategy> strategyMap = new EnumMap<>(ProductType.class);
		strategyMap.put(ProductType.SURFBOARD, new SurfboardCalculationStrategy());
		strategyMap.put(ProductType.BEACH_CHAIR, new BeachChairCalculationStrategy());
		strategyMap.put(ProductType.SUNSHADE, new SunshadeCalculationStrategy());
		strategyMap.put(ProductType.SAND_BOARD, new SandBoardCalculationStrategy());
		strategyMap.put(ProductType.BEACH_TABLE, new BeachTableCalculationStrategy());
		return strategyMap;
	}

	public List<ProductOrder> getOrderList(FilterProductDTO filterProductDTO) {
		logger.info("Getting order list with filter: {}", filterProductDTO);
		if (filterProductDTO.getFilterField() == null || filterProductDTO.getFilterValue() == null) {
			List<ProductOrder> orderList = productOrderRepository.findAll();
			logger.info("Retrieved order list: {}", orderList);
			return orderList;
		}

		List<ProductOrder> result;
		switch (filterProductDTO.getFilterField()) {
			case "userName":
				result = productOrderRepository.findByUserName(filterProductDTO.getFilterValue());
				break;
			case "id":
				Long filterValue = Long.parseLong(filterProductDTO.getFilterValue());
				Optional<ProductOrder> order = productOrderRepository.findById(filterValue);
				result = order.map(Collections::singletonList).orElse(Collections.emptyList());
				break;
			case "productType":
				result = productOrderRepository.findByProductType(ProductType.valueOf(filterProductDTO.getFilterValue()));
				break;
			case "timeHour":
				result = productOrderRepository.findByTimeHour(Integer.parseInt(filterProductDTO.getFilterValue()));
				break;
			case "productValue":
				result = productOrderRepository.findByProductValue(Long.parseLong(filterProductDTO.getFilterValue()));
				break;
			case "productTotal":
				result = productOrderRepository.findByProductTotal(Long.parseLong(filterProductDTO.getFilterValue()));
				break;
			case "userAmount":
				result = productOrderRepository.findByUserAmount(Long.parseLong(filterProductDTO.getFilterValue()));
				break;
			default:
				throw new IllegalArgumentException("Invalid filter field: " + filterProductDTO.getFilterField());
		}

		logger.info("Retrieved order list with filter: {}", result);
		return result;
	}

	@Transactional
	public void updateOrder(Long id, ProductOrderDTO productOrderDTO) {
		logger.info("Updating order with ID {}: {}", id, productOrderDTO);
		calculationStrategyMap = createCalculationStrategyMap();
		ProductOrder existingOrder = productOrderRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));

		existingOrder.setUserName(productOrderDTO.getUserName());
		existingOrder.setProductType(productOrderDTO.getProductType());
		existingOrder.setTimeHour(productOrderDTO.getTimeHour());

		if (calculationStrategyMap.containsKey(existingOrder.getProductType())) {
			ProductOrderCalculationStrategy calculationStrategy = calculationStrategyMap.get(existingOrder.getProductType());
			calculationStrategy.calculate(existingOrder);
		} else {
			throw new IllegalArgumentException("Invalid product type.");
		}

		productOrderRepository.save(existingOrder);
		logger.info("Order updated successfully.");
	}

	@Transactional
	public void deleteOrder(Long id) {
		logger.info("Deleting order with ID: {}", id);
		productOrderRepository.deleteById(id);
		logger.info("Order deleted successfully.");
	}
}
