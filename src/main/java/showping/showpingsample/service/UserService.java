package showping.showpingsample.service;

import showping.showpingsample.Dto.UserDto;

import java.util.Optional;
import java.util.List;

//서비스는 서비스 인터페이스를 정의하고 dao에서 구현하고 여기서 사용하기 쉽게 만들고 dao에서 이 service를 사용한다
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //회원가입
    public Long join(UserDto userDto){
       Optional<UserDto> result =userRepository.findByName(userDto.getUser_name());
       result.ifPresent(m->{
           throw new IllegalStateException("이미있는 회원");
       });

       userRepository.save(userDto);

       return userDto.getId();
    }

    //전체 회원 조회
    public List<UserDto> findUserAll(){
        return userRepository.findAll();
    }

}
