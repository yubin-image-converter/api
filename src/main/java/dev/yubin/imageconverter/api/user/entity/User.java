package dev.yubin.imageconverter.api.user.entity;

import de.huxhorn.sulky.ulid.ULID;
import dev.yubin.imageconverter.api.common.util.ULIDGenerator;
import dev.yubin.imageconverter.api.user.enums.OAuthProvider;
import dev.yubin.imageconverter.api.user.enums.Role;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "`users`",
    indexes = {@Index(name = "idx_user_email", columnList = "email")},
    uniqueConstraints = {@UniqueConstraint(columnNames = {"provider", "providerId"})})
public class User {
  private static final ULID ULID_GENERATOR = new ULID();

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

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Role role = Role.USER;

  @CreationTimestamp(source = SourceType.DB)
  @Column(updatable = false, nullable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp(source = SourceType.DB)
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @PrePersist
  public void generateIds() {
    if (this.id == null) this.id = ULIDGenerator.generate();
    if (this.publicId == null) this.publicId = "usr_" + this.id.substring(0, 8);
  }
}
