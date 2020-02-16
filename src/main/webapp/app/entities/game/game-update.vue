<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="trpgPlanningApplicationApp.game.home.createOrEditLabel" v-text="$t('trpgPlanningApplicationApp.game.home.createOrEditLabel')">Create or edit a Game</h2>
                <div>
                    <div class="form-group" v-if="game.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="game.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.game.gameName')" for="game-gameName">Game Name</label>
                        <input type="text" class="form-control" name="gameName" id="game-gameName"
                            :class="{'valid': !$v.game.gameName.$invalid, 'invalid': $v.game.gameName.$invalid }" v-model="$v.game.gameName.$model"  required/>
                        <div v-if="$v.game.gameName.$anyDirty && $v.game.gameName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.game.gameName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.game.playDate')" for="game-playDate">Play Date</label>
                        <div class="d-flex">
                            <input id="game-playDate" type="datetime-local" class="form-control" name="playDate" :class="{'valid': !$v.game.playDate.$invalid, 'invalid': $v.game.playDate.$invalid }"
                             required
                            :value="convertDateTimeFromServer($v.game.playDate.$model)"
                            @change="updateInstantField('playDate', $event)"/>
                        </div>
                        <div v-if="$v.game.playDate.$anyDirty && $v.game.playDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.game.playDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.game.playDate.ZonedDateTimelocal" v-text="$t('entity.validation.ZonedDateTimelocal')">
                                This field should be a date and time.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.game.playersLimit')" for="game-playersLimit">Players Limit</label>
                        <input type="number" class="form-control" name="playersLimit" id="game-playersLimit"
                            :class="{'valid': !$v.game.playersLimit.$invalid, 'invalid': $v.game.playersLimit.$invalid }" v-model.number="$v.game.playersLimit.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.game.pictureURL')" for="game-pictureURL">Picture URL</label>
                        <input type="text" class="form-control" name="pictureURL" id="game-pictureURL"
                            :class="{'valid': !$v.game.pictureURL.$invalid, 'invalid': $v.game.pictureURL.$invalid }" v-model="$v.game.pictureURL.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.game.description')" for="game-description">Description</label>
                        <input type="text" class="form-control" name="description" id="game-description"
                            :class="{'valid': !$v.game.description.$invalid, 'invalid': $v.game.description.$invalid }" v-model="$v.game.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.game.status')" for="game-status">Status</label>
                        <select class="form-control" name="status" :class="{'valid': !$v.game.status.$invalid, 'invalid': $v.game.status.$invalid }" v-model="$v.game.status.$model" id="game-status"  required>
                            <option value="PENDING" v-bind:label="$t('trpgPlanningApplicationApp.GameStatus.PENDING')">PENDING</option>
                            <option value="IN_PROGRESS" v-bind:label="$t('trpgPlanningApplicationApp.GameStatus.IN_PROGRESS')">IN_PROGRESS</option>
                            <option value="CANCELLED" v-bind:label="$t('trpgPlanningApplicationApp.GameStatus.CANCELLED')">CANCELLED</option>
                            <option value="ENDED" v-bind:label="$t('trpgPlanningApplicationApp.GameStatus.ENDED')">ENDED</option>
                        </select>
                        <div v-if="$v.game.status.$anyDirty && $v.game.status.$invalid">
                            <small class="form-text text-danger" v-if="!$v.game.status.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.game.gameSystem')" for="game-gameSystem">Game System</label>
                        <select class="form-control" id="game-gameSystem" name="gameSystem" v-model="game.gameSystem">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="game.gameSystem && gameSystemOption.id === game.gameSystem.id ? game.gameSystem : gameSystemOption" v-for="gameSystemOption in gameSystems" :key="gameSystemOption.id">{{gameSystemOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label v-text="$t('trpgPlanningApplicationApp.game.tags')" for="game-tags">Tags</label>
                        <select class="form-control" id="game-tags" multiple name="tags" v-model="game.tags">
                            <option v-bind:value="getSelected(game.tags, gameTagOption)" v-for="gameTagOption in gameTags" :key="gameTagOption.id">{{gameTagOption.gameTagName}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label v-text="$t('trpgPlanningApplicationApp.game.characters')" for="game-characters">Characters</label>
                        <select class="form-control" id="game-characters" multiple name="characters" v-model="game.characters">
                            <option v-bind:value="getSelected(game.characters, characterOption)" v-for="characterOption in characters" :key="characterOption.id">{{characterOption.characterName}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.game.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./game-update.component.ts">
</script>
