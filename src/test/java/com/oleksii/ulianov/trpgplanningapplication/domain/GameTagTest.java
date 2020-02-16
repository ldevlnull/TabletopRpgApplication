package com.oleksii.ulianov.trpgplanningapplication.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.oleksii.ulianov.trpgplanningapplication.web.rest.TestUtil;

public class GameTagTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameTag.class);
        GameTag gameTag1 = new GameTag();
        gameTag1.setId(1L);
        GameTag gameTag2 = new GameTag();
        gameTag2.setId(gameTag1.getId());
        assertThat(gameTag1).isEqualTo(gameTag2);
        gameTag2.setId(2L);
        assertThat(gameTag1).isNotEqualTo(gameTag2);
        gameTag1.setId(null);
        assertThat(gameTag1).isNotEqualTo(gameTag2);
    }
}
