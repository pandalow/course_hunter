package com.hunt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@MappedSuperclass
public abstract class Comment implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "content")
  private String content;

  @Column(name = "user_id")
  private Long userId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
  private User user;

  @Column(name = "level")
  private Integer level;

  @Column(name = "parent_id")
  private Long parentId;

  @Column(name = "root_id")
  private Long rootId;

  @Column(name = "is_deleted")
  private boolean isDeleted;

  @Column(name = "create_time")
  private Instant createTime;

  @PrePersist
  protected void onCreate() {
    createTime = Instant.now();
  }
}
