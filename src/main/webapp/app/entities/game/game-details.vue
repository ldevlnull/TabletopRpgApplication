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
                            <div class="gm-control-panel" v-if="authenticated && isUserGM()">
                                <!-- todo: localize -->
                                <span v-show="isGamePending()" class="btn btn-primary" @click="startGame()">Start Game</span>
                                <span v-show="isGameInProgress()" class="btn btn-primary" @click="endGame()">End Game</span>
                                <span v-show="isGameEnded()" class="btn btn-success" disabled>Game Ended</span>
                                <span v-show="isGameCancelled()" class="btn btn-danger" disabled="">Game Cancelled</span>
                                <router-link v-if="game.id" :to="{name: 'GameEdit', params: {gameId: game.id}}" tag="button" class="btn btn-secondary">
                                    <span>Edit game</span>
                                </router-link>
                                <span v-show="!isGameCancelled()" class="btn btn-danger" @click="cancelGame()">Cancel Game</span>
                            </div>
                            <div class="game-info">
                                <div class="game-info-col game-info-venue">
                                    <h4 class="game-info-title" v-text="$t('trpgPlanningApplicationApp.game.venue')">Venue</h4>
                                    <p>{{game.venue}}</p>
                                </div>
                                <div class="game-info-col game-info-date">
                                    <h4 class="game-info-title" v-text="$t('trpgPlanningApplicationApp.game.playDate')">Play Date</h4>
                                    <p>{{moment(game.playDate).format("DD MMMM YYYY, dddd HH:mm ")}}</p>
                                </div>
                            </div>
                            <div class="game-info">
                                <div class="game-info-col game-info-gm">
                                    <h4 class="game-info-title" v-text="$t('trpgPlanningApplicationApp.game.user')">Game Master</h4>
                                    <p>{{game.user.login}}</p>
                                </div>
                                <div class="game-info-col game-info-status">
                                    <h4 class="game-info-title" v-text="$t('trpgPlanningApplicationApp.game.status')">Game Status</h4>
                                    <!-- todo: localize -->
                                    <button v-show="isGamePending()" class="btn btn-secondary" disabled v-text="$t('trpgPlanningApplicationApp.GameStatus.PENDING')">Pending</button>
                                    <button v-show="isGameInProgress()" class="btn badge-info" disabled v-text="$t('trpgPlanningApplicationApp.GameStatus.IN_PROGRESS')">In Progress</button>
                                    <button v-show="isGameEnded()" class="btn btn-success" disabled v-text="$t('trpgPlanningApplicationApp.GameStatus.ENDED')">Ended</button>
                                    <button v-show="isGameCancelled()" class="btn btn-danger" disabled v-text="$t('trpgPlanningApplicationApp.GameStatus.CANCELLED')">Cancelled</button>
                                </div>
                            </div>
                            <div class="registered-characters-list">
                                <table v-if="game.characters && game.characters.length > 0" class="table table-dark">
                                    <tr align="right">
                                        <td colspan="3">Players: {{game.characters.length}}/{{game.playersLimit}}</td>
                                    </tr>
                                    <tr v-for="gameCharacter in game.characters" style="vertical-align: middle">
                                        <td style="width: 33%">{{gameCharacter.characterName}}</td>
                                        <td style="width: 33%">({{gameCharacter.user.login}})</td>
                                        <td v-if="authenticated" v-show="isGamePending() && isCharacterOfUser(gameCharacter)" style="width: 33%"><span class="btn btn-secondary" @click="leaveWithCharacter()">Leave</span>
                                        </td>
                                    </tr>
                                </table>
                                <div class="alert alert-info" v-else>
                                    <!-- todo: localize -->
                                    No characters are registered to this game yet!
                                </div>
                            </div>
                            <div class="register-button-open game-register">
                                <div class="container-fluid">
                                    <div class="btn btn-primary" v-on:click="showUserCharacterWithFetch()">
                                        Join Game <!-- todo: add localization -->
                                    </div>
                                    <span class="btn btn-secondary" v-show="this.showCharacters" @click="hideUserCharacters()">Hide</span>
                                    <div v-if="authenticated" v-show="this.showCharacters" class="user-characters-list">
                                        <table v-show="!isUserRegistered() && isGamePending()" class="table table-light">
                                            <tr v-if="userCharacters.length === 0">
                                                <td class="alert-warning" colspan="2">
                                                    <!-- todo: localize -->
                                                    <p>You have no characters that you can join with!</p>
                                                    <p>(Character must be alive, be of corresponding system and not be participation the not finished game)</p>
                                                </td>
                                            </tr>
                                            <tr v-else v-for="userCharacter in userCharacters">
                                                <td style="width: 50%">{{userCharacter.characterName}}</td>
                                                <td style="width: 50%"><span class="btn btn-secondary" @click="joinWithCharacter(userCharacter)">Join</span></td>
                                            </tr>
                                        </table>
                                        <!-- todo: localize -->
                                        <div v-show="isUserRegistered()" class="alert alert-warning">You are already registered to this game!</div>
                                        <div v-show="!isUserRegistered() && getFreeSlotsCount() === 0" class="alert alert-warning">No free slots!</div>
                                        <div v-show="!isUserRegistered() && !isGamePending()" class="alert alert-warning">The game has been started!</div>
                                    </div>
                                    <div v-else class="alert alert-warning">
                                        <!-- todo: localize -->
                                        You need to perfrom login to be able register the game.
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="game-body">
                    <div class="game-content">
                        <div class="game-text">{{game.description}}</div>
                        <div class="game-network">
                            <div class="game-sidebar">
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
    .gm-control-panel {
        margin-bottom: 20px;
    }
    .game-info-col {
        display: inline-block;
        vertical-align: top;
        font-size: 14px;
    }

    .game-info-venue, .game-info-gm {
        width: 57%;
        padding-right: 15px;
    }

    .game-info-date, .game-info-status {
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

    .register-button-open > .container-fluid > .btn {
        margin: 2em 0;
    }

    .registered-characters-list {
        margin: 2em 0;
    }

    .registered-characters-list > .table {
        margin: 0;
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

        .game-info-date, .game-info-title, .game-info-gm, .game-info-status {
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
