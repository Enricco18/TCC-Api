package br.com.pichau.api.repositories;

import br.com.pichau.api.controllers.response.DayBalance;
import br.com.pichau.api.models.ChargersLog;
import br.com.pichau.api.models.GenerationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface GenerationRepository extends PagingAndSortingRepository<GenerationLog, UUID> {
    List<GenerationLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    Page<GenerationLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
    List<GenerationLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end, Sort sort);
    @Query("SELECT new br.com.pichau.api.controllers.response.DayBalance(CAST(v.timestamp AS LocalDate), SUM(v.energyGenerated)) FROM GenerationLog v GROUP BY CAST(v.timestamp AS LocalDate)")
    List<DayBalance> findDayBalance(LocalDateTime start, LocalDateTime end);
}
