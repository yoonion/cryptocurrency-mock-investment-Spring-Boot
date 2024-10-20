package site.bitrun.cryptocurrency.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String email;
    private String password;
    private long asset; // 보유자산

    public Member(String username, String email, String password, long asset) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.asset = asset;
    }
}
