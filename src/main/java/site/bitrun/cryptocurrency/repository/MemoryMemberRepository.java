package site.bitrun.cryptocurrency.repository;

import org.springframework.stereotype.Repository;
import site.bitrun.cryptocurrency.domain.Member;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    public static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

}
