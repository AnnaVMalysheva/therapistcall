package ru.therapistcall;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.therapistcall.dtos.DoctorDto;
import ru.therapistcall.dtos.DoctorInfoDto;
import ru.therapistcall.dtos.DoctorPolyclinicDto;
import ru.therapistcall.entities.Polyclinic;
import ru.therapistcall.repositories.DoctorRepository;
import ru.therapistcall.repositories.PolyclinicRepository;
import ru.therapistcall.services.DoctorService;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TherapistCallApplication.class, webEnvironment = RANDOM_PORT)
@PropertySource("classpath:/application.properties")
@Slf4j
public class TherapistControllerIntegrationTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PolyclinicRepository polyclinicRepository;

    @Autowired
    private DoctorService doctorService;

    public static final String SAVE_DOCTOR_URL = "/saveDoctor";


    @After
    public void clean() {
        doctorRepository.deleteAll();
    }


    @Test
    public void addDoctorRequest() throws Exception {
        Polyclinic polyclinic = polyclinicRepository.save(Polyclinic.builder().name("test polyclinic").build());

        DoctorDto doctorDto = DoctorDto.builder().firstName("Anna").lastName("M").build();
        DoctorPolyclinicDto doctorPolyclinicDto = DoctorPolyclinicDto.builder().polyclinicId(polyclinic.getId()).equipped(true).build();

        DoctorInfoDto doctorInfoDto = new DoctorInfoDto();
        doctorInfoDto.setDoctorDto(doctorDto);
        doctorInfoDto.setDoctorPolyclinicDto(doctorPolyclinicDto);

        MvcResult result = mockMvc.perform(post(SAVE_DOCTOR_URL)
                .content(objectMapper.writeValueAsString(doctorInfoDto)).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        DoctorDto addedDoctorDto = objectMapper.readValue(result.getResponse().getContentAsString(), DoctorDto.class);

        assertEquals(doctorDto.getFirstName(), addedDoctorDto.getFirstName());
        assertEquals(doctorDto.getLastName(), addedDoctorDto.getLastName());

    }


}