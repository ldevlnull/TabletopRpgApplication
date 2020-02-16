<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('trpgPlanningApplicationApp.gameTag.home.title')" id="game-tag-heading">Game Tags</span>
            <router-link :to="{name: 'GameTagCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-game-tag">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('trpgPlanningApplicationApp.gameTag.home.createLabel')">
                    Create a new Game Tag
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
        <div class="alert alert-warning" v-if="!isFetching && gameTags && gameTags.length === 0">
            <span v-text="$t('trpgPlanningApplicationApp.gameTag.home.notFound')">No gameTags found</span>
        </div>
        <div class="table-responsive" v-if="gameTags && gameTags.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('trpgPlanningApplicationApp.gameTag.gameTagName')">Game Tag Name</span></th>
                    <th><span v-text="$t('trpgPlanningApplicationApp.gameTag.description')">Description</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="gameTag in gameTags"
                    :key="gameTag.id">
                    <td>
                        <router-link :to="{name: 'GameTagView', params: {gameTagId: gameTag.id}}">{{gameTag.id}}</router-link>
                    </td>
                    <td>{{gameTag.gameTagName}}</td>
                    <td>{{gameTag.description}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'GameTagView', params: {gameTagId: gameTag.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'GameTagEdit', params: {gameTagId: gameTag.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(gameTag)"
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
            <span slot="modal-title"><span id="trpgPlanningApplicationApp.gameTag.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-gameTag-heading" v-text="$t('trpgPlanningApplicationApp.gameTag.delete.question', {'id': removeId})">Are you sure you want to delete this Game Tag?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-gameTag" v-text="$t('entity.action.delete')" v-on:click="removeGameTag()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./game-tag.component.ts">
</script>
