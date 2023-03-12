package com.zam.studypetclinic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date date_schedule;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    private boolean status_schedule; // if true , schedule has filled


}
