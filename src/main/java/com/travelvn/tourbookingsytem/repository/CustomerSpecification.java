package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.Customer;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {

    /**
     * Tìm khách hàng theo tên
     * (Test CriteriaBuilder)
     *
     * @param name Tên khách hàng cần tìm
     * @return Danh sách các khách hàng có tên phù hợp
     */
    public static Specification<Customer> hasName(String name) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("lastname"), name);
    }

}
