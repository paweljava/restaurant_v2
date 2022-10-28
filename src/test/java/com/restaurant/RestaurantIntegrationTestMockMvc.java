package com.restaurant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.model.Meal;
import com.restaurant.model.Restaurant;
import com.restaurant.model.RestaurantType;
import com.restaurant.repository.MealRepository;
import com.restaurant.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static com.restaurant.model.RestaurantType.FRENCH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantIntegrationTestMockMvc {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MealRepository mealRepository;

    private Restaurant restaurant;
    private Meal meal;

    @BeforeEach
    public void beforeEach() {
        final var restaurantId = UUID.fromString("d5c46266-43d3-11ed-b878-0242ac120002");
        restaurant = new Restaurant(
                restaurantId,
                "Hamburgerlandia",
                "Wrocław",
                RestaurantType.AMERICAN);

        restaurantRepository.save(restaurant);
        meal = new Meal(restaurantId, "Pierogi", 22);
        mealRepository.save(meal);
    }

    @Test
    void should_create_restaurant() throws Exception {
        // given
        final var body = "{\"name\": \"Góralska\", \"address\": \"Węgry\", \"type\": \"ASIAN\"}";

        // when
        MvcResult mvcResult = mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        // then
        Restaurant response = objectMapper.readValue(mvcResult.getResponse()
                .getContentAsString(StandardCharsets.UTF_8), Restaurant.class);
        assertThat(response).isNotNull();
        // assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo("Góralska");
        assertThat(response.getAddress()).isEqualTo("Węgry");
        assertThat(response.getType().toString()).isEqualTo("ASIAN");
    }

    @Test
    void should_get_restaurants() throws Exception {
        // given
        // when
        mockMvc.perform(get("/restaurants"))
                .andDo(print())
                .andExpect(status().is(200));

        // then
    }

    @Test
    void should_get_restaurant_by_id() throws Exception {
        // given
        // when
        MvcResult mvcResult = mockMvc.perform(get("/restaurants/" + restaurant.getId()))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        // then
        Restaurant response = objectMapper.readValue(mvcResult.getResponse()
                .getContentAsString(StandardCharsets.UTF_8), Restaurant.class);
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(restaurant.getName());
        assertThat(response.getAddress()).isEqualTo(restaurant.getAddress());
        assertThat(response.getType()).isEqualTo(restaurant.getType());
    }

    @Test
    void should_update_restaurant() throws Exception {
        // given
        final var body = "{\"name\": \"Kołobrzeg\", \"address\": \"Austria\", \"type\": \"FRENCH\"}";

        // when
        MvcResult mvcResult = mockMvc.perform(put(("/restaurants/") + restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        // then
        Restaurant response = objectMapper.readValue(mvcResult.getResponse()
                .getContentAsString(StandardCharsets.UTF_8), Restaurant.class);
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("Kołobrzeg");
        assertThat(response.getAddress()).isEqualTo("Austria");
        assertThat(response.getType()).isEqualTo(FRENCH);
    }

    @Test
    void should_delete_restaurant() throws Exception {
        // given
        // when
        MvcResult mvcResult = mockMvc.perform(delete("/restaurants/" + restaurant.getId()))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        // then
    }

    @Test
    void should_create_meal() throws Exception {
        // given
        final var body = "{\"name\": \"Pizza\", \"price\": \"33\"}";

        // when
        MvcResult mvcResult = mockMvc.perform(post("/restaurants/" + restaurant.getId() + "/meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        // then
        Meal response = objectMapper.readValue(mvcResult.getResponse()
                .getContentAsString(StandardCharsets.UTF_8), Meal.class);
        assertThat(response).isNotNull();
        // assertThat(response.getRestaurantId()).isNotNull();
        // assertThat(response.getRestaurantId()).isEqualTo(restaurant.getId());
        assertThat(response.getName()).isEqualTo("Pizza");
        assertThat(response.getPrice()).isEqualTo(33);
    }

    @Test
    void should_update_meal() throws Exception {
        // given
        final var body = "{\"name\": \"Barszcz\", \"price\": \"25.7\"}";

        // when
        MvcResult mvcResult = mockMvc.perform(put("/restaurants/" + restaurant.getId() + "/meals/" + meal.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        // then
        Meal response = objectMapper.readValue(mvcResult.getResponse()
                .getContentAsString(StandardCharsets.UTF_8), Meal.class);
        assertThat(response).isNotNull();
        // assertThat(response.getRestaurantId()).isNotNull();
        // assertThat(response.getRestaurantId()).isEqualTo(restaurant.getId());
        assertThat(response.getName()).isEqualTo("Barszcz");
        assertThat(response.getPrice()).isEqualTo(25.7f);
    }

    @Test
    void should_delete_meal() throws Exception {
        // given
        // when
        MvcResult mvcResult = mockMvc.perform(delete("/restaurants/" + restaurant.getId() + "/meals/" + meal.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        // then
    }
}
