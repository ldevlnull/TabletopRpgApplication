package com.oleksii.ulianov.trpgplanningapplication.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A GameSystem.
 */
@Entity
@Table(name = "game_system")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GameSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "game_system_name", nullable = false)
    private String gameSystemName;

    @Column(name = "description")
    private String description;

    @Column(name = "picture_url")
    private String pictureURL;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameSystemName() {
        return gameSystemName;
    }

    public GameSystem gameSystemName(String gameSystemName) {
        this.gameSystemName = gameSystemName;
        return this;
    }

    public void setGameSystemName(String gameSystemName) {
        this.gameSystemName = gameSystemName;
    }

    public String getDescription() {
        return description;
    }

    public GameSystem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public GameSystem pictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
        return this;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameSystem)) {
            return false;
        }
        return id != null && id.equals(((GameSystem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GameSystem{" +
            "id=" + getId() +
            ", gameSystemName='" + getGameSystemName() + "'" +
            ", description='" + getDescription() + "'" +
            ", pictureURL='" + getPictureURL() + "'" +
            "}";
    }
}
