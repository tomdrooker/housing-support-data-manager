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
        Council sheffieldCouncil = new Council();
        sheffieldCouncil.setName("Sheffield City Council");
        sheffieldCouncil.setId(1L);
        councilRepository.save(sheffieldCouncil);

        List<Council> foundCouncils = councilRepository.findAll();

        assertNotNull(foundCouncils);
        assertThat(foundCouncils.size()).isEqualTo(1);
        assertThat(foundCouncils.get(0).getName().equalsIgnoreCase("Sheffield City Council"));
    }

}