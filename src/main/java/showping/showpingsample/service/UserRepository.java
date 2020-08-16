package showping.showpingsample.service;

import showping.showpingsample.Dto.UserDto;
import java.util.List;
import java.util.Optional;


public interface UserRepository {
    UserDto save(UserDto userDto); //회원가입
    List<UserDto> findAll();// 리스트 뽑기
    Optional<UserDto> findByName(String name);
}
