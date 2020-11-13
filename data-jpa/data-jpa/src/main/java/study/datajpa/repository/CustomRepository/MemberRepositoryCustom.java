package study.datajpa.repository.CustomRepository;
import study.datajpa.entity.Member;

import java.util.*;

public interface MemberRepositoryCustom {
    //spring data jpa가 아닌
    //내가 직접 만들고 싶을때
    List<Member> findMemberCustom();
}
