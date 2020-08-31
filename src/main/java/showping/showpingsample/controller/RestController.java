package showping.showpingsample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import showping.showpingsample.Dto.UserDto;
import showping.showpingsample.service.UserService;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private Logger log=LoggerFactory.getLogger(RestController.class);

    private UserService userService;

    public RestController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping("/todo/info")   //@RequestParam("id")int id
    public List<UserDto> Userinfo(){

        //log.info("아이디"+id);

        List<UserDto>users= userService.findUserAll();
//        for(UserDto user : users){
//            log.info("유저아이디"+user.getId());
//            if(user.getId()==id){
//                return user;
//            }
//        }

        return users;
    }
}
