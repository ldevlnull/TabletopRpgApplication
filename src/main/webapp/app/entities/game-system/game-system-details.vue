<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="gameSystem">
                <h2 class="jh-entity-heading"><span v-text="$t('trpgPlanningApplicationApp.gameSystem.detail.title')">GameSystem</span> {{gameSystem.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('trpgPlanningApplicationApp.gameSystem.gameSystemName')">Game System Name</span>
                    </dt>
                    <dd>
                        <span>{{gameSystem.gameSystemName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('trpgPlanningApplicationApp.gameSystem.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{gameSystem.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('trpgPlanningApplicationApp.gameSystem.pictureURL')">Picture URL</span>
                    </dt>
                    <dd>
                        <span>{{gameSystem.pictureURL}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="gameSystem.id" :to="{name: 'GameSystemEdit', params: {gameSystemId: gameSystem.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./game-system-details.component.ts">
</script>
