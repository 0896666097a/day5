package service;


import entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repository.OrdersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    JpaTransactionManager jpaTransactionManager;

    @Autowired
    static OrdersRepository repos;

    @Transactional
    public void insertOrder(Orders orders){
        repos.save(orders);
    }

    public Optional<Orders> findById(Long id){
        return repos.findById(Math.toIntExact(id));
    }

    public static List<Orders> findAll(){
        return (List<Orders>) repos.findAll();
    }

    public static List<Orders> findAllByOrderDate_Month(Integer month){
        return repos.findAllByOrderDate_Month(month);
    }

    public static List<Orders> findAllByOrderDetailProductName(String name){
        return repos.findAllByOrderDetailProductName(name);
    }

    public List<Orders> findAllByCustomerNameLike(String name){
        return repos.findAllByCustomerNameLike(name);
    }

    public void deleteById(Long id){
        repos.deleteById(Math.toIntExact(id));
    }


}