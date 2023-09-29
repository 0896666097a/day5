package repository;


import entity.OrderDetails;
import entity.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends CrudRepository<Orders,Integer> {
    <S extends Orders> S save(S entity);
    void save(OrderDetails orderDetail);
    @Query("FROM Orders o WHERE FUNCTION('MONTH',o.orderDate) = :month")


    List<Orders> findAllByOrderDetailProductName(String name);

    List<Orders> findAllByCustomerNameLike(String name);

    List<Orders> findAllByOrderDate_Month(Integer month);
}
