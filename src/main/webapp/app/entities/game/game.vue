<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('trpgPlanningApplicationApp.game.home.title')" id="game-heading">Games</span>
            <router-link :to="{name: 'GameCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-game">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span v-text="$t('trpgPlanningApplicationApp.game.home.createLabel')">
                    Create a new Game
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
                 dismissible
                 :variant="alertType"
                 @dismissed="dismissCountDown=0"
                 @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && gamesByDays && gamesByDays.length === 0">
            <span v-text="$t('trpgPlanningApplicationApp.game.home.notFound')">No games found</span>
        </div>
        <div>
            <div class="games">
                <div v-for="dayGames in gamesByDays">
                    <div class="popover-header">{{moment(Object.getOwnPropertyNames(dayGames)[0]).format("dddd, DD MMMM YYYY")}}</div>
                    <div class="game-view-content" v-for="game in dayGames[Object.getOwnPropertyNames(dayGames)[0]]" :key="game.id">
                        <div class="game block">
                            <router-link :to="{name: 'GameView', params: {gameId: game.id}}">
                                <b-card
                                    overlay
                                    :title="moment(game.playDate).format('HH:mm')"
                                    text-variant="white"
                                    :img-src="game.pictureURL"
                                    style="max-width: 300px;"
                                    img-height="400px"
                                    class="pic img-responsive"
                                    img-alt="Game Picture"
                                    body-class="top align-text-top"
                                >
                                    <div class="text-right top-right">
                                        <b-card-img style="max-width: 64px;" height="32px" :alt="game.gameSystem.gameSystemName" :src="game.gameSystem.pictureURL"></b-card-img>
                                    </div>
                                    <div class="text-center text-bottom">
                                        <div v-for="tag in game.tags" :key="tag.id">
                                            <router-link class="text-white text-stroke" :to="{name: 'GameTagView', params: {gameTagId: tag.id}}">
                                                {{tag.gameTagName}}
                                            </router-link>
                                        </div>
                                    </div>
                                </b-card>
                                <div class="text-center text">{{game.gameName}}</div>
                            </router-link>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <b-modal ref="removeEntity" id="removeEntity">
            <span slot="modal-title"><span id="trpgPlanningApplicationApp.game.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-game-heading" v-text="$t('trpgPlanningApplicationApp.game.delete.question', {'id': removeId})">Are you sure you want to delete this Game?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-game" v-text="$t('entity.action.delete')" v-on:click="removeGame()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./game.component.ts">
</script>

<style>
    .game-view-content {
        display: inline-flex;
        flex-direction: row;
    }

    .game {
        margin: 1em;
    }

    .text-stroke {
        color: white;
        text-shadow: -1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000, 1px 1px 0 #000;
    }

    .text-bottom {
        vertical-align: text-bottom;
    }
</style>
