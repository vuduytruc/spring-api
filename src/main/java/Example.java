import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
// import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@EnableAutoConfiguration
public class Example {

    // //demo
    // @RequestMapping(value = "/user/login", method = RequestMethod.POST, produces = "application/json")
    // String testLogin(HttpServletRequest request){
    //     String phone = request.getParameter("phone");
    //     System.out.println("show phone"+phone);
    //     return phone;
    // }


    // POST login(phone, password) RESPONSE {success, User}
    @CrossOrigin
    @RequestMapping(value = "/user/login", method = RequestMethod.POST, produces = "application/json")
    String login(@RequestParam("phone") String phone, @RequestParam("password") String password) {

        System.out.println("\n\nLogin(" + phone + ", " + password + ")\n\n");

        User user = User.login(phone, password);

        ResultPack rs;

        if (user == null) {
            rs = new ResultPack();
        } else {
            rs = new ResultPack("true", user);
        }

        return toJson(rs);
    }

    // GET User/list RESPONSE UserList
    @RequestMapping(value = "/user/list", method = RequestMethod.GET, produces = "application/json")
    String index() {
        System.out.println("/user/list -> index()");
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
