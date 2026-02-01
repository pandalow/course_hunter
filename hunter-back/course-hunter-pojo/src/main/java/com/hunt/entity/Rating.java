package com.hunt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "rating", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "course_id"})
})
public class Rating implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "score")
  private Integer score;

  @Column(name = "content")
  private String content;

  @Column(name = "user_id")
  private Long userId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private User user;

  //
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id")
  private Course course;

  // TimeStamp
  @Column(name = "create_time", updatable = false)
  private Instant createTime;

  @Column(name = "update_time")
  private Instant updateTime;

  @PrePersist
  protected void onCreate() {
    createTime = Instant.now();
    updateTime = Instant.now();
  }
  @PreUpdate
  protected void onUpdate() {
    updateTime = Instant.now();
  }
}
