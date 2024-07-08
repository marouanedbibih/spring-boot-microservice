package com.example.orderservice.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    public String saveOrder(Order order) {

        // Call catalog-service to check stock
        Boolean result = webClientBuilder.build()
                .get()
                .uri("http://catalog-service/catalog-api/product/check-stock/" + order.getProduct() + "/"
                        + order.getQuantity())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        if (result) {
            orderRepository.save(order);
            return "Order placed successfully!";
        }else {
            return "Order failed!  Product not in stock!";
        }
    }

}
