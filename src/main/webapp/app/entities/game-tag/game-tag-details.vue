<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="gameTag">
                <h2 class="jh-entity-heading"><span v-text="$t('trpgPlanningApplicationApp.gameTag.detail.title')">GameTag</span> {{gameTag.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('trpgPlanningApplicationApp.gameTag.gameTagName')">Game Tag Name</span>
                    </dt>
                    <dd>
                        <span>{{gameTag.gameTagName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('trpgPlanningApplicationApp.gameTag.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{gameTag.description}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="gameTag.id" :to="{name: 'GameTagEdit', params: {gameTagId: gameTag.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./game-tag-details.component.ts">
</script>
