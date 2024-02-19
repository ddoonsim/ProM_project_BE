package org.choongang.project;

import org.choongang.project.entities.Project;
import org.choongang.project.service.ProjectInfoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class ProjectTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProjectInfoService infoService;

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

    @Test
    @DisplayName("회원 별 참여 중인 프로젝트 목록 가져오기")
    public void printList() {
        List<Project> projects =  infoService.getProjects(1L);
        for (Project p : projects) {
            System.out.println(p);
        }
    }
}
