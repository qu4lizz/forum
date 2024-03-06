package qu4lizz.sni.forum.server.models.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import qu4lizz.sni.forum.server.models.entities.PermissionEntity;
import qu4lizz.sni.forum.server.models.enums.Role;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Data
public class JwtUser implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private Role role;
    private List<PermissionEntity> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<GrantedAuthority> authorities = new HashSet<>();

        if (permissions != null) {
            for (PermissionEntity permission : permissions) {
                if (permission.getUpdate()) {
                    authorities.add(new SimpleGrantedAuthority("UPDATE_TOPIC_" + permission.getIdTopic()));
                }
                if (permission.getWrite()) {
                    authorities.add(new SimpleGrantedAuthority("WRITE_TOPIC_" + permission.getIdTopic()));
                }
                if (permission.getDelete()) {
                    authorities.add(new SimpleGrantedAuthority("DELETE_TOPIC_" + permission.getIdTopic()));
                }
            }
        }

        if (role != null) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }

        return authorities;
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
}
