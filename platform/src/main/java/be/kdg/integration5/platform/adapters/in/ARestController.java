package be.kdg.integration5.platform.adapters.in;


import be.kdg.integration5.platform.domain.User;
import be.kdg.integration5.platform.ports.in.SearchUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ARestController {
    private final SearchUserUseCase searchUserUseCase;

    public ARestController(SearchUserUseCase searchUserUseCase) {
        this.searchUserUseCase = searchUserUseCase;
    }

    @GetMapping("/helloa")
    public void sayHelloA(){
        System.out.println("Hello BoundedContext A");
    }

    @PostMapping("/searchUser")
    public ResponseEntity<String> searchUser(@RequestParam String username){
        List<User> user = searchUserUseCase.searchUser(username);
        if (user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No user with this username found");
        }
        else {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body("User found: " + user);
        }
    }

}
