package com.oleksii.ulianov.trpgplanningapplication.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.oleksii.ulianov.trpgplanningapplication.web.rest.TestUtil;

public class GameSystemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameSystem.class);
        GameSystem gameSystem1 = new GameSystem();
        gameSystem1.setId(1L);
        GameSystem gameSystem2 = new GameSystem();
        gameSystem2.setId(gameSystem1.getId());
        assertThat(gameSystem1).isEqualTo(gameSystem2);
        gameSystem2.setId(2L);
        assertThat(gameSystem1).isNotEqualTo(gameSystem2);
        gameSystem1.setId(null);
        assertThat(gameSystem1).isNotEqualTo(gameSystem2);
    }
}
