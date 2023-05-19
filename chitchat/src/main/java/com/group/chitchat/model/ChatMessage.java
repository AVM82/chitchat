package com.group.chitchat.model;

import com.group.chitchat.model.enums.Subscription;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
public class ChatMessage {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "author_id", referencedColumnName = "id")
  private User author;

  @ManyToOne(targetEntity = Chitchat.class)
  @JoinColumn(name = "chitchat_id", referencedColumnName = "id")
  private Chitchat chitchat;

  @Column(name = "message")
  private String message;

  @Column(name = "created_time")
  private LocalDateTime createdTime;

  @Column(name = "subscription_type")
  @Enumerated(EnumType.STRING)
  private Subscription subscriptionType;
}
