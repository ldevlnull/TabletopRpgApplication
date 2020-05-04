<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('trpgPlanningApplicationApp.gameSystem.home.title')" id="game-system-heading">Game Systems</span>
            <router-link :to="{name: 'GameSystemCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-game-system">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('trpgPlanningApplicationApp.gameSystem.home.createLabel')">
                    Create a new Game System
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
        <div class="alert alert-warning" v-if="!isFetching && gameSystems && gameSystems.length === 0">
            <span v-text="$t('trpgPlanningApplicationApp.gameSystem.home.notFound')">No gameSystems found</span>
        </div>
        <div class="table-responsive" v-if="gameSystems && gameSystems.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('trpgPlanningApplicationApp.gameSystem.gameSystemName')">Game System Name</span></th>
                    <th><span v-text="$t('trpgPlanningApplicationApp.gameSystem.description')">Description</span></th>
                    <th><span v-text="$t('trpgPlanningApplicationApp.gameSystem.pictureURL')">Picture URL</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="gameSystem in gameSystems"
                    :key="gameSystem.id">
                    <td>
                        <router-link :to="{name: 'GameSystemView', params: {gameSystemId: gameSystem.id}}">{{gameSystem.id}}</router-link>
                    </td>
                    <td>{{gameSystem.gameSystemName}}</td>
                    <td>{{gameSystem.description}}</td>
                    <td>{{gameSystem.pictureURL}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'GameSystemView', params: {gameSystemId: gameSystem.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'GameSystemEdit', params: {gameSystemId: gameSystem.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(gameSystem)"
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
            <span slot="modal-title"><span id="trpgPlanningApplicationApp.gameSystem.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-gameSystem-heading" v-text="$t('trpgPlanningApplicationApp.gameSystem.delete.question', {'id': removeId})">Are you sure you want to delete this Game System?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-gameSystem" v-text="$t('entity.action.delete')" v-on:click="removeGameSystem()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./game-system.component.ts">
</script>
