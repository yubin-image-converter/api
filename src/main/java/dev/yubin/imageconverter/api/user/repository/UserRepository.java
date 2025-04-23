package dev.yubin.imageconverter.api.user.repository;

import dev.yubin.imageconverter.api.user.entity.User;
import dev.yubin.imageconverter.api.user.enums.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPublicId(String publicId);

    Optional<User> findByProviderAndProviderId(OAuthProvider provider, String providerId);
}
