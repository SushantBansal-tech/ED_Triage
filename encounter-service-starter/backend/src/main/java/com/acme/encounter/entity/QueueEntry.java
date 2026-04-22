package com.acme.encounter.entity;

@Entity
@Table(name = "queue_entries")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class QueueEntry {
    @Id
    @Column(name = "queue_entry_id")
    private String queueEntryId;

    @Column(name = "encounter_id", nullable = false, unique = true)
    private String encounterId;

    @Column(name = "current_queue_state", nullable = false)
    private String currentQueueState;

    @Column(name = "queue_bucket", nullable = false)
    private String queueBucket;

    @Column(name = "priority_code", nullable = false)
    private String priorityCode;

    @Column(name = "recommended_priority_code")
    private String recommendedPriorityCode;

    @Column(name = "manual_override_priority")
    private String manualOverridePriority;

    @Column(name = "queue_rank", nullable = false)
    private Integer queueRank;

    @Column(name = "location_code")
    private String locationCode;

    @Column(name = "room_bed_code")
    private String roomBedCode;

    @Column(name = "waiting_since", nullable = false)
    private OffsetDateTime waitingSince;

    @Column(name = "last_recomputed_at", nullable = false)
    private OffsetDateTime lastRecomputedAt;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}