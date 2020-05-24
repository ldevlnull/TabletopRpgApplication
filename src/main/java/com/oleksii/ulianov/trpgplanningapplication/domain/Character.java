package com.oleksii.ulianov.trpgplanningapplication.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Character.
 */
@Entity
@Table(name = "character")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Character implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "character_name", nullable = false)
    private String characterName;

    @Column(name = "is_alive")
    private Boolean isAlive;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @ManyToOne
    @JsonIgnoreProperties("characters")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("characters")
    private GameSystem gameSystem;

    @Column(name = "picture_url", length = 2083)
    private String pictureURL;

    @ManyToMany(mappedBy = "characters")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Game> games = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCharacterName() {
        return characterName;
    }

    public Character characterName(String characterName) {
        this.characterName = characterName;
        return this;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public Boolean isIsAlive() {
        return isAlive;
    }

    public Character isAlive(Boolean isAlive) {
        this.isAlive = isAlive;
        return this;
    }

    public void setIsAlive(Boolean isAlive) {
        this.isAlive = isAlive;
    }

    public User getUser() {
        return user;
    }

    public Character user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GameSystem getGameSystem() {
        return gameSystem;
    }

    public Character gameSystem(GameSystem gameSystem) {
        this.gameSystem = gameSystem;
        return this;
    }

    public void setGameSystem(GameSystem gameSystem) {
        this.gameSystem = gameSystem;
    }

    public Set<Game> getGames() {
        return games;
    }

    public Character games(Set<Game> games) {
        this.games = games;
        return this;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public Boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Character addGame(Game game) {
        this.games.add(game);
        game.getCharacters().add(this);
        return this;
    }

    public Character removeGame(Game game) {
        this.games.remove(game);
        game.getCharacters().remove(this);
        return this;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Character)) {
            return false;
        }
        return id != null && id.equals(((Character) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Character{" +
            "id=" + getId() +
            ", characterName='" + getCharacterName() + "'" +
            ", isAlive='" + isIsAlive() + "'" +
            "}";
    }
}
