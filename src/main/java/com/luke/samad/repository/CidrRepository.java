package com.luke.samad.repository;

import com.luke.samad.domain.Cidr;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CidrRepository extends CrudRepository<Cidr, Long> {
    public Cidr findByCidr(@Param("cidr") String cidr);
}
