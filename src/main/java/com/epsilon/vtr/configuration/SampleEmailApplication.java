package com.epsilon.vtr.configuration;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.epsilon.vtr.model.CustomerInfo;
import com.epsilon.vtr.model.ProductOrder;
import com.epsilon.vtr.service.OrderService;

public class SampleEmailApplication {

    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);

        OrderService orderService = (OrderService) context.getBean("orderService");
        orderService.sendOrderConfirmation(getDummyOrder());
        ((AbstractApplicationContext) context).close();
    }

    public static ProductOrder getDummyOrder(){
        ProductOrder order = new ProductOrder();
        order.setOrderId("1111");
        order.setProductName("Thinkpad T510");
        order.setStatus("confirmed");

        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setName("Websystique Admin");
        customerInfo.setAddress("WallStreet");
        customerInfo.setEmail("bsvmadhav@gmail.com");
        order.setCustomerInfo(customerInfo);
        return order;
    }

}
