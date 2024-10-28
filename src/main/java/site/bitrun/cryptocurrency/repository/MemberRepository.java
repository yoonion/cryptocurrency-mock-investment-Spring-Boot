package site.bitrun.cryptocurrency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.bitrun.cryptocurrency.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Member findById(long id);
}
