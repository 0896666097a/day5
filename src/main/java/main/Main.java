package main;

import configuration.JPAConfig;
import entity.OrderDetails;
import entity.Orders;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.OrdersRepository;
import service.OrderService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    static ApplicationContext context = new AnnotationConfigApplicationContext(JPAConfig.class);
    static OrdersRepository ordersRepository = (OrdersRepository) context.getBean("ordersRepository");

    public static void main(String[] args) {
//        createNewOrdersEntry();
//        findAll();

        findAllWithCurrentMonth();
//        findAllByProductName("thuong de");
//        findAllByPriceGreaterThan(1000);
    }

    public static void createNewOrdersEntry() {
        Orders orders = new Orders();
        orders.setOrderDate(LocalDate.of(2022, 3, 2));
        orders.setCustomerName("do ngoc");
        orders.setCustomerAddress("da nang");
        ordersRepository.save(orders);

        OrderDetails orderDetails = createNewOrderdetails(orders);
        orderDetails.setOrders(orders);
        ordersRepository.save(orderDetails);
    }

    public static void createNewOrdersEntryWithNewOrderDetail() {
        Orders orders = creteNewOrders();
        ordersRepository.save(orders);

        OrderDetails orderDetail = createNewOrderdetails(orders);
        ordersRepository.save(orderDetail);
    }

    private static Orders creteNewOrders() {
        Orders orders = new Orders();
        orders.setOrderDate(LocalDate.of(2022, 3, 2));
        orders.setCustomerName("do ngoc");
        orders.setCustomerAddress("da nang");
        return orders;
    }

    private static OrderDetails createNewOrderdetails(Orders orders) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setProductName("thuong de");
        orderDetails.setQuantity(23);
        orderDetails.setUnitPrice(12.5);
        orderDetails.setOrders(orders);
        return orderDetails;
    }
    public static void findAll() {
        List<Orders> ordersList = (List<Orders>) ordersRepository.findAll();
                for (Orders bookEntity: ordersList) {
                    System.out.println("Order: " + bookEntity.getId() + " - " + bookEntity.getOrderDate() + " - " + bookEntity.getCustomerName() + " - " + bookEntity.getCustomerAddress());
                    List<OrderDetails> detailsList =(List<OrderDetails>) bookEntity.getOrderDetailslist();

                    for (OrderDetails orderDetail: detailsList) {
                        System.out.println("  Order Detail: " + orderDetail.getId() + " - " + orderDetail.getProductName() + " - " + orderDetail.getQuantity());

            }
        }
    }

private static void findAllWithCurrentMonth(){
    LocalDate currentDate = LocalDate.now();
    Integer month = currentDate.getMonthValue();
    List<Orders> orders = OrderService.findAllByOrderDate_Month(month);
    if(!orders.isEmpty()){
        System.out.println("List all the orders in the current month "+month);
        for(Orders order:orders){
            System.out.println(order);
            order.getOrderDetail();
        }
    } else {
        System.out.println("Not found order in the current month "+month);
    }
}
    public static void findAllByPriceGreaterThan(double price){
        List<Orders> ordersEntityList = OrderService.findAll();
        Map<Orders,Double> map=new HashMap<>();
        for (Orders order: ordersEntityList){
            List<OrderDetails> orderDetails = (List<OrderDetails>) order.getOrderDetail();
            double total= orderDetails.stream().mapToDouble(orderDetail->orderDetail.getUnitPrice()*orderDetail.getQuantity()).sum();
            if(total>price) map.put(order,total);
        }
        if(!map.isEmpty()){
            System.out.println("List all orders which have total amount more than 1,000USD");
            map.forEach((order,total)->
                    System.out.println(order + " with total "+ total));
        } else {
            System.out.println("Not found list all orders which have total amount more than 1,000USD");
        }
    }

    public static void findAllByProductName(String name){
        List<Orders> orders=OrderService.findAllByOrderDetailProductName(name);
        if(!orders.isEmpty()){
            System.out.println(String.format("List all orders buy %s book", name));
            System.out.println(orders);
        } else {
            System.out.println(String.format("Not found list all orders buy %s book", name));
        }
    }
}