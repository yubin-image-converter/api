package dev.yubin.imageconverter.api.security.userdetails;

import dev.yubin.imageconverter.api.user.entity.User;
import dev.yubin.imageconverter.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String publicId) throws UsernameNotFoundException {
    User user =
        userRepository
            .findByPublicId(publicId)
            .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));
    return new CustomUserDetails(user);
  }
}
