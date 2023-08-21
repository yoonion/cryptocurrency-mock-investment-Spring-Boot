package site.bitrun.cryptocurrency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.bitrun.cryptocurrency.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
