package site.bitrun.cryptocurrency;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import site.bitrun.cryptocurrency.domain.Member;
import site.bitrun.cryptocurrency.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebSecurityConfigTest {

    @Autowired private PasswordEncoder passwordEncoder;

//    @Autowired private MemberRepository memberRepository;

    @Test
    @DisplayName("패스워드 암호화 테스트")
    void passwordEncode() {

//        Member findMember = memberRepository.findByEmail("zla2480@naver.com");

        String rawPassword = "1234";

        String encodePassword = passwordEncoder.encode(rawPassword);
        boolean result = passwordEncoder.matches("1234", encodePassword);

        System.out.println("result = " + result);

    }
}