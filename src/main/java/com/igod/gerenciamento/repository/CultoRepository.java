package com.igod.gerenciamento.repository;

import com.igod.gerenciamento.model.Culto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CultoRepository extends JpaRepository<Culto, Long> {

    Optional<Culto> findByStatus(Culto.StatusCulto status);
}