package com.easybuy.easybuy.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;

    public void saveOrderLine(OrderLineRequest request) {
        var orderLine = mapper.toOrderLine(request);
        repository.save(orderLine);
    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return repository.findAllByOrderId(orderId).stream()
                .map(mapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }
}
