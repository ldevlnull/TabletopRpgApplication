<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="trpgPlanningApplicationApp.gameSystem.home.createOrEditLabel" v-text="$t('trpgPlanningApplicationApp.gameSystem.home.createOrEditLabel')">Create or edit a GameSystem</h2>
                <div>
                    <div class="form-group" v-if="gameSystem.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="gameSystem.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.gameSystem.gameSystemName')" for="game-system-gameSystemName">Game System Name</label>
                        <input type="text" class="form-control" name="gameSystemName" id="game-system-gameSystemName"
                            :class="{'valid': !$v.gameSystem.gameSystemName.$invalid, 'invalid': $v.gameSystem.gameSystemName.$invalid }" v-model="$v.gameSystem.gameSystemName.$model"  required/>
                        <div v-if="$v.gameSystem.gameSystemName.$anyDirty && $v.gameSystem.gameSystemName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.gameSystem.gameSystemName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.gameSystem.description')" for="game-system-description">Description</label>
                        <input type="text" class="form-control" name="description" id="game-system-description"
                            :class="{'valid': !$v.gameSystem.description.$invalid, 'invalid': $v.gameSystem.description.$invalid }" v-model="$v.gameSystem.description.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.gameSystem.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./game-system-update.component.ts">
</script>
