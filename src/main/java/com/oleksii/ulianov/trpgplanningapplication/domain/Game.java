package com.oleksii.ulianov.trpgplanningapplication.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.oleksii.ulianov.trpgplanningapplication.domain.enumeration.GameStatus;

/**
 * A Game.
 */
@Entity
@Table(name = "game")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "game_name", nullable = false)
    private String gameName;

    @NotNull
    @Column(name = "play_date", nullable = false)
    private Instant playDate;

    @Column(name = "players_limit")
    private Integer playersLimit;

    @Column(name = "picture_url", length = 2083)
    private String pictureURL;

    @Column(name = "description", length = 8192)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GameStatus status;

    @ManyToOne
    @JsonIgnoreProperties("games")
    private GameSystem gameSystem;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "game_tags",
               joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "id"))
    private Set<GameTag> tags = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "game_characters",
               joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "characters_id", referencedColumnName = "id"))
    private Set<Character> characters = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public Game gameName(String gameName) {
        this.gameName = gameName;
        return this;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Instant getPlayDate() {
        return playDate;
    }

    public Game playDate(Instant playDate) {
        this.playDate = playDate;
        return this;
    }

    public void setPlayDate(Instant playDate) {
        this.playDate = playDate;
    }

    public Integer getPlayersLimit() {
        return playersLimit;
    }

    public Game playersLimit(Integer playersLimit) {
        this.playersLimit = playersLimit;
        return this;
    }

    public void setPlayersLimit(Integer playersLimit) {
        this.playersLimit = playersLimit;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public Game pictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
        return this;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getDescription() {
        return description;
    }

    public Game description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Game status(GameStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public GameSystem getGameSystem() {
        return gameSystem;
    }

    public Game gameSystem(GameSystem gameSystem) {
        this.gameSystem = gameSystem;
        return this;
    }

    public void setGameSystem(GameSystem gameSystem) {
        this.gameSystem = gameSystem;
    }

    public Set<GameTag> getTags() {
        return tags;
    }

    public Game tags(Set<GameTag> gameTags) {
        this.tags = gameTags;
        return this;
    }

    public Game addTags(GameTag gameTag) {
        this.tags.add(gameTag);
        gameTag.getGames().add(this);
        return this;
    }

    public Game removeTags(GameTag gameTag) {
        this.tags.remove(gameTag);
        gameTag.getGames().remove(this);
        return this;
    }

    public void setTags(Set<GameTag> gameTags) {
        this.tags = gameTags;
    }

    public Set<Character> getCharacters() {
        return characters;
    }

    public Game characters(Set<Character> characters) {
        this.characters = characters;
        return this;
    }

    public Game addCharacters(Character character) {
        this.characters.add(character);
        character.getGames().add(this);
        return this;
    }

    public Game removeCharacters(Character character) {
        this.characters.remove(character);
        character.getGames().remove(this);
        return this;
    }

    public void setCharacters(Set<Character> characters) {
        this.characters = characters;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Game)) {
            return false;
        }
        return id != null && id.equals(((Game) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Game{" +
            "id=" + getId() +
            ", gameName='" + getGameName() + "'" +
            ", playDate='" + getPlayDate() + "'" +
            ", playersLimit=" + getPlayersLimit() +
            ", pictureURL='" + getPictureURL() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
