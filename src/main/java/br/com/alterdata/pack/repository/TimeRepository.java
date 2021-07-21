package br.com.alterdata.pack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alterdata.pack.model.Time;

public interface TimeRepository extends JpaRepository<Time, Long> {
    Optional<Time> findById(Long id);

    Optional<Time> findByNomeContainingIgnoreCase(String nome);
}
