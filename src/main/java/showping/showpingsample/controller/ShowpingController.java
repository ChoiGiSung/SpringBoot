package showping.showpingsample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import showping.showpingsample.Dto.UserDto;
import showping.showpingsample.Dto.UserDtoFrom;
import showping.showpingsample.service.UserService;
import java.util.List;

@Controller
public class ShowpingController {

    private UserService userService;

    public ShowpingController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(){
        return "/showping/home";
    };

    @GetMapping("/showping/signup") //홈에서 회원가입으로
    public String signup(){
        return "/showping/signup";
    };

    @GetMapping("/showping/userlist") //홈에서 회원리스트로
    public String userlist(Model model){

        List<UserDto> Users=userService.findUserAll();
        model.addAttribute("Users",Users);
        return "/showping/userlist";
    };

    @PostMapping("/showping/new")//회원가입에서 오는거
    public String usernew(UserDtoFrom userDtoFrom){
        //회원가입에서 온 값들  서비스로 조인
        UserDto userDto=new UserDto();
        userDto.setUser_name(userDtoFrom.getUser_name());
        userDto.setCity(userDtoFrom.getCity());
        userDto.setTel(userDtoFrom.getTel());

        //여기에 넣어주기
        userService.join(userDto);

        return "redirect:/";
    }
}
