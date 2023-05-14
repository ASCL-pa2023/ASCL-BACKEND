package esgi.ascl.security.authentification;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    //todo verification des informations de l'utilisateur
    if (request.getEmail() == null || request.getEmail().isEmpty() ||
            request.getLastname() == null || request.getLastname().isEmpty() ||
            request.getPassword() == null || request.getPassword().isEmpty() ||
            request.getFirstname() == null || request.getFirstname().isEmpty()
    )
      return ResponseEntity.badRequest().build();

    return ResponseEntity.ok(service.register(request));
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    //todo verification des informations de l'utilisateur
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    //todo verification si le token est pas vide
    service.refreshToken(request, response);
  }


}
