package org.choongang.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class ProjectTest {

    @Autowired
    private MockMvc mockMvc;

    /*@Test
    @DisplayName("새 프로젝트 생성 테스트")
    void newProjectTest() throws Exception {
        RequestProjectForm form = RequestProjectForm.builder()
                .pName("test제목")
                .description("test 상세 설명")
                .build();

        ObjectMapper om = new ObjectMapper();
        String params = om.writeValueAsString(form);

        mockMvc.perform(
                post("/api/v1/project")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8")
                        .content(params)
                //.with(csrf().asHeader())
        ).andDo(print());
    }*/
}
