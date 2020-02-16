<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="trpgPlanningApplicationApp.gameTag.home.createOrEditLabel" v-text="$t('trpgPlanningApplicationApp.gameTag.home.createOrEditLabel')">Create or edit a GameTag</h2>
                <div>
                    <div class="form-group" v-if="gameTag.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="gameTag.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.gameTag.gameTagName')" for="game-tag-gameTagName">Game Tag Name</label>
                        <input type="text" class="form-control" name="gameTagName" id="game-tag-gameTagName"
                            :class="{'valid': !$v.gameTag.gameTagName.$invalid, 'invalid': $v.gameTag.gameTagName.$invalid }" v-model="$v.gameTag.gameTagName.$model"  required/>
                        <div v-if="$v.gameTag.gameTagName.$anyDirty && $v.gameTag.gameTagName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.gameTag.gameTagName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.gameTag.description')" for="game-tag-description">Description</label>
                        <input type="text" class="form-control" name="description" id="game-tag-description"
                            :class="{'valid': !$v.gameTag.description.$invalid, 'invalid': $v.gameTag.description.$invalid }" v-model="$v.gameTag.description.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.gameTag.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./game-tag-update.component.ts">
</script>
