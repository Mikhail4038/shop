package com.keiko.adressapp.repository;

import com.keiko.adressapp.entity.Address;
import com.keiko.adressapp.request.AddressRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    /*@Query (nativeQuery = true,
            value = "select * from address where employee_id =:id")*/

    @Query (nativeQuery = true,
            value = "select ad.id, ad.city, ad.state " +
                    "from address as ad " +
                    "join employee as em on ad.employee_id = em.id " +
                    "where ad.employee_id:=id")
    Optional<Address> findAddressByEmployeeId (@Param ("id") Long id);

    @Modifying
    @Transactional
    @Query (nativeQuery = true,
            value = "insert into address (city,state,employee_id)" +
                    "values(:city,:state,:employee_id)")
    void save (@Param ("city") String city, @Param ("state") String state, @Param ("employee_id") Integer employeeId);
}
