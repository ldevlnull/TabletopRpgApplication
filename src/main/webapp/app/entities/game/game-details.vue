<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="game">
                <h2 class="jh-entity-heading"><span v-text="$t('trpgPlanningApplicationApp.game.detail.title')">Game</span> {{game.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('trpgPlanningApplicationApp.game.gameName')">Game Name</span>
                    </dt>
                    <dd>
                        <span>{{game.gameName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('trpgPlanningApplicationApp.game.playDate')">Play Date</span>
                    </dt>
                    <dd>
                        <span v-if="game.playDate">{{$d(Date.parse(game.playDate), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('trpgPlanningApplicationApp.game.playersLimit')">Players Limit</span>
                    </dt>
                    <dd>
                        <span>{{game.playersLimit}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('trpgPlanningApplicationApp.game.pictureURL')">Picture URL</span>
                    </dt>
                    <dd>
                        <span>{{game.pictureURL}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('trpgPlanningApplicationApp.game.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{game.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('trpgPlanningApplicationApp.game.status')">Status</span>
                    </dt>
                    <dd>
                        <span v-text="$t('trpgPlanningApplicationApp.GameStatus.' + game.status)">{{game.status}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('trpgPlanningApplicationApp.game.gameSystem')">Game System</span>
                    </dt>
                    <dd>
                        <div v-if="game.gameSystem">
                            <router-link :to="{name: 'GameSystemView', params: {gameSystemId: game.gameSystem.id}}">{{game.gameSystem.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('trpgPlanningApplicationApp.game.tags')">Tags</span>
                    </dt>
                    <dd>
                        <span v-for="(tags, i) in game.tags" :key="tags.id">{{i > 0 ? ', ' : ''}}
                            <router-link :to="{name: 'GameTagView', params: {gameTagId: tags.id}}">{{tags.gameTagName}}</router-link>
                        </span>
                    </dd>
                    <dt>
                        <span v-text="$t('trpgPlanningApplicationApp.game.characters')">Characters</span>
                    </dt>
                    <dd>
                        <span v-for="(characters, i) in game.characters" :key="characters.id">{{i > 0 ? ', ' : ''}}
                            <router-link :to="{name: 'CharacterView', params: {characterId: characters.id}}">{{characters.characterName}}</router-link>
                        </span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="game.id" :to="{name: 'GameEdit', params: {gameId: game.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./game-details.component.ts">
</script>
