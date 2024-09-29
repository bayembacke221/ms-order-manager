package sn.ucad.mscustomerorder.repository;

import org.springframework.data.jpa.repository.Query;
import sn.ucad.mscustomerorder.models.Order;

public interface OrderRepository extends GenericRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.id = :id AND o.orderStatus = 'CREER'")
    Order findOrderByIdWhereStatusPending(Long id);
}
