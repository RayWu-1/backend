package backend.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {
    private String email, password;

    public LoginDto(String em, String pa) {
        this.email = em;
        this.password = pa;
    }

}
