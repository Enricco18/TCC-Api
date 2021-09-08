package br.com.pichau.api.repositories;

import br.com.pichau.api.controllers.response.DayBalance;
import br.com.pichau.api.models.ChargersLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public
interface ChargesRepository extends PagingAndSortingRepository<ChargersLog, Integer> {
    List<ChargersLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    Page<ChargersLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
    List<ChargersLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end, Sort sort);

    @Query("SELECT new br.com.pichau.api.controllers.response.DayBalance(CAST(v.timestamp AS LocalDate), SUM(v.energyUsed)) FROM ChargersLog v GROUP BY CAST(v.timestamp AS LocalDate)")
    List<DayBalance> findDayBalance(LocalDateTime start, LocalDateTime end);
}
