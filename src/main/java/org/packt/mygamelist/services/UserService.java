package org.packt.mygamelist.services;

import org.packt.mygamelist.domain.AppUser;
import org.packt.mygamelist.web.dto.AppUserDto;

public interface UserService {
    AppUser save(AppUserDto appUserDto);
}
