package org.packt.mygamelist.services;

import org.packt.mygamelist.domain.AppUser;
import org.packt.mygamelist.domain.AppUserRepository;
import org.packt.mygamelist.web.dto.AppUserDto;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final AppUserRepository appUserRepository;

    public UserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUser save(AppUserDto appUserDto) {
        AppUser appUser = new AppUser(appUserDto.getUsername(), appUserDto.getName(), appUserDto.getSurname(), appUserDto.getPassword(), appUserDto.getEmail());
        return appUserRepository.save(appUser);
    }

}
