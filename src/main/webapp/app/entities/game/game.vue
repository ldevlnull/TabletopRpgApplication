<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('trpgPlanningApplicationApp.game.home.title')" id="game-heading">Games</span>
            <router-link :to="{name: 'GameCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-game">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('trpgPlanningApplicationApp.game.home.createLabel')">
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
        <div class="alert alert-warning" v-if="!isFetching && games && games.length === 0">
            <span v-text="$t('trpgPlanningApplicationApp.game.home.notFound')">No games found</span>
        </div>
        <div class="table-responsive" v-if="games && games.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('trpgPlanningApplicationApp.game.gameName')">Game Name</span></th>
                    <th><span v-text="$t('trpgPlanningApplicationApp.game.playDate')">Play Date</span></th>
                    <th><span v-text="$t('trpgPlanningApplicationApp.game.playersLimit')">Players Limit</span></th>
                    <th><span v-text="$t('trpgPlanningApplicationApp.game.pictureURL')">Picture URL</span></th>
                    <th><span v-text="$t('trpgPlanningApplicationApp.game.description')">Description</span></th>
                    <th><span v-text="$t('trpgPlanningApplicationApp.game.status')">Status</span></th>
                    <th><span v-text="$t('trpgPlanningApplicationApp.game.gameSystem')">Game System</span></th>
                    <th><span v-text="$t('trpgPlanningApplicationApp.game.tags')">Tags</span></th>
                    <th><span v-text="$t('trpgPlanningApplicationApp.game.characters')">Characters</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="game in games"
                    :key="game.id">
                    <td>
                        <router-link :to="{name: 'GameView', params: {gameId: game.id}}">{{game.id}}</router-link>
                    </td>
                    <td>{{game.gameName}}</td>
                    <td v-if="game.playDate"> {{$d(Date.parse(game.playDate), 'short') }}</td>
                    <td>{{game.playersLimit}}</td>
                    <td>{{game.pictureURL}}</td>
                    <td>{{game.description}}</td>
                    <td v-text="$t('trpgPlanningApplicationApp.GameStatus.' + game.status)">{{game.status}}</td>
                    <td>
                        <div v-if="game.gameSystem">
                            <router-link :to="{name: 'GameSystemView', params: {gameSystemId: game.gameSystem.id}}">{{game.gameSystem.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <span v-for="(tags, i) in game.tags" :key="tags.id">{{i > 0 ? ', ' : ''}}
                            <router-link class="form-control-static" :to="{name: 'GameTagView', params: {gameTagId: tags.id}}">{{tags.gameTagName}}</router-link>
                        </span>
                    </td>
                    <td>
                        <span v-for="(characters, i) in game.characters" :key="characters.id">{{i > 0 ? ', ' : ''}}
                            <router-link class="form-control-static" :to="{name: 'CharacterView', params: {characterId: characters.id}}">{{characters.characterName}}</router-link>
                        </span>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'GameView', params: {gameId: game.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'GameEdit', params: {gameId: game.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(game)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
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
