package com.manager.data.housing.housingsupportmanager;

import com.manager.data.housing.housingsupportmanager.model.Council;
import com.manager.data.housing.housingsupportmanager.service.CouncilService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CouncilServiceTest {

    @Mock
    CouncilService councilService;

    List<Council> councilTestData = new ArrayList<Council>();

    @Test
    void testListAll() {
        Council sheffieldCouncil = new Council();
        Council manchesterCouncil = new Council();

        sheffieldCouncil.setName("Sheffield City Council");
        manchesterCouncil.setName("Manchester City Council");

        councilTestData.add(sheffieldCouncil);
        councilTestData.add(manchesterCouncil);

        when(councilService.listAll()).thenReturn(councilTestData);
        List<Council> foundCouncils = councilService.listAll();

        verify(councilService).listAll();
        assertThat(foundCouncils).hasSize(2);
        assertThat(foundCouncils.get(0).getName().equalsIgnoreCase("Sheffield City Council"));
    }

    @Test
    void testSave() {
        Council sheffieldCouncil = new Council();
        sheffieldCouncil.setName("Sheffield City Council");

        councilService.save(sheffieldCouncil);
        verify(councilService).save(sheffieldCouncil);
    }

    @Test
    void testGet() {
        Council council = new Council();
        council.setName("Leeds City Council");

        when(councilService.get(anyLong())).thenReturn(council);
        Council foundCouncil = councilService.get(13L);

        verify(councilService).get(anyLong());
        assertThat(foundCouncil).isNotNull();
        assertThat(foundCouncil.getName().equalsIgnoreCase("Leeds City Council"));
    }

    @Test
    void testDelete() {
        councilService.delete(1L);
        verify(councilService).delete(anyLong());
    }

}