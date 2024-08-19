package com.easybuy.easybuy.order;

import com.easybuy.easybuy.customer.CustomerClient;
import com.easybuy.easybuy.exception.BusinessException;
import com.easybuy.easybuy.kafka.OrderConfirmation;
import com.easybuy.easybuy.kafka.OrderProducer;
import com.easybuy.easybuy.orderline.OrderLineRequest;
import com.easybuy.easybuy.orderline.OrderLineService;
import com.easybuy.easybuy.product.ProductClient;
import com.easybuy.easybuy.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    public Integer createOrder(@Valid OrderRequest request) {
        //using OpenFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order" +
                        ":: no customer exists with provide customer id:" + request.customerId()
                ));
        //using RestTemplate
        var purchasedProducts = this.productClient.purchaseProducts(request.products());
        var order = repository.save(mapper.toOrder(request));
        for (PurchaseRequest purchaseRequest: request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        //todo start payment process

        orderProducer.sendOrderConfirmation(new OrderConfirmation(
                request.reference(),
                request.amount(),
                request.paymentMethod(),
                customer,
                purchasedProducts
        ));

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::toOrderResponse).orElseThrow(
                        ()-> new EntityNotFoundException(String.format("No order found with the provided ID:%d",orderId))
                );
    }
}
