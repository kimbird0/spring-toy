package hello.core.member;

public interface MemberService {
    void join(Member member);

    //회원조회
    Member findMember(Long memberId);
}
