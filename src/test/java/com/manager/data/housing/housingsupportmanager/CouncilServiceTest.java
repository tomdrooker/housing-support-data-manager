package com.manager.data.housing.housingsupportmanager;

import com.manager.data.housing.housingsupportmanager.model.Council;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouncilServiceTest {

    @Mock
    CouncilService councilService;

    @Test
    void testListAll() {
        List<Council> councilTestData = new ArrayList<Council>();
        councilTestData.add(new Council());
        councilTestData.add(new Council());

        when(councilService.listAll()).thenReturn(councilTestData);
        List<Council> foundCouncils = councilService.listAll();

        verify(councilService).listAll();
        assertThat(foundCouncils).hasSize(2);
    }

    @Test
    void testSave() {
//        Council sheffieldCouncil = new Council();
//        sheffieldCouncil.setName("Sheffield City Council");
//
//        when(councilService.save(any(Council.class))).thenReturn(sheffieldCouncil);
//
//        Council savedCouncil = councilService.save(new Council());
//
//        verify(councilService).save(any(Council.class));
    }

    @Test
    void testGet() {
        Council council = new Council();

        when(councilService.get(anyLong())).thenReturn(council);
        Council foundCouncil = councilService.get(13L);

        verify(councilService).get(anyLong());
        assertThat(foundCouncil).isNotNull();
    }

    @Test
    void testDelete() {
        councilService.delete(1L);
        verify(councilService).delete(anyLong());
    }

}