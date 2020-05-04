<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="game" id="game-root">
                <div class="game-header">
                    <div class="game-header-inner">
                        <div class="game-header-left">
                            <img class="game-image" :src="game.pictureURL" :title="game.gameName" alt>
                        </div>
                        <div class="game-header-right">
                            <div v-if="game.gameSystem" class="game-system">
                                <span class="game-system-item">
                                    <img class="game-system-image" :src="game.gameSystem.pictureURL" alt>
                                    <span class="game-system-name">{{game.gameSystem.gameSystemName}}</span>
                                </span>
                            </div>
                            <h1 class="game-title">{{game.gameName}}</h1>
                            <div class="game-info">
                                <div class="game-info-col game-info-venue">
                                    <h4 class="game-info-title" v-text="$t('trpgPlanningApplicationApp.game.venue')">Venue</h4>
                                    <p>TODO VENUE</p>
                                </div>
                                <div class="game-info-col game-info-date">
                                    <h4 class="game-info-title" v-text="$t('trpgPlanningApplicationApp.game.playDate')">Play Date</h4>
                                    <p>{{moment(game.playDate).format("DD MMMM YYYY, dddd HH:mm ")}}</p>
                                </div>
                            </div>
                            <div class="register-button-open game-register">
                                <router-link v-if="game.id" :to="{name: 'GameEdit', params: {gameId: game.id}}" tag="button" class="btn btn-primary">
                                    <span>TODO: REGISTER</span>
                                </router-link>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="game-body">
                    <div class="game-content">
                        <div class="game-text">{{game.description}}</div>
                        <div class="game-network">
                            <div class="game-sidebar">
                                <!--                                <router-link v-if="game.id" :to="{name: 'GameEdit', params: {gameId: game.id}}" tag="button" class="btn btn-primary">-->
                                <!--                                    <span>TODO: REGISTER</span>-->
                                <!--                                </router-link>-->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./game-details.component.ts">
</script>

<style>
    #game-root {
        font-family: "Open Sans", Arial, sans-serif;
        font-size: 14px;
        position: relative;
        height: 100%;
        width: 100%;
        color: #535353;
        background: #fff;
        display: block;
        line-height: 1;
        min-height: 100%;
        box-sizing: border-box;
    }

    .game-header {
        padding-top: 50px;
        position: relative;
    }

    .game-header-inner {
        padding: 0 8%;
        position: relative;
        max-width: 1600px;
        margin: 0 auto;
    }

    .game-header-left {
        float: left;
        width: 35%;
    }

    .game-image {
        max-width: 100%;
        vertical-align: bottom;
        border: 0;
    }

    .game-header-right {
        margin-bottom: 120px;
        position: relative;
        padding-left: 40%;
    }

    .game-system {
        color: #989898;
        margin-bottom: 10px;
        font-size: 12px;
        letter-spacing: 1px;
    }

    .game-system-item {
        margin-right: 10px;
    }

    .game-system-image {
        width: 32px;
        height: 16px;
        max-width: 100%;
        vertical-align: bottom;
        border: 0;
    }

    .game-system-name {
        padding-left: 4px;
    }

    .game-title {
        color: #2d282c;
        font-size: 34px;
        line-height: 1.1;
        margin-bottom: .7em;
    }

    .game-info {
        padding-bottom: 40px;
    }

    .game-info-col {
        display: inline-block;
        vertical-align: top;
        font-size: 14px;
    }

    .game-info-venue {
        width: 57%;
        padding-right: 15px;
    }

    .game-info-date {
        width: 42%;
    }

    .game-info-title {
        color: #989898;
        margin-bottom: 10px;
    }

    .game-body {
        padding: 0 8%;
        position: relative;
        max-width: 1600px;
        margin: 0 auto;
    }

    .game-content {
        float: right;
        width: 60%;
        margin-bottom: 70px;
    }

    .game-text {
        margin-bottom: 35px;
        font-size: 16px;
        line-height: 1.7;
        color: #2e282c;
    }

    .game-network {
        margin-bottom: 10px;
        text-align: center;
    }

    @media screen and (max-width: 760px) {
        .game-header-inner {
            padding: 0;
        }

        .game-header-left {
            float: none;
            width: auto;
        }

        .game-header-right {
            padding-left: 0;
            margin: 5% 0;
        }

        .game-info-date {
            width: auto;
        }

        .game-info-title {
            width: auto;
        }

        .game-body {
            padding: 0;
        }

        .game-content {
            float: none;
            width: auto;
        }

        .container-fluid {
            padding-left: 0;
            padding-right: 0;
        }
    }
</style>
