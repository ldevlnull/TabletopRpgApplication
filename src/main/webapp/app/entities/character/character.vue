<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('trpgPlanningApplicationApp.character.home.title')" id="character-heading">Characters</span>
            <router-link :to="{name: 'CharacterCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-character">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('trpgPlanningApplicationApp.character.home.createLabel')">
                    Create a new Character
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
        <div class="alert alert-warning" v-if="!isFetching && characters && characters.length === 0">
            <span v-text="$t('trpgPlanningApplicationApp.character.home.notFound')">No characters found</span>
        </div>
        <div class="table-responsive" v-if="characters && characters.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('characterName')"><span v-text="$t('trpgPlanningApplicationApp.character.characterName')">Character Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'characterName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('isAlive')"><span v-text="$t('trpgPlanningApplicationApp.character.isAlive')">Is Alive</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isAlive'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('user.id')"><span v-text="$t('trpgPlanningApplicationApp.character.user')">User</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'user.id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('gameSystem.id')"><span v-text="$t('trpgPlanningApplicationApp.character.gameSystem')">Game System</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'gameSystem.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="character in characters"
                    :key="character.id">
                    <td>
                        <router-link :to="{name: 'CharacterView', params: {characterId: character.id}}">{{character.id}}</router-link>
                    </td>
                    <td>{{character.characterName}}</td>
                    <td>{{character.isAlive}}</td>
                    <td>
                        {{character.user ? character.user.id : ''}}
                    </td>
                    <td>
                        <div v-if="character.gameSystem">
                            <router-link :to="{name: 'GameSystemView', params: {gameSystemId: character.gameSystem.id}}">{{character.gameSystem.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CharacterView', params: {characterId: character.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CharacterEdit', params: {characterId: character.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(character)"
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
            <span slot="modal-title"><span id="trpgPlanningApplicationApp.character.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-character-heading" v-text="$t('trpgPlanningApplicationApp.character.delete.question', {'id': removeId})">Are you sure you want to delete this Character?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-character" v-text="$t('entity.action.delete')" v-on:click="removeCharacter()">Delete</button>
            </div>
        </b-modal>
        <div v-show="characters && characters.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./character.component.ts">
</script>
