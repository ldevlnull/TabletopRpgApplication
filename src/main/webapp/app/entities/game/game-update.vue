<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
                <h2 id="trpgPlanningApplicationApp.game.home.createOrEditLabel" v-text="$t('trpgPlanningApplicationApp.game.home.createOrEditLabel')">Create or edit a Game</h2>
                <div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.game.gameName')" for="game-gameName">Game Name</label>
                        <input type="text" class="form-control" name="gameName" id="game-gameName"
                               :class="{'valid': !$v.game.gameName.$invalid, 'invalid': $v.game.gameName.$invalid }" v-model="$v.game.gameName.$model" required/>
                        <div v-if="$v.game.gameName.$anyDirty && $v.game.gameName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.game.gameName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.game.gameName.minLength">
                                Name of the game should be at least {{ $v.game.gameName.$params.minLength.min }} symbols long.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.game.playDate')" for="game-playDate">Play Date</label>
                        <div class="d-flex">
                            <input id="game-playDate" type="datetime-local" class="form-control" name="playDate"
                                   dataformatas="yyyy-MM-ddTHH:mm"
                                   :class="{'valid': !$v.game.playDate.$invalid, 'invalid': $v.game.playDate.$invalid }"
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
                        <small class="form-text text-danger" v-if="!$v.game.playDate.minDate" v-text="$t('entity.validation.minDate')">
                            Date cannot be in the past.
                        </small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.game.venue')" for="game-venue">Venue</label>
                        <input type="text" class="form-control" name="venue" id="game-venue"
                               :class="{'valid': !$v.game.venue.$invalid, 'invalid': $v.game.venue.$invalid }" v-model="$v.game.venue.$model" required/>
                        <div v-if="$v.game.gameName.$anyDirty && $v.game.gameName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.game.venue.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.game.venue.minLength">
                                Venue of the game should be at least {{ $v.game.venue.$params.minLength.min }} symbols long.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.game.playersLimit')" for="game-playersLimit">Players Limit</label>
                        <input type="number" class="form-control" name="playersLimit" id="game-playersLimit"
                               required
                               :class="{'valid': !$v.game.playersLimit.$invalid, 'invalid': $v.game.playersLimit.$invalid }" v-model.number="$v.game.playersLimit.$model"/>
                        <small class="form-text text-danger" v-if="!$v.game.playersLimit.between">
                            The number of players limit should be in range from {{ $v.game.playersLimit.$params.between.min }} to {{ $v.game.playersLimit.$params.between.max }}.
                        </small>
                    </div>
                    <div class="form-group">
                        <div v-show="$v.game.pictureURL">
                            <div class="alert alert-info">Image Preview</div>
                            <img :src="$v.game.pictureURL" class="rounded mx-auto d-block" alt>
                        </div>
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.game.pictureURL')" for="game-pictureURL">Picture URL</label>
                        <input type="url" class="form-control" name="pictureURL" id="game-pictureURL"
                               :class="{'valid': !$v.game.pictureURL.$invalid, 'invalid': $v.game.pictureURL.$invalid }" v-model="$v.game.pictureURL.$model"/>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.game.description')" for="game-description">Description</label>
                        <textarea class="form-control" name="description" id="game-description"
                                  :class="{'valid': !$v.game.description.$invalid, 'invalid': $v.game.description.$invalid}" v-model="$v.game.description.$model">
                        </textarea>
                        <small class="form-text text-danger" v-if="!$v.game.description.maxLength">
                            Max description length is {{ $v.game.description.$params.maxLength.max }}.
                        </small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('trpgPlanningApplicationApp.game.gameSystem')" for="game-gameSystem">Game System</label>
                        <select class="form-control" id="game-gameSystem" name="gameSystem" v-model="game.gameSystem">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="game.gameSystem && gameSystemOption.id === game.gameSystem.id ? game.gameSystem : gameSystemOption"
                                    v-for="gameSystemOption in gameSystems" :key="gameSystemOption.id">{{gameSystemOption.gameSystemName}}
                            </option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label v-text="$t('trpgPlanningApplicationApp.game.tags')" for="game-tags">Tags</label>
                        <select class="form-control" id="game-tags" multiple name="tags" v-model="game.tags">
                            <option v-bind:value="getSelected(game.tags, gameTagOption)" v-for="gameTagOption in gameTags" :key="gameTagOption.id">{{gameTagOption.gameTagName}}
                            </option>
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
