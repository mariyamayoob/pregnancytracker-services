package com.ihi.pregnancytracker.repository;

import com.ihi.pregnancytracker.beans.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findAppUserByFhirID(String fhirID);

    @Modifying
    @Query("UPDATE AppUser c SET c.c_d1 = :cal, c.c_d2 = c.c_d1, c.c_d3=c.c_d2, c.c_d4=c.c_d3, c.c_d5=c.c_d4, c.c_d6=c.c_d5,c.c_d7=c.c_d6 WHERE c.fhirID = :fhir")
    int updateCalorie(@Param("fhir") String fhir, @Param("cal") float cal);



}