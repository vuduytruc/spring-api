import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
//import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class Example {

    //POST login(phone, password) RESPONSE {result, user}
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    String login(@RequestParam("phone") String phone, @RequestParam("password") String password) {
        User user = User.login(phone, password);

        ResultPack rs;

        if (user == null) {
            rs = new ResultPack();
        } else {
            rs = new ResultPack("true", user);
        }

        return toJson(rs);
    }

    //GET user/{phone} RESPONSE user
    @RequestMapping(value = "/user/{phone}", method = RequestMethod.GET)
    String login(@PathVariable("phone") String phone) {
        return toJson(User.findByPhone(phone));
    }

    //GET user/list RESPONSE userList
    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    String index() {
        return toJson(User.getAll());
    }



    public static void main(String[] args) throws Exception {
        SpringApplication.run(Example.class, args);
    }

    String toJson(Object obj) {
        com.google.gson.Gson gson = new com.google.gson.Gson();
        return gson.toJson(obj);
    }

}

class ResultPack {
    public String success = "false";
    public User user = null;

    ResultPack() {
        //
    }

    ResultPack(String success, User user) {
        this.success = success;
        this.user = user;
    }
}
