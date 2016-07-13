package com.epsilon.vtr.service;

import com.epsilon.vtr.model.ProductOrder;

public interface OrderService {

    public void sendOrderConfirmation(ProductOrder productOrder);
}
