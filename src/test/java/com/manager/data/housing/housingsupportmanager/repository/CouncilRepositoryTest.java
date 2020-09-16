package com.manager.data.housing.housingsupportmanager.repository;

import com.manager.data.housing.housingsupportmanager.model.Council;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CouncilRepositoryTest {

    @Autowired
    CouncilRepository councilRepository;

    @Test
    void testListAll() {
        Council council = new Council();
        council.setName("Sheffield City Council");
        council.setAddress("1 main street");
        council.setEmail("housing@sheffield.gov.uk");
        council.setPhone("0114 2345678");
        council.setDhp("http://www.dhp.co.uk");
        council.setList("http://www.list.co.uk");
        council.setInfo("http://www.info.co.uk");
        councilRepository.save(council);

        List<Council> foundCouncils = councilRepository.findAll();

        assertNotNull(foundCouncils);
        assertThat(foundCouncils.size()).isEqualTo(1);
        assertThat(foundCouncils.get(0).getName().equalsIgnoreCase("Sheffield City Council"));
    }

}