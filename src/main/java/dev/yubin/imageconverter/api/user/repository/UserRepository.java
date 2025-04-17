package dev.yubin.imageconverter.api.user.repository;

import dev.yubin.imageconverter.api.user.entity.User;
import dev.yubin.imageconverter.api.user.enums.OAuthProvider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  Optional<User> findByProviderAndProviderId(OAuthProvider provider, String providerId);
}
