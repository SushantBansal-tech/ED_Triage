package com.acme.encounter.repository;

import com.acme.encounter.entity.QueueEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QueueEntryRepository extends JpaRepository<QueueEntry, Long> {

    // Use this to fetch active entries for QueueRankingService.recompute()
    List<QueueEntry> findByStatus(String status);

    // Use this for the UI: "Get all in-queue patients sorted by rank"
    List<QueueEntry> findByStatusOrderByQueueRankAsc(String status);
}