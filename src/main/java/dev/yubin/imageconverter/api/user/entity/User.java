package dev.yubin.imageconverter.api.user.entity;

import dev.yubin.imageconverter.api.user.enums.OAuthProvider;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import de.huxhorn.sulky.ulid.ULID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "users",
    indexes = {@Index(name = "idx_user_email", columnList = "email")},
    uniqueConstraints = {@UniqueConstraint(columnNames = {"provider", "providerId"})})
public class User {

  @Id
  @Column(nullable = false, updatable = false, length = 26)
  @Setter(AccessLevel.NONE)
  private String id;

  @Column(nullable = false, unique = true, length = 36)
  private String publicId; // 클라이언트 노출용

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private OAuthProvider provider;

  @Column(nullable = false)
  private String providerId;

  private String name;

  @CreationTimestamp(source = SourceType.DB)
  @Column(updatable = false, nullable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp(source = SourceType.DB)
  @Column(nullable = false)
  private LocalDateTime updatedAt;


  @PrePersist
  public void generateIds() {
    ULID ulid = new ULID();
    if (this.id == null) this.id = ulid.nextULID();
    if (this.publicId == null) this.publicId = "usr_" + ulid.nextULID().substring(0, 8);
  }
}
