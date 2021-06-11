package main.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;


class RestServiceTest {

    /**
     * TIENE UN METODO GET JSON
     */

    /*
    @Mock
    Double latMock, lonMock;
    String latitudeMock, longitudeMock, query;
    Map<String, Object>  dataMock;
    @Test
    void getGeoData() {
        when(latMock.toString().replace(",", ".")).thenReturn(latitudeMock);
        when(lonMock.toString().replace(",", ".")).thenReturn(longitudeMock);
        when(String.format("https://nominatim.openstreetmap.org/reverse?format=json&lat=%s8&lon=%s&accept-language=en", latitudeMock, longitudeMock)).thenReturn(query);
        when((Map<String, Object>) getJSON(query).get("address")).thenReturn(dataMock);
        when(!dataMock.containsKey("city")).thenReturn(true);
        when(dataMock.containsKey("village")).thenReturn(true);
        when(dataMock.containsKey("town")).thenReturn(true);
        when(dataMock.containsKey("road")).thenReturn(true);
        assertNotNull(dataMock);
    }


     */

}