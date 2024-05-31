package org.packt.mygamelist.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.packt.mygamelist.domain.AppUser}
 */

@Setter
@Getter
@Value
public class AppUserDto implements Serializable {
    String username;
    String name;
    String surname;
    String password;
    String email;
}