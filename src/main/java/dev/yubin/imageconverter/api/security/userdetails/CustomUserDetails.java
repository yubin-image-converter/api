package dev.yubin.imageconverter.api.security.userdetails;

import dev.yubin.imageconverter.api.user.entity.User;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class CustomUserDetails implements UserDetails {
  //    private final String publicId;
  //    private final String email;
  //    private final Role role;
  //
  //    public CustomUserDetails(User user) {
  //        this.publicId = user.getPublicId();
  //        this.email = user.getEmail();
  //        this.role = user.getRole();
  //    }
  private final User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public CustomUserDetails(User user) {
    this.user = user;
  }

  //    @Override
  //    public Collection<? extends GrantedAuthority> getAuthorities() {
  //        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  //    }
  //
  //    @Override public String getPassword() { return null; } // 패스워드 인증 안 하면 null
  //    @Override public String getUsername() { return email; }
  //
  //    @Override public boolean isAccountNonExpired() { return true; }
  //    @Override public boolean isAccountNonLocked() { return true; }
  //    @Override public boolean isCredentialsNonExpired() { return true; }
  //    @Override public boolean isEnabled() { return true; }
  //
  //    public String getPublicId() { return publicId; }
  //    public Role getRole() { return role; }
}
