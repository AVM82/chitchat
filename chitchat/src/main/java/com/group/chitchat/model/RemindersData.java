package com.group.chitchat.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reminder_data")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RemindersData {

  @Id
  @Column(name = "chitchat_id")
  private Long chitchatId;

  @Column(name = "time")
  private LocalDateTime startTime;

  @Column(name = "link")
  private String link;

  @Column(name = "reminded")
  private Boolean reminded;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "reminder_emails", joinColumns = @JoinColumn(name = "data_id"))
  @Column(name = "email")
  private Set<String> emails;

  @OneToOne
  @MapsId
  private Chitchat chitchat;

  @Override
  public String toString() {
    return "RemindersData{"
        + "chitchatId=" + chitchatId
        + ", startTime=" + startTime
        + ", link='" + link + '\''
        + ", emails=" + emails
        + ", chitchat=" + chitchat
        + '}';
  }
}
