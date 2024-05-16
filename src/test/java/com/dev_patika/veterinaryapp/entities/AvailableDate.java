package com.dev_patika.veterinaryapp.entities;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.checkerframework.checker.units.qual.C;

import java.time.LocalDate;

@Entity
@Data
@Table (name = "availableDates")
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDate {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    @Column (name = "availableDate_id")
    private Long id;

    @NotNull
    @Column(name = "availableDate")
    private LocalDate availabledate;

}
