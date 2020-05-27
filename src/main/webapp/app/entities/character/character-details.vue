<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="character">
                <h2 class="jh-entity-heading"><span v-text="$t('trpgPlanningApplicationApp.character.detail.title')">Character</span> {{character.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('trpgPlanningApplicationApp.character.characterName')">Character Name</span>
                    </dt>
                    <dd>
                        <span>{{character.characterName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('trpgPlanningApplicationApp.character.isAlive')">Is Alive</span>
                    </dt>
                    <dd>
                        <span>{{character.isAlive}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('trpgPlanningApplicationApp.character.user')">User</span>
                    </dt>
                    <dd>
                        {{character.user ? character.user.login : ''}}
                    </dd>
                    <dt>
                        <span v-text="$t('trpgPlanningApplicationApp.character.gameSystem')">Game System</span>
                    </dt>
                    <dd>
                        <div v-if="character.gameSystem">
                            <router-link :to="{name: 'GameSystemView', params: {gameSystemId: character.gameSystem.id}}">{{character.gameSystem.gameSystemName}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="character.id" :to="{name: 'CharacterEdit', params: {characterId: character.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./character-details.component.ts">
</script>
