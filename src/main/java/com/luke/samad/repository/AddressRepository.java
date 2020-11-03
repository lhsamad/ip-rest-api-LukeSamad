package com.luke.samad.repository;

import com.luke.samad.domain.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
    public Address findByIp(@Param("ip") String personId);
}
