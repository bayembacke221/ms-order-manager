package sn.ucad.mscustomerorder.repository;

import sn.ucad.mscustomerorder.models.OrderDetail;

import java.util.List;

public interface OrderDetailRepository extends GenericRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderId(Long orderId);

    void deleteByOrderId(Long id);
}
