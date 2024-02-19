package org.choongang.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.choongang.commons.constants.MemberType;
import org.choongang.commons.rests.JSONData;
import org.choongang.member.controller.RequestLogin;
import org.choongang.member.entities.Member;
import org.choongang.member.service.MemberJoinService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@Transactional
public class MemberLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberJoinService joinService;

    @Autowired
    private PasswordEncoder encoder;

    private Member member;

    @BeforeEach
    void init() {
        member = Member.builder()
                .email("user01@test.org")
                .password(encoder.encode("_aA123456"))
                .name("사용자01")
                .mobile("01000000000")
                .type(MemberType.USER)
                .build();
        joinService.save(member);
    }

    @Test
    @DisplayName("로그인시 토큰이 발급 되는지 확인")
    void loginTest() throws Exception {
        RequestLogin form = RequestLogin.builder()
                .email(member.getEmail())
                .password("_aA123456")
                .build();
        ObjectMapper om = new ObjectMapper();
        String params = om.writeValueAsString(form);

        String body = mockMvc.perform(
                        post("/api/v1/member/token")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .content(params)
                ).andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        JSONData data = om.readValue(body, JSONData.class);
        String accessToken = (String)data.getData();

    }

    @Test
    @DisplayName("미로그인 상태(토큰이 없는)시 회원 전용 URL 접근 통제 테스트")
    void guestAccessTest() throws Exception {
        mockMvc.perform(get("/api/v1/member/info"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}