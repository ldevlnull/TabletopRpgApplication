<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="trpgPlanningApplicationApp.character.home.createOrEditLabel" v-text="$t('trpgPlanningApplicationApp.character.home.createOrEditLabel')">Create or edit a Character</h2>
                <div>
                    <div class="form-group" v-if="character.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="character.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.character.characterName')" for="character-characterName">Character Name</label>
                        <input type="text" class="form-control" name="characterName" id="character-characterName"
                            :class="{'valid': !$v.character.characterName.$invalid, 'invalid': $v.character.characterName.$invalid }" v-model="$v.character.characterName.$model"  required/>
                        <div v-if="$v.character.characterName.$anyDirty && $v.character.characterName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.character.characterName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.character.isAlive')" for="character-isAlive">Is Alive</label>
                        <input type="checkbox" class="form-check" name="isAlive" id="character-isAlive"
                            :class="{'valid': !$v.character.isAlive.$invalid, 'invalid': $v.character.isAlive.$invalid }" v-model="$v.character.isAlive.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.character.user')" for="character-user">User</label>
                        <select class="form-control" id="character-user" name="user" v-model="character.user">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="character.user && userOption.id === character.user.id ? character.user : userOption" v-for="userOption in users" :key="userOption.id">{{userOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.character.gameSystem')" for="character-gameSystem">Game System</label>
                        <select class="form-control" id="character-gameSystem" name="gameSystem" v-model="character.gameSystem">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="character.gameSystem && gameSystemOption.id === character.gameSystem.id ? character.gameSystem : gameSystemOption" v-for="gameSystemOption in gameSystems" :key="gameSystemOption.id">{{gameSystemOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.character.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./character-update.component.ts">
</script>
