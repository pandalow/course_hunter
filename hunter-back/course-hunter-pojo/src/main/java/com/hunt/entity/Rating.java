package com.hunt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@MappedSuperclass
public abstract class Rating {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "rating")
  private Integer rating;

  @Column(name = "content")
  private String content;

  @Column(name = "create_time")
  private Instant createTime;

  @Column(name = "update_time")
  private Instant updateTime;

  @PrePersist
  protected void onCreate() {
    createTime = Instant.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updateTime = Instant.now();
  }
}
