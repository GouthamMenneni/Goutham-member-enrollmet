package com.memberenroll.MemberEnrollment;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(MemberController.class)
public class MemberControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberRepository memberRepository;
    
    @InjectMocks
    private MemberController memberController;

    @Test
    public void testEnrollMember_ValidInput_ReturnsCreated() throws Exception {
        Member member = new Member();
        member.setFirstName("Goutham");
        member.setLastName("Menneni");
        member.setEmail("gouthamtech@example.com");
        member.setBirthdate(LocalDate.of(1993, 24, 6));

        when(memberRepository.save(any(Member.class))).thenReturn(member);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"birthdate\":\"1990-01-01\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Goutham"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Menneni"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("gouthamtech@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthdate").value("1993-24-06"));
    }

    @Test
    public void testGetMember_ExistingMember_ReturnsMember() throws Exception {
        Member member = new Member();
        member.setId(1L);
        member.setFirstName("John");
        member.setLastName("Doe");
        member.setEmail("john.doe@example.com");
        member.setBirthdate(LocalDate.of(1998, 24, 06));

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Goutham"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Menneni"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("gouthamtech@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthdate").value("1998-24-06"));
    }
}

