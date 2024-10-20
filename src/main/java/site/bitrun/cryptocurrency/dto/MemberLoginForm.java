package site.bitrun.cryptocurrency.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginForm {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
