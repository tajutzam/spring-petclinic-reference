package com.zam.studypetclinic.repository;

import com.zam.studypetclinic.entity.ScheduleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleDetailsRepo extends JpaRepository<ScheduleDetails , Integer> {
}
