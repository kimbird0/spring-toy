//package hello.jdbc.service;
//
//import hello.jdbc.domain.Member;
//import hello.jdbc.repository.MemberRepositoryV2;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//@Slf4j
//@RequiredArgsConstructor
//public class MemberServiceV2 {
//    private final DataSource dataSource;
//    private final MemberRepositoryV2 memberRepository;
//
//
//    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
//        Connection con = dataSource.getConnection();
//        try {
//            con.setAutoCommit(false);
//            //비즈니스 로직
//            //트랜잭션 시작
//            bizLogic(con, fromId, toId, money);
//            con.commit();
//            //커밋 롤백
//        } catch (Exception e){
//            con.rollback();
//            throw new IllegalStateException(e);
//        } finally {
//            release(con);
//        }
//
//
//    }
//
//    private void bizLogic(Connection con, String fromId, String toId, int money) throws SQLException {
//        Member fromMember = memberRepository.findById(con, fromId);
//        Member toMember = memberRepository.findById(con, toId);
//
//        memberRepository.update(con, fromId, fromMember.getMoney() - money);
//        validation(toMember);
//        memberRepository.update(con, toId, toMember.getMoney() + money);
//    }
//
//    private void validation(Member toMember) {
//        if (toMember.getMemberId().equals("ex")){
//            throw new IllegalStateException("이체중 예외 발생");
//        }
//    }
//    private void release(Connection con) {
//        if(con != null){
//            try {
//                con.setAutoCommit(true);
//                con.close();
//            } catch (Exception e){
//                log.info("error",  e);
//            }
//        }
//    }
//
//
//}
package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 파라미터 연동, 풀을 고려한 종료
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV3_3 {
    private final MemberRepositoryV3 memberRepository;

    @Transactional
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
                bizLogic(fromId, toId, money);
    }
        //트랜잭션 시작
    private void bizLogic(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);
        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }
    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }

}
