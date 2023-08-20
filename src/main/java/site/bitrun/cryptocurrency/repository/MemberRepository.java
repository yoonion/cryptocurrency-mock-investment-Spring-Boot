package site.bitrun.cryptocurrency.repository;

import site.bitrun.cryptocurrency.domain.Member;

public interface MemberRepository {

    void save(Member member);

    // void findAll(Member member);
}
