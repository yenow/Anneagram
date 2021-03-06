package com.enneagram.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.enneagram.controller.LogExample;
import com.enneagram.vo.MemberVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "file:src/main/webapp/WEB-INF/spring/test-context.xml")
/* MemberDAO 테스트 클래스 */
public class MemberDAOTest {
	private static final Log LOG = LogFactory.getLog( LogExample.class );
	
	@Autowired
	private MemberDAO memberDAO;
	@Autowired
	private PersonalityDAO testDAO;
	
	
	@Before
	@Test
	public void setMemberVO() {
		memberDAO.deleteById("testId");
		MemberVO memberVO = new MemberVO(0, "testId", "testPassword", "테스트", "테스트", "test@test.com", "01000000000", "M", "관리자", "12-12", null,0);
		memberDAO.memberInsert(memberVO);  
	}
	
	/* memberInsert() Test 코드 */
	@Test
	public void memberInsertTest() {   
		
		memberDAO.deleteALL();
		assertSame(memberDAO.getTotalCount(), 0);  // deleteALL 검증
		
		int beforeCount = memberDAO.getTotalCount();
		MemberVO memberVO = new MemberVO(0, "testId2", "testPassword", "테스트", "테스트", "test@test.com", "01000000000", "M", "관리자",  "12-12", null,0);
		memberDAO.memberInsert(memberVO);
		assertSame(memberDAO.getTotalCount(), beforeCount+1);
		
		memberDAO.deleteById("testId2");
	}
	
	/* login_confirm() Test */
	@Test
	public void login_confirmTest() {
		MemberVO member = memberDAO.getMemberById("testId");
		assertThat(member.getId(), is("testId"));
		memberDAO.deleteById("testId");
		LOG.info("login_confirm() : success");
		

	}
	
	/* memberUpdate() Test */
	@Test
	public void memberUpdateTest() {
		MemberVO member = memberDAO.getMemberById("testId");
		member.setPassword("TestPassword");
		member.setNickname("TestNickName");
		member.setEmail("TestEMail");
		member.setTel(null);
		memberDAO.memberUpdate(member);
		MemberVO member2 = memberDAO.getMemberById("testId");
		
		assertThat(member.getPassword(), is(member2.getPassword()));
		assertThat(member.getNickname(), is(member2.getNickname()));
		assertThat(member.getEmail(), is(member2.getEmail()));
		assertThat(member.getTel(), is(member2.getTel()));
		
	}
	
	/* getMemberVO() Test */
	@Test
	public void getMemberVOTest() {
		MemberVO member = memberDAO.getMemberById("testId");
		MemberVO memberVo = memberDAO.getMemberVO(member.getMno());
		assertThat(memberVo.getId(), is(member.getId()));
	}
	
	@Test
	public void getMytypeTest() {
		
		
		//PersonalityVO person = memberDAO.getMytype(mno);
	}
}
