package api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import api.model.Color;
import api.model.ColorRepository;
import api.model.Statistic;
import api.model.StatisticRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
@Tag("integration")
public class BackOfficeRessourceTestMock {

    @InjectMock
    StatisticRepository statisticRepository;

    @InjectMock ColorRepository colorRepository;

    BackOfficeRessource backOfficeRessource = new BackOfficeRessource();

    private Statistic statistic;
    private Color color;



    @BeforeEach
    void setUp() {
        statistic = new Statistic();
        statistic.id = 1L;
        statistic.number_of_user_connected = 30;
        statistic.number_of_user_compte = 100;
        statistic.number_of_user_commands = 50;
        statistic.number_of_abandoned_bag = 10;
        color = new Color();
        color.id = 1L;
        color.color_name = "black";
        color.number_of_commands = 16;
    }

    @Test
    void getNumberOfUserConnected() {
        List<Statistic> stats = new ArrayList<Statistic>();
        stats.add(statistic);
        Mockito.when(statisticRepository.list("SELECT number_of_user_connected FROM Statistic")).thenReturn(stats);
        Response response = backOfficeRessource.getNumberOfUserConnected();
        assertNotNull(response);
        assertFalse(stats.isEmpty());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(stats.get(0).number_of_user_connected, 30);

    }

    @Test
    void getNumbOfAccountsCreated() {
        List<Color> colors = new ArrayList<>();
        colors.add(color);
        Mockito.when(colorRepository.list("SELECT color_name, number_of_commands FROM Color")).thenReturn(colors);
        Response response = backOfficeRessource.getColorsStats();
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        List<Color> color_entity = (List<Color>) response.getEntity();
        assertEquals(color_entity.size(), 7);
        Color corresponding_color = colors.get(0);
        assertEquals(corresponding_color.number_of_commands, 16);
        assertEquals(corresponding_color.color_name, "black");

    }


    
}
